package com.yueny.demo.micros.boot.spring.configure.controller;

import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yueny.demo.micros.boot.spring.configure.api.request.JobForMatcherRequest;
import com.yueny.rapid.data.resp.pojo.response.NormalResponse;
import com.yueny.rapid.lang.json.JsonUtil;

/*
 * @Controller和@ResponseBody的合集
 * @ResponseBody:该注解修饰的函数，会将结果直接填充到HTTP的响应体中，一般用于构建RESTful的api，该注解一般会配合@RequestMapping一起使用。
 */
@RestController
public class SampleController extends BaseController {
	@RequestMapping("/service/{key}")
	public NormalResponse<String> key(@PathVariable final String key) {
		logger.info("{}/{} 被访问了~！", key, getEnv());

		try {
			TimeUnit.MILLISECONDS.sleep(1000);
		} catch (final InterruptedException e) {
			System.out.println("sleep Interrupted!");
		}

		final NormalResponse<String> reps = new NormalResponse<>();
		reps.setData(key + "被访问了~！");
		return reps;
	}

	@RequestMapping(value = "/post/data", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public NormalResponse<Boolean> matcherData(@RequestBody @Valid final JobForMatcherRequest req,
			final BindingResult bindingResult) {
		final NormalResponse<Boolean> reps = new NormalResponse<Boolean>();
		reps.setData(true);

		respWrite(JsonUtil.toJson(reps));

		logger.info("/post/data 被访问了~, resp:{}.", reps);
		return reps;
	}

	@RequestMapping(value = "/post/submit", method = RequestMethod.POST)
	public NormalResponse<JobForMatcherRequest> matcherSubmit(@RequestBody @Valid final JobForMatcherRequest req,
			final BindingResult bindingResult) {
		final NormalResponse<JobForMatcherRequest> reps = new NormalResponse<JobForMatcherRequest>();
		reps.setData(req);

		logger.info("/post/submit 被访问了~, resp:{}.", reps);
		return reps;
	}

}
