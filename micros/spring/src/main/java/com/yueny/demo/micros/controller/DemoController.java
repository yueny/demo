package com.yueny.demo.micros.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yueny.demo.common.example.bo.ModifyDemoBo;
import com.yueny.demo.common.example.service.IDataPrecipitationService;

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

	/**
	 *
	 */
	@RequestMapping(value = "/")
	@ResponseBody
	public List<ModifyDemoBo> bar() {
		// return "Hello World!";
		return dataPrecipitationService.queryAll();
	}

}
