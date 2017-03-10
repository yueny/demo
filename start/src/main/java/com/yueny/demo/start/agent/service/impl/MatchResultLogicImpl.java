package com.yueny.demo.start.agent.service.impl;

import org.springframework.stereotype.Service;

import com.yueny.demo.start.agent.service.MatchResultLogic;

import lombok.extern.slf4j.Slf4j;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年3月10日 下午2:36:44
 *
 */
@Service
@Slf4j
public class MatchResultLogicImpl implements MatchResultLogic {
	@Override
	public boolean match(final String id) {
		log.info("资产匹配match：{}。", id);
		return true;
	}

}
