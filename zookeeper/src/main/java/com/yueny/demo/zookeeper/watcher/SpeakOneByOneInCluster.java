package com.yueny.demo.zookeeper.watcher;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.KeeperException.NodeExistsException;
import org.apache.zookeeper.KeeperException.SessionExpiredException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 使用Zookeeper来控制集群中的服务器，同一时刻只会有一台做指定的事情，名称多台同时做会产生冲突的情况。<br>
 * 这个示例演示的是集群中的服务器，同一时刻只会有一台说话，当这台服务服务器说话时，其它的服务器不可以说话。<br>
 * 如果在操作的过程中，连接Zookeeper过期了，会自动进行重连；并确保其在同一个服务器上面布署多个示例也可以正常运行。<br>
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年1月9日 上午12:19:42
 *
 */
public class SpeakOneByOneInCluster implements Watcher {// 连接Zookeeper
														// Server的客户端连接
	/**
	 * 用于选举可用于做事情的应用
	 */
	static public class Barrier extends SpeakOneByOneInCluster implements
			Runnable {

		private static final Logger LOG;
		static {
			// Keep these two lines together to keep the initialization order
			// explicit
			LOG = LoggerFactory.getLogger(Barrier.class);
		}
		String nodeName;

		/**
		 * Barrier constructor
		 *
		 * @param address
		 * @param root
		 * @param size
		 */
		Barrier(final String address, final String root) {
			super(address);
			this.root = root;
			// Create barrier node
			if (zk != null) {
				try {
					final Stat s = zk.exists(root, false);
					if (s == null) {
						zk.create(root, new byte[0], Ids.OPEN_ACL_UNSAFE,
								CreateMode.PERSISTENT);
					}
				} catch (final KeeperException e) {
					writeLog("Keeper exception when instantiating queue: "
							+ e.toString());
				} catch (final InterruptedException e) {
					writeLog("Interrupted exception");
				}
			}
			// My node name
			try {
				this.nodeName = new StringBuilder(InetAddress.getLocalHost()
						.getCanonicalHostName()).append(
						(int) (Math.random() * 100)).toString();
			} catch (final UnknownHostException e) {
				writeLog(e.toString());
			}
		}

		@Override
		public void run() {
			while (true) {
				try {
					lock();
				} catch (final NodeExistsException e) {
				} catch (final SessionExpiredException e) {
					try {
						// Session过期了，进行重连
						connect(address);
					} catch (final IOException e1) {
						LOG.error(e1.getMessage(), e1);
					}
				} catch (final Exception e) {
					LOG.error(e.getMessage(), e);
				}
			}
		}

		/**
		 * Join barrier
		 *
		 * @return
		 * @throws KeeperException
		 * @throws InterruptedException
		 */

		void lock() throws KeeperException, InterruptedException {
			final Stat stat = zk.exists(LOCK_NODE, false);
			if (stat != null) {
				return;
			}
			zk.create(LOCK_NODE, new byte[0], Ids.OPEN_ACL_UNSAFE,
					CreateMode.EPHEMERAL);
			writeLog(SpeakOneByOneInCluster.nodeName + ":create node "
					+ LOCK_NODE);
			getLock = Boolean.TRUE;
			final List<String> childs = zk.getChildren(root, true);
			// 节点存在了就返回不处理
			if (!(childs == null || childs.size() == 0)) {
				return;
			}
			zk.create(root + "/" + nodeName, new byte[0], Ids.OPEN_ACL_UNSAFE,
					CreateMode.EPHEMERAL);
			writeLog(SpeakOneByOneInCluster.nodeName + ":create node " + root
					+ "/" + nodeName);
			SpeakOneByOneInCluster.nodeName = this.nodeName;
		}
	}

	/**
	 * Producer-Consumer queue
	 */
	static public class Speak extends SpeakOneByOneInCluster implements
			Runnable {

		private static final Logger LOG;
		static {
			// Keep these two lines together to keep the initialization order
			// explicit
			LOG = LoggerFactory.getLogger(Speak.class);
		}

		/**
		 * Constructor of speaker
		 *
		 * @param address
		 * @param root
		 */
		Speak(final String address, final String root) {
			super(address);
			this.root = root;
			// Create ZK node name
			if (zk != null) {
				try {
					final Stat s = zk.exists(this.root, false);
					if (s == null) {
						zk.create(this.root, new byte[0], Ids.OPEN_ACL_UNSAFE,
								CreateMode.PERSISTENT);
					}
				} catch (final SessionExpiredException e) {
					try {
						// Session过期了，进行重连
						connect(address);
					} catch (final IOException e1) {
						LOG.error(e1.getMessage(), e1);
					}
				} catch (final KeeperException e) {
					writeLog("Keeper exception when instantiating queue: "
							+ e.toString());
				} catch (final InterruptedException e) {
					writeLog("Interrupted exception");
				}
			}
		}

		@Override
		public void run() {
			try {
				speak();
			} catch (final InterruptedException e) {
				LOG.error(e.getMessage(), e);
			}
		}

		/**
		 * Add element to the queue.
		 *
		 * @param i
		 * @return
		 */

		boolean check() {
			try {
				if (getLock == Boolean.FALSE) {
					return false;
				}
				final List<String> childs = zk.getChildren(root, true);
				if (childs == null || childs.size() == 0) {
					return false;
				}
				if (childs.size() != 1) {
					throw new RuntimeException("childs's size is "
							+ childs.size() + ", it must be 1.");
				}
				if (childs.get(0).endsWith(SpeakOneByOneInCluster.nodeName)) {
					return true;
				}
				return false;
			} catch (final SessionExpiredException e) {
				try {
					// Session过期了，进行重连
					connect(address);
				} catch (final IOException e1) {
					LOG.error(e1.getMessage(), e1);
				}
				return false;
			} catch (final Exception e) {
				LOG.error(e.getMessage(), e);
				return false;
			}

		}

		void doSpeak() {
			try {
				writeLog(SpeakOneByOneInCluster.nodeName
						+ ": Now only I can speak. Let me sleep 5s.");
				Thread.sleep(5000);
				writeLog(SpeakOneByOneInCluster.nodeName + ": I week up now.");
				unlock();
			} catch (final SessionExpiredException e) {
				try {
					connect(address);
					unlock();
				} catch (final Exception e1) {
					LOG.error(e1.getMessage(), e1);
				}
			} catch (final Exception e) {
				LOG.error(e.getMessage(), e);
			}
		}

		void speak() throws InterruptedException {
			while (true) {
				// writeLog(CopyOneFileInMultiVM.choosedNodeName +
				// ":try speak...");
				synchronized (LOCK) {
					if (!check()) {
						// writeLog(CopyOneFileInMultiVM.choosedNodeName +
						// ":copy condition not ready,wait.");
						LOCK.wait();
					} else {
						writeLog(SpeakOneByOneInCluster.nodeName + ":speak now");
						doSpeak();
					}
				}
			}
		}

		/**
		 * delete the lock node
		 *
		 * @return
		 * @throws KeeperException
		 * @throws InterruptedException
		 */

		void unlock() throws KeeperException, InterruptedException {
			writeLog(SpeakOneByOneInCluster.nodeName + ":Begin to delete node "
					+ SpeakOneByOneInCluster.nodeName + " and node "
					+ LOCK_NODE);
			zk.delete(root + "/" + SpeakOneByOneInCluster.nodeName, 0);
			zk.delete(LOCK_NODE, 0);
			getLock = Boolean.FALSE;
			writeLog(SpeakOneByOneInCluster.nodeName
					+ ":Resource release OK. It's you turn, bye.");
		}
	}

	// 节点锁，只有能够创建该节点的应用示例，才可以做指定的事情，如本例中的说话
	private static String LOCK_NODE = "/zk_test/lock";;
	private static final Logger LOG;
	// 当前节点的名称，由服务器的名称加随机数据组成，之所以使用这种方式，那是考虑到同一台服务器上可能会布署多个应用示例，而不一定是每个应用示例布署到集群的不同服务器上
	private static String nodeName = "";
	// 用于表示当前应用是否拿到了节点锁，然后根据是否拿到节点锁，才可以做指定的事情，如本例中的说话
	static boolean getLock;
	// 锁
	static Object LOCK = new Object();
	static ZooKeeper zk = null;
	static {
		// Keep these two lines together to keep the initialization order
		// explicit
		LOG = LoggerFactory.getLogger(SpeakOneByOneInCluster.class);
	}

	public static void main(final String[] args) throws InterruptedException {
		new Thread(new Barrier("localhost:20801", "/zk_test/chooseCopyMachine"))
				.start();
		new Thread(new Speak("localhost:20801", "/zk_test/chooseCopyMachine"))
				.start();

		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				try {
					if (zk.exists(LOCK_NODE, false) != null) {
						zk.delete(LOCK_NODE, -1);
					}
					zk.close();
				} catch (final Exception e) {
					LOG.error(e.getMessage(), e);
				}
			}
		});
	}

	final String address;

	// 日志文件
	final File logFile = new File("D:/test/logFile.txt");

	// 用户操作的根节点，与“/”是不同的，用户可以控制
	String root;

	public SpeakOneByOneInCluster(final String address) {
		this.address = address;
		if (zk == null) {
			try {
				connect(address);
			} catch (final IOException e) {
				writeLog(e.toString());
				zk = null;
			}
		}
	}

	@Override
	synchronized public void process(final WatchedEvent event) {
		synchronized (LOCK) {
			if (!event.getPath().equals(LOCK_NODE)) {
				LOCK.notify();
			}
		}
	}

	protected void connect(final String address) throws IOException {
		writeLog("Starting ZK:");
		zk = new ZooKeeper(address, 2181, this);
		writeLog("Finished starting ZK: " + zk);
	}

	protected void writeLog(final String str) {
		try {
			System.out.println(str);
			FileUtils.writeStringToFile(logFile, str + "\n", true);
		} catch (final IOException e) {
			LOG.error(e.getMessage(), e);
		}
	}
}
