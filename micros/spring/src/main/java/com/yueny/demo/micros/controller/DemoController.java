package com.yueny.demo.micros.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yueny.demo.micros.example.bo.ModifyDemoBo;
import com.yueny.demo.micros.example.service.IDataPrecipitationService;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年2月16日 下午8:23:11
 *
 */
@Controller
public class DemoController {
	@Autowired
	private IDataPrecipitationService dataPrecipitationService;

	/**
	 *
	 */
	@RequestMapping(value = "/demo/bar", method = RequestMethod.GET)
	@ResponseBody
	public List<ModifyDemoBo> bar() {
		return dataPrecipitationService.queryAll();
	}

}
