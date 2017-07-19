/**
 *
 */
package com.yueny.demo.rocketmq.consumer.data;

import com.yueny.rapid.lang.mask.pojo.instance.AbstractMaskBo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年7月19日 上午9:18:45
 *
 */
public class ScallorEvent extends AbstractMaskBo {
	/**
	 * 数据
	 */
	@Setter
	private String data;
	/**
	 * 业务方传递的messageId
	 */
	@Getter
	@Setter
	private String messageId;
	/**
	 * MQ msgId
	 */
	@Getter
	@Setter
	private String msgId;

}
