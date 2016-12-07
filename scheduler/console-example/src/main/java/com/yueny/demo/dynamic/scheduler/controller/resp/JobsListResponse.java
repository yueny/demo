package com.yueny.demo.dynamic.scheduler.controller.resp;

import com.yueny.demo.dynamic.scheduler.entry.JobInfo;
import com.yueny.rapid.data.resp.pojo.response.ListResponse;

import lombok.Getter;
import lombok.Setter;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年11月29日 下午9:55:32
 *
 */
@Deprecated
public class JobsListResponse extends ListResponse<JobInfo> {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/**
	 *
	 */
	@Getter
	@Setter
	private int total;
}
