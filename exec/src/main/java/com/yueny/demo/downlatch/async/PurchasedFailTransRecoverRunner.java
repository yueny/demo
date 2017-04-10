package com.yueny.demo.downlatch.async;

import java.util.List;

import com.google.common.collect.Lists;
import com.yueny.demo.downlatch.bo.RecoverResult;

public class PurchasedFailTransRecoverRunner extends AbstractRecoverRunner {

	@Override
	public List<RecoverResult> execute() {
		final List<RecoverResult> lists = Lists.newArrayListWithCapacity(10);
		for (int i = 0; i < 10; i++) {
			final RecoverResult r = new RecoverResult();
			r.setTransId(String.valueOf(i * i));
			r.setReason("OK");
			r.setSucc(i % 2 == 0);

			lists.add(r);
		}
		return lists;
	}

}
