package com.yueny.demo.zookeeper.demo2;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

/**
 * watcher分为两大类：data watches和child watches。getData()和exists()上可以设置data
 * watches，getChildren()上可以设置child watches。
 *
 * setData()会触发data watches;
 *
 * create()会触发data watches和child watches;
 *
 * delete()会触发data watches和child watches.
 *
 * 如果对一个不存在的节点调用了exists()，并设置了watcher，而在连接断开的情况下create/delete了该znode，则watcher会丢失
 * 。
 *
 * 在server端用一个map来存放watcher，所以相同的watcher在map中只会出现一次，只要watcher被回调一次，它就会被删除----
 * map解释了watcher的一次性。比如如果在getData()和exists()上设置的是同一个data
 * watcher，调用setData()会触发data watcher，但是getData()和exists()只有一个会收到通知。
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年1月10日 下午6:49:33
 *
 */
public class SelfWatcherDemo implements Watcher {
	public static void main(final String[] args) {
		final SelfWatcherDemo inst = new SelfWatcherDemo("10.9.1.189:2181");
		inst.setWatcher();
		inst.trigeWatcher();
		inst.disconnect();
	}

	ZooKeeper zk = null;

	SelfWatcherDemo(final String address) {
		try {
			// 在创建ZooKeeper时第三个参数负责设置该类的默认构造函数
			zk = new ZooKeeper(address, 3000, this);
			zk.create("/root", "insert".getBytes(), Ids.OPEN_ACL_UNSAFE,
					CreateMode.EPHEMERAL);
		} catch (final IOException e) {
			e.printStackTrace();
			zk = null;
		} catch (final KeeperException e) {
			e.printStackTrace();
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void process(final WatchedEvent event) {
		System.out.println(event.toString());
	}

	void disconnect() {
		if (zk != null) {
			try {
				zk.close();
			} catch (final InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	void setWatcher() {
		try {
			final Stat s = zk.exists("/root", false);
			if (s != null) {
				zk.getData("/root", true, s);
			}
		} catch (final KeeperException e) {
			e.printStackTrace();
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
	}

	void trigeWatcher() {
		try {
			// 此处不设置watcher
			final Stat s = zk.exists("/root", false);
			// 修改数据时需要提供version，version设为-1表示强制修改
			zk.setData("/root", "update".getBytes(), s.getVersion());
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

}
