package com.yueny.demo.zookeeper.group;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;

/**
 * 成员加入组
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年2月25日 下午11:52:43
 *
 */
public class JoinGroup extends ConnectionWatcher {
	public static void main(final String[] args) throws KeeperException,
			InterruptedException, IOException {
		final JoinGroup joinGroup = new JoinGroup();

		joinGroup
				.connect("192.168.87.134:2185,192.168.87.134:2186,192.168.87.134:2187");
		joinGroup.join("demo/group", "IXxxDemoService");
		joinGroup.join("demo/group", "IAaaDemoService");
		joinGroup.join("demo/group", "IBbbDemoService");

		// stay alive until process is killed or thread is interrupted
		Thread.sleep(10000);
	}

	public void join(final String groupName, final String memberName)
			throws KeeperException, InterruptedException {
		final String path = "/" + groupName + "/" + memberName;

		if (zk.exists(path, this) != null) {
			System.out.println("组:" + groupName + "下成员:" + memberName
					+ " 已经存在:" + zk.getChildren("/" + groupName, this));
		} else {
			final String createPath = zk.create(path,
					"member成员".getBytes()/* data */, Ids.OPEN_ACL_UNSAFE,
					CreateMode.EPHEMERAL);
			System.out.println("将成员" + memberName + "加入组" + groupName
					+ "。createPath:" + createPath);
		}
	}
}
