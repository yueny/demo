package com.yueny.demo.downlatch.async;

import java.util.ArrayList;
import java.util.List;

import com.yueny.demo.downlatch.bo.RecoverResult;

public class PurchasedFailTransRecoverRunner extends AbstractRecoverRunner {

	@Override
	public List<RecoverResult> execute() {
		final List<RecoverResult> lists = new ArrayList<RecoverResult>(getTransIdList().size());

		for (int i = 0; i < getTransIdList().size(); i++) {
			final RecoverResult result = new RecoverResult();
			result.setTransId(getTransIdList().get(i));

			if (i % 2 == 0) {
				result.setReason("OK");
				result.setSucc(true);
			} else {
				result.setReason("FAIL");
				result.setSucc(false);
			}

			lists.add(result);
		}
		return lists;
	}

}
