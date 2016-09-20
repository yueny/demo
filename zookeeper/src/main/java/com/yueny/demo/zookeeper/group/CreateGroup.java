package com.yueny.demo.zookeeper.group;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

/**
 * 新建表示组的znode
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年2月24日 下午10:07:20
 *
 */
public class CreateGroup implements Watcher {
	public static void main(final String[] args) throws IOException,
			InterruptedException, KeeperException {
		final CreateGroup group = new CreateGroup();

		group.connect("192.168.87.134:2185,192.168.87.134:2186,192.168.87.134:2187");
		group.create("demo/group/IBbbDemoService/aa");
		group.create("demo/group/IBbbDemoService/bb");
		group.create("demo/group/IBbbDemoService/cc");
		group.close();
	}

	private final CountDownLatch connectedSignal = new CountDownLatch(1);

	private ZooKeeper zk = null;

	public void close() throws InterruptedException {
		zk.close();
	}

	public void connect(final String hosts) throws IOException,
			InterruptedException {
		zk = new ZooKeeper(hosts, 5000, this);
		// 构造函数的调用时立即返回的，所以等待
		connectedSignal.await();
	}

	public void create(final String groupName) throws InterruptedException,
			KeeperException {
		final String path = "/" + groupName;

		if (zk.exists(path, this) != null) {
			System.out.println("pathath已经存在:" + zk.getChildren(path, this));
		} else {
			// 创建类型CreateMode，分为短暂的和持久的。
			// 短暂的ephe，当创建znode的客户端断开连接时，不论是明确断开还是任何原因终止，短暂znode都会被服务器删除。
			final String createPath = zk.create(path,
					"我的数据".getBytes()/* data */, Ids.OPEN_ACL_UNSAFE,
					CreateMode.PERSISTENT);
			System.out.println("createPath:" + createPath);
		}
	}

	/**
	 * @see org.apache.zookeeper.Watcher#process(org.apache.zookeeper.WatchedEvent)
	 */
	@Override
	public void process(final WatchedEvent event) {
		// Watch类被用于获取zk对象是否准备就绪的信息。客户端与zk服务建立连接后，调用接口process
		System.out.println("process : " + event.getState());
		if (event.getState() == KeeperState.SyncConnected) {
			connectedSignal.countDown();
		}
	}
}
