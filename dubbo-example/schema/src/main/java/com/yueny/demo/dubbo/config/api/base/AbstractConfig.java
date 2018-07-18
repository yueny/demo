package com.yueny.demo.dubbo.config.api.base;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yueny.rapid.lang.mask.pojo.instance.AbstractMaskBo;

import lombok.Getter;
import lombok.Setter;

/**
 * 配置解析的工具方法、公共方法 Created by yueny on 2017/4/1 0001.
 */
public abstract class AbstractConfig extends AbstractMaskBo implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -5137368697748270907L;

	/**
	 * id
	 */
	@Getter
	@Setter
	protected String id;

	protected final Logger logger = LoggerFactory.getLogger(getClass());
}
