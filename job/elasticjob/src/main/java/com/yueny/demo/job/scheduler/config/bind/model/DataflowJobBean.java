package com.yueny.demo.job.scheduler.config.bind.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import com.yueny.demo.job.scheduler.config.bind.JopType;
import com.yueny.rapid.lang.mask.pojo.instance.AbstractMaskBo;

import lombok.Getter;
import lombok.Setter;

/**
 * 执行流式任务对象
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年6月19日 下午6:20:51
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class DataflowJobBean extends AbstractMaskBo implements IDataflowJobBean {
	/**
	 * 任务执行表达式(作业启动时间的cron表达式)
	 */
	@XmlElement(name = "cron", required = true)
	@Getter
	@Setter
	private String cron;
	/**
	 * 作业描述信息
	 */
	@XmlAttribute(name = "description")
	@Getter
	@Setter
	private String description;
	/**
	 * 作业是否启动时禁止
	 */
	@XmlAttribute(name = "disabled")
	@Getter
	@Setter
	private boolean disabled = false;
	/**
	 * 是否开启失效转移
	 */
	@XmlAttribute(name = "failover")
	@Getter
	@Setter
	private boolean failover = true;
	// monitorPort=9888
	/**
	 * maxTimeDiffSeconds， 默认-1
	 */
	@XmlElement(name = "maxTimeDiffSeconds")
	@Getter
	@Setter
	private int maxTimeDiffSeconds = -1;
	/**
	 * 监控作业执行时状态
	 */
	@XmlAttribute(name = "monitorExecution")
	@Getter
	@Setter
	private boolean monitorExecution = true;
	/**
	 * Class Name 作业名称
	 */
	@XmlAttribute(name = "name", required = true)
	@Getter
	@Setter
	private String name;
	/**
	 * 本地配置是否可覆盖注册中心配置
	 */
	@XmlAttribute(name = "overwrite")
	@Getter
	@Setter
	private boolean overwrite = true;
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
	 * 是否为流式处理作业，默认为 false
	 */
	@XmlAttribute(name = "streamingProcess")
	@Getter
	@Setter
	private boolean streamingProcess = false;
	/**
	 * JopType
	 */
	@Getter
	private final JopType type = JopType.DATAFLOW;

}
