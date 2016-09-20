package com.yueny.demo.zookeeper.group;

import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.KeeperException;

/**
 * 查看组成员列表
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年2月26日 上午12:04:47
 *
 */
public class ShowListGroup extends ConnectionWatcher {
	public static void main(final String[] args) throws KeeperException,
			InterruptedException, IOException {
		final ShowListGroup listGroup = new ShowListGroup();

		listGroup
				.connect("192.168.87.134:2185,192.168.87.134:2186,192.168.87.134:2187");
		listGroup.list("demo/group");
		listGroup.list("demo/group1");// znode does not exist!
		listGroup.list("zookeeper/quota");// no members in group

		listGroup.close();
	}

	public void list(final String groupName) throws KeeperException,
			InterruptedException {
		try {
			final String path = "/" + groupName;

			final List<String> children = zk.getChildren(path, false);
			if (children.isEmpty()) {
				System.out.println("no members in group: " + groupName);
				return;
			}

			for (final String child : children) {
				System.out.println("child:" + child);
			}
		} catch (final KeeperException.NoNodeException e) {
			System.out.println("Group " + groupName + " does not exist!");
		}
	}

}
