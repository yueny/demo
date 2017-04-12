package com.yueny.demo.downlatch.async;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yueny.demo.downlatch.bo.RecoverResult;
import com.yueny.demo.downlatch.holder.TransResultHolder;

import lombok.Getter;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年3月22日 下午1:15:51
 *
 */
public abstract class AbstractRecoverRunner implements Runnable {
	private static Logger logger = LoggerFactory.getLogger(AbstractRecoverRunner.class);

	private CountDownLatch latch;

	private TransResultHolder resultHolder;
	@Getter
	private List<String> transIdList;

	public abstract List<RecoverResult> execute();

	public void init(final List<String> transIdList, final TransResultHolder resultHolder,
			final CountDownLatch countDownLatch) {
		this.latch = countDownLatch;
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
			results = proAllFail(e.getMessage());
		} finally {
			logger.info("发现有一个倒计时门闩关闭了...");
			latch.countDown();
		}

		resultHolder.addResults(results);
	}

	private List<RecoverResult> proAllFail(final String errorMsg) {
		final List<RecoverResult> results = new ArrayList<RecoverResult>();
		for (final String transId : getTransIdList()) {
			final RecoverResult result = new RecoverResult();
			result.setTransId(transId);
			result.setReason(errorMsg);
			result.setSucc(false);
			results.add(result);
		}
		return results;
	}

}
