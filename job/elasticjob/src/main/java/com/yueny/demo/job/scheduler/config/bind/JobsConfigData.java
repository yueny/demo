package com.yueny.demo.job.scheduler.config.bind;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Lists;
import com.yueny.rapid.lang.mask.pojo.instance.AbstractMaskBo;

import lombok.Setter;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年6月19日 下午6:24:26
 *
 */
@XmlRootElement(name = "jobs")
@XmlAccessorType(XmlAccessType.FIELD)
public class JobsConfigData extends AbstractMaskBo {

	/**
	 * 单个任务配置
	 */
	@XmlElement(name = "job", required = true)
	@Setter
	private List<JobBean> jobs;

	public List<JobBean> getJobs() {
		if (this.jobs == null) {
			this.jobs = Lists.newArrayList();
		}
		return jobs;
	}

	public synchronized boolean plusData(final List<JobBean> settingData) {
		return getJobs().addAll(settingData);
	}

}
