package com.yueny.demo.rocketmq.consumer.enums;

import java.util.List;

import com.google.common.collect.Lists;
import com.yueny.demo.rocketmq.consumer.message.IComponentType;

import lombok.Setter;

public enum MessageComponentType implements IComponentType {
	COUNTER_MESSAGE_ACCEPTED("server.source.append.message.accepted");

	public static List<String> transform() {
		final List<String> s = Lists.newArrayList();
		for (final MessageComponentType type : MessageComponentType.values()) {
			s.add(type.name());
		}
		return s;
	}

	@Setter
	private String node;

	MessageComponentType(final String node) {
		this.node = node;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.yueny.demo.rocketmq.consumer.monitor.IComponentType#node()
	 */
	@Override
	public String node() {
		return this.node;
	}

}
