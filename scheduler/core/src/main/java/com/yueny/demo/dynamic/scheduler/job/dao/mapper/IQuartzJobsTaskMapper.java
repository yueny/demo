package com.yueny.demo.dynamic.scheduler.job.dao.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yueny.demo.dynamic.scheduler.job.entry.QuartzJobsTaskEntry;

/**
 * 数据落地服务
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年8月10日 下午4:25:18
 *
 */
@Repository
public interface IQuartzJobsTaskMapper {
	List<QuartzJobsTaskEntry> selectAll();
}
