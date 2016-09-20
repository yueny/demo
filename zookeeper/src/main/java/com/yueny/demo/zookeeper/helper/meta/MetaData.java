package com.yueny.demo.zookeeper.helper.meta;

import java.io.Serializable;

import lombok.Data;

/**
 * 实例的元数据，保存了一些与实例生命周期控制有关的数据<br>
 * 所有存储在节点上的对象必须是可序列化的，也就是必须实现Serializable接口，否则无法保存。
 * 这个问题在memcached和ZooKeeper上都存在的。
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年1月10日 上午12:47:57
 *
 */
@Data
public class MetaData implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 5442027307070475118L;

	/** 创建时间 */
	private Long createTm;

	/**
	 * 实例的ID。
	 */
	private String id;

	/**
	 * 最后一次访问时间，每次调用Request.getSession方法时都会去更新这个值。用来计算当前是否超时。
	 * 如果lastAccessTm+maxIdle小于System.currentTimeMillis()，就表示当前Session超时。
	 */
	private Long lastAccessTm;

	/**
	 * 最大空闲时间，默认情况下是30分钟。
	 */
	private Long maxIdle;

	/**
	 * 表示当前 是否可用，如果超时，则此属性为false。
	 */
	private Boolean validate = false;

	/**
	 * 为了冗余Znode的version值，用来实现乐观锁，对 节点的元数据进行更新操作。
	 */
	private int version = 0;

	/**
	 *
	 * 构造方法
	 */
	public MetaData() {
		this.createTm = System.currentTimeMillis();
		this.lastAccessTm = this.createTm;
		this.validate = true;
	}

}
