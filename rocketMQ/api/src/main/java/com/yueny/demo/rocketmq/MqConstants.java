package com.yueny.demo.rocketmq;

import java.nio.charset.Charset;

import com.yueny.demo.rocketmq.enums.CharsetType;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年7月19日 下午12:31:59
 *
 */
public class MqConstants {
	/**
	 * tags<br>
	 *
	 * @author yueny09 <deep_blue_yang@163.com>
	 *
	 * @DATE 2016年8月23日 上午10:36:55
	 *
	 */
	public enum Tags {
		/**
		 * 仅供测试
		 */
		MQ_DEMO_TAG_MSG,
		/**
		 * tags:消息
		 */
		MQ_NOTIFIES_TAG_MSG,
		// /**
		// * tags:预警
		// */
		// NOTIFIES_TAG_WARNING
		;
	}

	/**
	 * topic<br>
	 * allowing only ^[%|a-zA-Z0-9_-]+$
	 *
	 * @author yueny09 <deep_blue_yang@163.com>
	 *
	 * @DATE 2016年8月23日 上午10:34:44
	 */
	public enum Topic {
		/**
		 * 仅供测试的TOPIC
		 */
		MQ_DEMO_TOPIC_TEST;
	}

	/**
	 * 默认编码
	 */
	public static Charset DEFAULT_CHARSET_TYPE = Charset.forName(CharsetType.UTF8.charset());

}
