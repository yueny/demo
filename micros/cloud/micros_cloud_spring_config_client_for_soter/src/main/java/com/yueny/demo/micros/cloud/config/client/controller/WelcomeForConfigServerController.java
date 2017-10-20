package com.yueny.demo.micros.cloud.config.client.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Description;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yueny.demo.micros.cloud.config.client.model.Fx;

@RestController
@Description("欢迎页")
public class WelcomeForConfigServerController {
	@Value("${info.profile}")
	private String profile;
	@Value("${cloud.msg.success}")
	private String successMsg;

	@GetMapping(value = "/")
	public String home(final Model model) {
		System.out.println("profile: " + profile);

		final List<Fx> fxs = new ArrayList<>();
		fxs.add(Fx.builder().userName("袁洋").build());
		model.addAttribute("list", fxs);

		model.addAttribute("profile", profile);
		model.addAttribute("successMsg", successMsg);

		return "index";
	}

}
