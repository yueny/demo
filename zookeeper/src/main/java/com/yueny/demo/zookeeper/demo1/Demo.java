package com.yueny.demo.zookeeper.demo1;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yueny.demo.zookeeper.helper.Configuration;
import com.yueny.demo.zookeeper.helper.ZooKeeperHelper;

public class Demo {
	/**
	 * 根节点
	 */
	public static final String ROOT = "/demo";
	private static final Logger logger = LoggerFactory.getLogger(Demo.class);

	public static void main(final String[] args) throws IOException {
		final Configuration config = new Configuration();
		config.setServers("192.168.87.132:2185,192.168.87.132:2186");
		config.setTimeout(3000L);
		config.setPoolSize(2);

		ZooKeeperHelper.initialize(config);

		ZooKeeperHelper.createGroupNode();

		final ZooKeeper zk = ZooKeeperHelper.connect();
		// ZooKeeper zkp = new ZooKeeper("", 8000, null);

		try {
			// 取得ROOT节点下的子节点名称,返回List<String>
			final List<String> children = zk.getChildren("/zookeeper", true);
			logger.info("Children of {} node: {}.", "zookeeper",
					Arrays.toString(children.toArray()));
			for (final String child : children) {
				logger.info("child: {}.", child);
			}

			// final DemoBo bo = new DemoBo();
			// bo.setId("11");
			// bo.setName("11 名字");
			//
			// final String path = ROOT + "/" + bo.getId();
			// zk.create(path, SerializationUtils.serialize(bo),
			// ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			//
			// if (zk.exists(ROOT, false) != null) {
			// logger.error("根目录{}已经存在！", ROOT);
			// }
			//
			// try {
			// // 当节点名已存在时再去创建它会抛出KeeperException(即使本次的ACL、CreateMode和上次的不一样)
			// zk.create(path, SerializationUtils.serialize(bo),
			// Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			// } catch (final KeeperException e) {
			// logger.error("故意报错的哦！", e);
			// // 不适用 Throwables
			// // logger.error("错误：{}！", Throwables.getStackTraceAsString(e));
			// }
			//
			// // 创建SEQUENTIAL节点
			// // zk.create(ROOT + "/node-", "same data".getBytes(),
			// // Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
			//
			// // 取得ROOT节点下的子节点名称,返回List<String>
			// final List<String> children1 = zk.getChildren(ROOT, true);
			// logger.info("Children of {} node: {}.", ROOT,
			// Arrays.toString(children1.toArray()));
			// for (final String child : children1) {
			// logger.info("child: {}.", child);
			// }

			// // 节点删除
			// for (final String node : children) {
			// // 删除ROOT+node这个节点，第二个参数为版本，－1的话直接删除，无视版本
			// zk.delete(ROOT + "/" + node, -1);
			// }
			// // 根目录ROOT删除
			// zk.delete(ROOT, -1);

			zk.close();
		} catch (final Exception e) {
			logger.error("错误！", e);
		}
	}

}
