package com.yueny.demo.micros.api.request;

import java.util.Date;

import com.yueny.rapid.data.resp.pojo.request.BaseRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年4月28日 下午5:41:43
 *
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobForMatcherRequest extends BaseRequest {
	/**
	 *
	 */
	private static final long serialVersionUID = -2662027252674284293L;
	/**
	 * 资方资产每轮处理数据量
	 */
	@Getter
	@Setter
	private Integer borrowRound;
	/**
	 * 每次处理任务的时间间隔,单位秒.默认30秒
	 */
	@Getter
	@Setter
	private Long delay;
	/**
	 * 资金错配时的区间维度
	 */
	@Getter
	@Setter
	private String irregularLendCapacityInterval;
	/**
	 * 投资方资金每轮处理数据量
	 */
	@Getter
	@Setter
	private int lendRound;
	/**
	 * 标记
	 */
	@Getter
	@Setter
	private String mask;
	/**
	 * 申请时间
	 */
	@Getter
	@Setter
	private Date timer;
}
