package com.yueny.demo.downlatch.bo;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年3月22日 下午1:30:59
 *
 */
public class RecoverResult {
	private String reason;
	private boolean succ;
	private String transId;

	public String getReason() {
		return reason;
	}

	public String getTransId() {
		return transId;
	}

	public boolean isSucc() {
		return succ;
	}

	public void setReason(final String reason) {
		this.reason = reason;
	}

	public void setSucc(final boolean succ) {
		this.succ = succ;
	}

	public void setTransId(final String transId) {
		this.transId = transId;
	}

}
