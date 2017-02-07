package com.yueny.demo.dynamic.scheduler.job.service.impl;

import java.util.Collections;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yueny.demo.dynamic.scheduler.job.bo.QuartzJobsTaskBo;
import com.yueny.demo.dynamic.scheduler.job.dao.mapper.IQuartzJobsTaskMapper;
import com.yueny.demo.dynamic.scheduler.job.entry.QuartzJobsTaskEntry;
import com.yueny.demo.dynamic.scheduler.job.service.BaseSevice;
import com.yueny.demo.dynamic.scheduler.job.service.IQuartzJobsTaskService;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年8月5日 上午9:57:38
 *
 */
@Service
public class QuartzJobsTaskServiceImpl extends BaseSevice implements IQuartzJobsTaskService {
	@Autowired
	private IQuartzJobsTaskMapper modifyDemoDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yueny.demo.dynamic.scheduler.job.service.IQuartzJobsTaskService#
	 * queryAll()
	 */
	@Override
	public List<QuartzJobsTaskBo> queryAll() {
		final List<QuartzJobsTaskEntry> entrys = modifyDemoDao.selectAll();

		if (CollectionUtils.isEmpty(entrys)) {
			return Collections.emptyList();
		}

		return map(entrys, QuartzJobsTaskBo.class);
	}

}
