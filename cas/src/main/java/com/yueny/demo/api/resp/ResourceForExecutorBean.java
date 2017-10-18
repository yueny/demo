package com.yueny.demo.api.resp;

import lombok.Data;

/**
 * 系统资源
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年10月18日 下午3:58:57
 *
 * @param <T>
 */
@Data
public class ResourceForExecutorBean {
	/**
	 * 资源描述
	 */
	private String desc;
	/**
	 * 资源地址
	 */
	private String resource;

}
