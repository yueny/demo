package com.yueny.demo.micros.tomcat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yueny.demo.common.example.service.IDataPrecipitationService;
import com.yueny.demo.micros.tomcat.api.request.JobForMatcherRequest;
import com.yueny.rapid.data.resp.pojo.response.NormalResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年2月16日 下午8:23:11
 *
 */
@RestController
@Slf4j
public class DemoController {
	@Autowired
	private IDataPrecipitationService dataPrecipitationService;
	@Value("${app.profile.env}")
	private String env;

	/*
	 * 提供路由信息，负责URL到Controller中的具体函数的映射。
	 */

	@RequestMapping("/")
	public int home() {
		log.info("{} 被访问了~！", env);
		return dataPrecipitationService.queryAll().size();
	}

	@RequestMapping(value = "/post/data", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public NormalResponse<JobForMatcherRequest> matcherData(@RequestBody final JobForMatcherRequest req,
			final BindingResult bindingResult) {
		final NormalResponse<JobForMatcherRequest> reps = new NormalResponse<JobForMatcherRequest>();
		reps.setData(req);

		log.info("/post/data 被访问了~, resp:{}.", reps);
		return reps;
	}

	@RequestMapping(value = "/post/datas", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public NormalResponse<String> matcherDatas(@RequestParam final Integer borrowRound,
			@RequestParam(value = "mask") final String masks, final BindingResult bindingResult) {
		final NormalResponse<String> reps = new NormalResponse<>();
		reps.setData(masks);

		log.info("/post/datas 被访问了~, resp:{}.", reps);
		return reps;
	}

	@RequestMapping(value = "/post/submit", method = RequestMethod.POST)
	public NormalResponse<JobForMatcherRequest> matcherSubmit(@RequestBody final JobForMatcherRequest req,
			final BindingResult bindingResult) {
		final NormalResponse<JobForMatcherRequest> reps = new NormalResponse<JobForMatcherRequest>();
		reps.setData(req);

		log.info("/post/submit 被访问了~, resp:{}.", reps);
		return reps;
	}

}
