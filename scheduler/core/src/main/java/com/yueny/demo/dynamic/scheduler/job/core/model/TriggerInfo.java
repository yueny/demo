/**
 *
 */
package com.yueny.demo.dynamic.scheduler.job.core.model;

import org.quartz.Trigger;
import org.quartz.TriggerKey;

import com.yueny.demo.dynamic.scheduler.job.core.enums.JobStatusType;
import com.yueny.superclub.api.pojo.instance.AbstractBo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年2月8日 下午9:25:18
 * @since
 */
@Getter
@Setter
public class TriggerInfo extends AbstractBo {
	/**
	 *
	 */
	private static final long serialVersionUID = -4161237644755730137L;

	private JobStatusType taskStatus;
	private Trigger trigger;
	private TriggerKey triggerKey;
}
