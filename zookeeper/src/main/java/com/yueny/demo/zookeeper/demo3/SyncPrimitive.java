package com.yueny.demo.zookeeper.demo3;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Random;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年1月10日 下午7:50:54
 *
 */
public class SyncPrimitive implements Watcher {
	/**
	 * 所有的线程都到达barrier后才能进行后续的计算<br>
	 * 或者<br>
	 * 所有的线程都完成自己的计算后才能离开barrier<br>
	 *
	 * @author yueny09 <deep_blue_yang@163.com>
	 *
	 * @DATE 2016年1月10日 下午7:58:20
	 *
	 */
	public static class Barrier extends SyncPrimitive {
		String name;
		int size;

		Barrier(final String address, final String root, final int size) {
			super(address);
			this.root = root;
			this.size = size;
			if (zk != null) {
				try {
					// 一个barrier建立一个根目录
					final Stat s = zk.exists(root, false); // 不注册watcher
					if (s == null) {
						zk.create(root, new byte[0], Ids.OPEN_ACL_UNSAFE,
								CreateMode.PERSISTENT);
					}
				} catch (final KeeperException e) {
					System.out
							.println("keeper exception when instantiating queue:"
									+ e.toString());
				} catch (final InterruptedException e) {
					System.out.println("Interrupted exception.");
				}
			}

			try {
				// 获取自己的主机名
				name = new String(InetAddress.getLocalHost()
						.getCanonicalHostName().toString());
			} catch (final UnknownHostException e) {
				System.out.println(e.toString());
			}
		}

		/**
		 * 1.建一个根节点"/root"<br>
		 * 2.想进入barrier的线程在"/root"下建立一个子节点"/root/name"<br>
		 * 3.循环监听"/root"孩子节点数目的变化，当其达到size时就说明有size个线程都已经barrier点了<br>
		 */
		public boolean enter() throws KeeperException, InterruptedException {
			// 在根目录下创建一个子节点.create和delete都会触发children
			// wathes,这样getChildren就会收到通知，process()就会被调用
			zk.create(root + "/" + name, new byte[0], Ids.OPEN_ACL_UNSAFE,
					CreateMode.EPHEMERAL_SEQUENTIAL);
			// 一直等，直到根目录下的子节点数目达到size时，函数退出
			while (true) {
				synchronized (mutex) {
					final List<String> list = zk.getChildren(root, true);
					if (list.size() < size) {
						mutex.wait(); // 释放mutex上的锁
					} else {
						return true;
					}
				}
			}
		}

		/**
		 * 1.想离开barrier的线程删除其在"/root"下建立的子节点<br>
		 * 2.循环监听"/root"孩子节点数目的变化，当size减到0时它就可以离开barrier了<br>
		 */
		public boolean leave() throws KeeperException, InterruptedException {
			// 删除自己创建的节点
			zk.delete(root + "/" + name, 0);
			// 一直等，直到根目录下有子节点时，函数退出
			while (true) {
				synchronized (mutex) {
					final List<String> list = zk.getChildren(root, true);
					if (list.size() > 0) {
						mutex.wait();
					} else {
						return true;
					}
				}
			}
		}
	}

	/**
	 * 产生--消费模型，先生产的先被消费。<br>
	 * 1.建立一个根节点"/root"<br>
	 * 2.生产线程在"/root"下建立一个SEQUENTIAL子节点<br>
	 * 3.消费线程检查"/root"有没有子节点，如果没有就循环监听"/root"子节点的变化，直到它有子节点。删除序号最小的子节点。<br>
	 *
	 * @author yueny09 <deep_blue_yang@163.com>
	 *
	 * @DATE 2016年1月10日 下午8:01:48
	 *
	 */
	public static class Queue extends SyncPrimitive {
		Queue(final String address, final String name) {
			super(address);
			this.root = name;
			if (zk != null) {
				try {
					// 一个queue建立一个根目录
					final Stat s = zk.exists(root, false);
					if (s == null) {
						zk.create(root, new byte[0], Ids.OPEN_ACL_UNSAFE,
								CreateMode.PERSISTENT);
					}
				} catch (final KeeperException e) {
					System.out
							.println("keeper exception when instantiating queue:"
									+ e.toString());
				} catch (final InterruptedException e) {
					System.out.println("Interrupted exception.");
				}
			}
		}

		public int consume() throws KeeperException, InterruptedException {
			int retvalue = -1;
			final Stat stat = null;
			while (true) {
				synchronized (mutex) {
					final List<String> list = zk.getChildren(root, true); // 并不能保证list[0]就是序号最小的
					// 如果根目录下没有子节点就一直等
					if (list.size() == 0) {
						System.out.println("Going to wait");
						mutex.wait();
					}
					// 找到序号最小的节点将其删除
					else {
						Integer min = new Integer(list.get(0).substring(7));
						for (final String s : list) {
							final Integer tmp = new Integer(s.substring(7));
							if (tmp < min) {
								min = tmp;
							}
						}
						System.out.println("Temporary value:" + root
								+ "/element" + min);
						final byte[] b = zk.getData(root + "/element" + min,
								false, stat);
						zk.delete(root + "/element" + min, 0);
						final ByteBuffer buffer = ByteBuffer.wrap(b);
						retvalue = buffer.getInt();
						return retvalue;
					}
				}
			}
		}

		// 参数i是要创建节点的data
		public boolean produce(final int i) throws KeeperException,
				InterruptedException {
			final ByteBuffer b = ByteBuffer.allocate(4);
			byte[] value;
			b.putInt(i);
			value = b.array();

			// 根目录下创建一个子节点，因为是SEQUENTIAL的，所以先创建的节点具有较小的序号
			zk.create(root + "/element", value, Ids.OPEN_ACL_UNSAFE,
					CreateMode.PERSISTENT_SEQUENTIAL);
			return true;
		}
	}

	private static Integer mutex;
	private static ZooKeeper zk = null;

	public static void main(final String[] args) {
		// queueTest("10.9.1.189:2181", 2, "p");
		// queueTest("10.9.1.189:2181", 2, "s");
		barrierTest("10.9.1.189:2181", 2);
	}

	private static void barrierTest(final String address, final int size) {
		final Barrier b = new Barrier(address, "/prim", size);
		try {
			final boolean flag = b.enter();
			System.out.println("Enter barrier:" + size);
			if (!flag) {
				System.out.println("Error when entering the barrier");
			}
		} catch (final KeeperException e) {

		} catch (final InterruptedException e) {
		}

		final Random rand = new Random();
		final int r = rand.nextInt(100);
		for (int i = 0; i < r; i++) {
			try {
				Thread.sleep(100);
			} catch (final InterruptedException e) {

			}
		}
		try {
			b.leave();
		} catch (final KeeperException e) {

		} catch (final InterruptedException e) {
		}
		System.out.println("Left barrier");
	}

	/**
	 * @param address
	 * @param size
	 * @param op
	 *            p/s
	 */
	private static void queueTest(final String address, final int size,
			final String op) {
		final Queue q = new Queue(address, "/quene");
		System.out.println("Input:" + address);
		int i;
		final Integer max = size;

		if (op.equals("p")) {
			System.out.println("Producer");
			for (i = 0; i < max; i++) {
				try {
					q.produce(10 + 1);
				} catch (final KeeperException e) {

				} catch (final InterruptedException e) {
				}
			}
		} else {
			System.out.println("Consumer");
			for (i = 0; i < max; i++) {
				try {
					final int r = q.consume();
					System.out.println("Item:" + r);
				} catch (final KeeperException e) {
					i--;
				} catch (final InterruptedException e) {
				}
			}
		}
	}

	String root;

	// 同步原语
	public SyncPrimitive(final String address) {
		if (zk == null) {
			try {
				System.out.println("Starting ZK:");
				// 建立Zookeeper连接，并且指定watcher
				zk = new ZooKeeper(address, 3000, this);
				// 初始化锁对象
				mutex = new Integer(-1);
				System.out.println("Finished starting ZK:" + zk);
			} catch (final IOException e) {
				System.out.println(e.toString());
				zk = null;
			}
		}
	}

	@Override
	synchronized public void process(final WatchedEvent event) {
		synchronized (mutex) {
			// 有事件发生时，调用notify，使其他wait()点得以继续
			mutex.notify();
		}
	}
}
