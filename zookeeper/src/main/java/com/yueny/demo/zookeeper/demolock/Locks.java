package com.yueny.demo.zookeeper.demolock;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

/**
 * 获得锁：<br>
 * 1.创建根节点"/root"<br>
 * 2.在根节点下新建子节点"/root/c-xxxxxx"，SEQUENTIAL模式<br>
 * 3.对根节点调用getChildren()，如果第2步创建的节点是所有子节点中序号最小的，则获得锁；否则进入第4步<br>
 * 4.在序号最小的子节点上调用exists()，当序号最小的子节点被删除后返回第3步<br>
 *
 * 释放锁：<br>
 * 删除自己创建的子节点即可<br>
 *
 * 读锁（共享锁）和写锁（排斥锁）并存的情况跟单独只有排斥锁的情况有几点不同：<br>
 * 1.当一个线程想施加读锁时就新建一个节点"/root/read-xxxxxx"，施加写锁时就新建一个节点"/root/write-xxxxxx";<br>
 * 2.欲施加读锁的线程查看"/root"下有没有“write"开头的节点，如果没有则直接获得读锁；如果有，但是"write"节点的序号比自己刚才创建的"
 * read"节点的序号要大说明是先施加的读锁后施加的写锁，所以依然获得读锁；else，在序号最小的"write"节点上调用exists，等待它被删除。
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年1月10日 下午8:15:45
 *
 */
public class Locks implements Watcher {
	static Integer mutex = null;
	static ZooKeeper zk = null;

	public static void main(final String[] args) throws KeeperException,
			InterruptedException {
		final Locks lock1 = new Locks("localhost:2181");
		if (lock1.getLock()) {
			System.out.println("T1 Get lock at " + System.currentTimeMillis());
			for (int i = 0; i < 1000; ++i) {
				Thread.sleep(5000);
			}
			lock1.releaseLock();
		}
		final Locks lock2 = new Locks("localhost:2181");
		if (lock2.getLock()) {
			System.out.println("T2 Get lock at " + System.currentTimeMillis());
			lock2.releaseLock();
		}
	}

	String name = null;

	String path = null;

	Locks(final String address) {
		try {
			zk = new ZooKeeper(address, 2000, this);
			zk.create("/lock", new byte[0], Ids.OPEN_ACL_UNSAFE,
					CreateMode.EPHEMERAL);
			mutex = new Integer(-1);
			name = new String(InetAddress.getLocalHost().getCanonicalHostName()
					.toString());
		} catch (final IOException e) {
			zk = null;
		} catch (final KeeperException e) {
			e.printStackTrace();
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	synchronized public void process(final WatchedEvent event) {
		synchronized (mutex) {
			mutex.notify();
		}
	}

	private int minSeq(final List<String> list) {
		int min = Integer.parseInt(list.get(0).substring(14));
		for (int i = 1; i < list.size(); i++) {
			if (min < Integer.parseInt(list.get(i).substring(14))) {
				min = Integer.parseInt(list.get(i).substring(14));
			}
		}
		return min;
	}

	boolean getLock() throws KeeperException, InterruptedException {
		// create方法返回新建的节点的完整路径
		path = zk.create("/lock/" + name + "-", new byte[0],
				Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
		int min;
		while (true) {
			synchronized (mutex) {
				final List<String> list = zk.getChildren("/lock", false);
				min = minSeq(list);
				// 如果刚建的节点是根节点的所有子节点中序号最小的，则获得了锁，可以返回true
				if (min == Integer.parseInt(path.substring(14))) {
					return true;
				} else {
					mutex.wait(); // 等待事件（新建节点或删除节点）发生
					while (true) {
						final Stat s = zk.exists("/lock/" + name + "-" + min,
								true); // 查看序号最小的子节点还在不在
						if (s != null) {
							mutex.wait();
						} else {
							break;
						}
					}
				}
			}
		}
	}

	boolean releaseLock() throws KeeperException, InterruptedException {
		if (path != null) {
			zk.delete(path, -1);
			path = null;
		}
		return true;
	}

}
