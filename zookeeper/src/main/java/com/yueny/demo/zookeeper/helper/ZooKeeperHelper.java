package com.yueny.demo.zookeeper.helper;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yueny.demo.zookeeper.helper.meta.MetaData;
import com.yueny.demo.zookeeper.helper.watcher.ConnectionWatcher;

/**
 * 待完善，比如要添加Session监听事件的支持、对ZooKeeper上被标记为不可用的Session节点的删除、
 * 对Session进行监控和管理的控制台以及非常难解决的ClassLoader问题等
 * 。另外，前文也提到了，分布式Session的实现是和某个Web容器紧密耦合的
 * ，这一点让我很不爽。因为需要针对不同的Web容器各自实现一套Session的管理机制
 * 。不过我相信通过良好的设计，可以实现通用的组件。目前我已经实现了在Jetty和Tomcat容器下的分布式Session。
 *
 * 在文章的最后，我们讨论一下如何解决ClassLoader问题。其实，在OSGi框架下，这个问题并不是很麻烦。因为，
 * 我们可以将所有领域对象类打包成一个单独的Bundle
 * 。同时将分布式Session的Filter实现也打包成一个Bundle。通过动态引用的方式，就可以引入所有领域对象的类型了
 * 。但在非OSGi环境下，只能将领域对象的类文件在每个子系统中都包含一份来解决ClassLoader问题
 * 。这样会造成一个问题，就是当领域对象发生变化时，我需要重启所有的子系统
 * ，来装载更新后的领域对象类，而不像在OSGi下，只需要重启这个领域对象Bundle就可以了。
 *
 * CreateMode:<br>
 * PERSISTENT：创建后只要不删就永久存在。创建持久化节点，对应机器关闭连接后节点/数据不会消失。<br>
 * EPHEMERAL：创建瞬时节点，会话结束年结点自动被删除，EPHEMERAL结点不允许有子节点，
 * Zookeeper在感知连接机器宕机后会清除它创建的瞬时节点。 <br>
 * SEQUENTIAL：节点名末尾会自动追加一个10位数的单调递增的序号，同一个节点的所有子节点序号是单调递增的<br>
 * PERSISTENT_SEQUENTIAL：如果PATH是以’/’结尾则以这个PATH作为父节点，创建一个子节点
 * ，其子节点名字是一个按先后顺序排列的数值（会自动加上 0000000000
 * 自增的后缀）；否则创建一个名字是‘/’后面字符加上先后顺序排列的数值字符串的节点，同样创建持久节点。<br>
 * EPHEMERAL_SEQUENTIAL：穿件瞬时顺序节点，和PERSISTENT_SEQUENTIAL一样，区别在于它是瞬时的。session
 * 过期自动删除，也会加数字的后缀。<br>
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年1月10日 上午1:49:07
 *
 */
public class ZooKeeperHelper {
	/**
	 *
	 */
	private static final String GROUP_NAME = "/demo";
	/**
	 * 多个以逗号分隔。如 192.168.87.131:2185,192.168.87.131:2186
	 */
	private static String hosts;
	/**
	 *
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(ZooKeeperHelper.class);
	/**
	 *
	 */
	private static ExecutorService pool = Executors.newCachedThreadPool();

	/**
	 *
	 * 创建指定Session ID的节点(异步操作)
	 *
	 * @param sid
	 *
	 * @param waitFor
	 *            是否等待执行结果
	 *
	 * @return
	 */
	public static String asynCreateSessionNode(final MetaData metadata,
			final boolean waitFor) {
		final Callable<String> task = new Callable<String>() {
			@Override
			public String call() throws Exception {
				return createSessionNode(metadata);
			}
		};

		try {
			final Future<String> result = pool.submit(task);
			// 如果需要等待执行结果
			if (waitFor) {
				while (true) {
					if (result.isDone()) {
						return result.get();
					}
				}
			}
		} catch (final Exception e) {
			logger.error("异常", e);
		}

		return null;
	}

	/**
	 *
	 * 删除指定Session ID的节点(异步操作)
	 *
	 * @param sid
	 *
	 * @param waitFor
	 *            是否等待执行结果
	 *
	 * @return
	 */
	public static boolean asynDeleteSessionNode(final String sid,
			final boolean waitFor) {
		final Callable<Boolean> task = new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				return deleteSessionNode(sid);
			}
		};

		try {
			final Future<Boolean> result = pool.submit(task);
			// 如果需要等待执行结果
			if (waitFor) {
				while (true) {
					if (result.isDone()) {
						return result.get();
					}
				}
			}
		} catch (final Exception e) {
			logger.error("异常", e);
		}

		return false;
	}

	/**
	 *
	 * 删除指定Session ID的节点(异步操作)
	 *
	 * @param sid
	 *
	 * @param waitFor
	 *            是否等待执行结果
	 *
	 * @return
	 */
	public static boolean asynSetSessionData(final String sid,
			final String name, final Object value, final boolean waitFor) {
		final Callable<Boolean> task = new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				return setSessionData(sid, name, value);
			}
		};

		try {
			final Future<Boolean> result = pool.submit(task);
			// 如果需要等待执行结果
			if (waitFor) {
				while (true) {
					if (result.isDone()) {
						return result.get();
					}
				}
			}
		} catch (final Exception e) {
			logger.error("异常", e);
		}

		return false;
	}

	/**
	 *
	 * 关闭一个会话
	 */
	public static void close(final ZooKeeper zk) {
		if (zk != null) {
			try {
				zk.close();
			} catch (final InterruptedException e) {
				logger.error("异常", e);
			}
		}
	}

	/**
	 * 连接服务器
	 *
	 * @return
	 */
	public static ZooKeeper connect() {
		final ConnectionWatcher cw = new ConnectionWatcher();
		final ZooKeeper zk = cw.connection(hosts);
		return zk;
	}

	/**
	 *
	 * 创建持久态的组节点
	 */
	public static void createGroupNode() {
		final ZooKeeper zk = connect();

		if (zk != null) {
			try {
				// 检查节点是否存在
				final Stat stat = zk.exists(GROUP_NAME, false);

				// stat为null表示无此节点，需要创建
				if (stat == null) {
					// 创建组件点
					final String createPath = zk.create(GROUP_NAME, null,
							Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

					logger.info("创建节点：{} 完成！", createPath);
				} else {
					logger.info("组节点：{} 已存在，无需创建。跳过继续。", GROUP_NAME);
				}
			} catch (final KeeperException e) {
				logger.error("异常", e);
			} catch (final InterruptedException e) {
				logger.error("异常", e);
			} finally {
				close(zk);
			}
		}
	}

	/**
	 *
	 * 创建持久态的指定Session ID的节点
	 *
	 * @param sid
	 *            Session ID
	 *
	 * @return
	 */
	public static String createSessionNode(final MetaData metadata) {
		if (metadata == null) {
			return null;
		}

		final ZooKeeper zk = connect(); // 连接服务期

		if (zk != null) {
			final String path = GROUP_NAME + "/" + metadata.getId();

			try {
				// 检查节点是否存在
				final Stat stat = zk.exists(path, false);

				// stat为null表示无此节点，需要创建
				if (stat == null) {
					// 创建组件点
					final String createPath = zk.create(path, null,
							Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

					logger.debug("创建Session节点完成:[" + createPath + "]");

					// 写入节点数据
					zk.setData(path, SerializationUtils.serialize(metadata), -1);

					return createPath;
				}
			} catch (final KeeperException e) {
				logger.error("异常", e);
			} catch (final InterruptedException e) {
				logger.error("异常", e);
			} finally {
				close(zk);
			}
		}
		return null;
	}

	/**
	 *
	 * 删除指定Session ID的节点
	 *
	 * @param sid
	 *            Session ID
	 *
	 * @return
	 */
	public static boolean deleteSessionNode(final String sid) {
		final ZooKeeper zk = connect(); // 连接服务期

		if (zk != null) {
			final String path = GROUP_NAME + "/" + sid;

			try {
				// 检查节点是否存在
				final Stat stat = zk.exists(path, false);

				// 如果节点存在则删除之
				if (stat != null) {
					// 先删除子节点
					final List<String> nodes = zk.getChildren(path, false);

					if (nodes != null) {
						for (final String node : nodes) {
							zk.delete(path + "/" + node, -1);
						}
					}

					// 删除父节点
					zk.delete(path, -1);
					logger.debug("删除Session节点完成:[" + path + "]");
					return true;
				}
			} catch (final KeeperException e) {
				logger.error("异常", e);
			} catch (final InterruptedException e) {
				logger.error("异常", e);
			} finally {
				close(zk);
			}
		}
		return false;
	}

	/**
	 * 销毁,关闭
	 */
	public static void destroy() {
		if (pool != null) {
			pool.shutdown();// 关闭
		}
	}

	/**
	 *
	 * 返回指定ID的Session元数据
	 *
	 * @param id
	 *
	 * @return
	 */
	public static MetaData getMetaData(final String id, final ZooKeeper zk) {
		if (zk != null) {
			final String path = GROUP_NAME + "/" + id;

			try {
				// 检查节点是否存在
				final Stat stat = zk.exists(path, false);

				// stat为null表示无此节点
				if (stat == null) {
					return null;
				}

				// 获取节点上的数据
				final byte[] data = zk.getData(path, false, null);
				if (data != null) {
					// 反序列化
					final Object obj = SerializationUtils.deserialize(data);
					// 转换类型
					if (obj instanceof MetaData) {
						final MetaData metadata = (MetaData) obj;
						// 设置当前版本号
						metadata.setVersion(stat.getVersion());
						return metadata;
					}
				}

			} catch (final KeeperException e) {
				logger.error("异常", e);
			} catch (final InterruptedException e) {
				logger.error("异常", e);
			}
		}
		return null;
	}

	/**
	 *
	 * 返回指定Session ID的节点下数据
	 *
	 * @param sid
	 *            Session ID
	 *
	 * @param name
	 *            数据节点的名称
	 *
	 * @param value
	 *            数据
	 *
	 * @return
	 */
	public static Object getSessionData(final String sid, final String name) {
		final ZooKeeper zk = connect(); // 连接服务器

		if (zk != null) {
			final String path = GROUP_NAME + "/" + sid;

			try {
				// 检查指定的Session节点是否存在
				Stat stat = zk.exists(path, false);

				if (stat != null) {
					// 查找数据节点是否存在
					final String dataPath = path + "/" + name;

					stat = zk.exists(dataPath, false);

					Object obj = null;
					if (stat != null) {
						// 获取节点数据
						final byte[] data = zk.getData(dataPath, false, null);
						if (data != null) {
							// 反序列化
							obj = SerializationUtils.deserialize(data);
						}
					}
					return obj;
				}
			} catch (final KeeperException e) {
				logger.error("异常", e);
			} catch (final InterruptedException e) {
				logger.error("异常", e);
			} finally {
				close(zk);
			}
		}
		return null;
	}

	/**
	 *
	 * 返回ZooKeeper服务器上的Session节点的所有数据，并装载为Map
	 *
	 * @param id
	 *
	 * @return
	 */
	public static Map<String, Object> getSessionMap(final String id) {
		final ZooKeeper zk = connect();
		if (zk != null) {
			final String path = GROUP_NAME + "/" + id;
			try {
				// 获取元数据
				final MetaData metadata = getMetaData(path, zk);

				// 如果不存在或是无效，则直接返回null
				if (metadata == null || !metadata.getValidate()) {
					return null;
				}

				// 获取所有子节点
				final List<String> nodes = zk.getChildren(path, false);
				// 存放数据
				final Map<String, Object> sessionMap = new HashMap<>();

				for (final String node : nodes) {
					final String dataPath = path + "/" + node;
					final Stat stat = zk.exists(dataPath, false);

					// 节点存在
					if (stat != null) {
						// 提取数据
						final byte[] data = zk.getData(dataPath, false, null);

						if (data != null) {
							sessionMap.put(node,
									SerializationUtils.deserialize(data));
						} else {
							sessionMap.put(node, null);
						}
					}
				}
				return sessionMap;
			} catch (final KeeperException e) {
				logger.error("异常", e);
			} catch (final InterruptedException e) {
				logger.error("异常", e);
			} finally {
				close(zk);
			}
		}
		return null;
	}

	/**
	 *
	 * 初始化
	 */
	public static void initialize(final Configuration config) {
		hosts = config.getServers();
	}

	/**
	 * 验证指定ID的节点是否有效
	 *
	 * @param id
	 * @return
	 */
	public static boolean isValid(final String id) {
		final ZooKeeper zk = connect();
		if (zk != null) {
			try {
				return isValid(id, zk);
			} finally {
				close(zk);
			}
		}
		return false;
	}

	/**
	 *
	 * 验证指定ID的节点是否有效
	 *
	 * @param id
	 *
	 * @param zk
	 *
	 * @return
	 */
	public static boolean isValid(final String id, final ZooKeeper zk) {
		if (zk != null) {
			// 获取元数据
			final MetaData metadata = getMetaData(id, zk);

			// 如果不存在或是无效，则直接返回null
			if (metadata == null) {
				return false;
			}
			return metadata.getValidate();
		}
		return false;
	}

	/**
	 *
	 * 删除指定Session ID的节点下数据
	 *
	 * @param sid
	 *            Session ID
	 *
	 * @param name
	 *            数据节点的名称
	 *
	 * @param value
	 *            数据
	 *
	 * @return
	 */
	public static void removeSessionData(final String sid, final String name) {
		final ZooKeeper zk = connect(); // 连接服务器

		if (zk != null) {
			final String path = GROUP_NAME + "/" + sid;

			try {
				// 检查指定的Session节点是否存在
				Stat stat = zk.exists(path, false);
				if (stat != null) {
					// 查找数据节点是否存在
					final String dataPath = path + "/" + name;

					stat = zk.exists(dataPath, false);
					if (stat != null) {
						// 删除节点
						zk.delete(dataPath, -1);
					}
				}
			} catch (final KeeperException e) {
				logger.error("异常", e);
			} catch (final InterruptedException e) {
				logger.error("异常", e);
			} finally {
				close(zk);
			}
		}
	}

	/**
	 *
	 * 在指定Session ID的节点下添加数据节点
	 *
	 * @param sid
	 *            Session ID
	 *
	 * @param name
	 *            数据节点的名称
	 *
	 * @param value
	 *            数据
	 *
	 * @return
	 */
	public static boolean setSessionData(final String sid, final String name,
			final Object value) {
		boolean result = false;

		final ZooKeeper zk = connect(); // 连接服务器
		if (zk != null) {
			final String path = GROUP_NAME + "/" + sid;

			try {
				// 检查指定的Session节点是否存在
				Stat stat = zk.exists(path, false);

				// 如果节点存在则删除之
				if (stat != null) {
					// 查找数据节点是否存在，不存在就创建一个
					final String dataPath = path + "/" + name;

					stat = zk.exists(dataPath, false);
					if (stat == null) {
						// 创建数据节点
						zk.create(dataPath, null, Ids.OPEN_ACL_UNSAFE,
								CreateMode.PERSISTENT);
						logger.debug("创建数据节点完成[" + dataPath + "]");
					}

					// 在节点上设置数据，所有数据必须可序列化
					if (value instanceof Serializable) {
						int dataNodeVer = -1;

						if (stat != null) {
							// 记录数据节点的版本
							dataNodeVer = stat.getVersion();
						}

						final byte[] data = SerializationUtils
								.serialize((Serializable) value);

						stat = zk.setData(dataPath, data, dataNodeVer);
						logger.debug("更新数据节点数据完成[" + dataPath + "][" + value
								+ "]");
						result = true;
					}
				}
			} catch (final KeeperException e) {
				logger.error("异常", e);
			} catch (final InterruptedException e) {
				logger.error("异常", e);
			} finally {
				close(zk);
			}
		}

		return result;
	}

	/**
	 *
	 * 更新Session节点的元数据
	 *
	 * @param id
	 *            Session ID
	 *
	 * @param version
	 *            更新版本号
	 *
	 * @param zk
	 */
	public static void updateMetaData(final MetaData metadata,
			final ZooKeeper zk) {
		try {
			if (metadata != null) {
				final String id = metadata.getId();
				final Long now = System.currentTimeMillis(); // 当前时间

				// 检查是否过期
				final Long timeout = metadata.getLastAccessTm()
						+ metadata.getMaxIdle(); // 空闲时间

				// 如果空闲时间小于当前时间，则表示Session超时
				if (timeout < now) {
					metadata.setValidate(false);
					logger.debug("Session节点已超时[" + id + "]");
				}

				// 设置最后一次访问时间
				metadata.setLastAccessTm(now);

				// 更新节点数据
				final String path = GROUP_NAME + "/" + id;

				final byte[] data = SerializationUtils.serialize(metadata);
				zk.setData(path, data, metadata.getVersion());
				logger.debug("更新Session节点的元数据完成[" + path + "]");
			}

		} catch (final KeeperException e) {
			logger.error("异常", e);
		} catch (final InterruptedException e) {
			logger.error("异常", e);
		}
	}

	/**
	 *
	 * 更新Session节点的元数据
	 *
	 * @param id
	 *            Session ID
	 *
	 * @param version
	 *            更新版本号
	 *
	 * @param zk
	 */
	public static void updateMetaData(final String id) {
		final ZooKeeper zk = connect();
		try {
			// 获取元数据
			final MetaData metadata = getMetaData(id, zk);
			if (metadata != null) {
				updateMetaData(metadata, zk);
			}
		} finally {
			close(zk);
		}
	}

}
