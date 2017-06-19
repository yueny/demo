package com.yueny.demo.job.scheduler.config.bind;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import com.yueny.rapid.lang.mask.pojo.instance.AbstractMaskBo;

import lombok.Getter;
import lombok.Setter;

/**
 * 执行任务对象
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年6月19日 下午6:20:51
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class JobBean extends AbstractMaskBo {
	/**
	 * 任务执行表达式(作业启动时间的cron表达式)
	 */
	@XmlElement(name = "cron", required = true)
	@Getter
	@Setter
	private String cron;
	/**
	 * Class Name 作业名称
	 */
	@XmlAttribute(name = "name", required = true)
	@Getter
	@Setter
	private String name;
	/**
	 * 设置分片序列号和个性化参数对照表.
	 *
	 * <p>
	 * 分片序列号和参数用等号分隔, 多个键值对用逗号分隔. 类似map. 分片序列号从0开始, 不可大于或等于作业分片总数. 如:
	 * 0=a,1=b,2=c
	 * </p>
	 *
	 * @param shardingItemParameters
	 *            分片序列号和个性化参数对照表
	 *
	 * @return 作业配置构建器
	 */
	@XmlElement(name = "shardingItemParameters")
	@Getter
	@Setter
	private String shardingItemParameters;
	/**
	 * 作业分片总数，默认1片
	 */
	@XmlElement(name = "shardingTotalCount")
	@Getter
	@Setter
	private int shardingTotalCount = 1;
	/**
	 * JopType
	 */
	@XmlAttribute(name = "type", required = true)
	@Getter
	@Setter
	private JopType type;

}
