package com.yueny.demo.zookeeper.helper.watcher;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ZooKeeper服务器的实现类
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年1月10日 上午1:22:15
 *
 */
public class ConnectionWatcher implements Watcher {
	private static final Integer SESSION_TIMEOUT = 5000;

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final CountDownLatch signal = new CountDownLatch(1);

	/**
	 * 建立连接
	 *
	 * @param servers
	 *            多个则以,分割
	 * @return
	 */
	public ZooKeeper connection(final String servers) {
		return connection(servers, SESSION_TIMEOUT);
	}

	/**
	 * 建立连接
	 *
	 * @param servers
	 *            多个则以,分割
	 * @param timeout
	 *            超时时间
	 * @return
	 */
	public ZooKeeper connection(final String servers, final Integer timeout) {
		ZooKeeper zk;
		try {
			zk = new ZooKeeper(servers, SESSION_TIMEOUT, this);
			signal.await();
			// signal.await(10L, TimeUnit.SECONDS)
			return zk;
		} catch (final IOException e) {
			logger.error("异常", e);
		} catch (final InterruptedException e) {
			logger.error("异常", e);
		}

		return null;
	}

	/*
	 * ZooKeeper通过Watcher机制实现客户端与服务器之间的松耦合交互，在process方法中，通过对各种事件的监听，可以进行异步的回调处理。
	 * 
	 * 这里的SESSION_TIMEOUT并不是Web容器中Session的超时。这是ZooKeeper对一个客户端的连接，即一个连接会话的超时设置。
	 * 该值一般设置在2～5秒之间。
	 * 
	 * @see
	 * org.apache.zookeeper.Watcher#process(org.apache.zookeeper.WatchedEvent)
	 */
	@Override
	public void process(final WatchedEvent event) {
		System.out.println("监控事件状态:" + event.getState() + ":" + event.getType()
				+ ":" + event.getWrapper() + ":" + event.getPath());

		final KeeperState state = event.getState();

		if (state == KeeperState.SyncConnected) {
			signal.countDown();
		}
	}

}
