/**
 *
 */
package com.yueny.demo.rocketmq.consumer.data;

import com.yueny.rapid.lang.mask.pojo.instance.AbstractMaskBo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年7月19日 上午9:18:45
 *
 */
@Builder
@AllArgsConstructor
public class ScallorEvent extends AbstractMaskBo {
	/**
	 * 数据
	 */
	@Getter
	private final String data;
	/**
	 * 业务方传递的messageId
	 */
	@Getter
	private final String messageId;
	/**
	 * MQ msgId
	 */
	@Getter
	private final String msgId;

}
