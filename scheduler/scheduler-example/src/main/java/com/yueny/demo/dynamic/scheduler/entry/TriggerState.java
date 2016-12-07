package com.yueny.demo.dynamic.scheduler.entry;

/**
 * 触发器状态
 * 
 * @ClassName: TriggerState
 * @Description: TODO
 * @author: zhouhua
 * @date: 2016年3月15日 下午5:06:18
 */
public interface TriggerState {

	String STATE_ACQUIRED = "ACQUIRED";

	String STATE_BLOCKED = "BLOCKED";

	String STATE_COMPLETE = "COMPLETE";

	String STATE_ERROR = "ERROR";

	String STATE_EXECUTING = "EXECUTING";

	String STATE_PAUSED = "PAUSED";

	// STATES
	String STATE_WAITING = "WAITING";
}
