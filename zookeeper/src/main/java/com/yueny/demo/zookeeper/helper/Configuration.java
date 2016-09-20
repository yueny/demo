package com.yueny.demo.zookeeper.helper;

import lombok.Data;

/**
 * 参数配置
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年1月10日 上午1:52:45
 *
 */
@Data
public class Configuration {
	/**
	 *
	 */
	private Integer poolSize;
	/**
	 * zookeeper服务器
	 */
	private String servers;
	/**
	 * 超时时间
	 */
	private Long timeout;
}
