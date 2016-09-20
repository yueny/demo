package com.yueny.demo.zookeeper.group;

import java.io.IOException;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.zookeeper.KeeperException;

/**
 * 删除组
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年2月26日 上午12:28:23
 *
 */
public class DeleteGroup extends ConnectionWatcher {
	public static void main(final String[] args) throws KeeperException,
			InterruptedException, IOException {
		final DeleteGroup deleteGroup = new DeleteGroup();

		deleteGroup
				.connect("192.168.87.134:2185,192.168.87.134:2186,192.168.87.134:2187");
		deleteGroup.delete("demo/group/IBbbDemoService");

		deleteGroup.close();
	}

	public void delete(final String groupName) throws KeeperException,
			InterruptedException {
		try {
			final String path = "/" + groupName;

			final List<String> children = zk.getChildren(path, false);
			if (CollectionUtils.isNotEmpty(children)) {
				for (final String child : children) {
					System.out.println("delete path :" + path + " 下的child："
							+ child + "!");
					zk.delete(path + "/" + child, -1);
				}
				System.out.println("delete path :" + path + "!");
				zk.delete(path, -1);
			}
		} catch (final KeeperException.NoNodeException e) {
			System.out.println("Group " + groupName + " does not exist!");
		}
	}
}
