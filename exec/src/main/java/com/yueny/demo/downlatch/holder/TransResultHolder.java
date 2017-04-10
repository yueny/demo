package com.yueny.demo.downlatch.holder;

import java.util.List;
import java.util.Vector;

import org.apache.commons.collections4.CollectionUtils;

import com.yueny.demo.downlatch.bo.RecoverResult;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年3月22日 下午1:15:25
 *
 */
public class TransResultHolder {
	private final List<RecoverResult> failList = new Vector<RecoverResult>();

	private Integer recoverCount = 0;

	private final List<String> succList = new Vector<String>();

	public synchronized void addResults(final List<RecoverResult> resultList) {
		if (!CollectionUtils.isEmpty(resultList)) {
			recoverCount += resultList.size();
			for (final RecoverResult recoverResult : resultList) {
				if (recoverResult.isSucc()) {
					succList.add(recoverResult.getTransId());
				} else {
					failList.add(recoverResult);
				}
			}
		}
	}

	public synchronized List<RecoverResult> getFailList() {
		return failList;
	}

	public synchronized Integer getRecoverCount() {
		return recoverCount;
	}

	public synchronized List<String> getSuccList() {
		return succList;
	}

}
