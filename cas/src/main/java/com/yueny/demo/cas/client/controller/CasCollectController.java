package com.yueny.demo.cas.client.controller;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Description;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yueny.demo.api.CollectConst;
import com.yueny.demo.api.ExecutorBean;
import com.yueny.demo.api.resp.ResourceGroup;
import com.yueny.demo.util.AnnotationManagerUtil;
import com.yueny.rapid.data.resp.pojo.response.NormalResponse;

import lombok.extern.slf4j.Slf4j;

@RestController
@Description("CAS系统信息搜集服务")
@Slf4j
public class CasCollectController {
	/**
	 * 系统标识
	 */
	@Value("${cas.client.system:0}")
	private String casClientSystem;

	/**
	 * 获取资源地址 后期添加公私yao
	 */
	@RequestMapping(value = CollectConst.CAS_COLLECT_RESOURCE)
	public NormalResponse<ResourceGroup> collect() {
		log.info("CAS系统信息搜集服务~");
		final NormalResponse<ResourceGroup> resp = new NormalResponse<>();

		// 获取资源
		Map<String, ExecutorBean<? extends Annotation>> mmap = null;
		try {
			// 获取资源
			mmap = AnnotationManagerUtil.getMappingMethod("com.yueny.demo.cas.client.controller");
		} catch (final Exception e) {
			log.error("系统异常：", e);
			resp.setCode("60009901");
			resp.setMessage("资源获取失败！");
			return resp;
		}

		try {
			final ResourceGroup group = new ResourceGroup(mmap);
			group.setCasClientSystem(casClientSystem);
			resp.setData(group);
		} catch (final Exception e) {
			log.error("系统异常：", e);
			resp.setCode("60009902");
			resp.setMessage("系统[" + casClientSystem + "]忙！");
		}

		return resp;
	}

}
