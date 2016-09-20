package com.yueny.demo.zookeeper.group;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;

/**
 * 等待与zookeeper创建连接
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年2月25日 下午11:49:31
 *
 */
public class ConnectionWatcher implements Watcher {
	private final CountDownLatch connectedSignal = new CountDownLatch(1);

	protected ZooKeeper zk = null;

	public void close() throws InterruptedException {
		zk.close();
	}

	public void connect(final String hosts) throws IOException,
			InterruptedException {
		zk = new ZooKeeper(hosts, 5000, this);
		// 构造函数的调用时立即返回的，所以等待
		connectedSignal.await();
	}

	@Override
	public void process(final WatchedEvent event) {
		// Watch类被用于获取zk对象是否准备就绪的信息。客户端与zk服务建立连接后，调用接口process
		System.out.println("process : " + event.getState());
		if (event.getState() == KeeperState.SyncConnected) {
			connectedSignal.countDown();
		}
	}

}
