package com.yueny.demo.dubbo.api;

import com.yueny.rapid.data.resp.pojo.response.NormalResponse;

/**
 * Created by yueny on 2018/7/18 0018.
 */
public interface IGreetingService {
    /**
     * 获取 greeting 信息
     * @param greetingName
     * @return
     */
    NormalResponse<String> getGreeting(String greetingName);

}
