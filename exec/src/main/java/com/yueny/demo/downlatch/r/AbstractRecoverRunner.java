package com.yueny.demo.downlatch.r;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yueny.demo.downlatch.TransResultHolder;
import com.yueny.demo.downlatch.bo.RecoverResult;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年3月22日 下午1:15:51
 *
 */
public abstract class AbstractRecoverRunner implements Runnable {
	private static Logger logger = LoggerFactory
			.getLogger(AbstractRecoverRunner.class);

	protected CountDownLatch countDownLatch;

	protected List<String> transIdList;

	private TransResultHolder resultHolder;

	public void init(final List<String> transIdList,
			final TransResultHolder resultHolder,
			final CountDownLatch countDownLatch) {
		this.countDownLatch = countDownLatch;
		this.transIdList = transIdList;
		this.resultHolder = resultHolder;
	}

	/**
	 * @see java.util.concurrent.Callable#run()
	 */
	@Override
	public void run() {
		List<RecoverResult> results = null;
		try {
			results = this.execute();
		} catch (final Exception e) {
			logger.error("Exception: ", e);
			results = proFail(e.getMessage());
		} finally {
			countDownLatch.countDown();
		}
		resultHolder.addResults(results);
	}

	private List<RecoverResult> proFail(final String errorMsg) {
		final List<RecoverResult> results = new ArrayList<RecoverResult>();
		for (final String transId : transIdList) {
			final RecoverResult result = new RecoverResult();
			result.setTransId(transId);
			result.setReason(errorMsg);
			result.setSucc(false);
			results.add(result);
		}
		return results;
	}

	public abstract List<RecoverResult> execute();

}
