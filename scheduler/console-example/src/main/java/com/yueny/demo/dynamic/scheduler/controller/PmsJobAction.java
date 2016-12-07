// package com.acca.job.spring.action;
//
// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;
//
// import org.quartz.SchedulerException;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Scope;
// import org.springframework.stereotype.Controller;
//
// import com.acca.job.spring.job.JobInfo;
// import com.acca.job.spring.job.ScheduleService;
// import com.opensymphony.xwork2.ActionSupport;
// import com.opensymphony.xwork2.ModelDriven;
// import com.sun.org.apache.bcel.internal.generic.NEW;
//
/// **
// * 作业管理即监控
// public class PmsJobAction extends ActionSupport implements
// ModelDriven<JobInfo> {
// private Map jsonMap = new HashMap();
//
// private JobInfo jobInfo=new JobInfo();

// /**
// * 暂停作业
// *
// * @Title: pause
// * @Description: TODO
// * @return
// * @return: String
// */
// public String pause() {
// try {
// scheduleService.pauseTrigger(jobInfo.getTrigger_name(),
// jobInfo.getTrigger_group());
// } catch (Exception e) {
// jsonMap.put("flag", false);
// jsonMap.put("msg", "暂停作业时发生异常");
// }
// jsonMap.put("flag", true);
// jsonMap.put("msg", "暂停作业成功");
// return "pause";
//
// }
//
// /**
// * 重启作业
// *
// * @Title: restart
// * @Description: TODO
// * @return
// * @return: String
// */
// public String restart() {
// try {
// scheduleService.resumeTrigger(jobInfo.getTrigger_name(),
// jobInfo.getTrigger_group());
// } catch (Exception e) {
// jsonMap.put("flag", false);
// jsonMap.put("msg", "重启作业时发生异常");
// }
// jsonMap.put("flag", true);
// jsonMap.put("msg", "重启作业成功");
// return "restart";
// }
//
// public String executeNow() {
// try {
// scheduleService.triggerJob(jobInfo.getJob_name(), jobInfo.getJob_group());
// } catch (SchedulerException e) {
// jsonMap.put("flag", false);
// jsonMap.put("msg", "执行作业时发生异常");
// }
//
// jsonMap.put("flag", true);
// jsonMap.put("msg", "执行作业成功");
//
// return "executeNow";
// }
//
// public Map getJsonMap() {
// return jsonMap;
// }
//
// public void setJsonMap(Map jsonMap) {
// this.jsonMap = jsonMap;
// }
//
// public JobInfo getModel() {
// return jobInfo;
// }
//
// }
