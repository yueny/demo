package com.yueny.demo.dynamic.scheduler.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.yueny.demo.dynamic.scheduler.entry.JobInfo;

/**
 * Quartz定时作业数据操作类
 *
 * @ClassName: QuartzDao
 * @Description: TODO
 * @author: zhouhua
 * @date: 2016年3月14日 下午1:16:41
 */
@Deprecated
public class QuartzDao {

	public static final String QRTZ_SQL = "select tr.*,cr.CRON_EXPRESSION,jo.JOB_CLASS_NAME from qrtz_triggers tr,QRTZ_CRON_TRIGGERS cr,QRTZ_JOB_DETAILS jo  "
			+ " where cr.SCHED_NAME=tr.SCHED_NAME and cr.TRIGGER_NAME=tr.TRIGGER_NAME and cr.TRIGGER_GROUP=tr.TRIGGER_GROUP  "
			+ " and jo.SCHED_NAME=tr.SCHED_NAME and jo.JOB_NAME=tr.JOB_NAME and jo.JOB_GROUP=tr.JOB_GROUP";

	@Autowired
	private DataSource dataSource;

	/**
	 * 根据条件查询Quartz作业的信息
	 *
	 * @Title: getList
	 * @Description: TODO
	 * @param params
	 *            参数集合
	 * @return
	 * @return: List<JobDetail>
	 */
	public List<JobInfo> getList(final Map<String, String> params) {

		final List<Map<String, Object>> resultsList = getJdbcTemplate().queryForList(QRTZ_SQL);

		if (resultsList == null || resultsList.size() == 0) {

			return null;
		}

		final List<JobInfo> details = new ArrayList<JobInfo>(resultsList.size());

		for (final Map<String, Object> map : resultsList) {
			details.add(createJobDetail(map));
		}

		return details;

	}

	/**
	 * 创建作业明细信息
	 *
	 * @Title: createJobDetail
	 * @Description: TODO
	 * @param map
	 * @return
	 * @return: JobDetail
	 */
	private JobInfo createJobDetail(final Map<String, Object> map) {

		final JobInfo detail = new JobInfo();

		detail.setSched_name(map.get("sched_name") + "");
		detail.setTrigger_name(map.get("trigger_name") + "");
		detail.setTrigger_group(map.get("trigger_group") + "");
		detail.setDesccription(map.get("desccription") + "");
		detail.setJob_name(map.get("job_name") + "");
		detail.setJob_group(map.get("job_group") + "");
		detail.setNext_fire_time(map.get("next_fire_time") + "");
		detail.setPrev_fire_time(map.get("prev_fire_time") + "");
		detail.setCron_expression(map.get("cron_expression") + "");
		detail.setPriopity(map.get("priopity") + "");
		detail.setEnd_time(map.get("end_time") + "");
		detail.setStart_time(map.get("start_time") + "");
		detail.setJob_class_name(map.get("job_class_name") + "");
		detail.setTrigger_state(map.get("trigger_state") + "");
		detail.setTrigger_type(map.get("trigger_type") + "");
		return detail;
	}

	private JdbcTemplate getJdbcTemplate() {
		return new JdbcTemplate(dataSource);
	}
}
