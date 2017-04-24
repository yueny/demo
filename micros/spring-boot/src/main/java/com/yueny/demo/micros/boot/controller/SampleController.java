package com.yueny.demo.micros.boot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yueny.demo.common.example.bo.ModifyDemoBo;
import com.yueny.demo.common.example.service.IDataPrecipitationService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class SampleController {
	@Autowired
	private IDataPrecipitationService dataPrecipitationService;

	@RequestMapping("/")
	// @GetMapping("/")
	@ResponseBody
	public List<ModifyDemoBo> home() {
		log.info("被访问了~！");
		return dataPrecipitationService.queryAll();
	}
}
