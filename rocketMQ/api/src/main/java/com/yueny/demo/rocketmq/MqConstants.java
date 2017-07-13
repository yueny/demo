package com.yueny.demo.rocketmq;

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
		DEMO_TAG_MQ_MSG("demo_mq_tag"),
		/**
		 * tags:消息
		 */
		NOTIFIES_TAG_MSG("demo_mq_notifies_tag"),
		/**
		 * tags:预警
		 */
		NOTIFIES_TAG_WARNING("demo_mq_warning_tag");

		private final String tag;

		Tags(final String tag) {
			this.tag = tag;
		}

		public String tag() {
			return this.tag;
		}
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
		 * 仅供测试
		 */
		DEMO_MQ_TOPIC("demo_mq_topic"),
		/**
		 * 通知的MQ topic
		 */
		NOTIFIES_MQ_TOPIC("demo_notifies_mq_topic");

		private final String topic;

		Topic(final String topic) {
			this.topic = topic;
		}

		public String topic() {
			return this.topic;
		}
	}

}
