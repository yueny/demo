package com.yueny.demo.dubbo.api;

import com.yueny.demo.dubbo.api.IGreetingService;
import com.yueny.rapid.data.resp.pojo.response.NormalResponse;

/**
 * Created by yueny on 2018/7/18 0018.
 */
public class GreetingService implements IGreetingService{
	@Override
	public NormalResponse<String> getGreeting(String greetingName) {
		NormalResponse<String> resp = new NormalResponse<>();
		resp.setData(greetingName);

		return resp;
	}

}
