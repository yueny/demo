//package com.yueny.demo.zookeeper.watcher;
//
//import java.io.IOException;
//import java.util.List;
//
//import org.apache.zookeeper.CreateMode;
//import org.apache.zookeeper.KeeperException;
//import org.apache.zookeeper.KeeperException.SessionExpiredException;
//import org.apache.zookeeper.ZooDefs.Ids;
//import org.apache.zookeeper.data.Stat;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.google.common.base.Throwables;
//
///**
// * Producer-Consumer queue
// *
// * @author yueny09 <deep_blue_yang@163.com>
// *
// * @DATE 2016年1月9日 上午12:26:45
// *
// */
//public class Speak extends Base implements Runnable {
//	private static final Logger logger;
//	static {
//		// Keep these two lines together to keep the initialization order
//		// explicit
//		logger = LoggerFactory.getLogger(Speak.class);
//	}
//	private final SpeakOneByOneInCluster cluster;
//
//	/**
//	 * Constructor of speaker
//	 *
//	 * @param address
//	 * @param root
//	 */
//	Speak(final SpeakOneByOneInCluster cluster, final String root) {
//		this.cluster = cluster;
//
//		// Create ZK node name
//		if (cluster.zk != null) {
//			try {
//				final Stat s = cluster.zk.exists(root, false);
//				if (s == null) {
//					cluster.zk.create(root, new byte[0], Ids.OPEN_ACL_UNSAFE,
//							CreateMode.PERSISTENT);
//				}
//			} catch (final SessionExpiredException e) {
//				try {
//					// Session过期了，进行重连
//					cluster.connect();
//				} catch (final IOException e1) {
//					logger.error(e1.getMessage(), e1);
//					Throwables.propagate(e1);
//				}
//			} catch (final KeeperException e) {
//				cluster.writeLog("Keeper exception when instantiating queue: "
//						+ e.toString());
//			} catch (final InterruptedException e) {
//				cluster.writeLog("Interrupted exception");
//			}
//		}
//	}
//
//	@Override
//	public void run() {
//		try {
//			speak();
//		} catch (final InterruptedException e) {
//			logger.error(e.getMessage(), e);
//			Throwables.propagate(e);
//		}
//	}
//
//	boolean check() {
//		try {
//			if (getLock == Boolean.FALSE) {
//				return false;
//			}
//			final List<String> childs = cluster.zk.getChildren(cluster.root,
//					true);
//			if (childs == null || childs.size() == 0) {
//				return false;
//			}
//			if (childs.size() != 1) {
//				throw new RuntimeException("childs's size is " + childs.size()
//						+ ", it must be 1.");
//			}
//			if (childs.get(0).endsWith(cluster.nodeName)) {
//				return true;
//			}
//			return false;
//		} catch (final SessionExpiredException e) {
//			try {
//				// Session过期了，进行重连
//				cluster.connect();
//			} catch (final IOException e1) {
//				logger.error(e1.getMessage(), e1);
//				Throwables.propagate(e1);
//			}
//			return false;
//		} catch (final Exception e) {
//			logger.error(e.getMessage(), e);
//			Throwables.propagate(e);
//			return false;
//		}
//
//	}
//
//	void doSpeak() {
//		try {
//			cluster.writeLog(cluster.nodeName
//					+ ": Now only I can speak. Let me sleep 5s.");
//			Thread.sleep(5000);
//			cluster.writeLog(cluster.nodeName + ": I week up now.");
//			unlock();
//		} catch (final SessionExpiredException e) {
//			try {
//				cluster.connect();
//				unlock();
//			} catch (final Exception e1) {
//				logger.error(e1.getMessage(), e1);
//				Throwables.propagate(e1);
//			}
//		} catch (final Exception e) {
//			logger.error(e.getMessage(), e);
//			Throwables.propagate(e);
//		}
//	}
//
//	void speak() throws InterruptedException {
//		while (true) {
//			// writeLog(CopyOneFileInMultiVM.choosedNodeName + ":try speak...");
//			synchronized (cluster.lock) {
//				if (!check()) {
//					// writeLog(CopyOneFileInMultiVM.choosedNodeName +
//					// ":copy condition not ready,wait.");
//					cluster.lock.wait();
//				} else {
//					cluster.writeLog(cluster.nodeName + ":speak now");
//					doSpeak();
//				}
//			}
//		}
//	}
//
//	/**
//	 * delete the lock node
//	 *
//	 * @return
//	 * @throws KeeperException
//	 * @throws InterruptedException
//	 */
//	void unlock() throws KeeperException, InterruptedException {
//		cluster.writeLog(cluster.nodeName + ":Begin to delete node "
//				+ cluster.nodeName + " and node " + cluster.lockNode);
//		cluster.zk.delete(cluster.root + "/" + cluster.nodeName, 0);
//		cluster.zk.delete(cluster.lockNode, 0);
//		getLock = Boolean.FALSE;
//		cluster.writeLog(cluster.nodeName
//				+ ":Resource release OK. It's you turn, bye.");
//	}
// }
