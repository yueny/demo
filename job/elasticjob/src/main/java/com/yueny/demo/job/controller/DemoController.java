package com.yueny.demo.job.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yueny.demo.common.example.bo.ModifyDemoBo;
import com.yueny.demo.common.example.service.IDataPrecipitationService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年2月16日 下午8:23:11
 *
 */
@Controller
@Slf4j
public class DemoController {
	@Autowired
	private IDataPrecipitationService dataPrecipitationService;

	/**
	 *
	 */
	@RequestMapping(value = { "/", "welcome" }, method = RequestMethod.GET)
	@ResponseBody
	public List<ModifyDemoBo> bar() {
		try {
			return dataPrecipitationService.queryAll();
		} catch (final Exception e) {
			log.error("exception:", e);
		}
		return null;
	}

	@RequestMapping(value = "/healthy", method = RequestMethod.GET)
	@ResponseBody
	public String healthy() {
		return "OK";
	}

}
