package com.yueny.demo.micros.boot.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yueny.demo.common.example.service.IDataPrecipitationService;
import com.yueny.demo.micros.boot.api.request.JobForMatcherRequest;
import com.yueny.rapid.data.resp.pojo.response.NormalResponse;
import com.yueny.rapid.lang.json.JsonUtil;

import io.reactivex.Observable;
import lombok.extern.slf4j.Slf4j;

/*
 * @Controller和@ResponseBody的合集
 * @ResponseBody:该注解修饰的函数，会将结果直接填充到HTTP的响应体中，一般用于构建RESTful的api，该注解一般会配合@RequestMapping一起使用。
 */
@RestController
@Slf4j
public class SampleController extends BaseController {
	@Autowired
	private IDataPrecipitationService dataPrecipitationService;

	/*
	 * 提供路由信息，负责URL到Controller中的具体函数的映射。
	 */
	@RequestMapping("/")
	// @GetMapping("/")
	public int home() {
		log.info("{} 被访问了~！", getEnv());
		return dataPrecipitationService.queryAll().size();
	}

	@RequestMapping(value = "/post/data", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public NormalResponse<Boolean> matcherData(@RequestBody @Valid final JobForMatcherRequest req,
			final BindingResult bindingResult) {
		final NormalResponse<Boolean> reps = new NormalResponse<Boolean>();
		reps.setData(true);

		respWrite(JsonUtil.toJson(reps));

		log.info("/post/data 被访问了~, resp:{}.", reps);
		return reps;
	}

	@RequestMapping(value = "/post/submit", method = RequestMethod.POST)
	public NormalResponse<JobForMatcherRequest> matcherSubmit(@RequestBody @Valid final JobForMatcherRequest req,
			final BindingResult bindingResult) {
		final NormalResponse<JobForMatcherRequest> reps = new NormalResponse<JobForMatcherRequest>();
		reps.setData(req);

		log.info("/post/submit 被访问了~, resp:{}.", reps);
		return reps;
	}

	private void he() {
		final Observable<String> observer = Observable.just("Hello");
	}
}
