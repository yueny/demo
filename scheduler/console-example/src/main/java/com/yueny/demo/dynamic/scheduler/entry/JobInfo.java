package com.yueny.demo.dynamic.scheduler.entry;

import com.yueny.demo.dynamic.scheduler.util.DateTimeUtils;

/**
 * 作业明细实体类，由于页面展示
 *
 * @ClassName: JobDetail
 * @Description: TODO
 * @author: zhouhua
 * @date: 2016年3月14日 上午9:29:40
 */
public class JobInfo {

	// 时间规则描述
	private String cron_expression;
	// 触发器功能描述
	private String desccription;
	// 结束时间
	private String end_time;
	// 作业的类名称
	private String job_class_name;
	// 作业分组
	private String job_group;
	// 作业名称
	private String job_name;
	// 下次执行时间
	private String next_fire_time;
	// 上次执行时间
	private String prev_fire_time;
	// 优先级
	private String priopity;
	// 调度器名称
	private String sched_name;
	// 开始时间
	private String start_time;
	// 触发器分组
	private String trigger_group;
	// 触发器名称
	private String trigger_name;
	// 触发器状态
	private String trigger_state;
	// 触发器类型
	private String trigger_type;

	public String getCron_expression() {
		return cron_expression;
	}

	public String getDesccription() {
		return desccription;
	}

	public String getEnd_time() {
		return end_time;
	}

	public String getJob_class_name() {
		return job_class_name;
	}

	public String getJob_group() {
		return job_group;
	}

	public String getJob_name() {
		return job_name;
	}

	public String getNext_fire_time() {
		return next_fire_time;
	}

	public String getPrev_fire_time() {
		return prev_fire_time;
	}

	public String getPriopity() {
		return priopity;
	}

	public String getSched_name() {
		return sched_name;
	}

	public String getStart_time() {
		return start_time;
	}

	public String getTrigger_group() {
		return trigger_group;
	}

	public String getTrigger_name() {
		return trigger_name;
	}

	public String getTrigger_state() {
		return trigger_state;
	}

	public String getTrigger_type() {
		return trigger_type;
	}

	public void setCron_expression(final String cron_expression) {
		this.cron_expression = cron_expression;
	}

	public void setDesccription(final String desccription) {
		this.desccription = desccription;
	}

	public void setEnd_time(final String end_time) {
		this.end_time = end_time;
	}

	public void setJob_class_name(final String job_class_name) {
		this.job_class_name = job_class_name;
	}

	public void setJob_group(final String job_group) {
		this.job_group = job_group;
	}

	public void setJob_name(final String job_name) {
		this.job_name = job_name;
	}

	public void setNext_fire_time(final String next_fire_time) {
		this.next_fire_time = DateTimeUtils.fomat(next_fire_time, "yyyy-MM-dd HH:mm:ss SSS");
	}

	public void setPrev_fire_time(final String prev_fire_time) {
		this.prev_fire_time = DateTimeUtils.fomat(prev_fire_time, "yyyy-MM-dd HH:mm:ss SSS");
	}

	public void setPriopity(final String priopity) {
		this.priopity = priopity;
	}

	public void setSched_name(final String sched_name) {
		this.sched_name = sched_name;
	}

	public void setStart_time(final String start_time) {
		this.start_time = start_time;
	}

	public void setTrigger_group(final String trigger_group) {
		this.trigger_group = trigger_group;
	}

	public void setTrigger_name(final String trigger_name) {
		this.trigger_name = trigger_name;
	}

	public void setTrigger_state(final String trigger_state) {
		if (TriggerState.STATE_WAITING.equals(trigger_state)) {
			this.trigger_state = "等待中";
		}
		if (TriggerState.STATE_ACQUIRED.equals(trigger_state)) {
			this.trigger_state = "执行中";
		}
		if (TriggerState.STATE_COMPLETE.equals(trigger_state)) {
			this.trigger_state = "执行完成";
		}

		if (TriggerState.STATE_BLOCKED.equals(trigger_state)) {
			this.trigger_state = "阻塞中";
		}
		if (TriggerState.STATE_PAUSED.equals(trigger_state)) {
			this.trigger_state = "暂停";
		}
	}

	public void setTrigger_type(final String trigger_type) {
		this.trigger_type = trigger_type;
	}

}
