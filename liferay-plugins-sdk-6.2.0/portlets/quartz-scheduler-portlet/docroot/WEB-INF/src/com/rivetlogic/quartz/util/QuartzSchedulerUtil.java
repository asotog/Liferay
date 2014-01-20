/**
 * 
 */
package com.rivetlogic.quartz.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.portlet.PortletRequest;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.scheduler.CronTrigger;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelperUtil;
import com.liferay.portal.kernel.scheduler.SchedulerException;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.TriggerState;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerResponse;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.rivetlogic.quartz.bean.SchedulerJobBean;
import com.rivetlogic.quartz.bean.impl.SchedulerJobBeanImpl;
import com.rivetlogic.quartz.sort.EndTimeComparator;
import com.rivetlogic.quartz.sort.GroupNameComparator;
import com.rivetlogic.quartz.sort.JobNameComparator;
import com.rivetlogic.quartz.sort.NextFireTimeComparator;
import com.rivetlogic.quartz.sort.PreviousFireTimeComparator;
import com.rivetlogic.quartz.sort.StartTimeComparator;
import com.rivetlogic.quartz.sort.StateComparator;
import com.rivetlogic.quartz.sort.StorageTypeComparator;

/**
 * @author steven.barba
 *
 */
public class QuartzSchedulerUtil {
	
	public static final SimpleDateFormat FORMAT_DATE_TIME = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
	private static final SimpleDateFormat FORMAT_DATE = new SimpleDateFormat("MM/dd/yyyy");

	public static JSONObject getJSONSchedulerJob(SchedulerResponse schedulerResponse) {
		JSONObject jsonSchedulerJob = JSONFactoryUtil.createJSONObject();

		TriggerState triggerState = SchedulerEngineHelperUtil.getJobState(schedulerResponse);
		Date startTime = SchedulerEngineHelperUtil.getStartTime(schedulerResponse);
		Date endTime = SchedulerEngineHelperUtil.getEndTime(schedulerResponse);
		Date previousFireTime = SchedulerEngineHelperUtil.getPreviousFireTime(schedulerResponse);
		Date nextFireTime = SchedulerEngineHelperUtil.getNextFireTime(schedulerResponse);
		StorageType storageType = schedulerResponse.getStorageType();

		jsonSchedulerJob.put("jobName", schedulerResponse.getJobName());
		jsonSchedulerJob.put("triggerState", (triggerState == null ? "-" : triggerState.toString()));
		jsonSchedulerJob.put("startTime", (startTime == null ? "-" : FORMAT_DATE_TIME.format(startTime)));
		jsonSchedulerJob.put("endTime", (endTime == null ? "-" : FORMAT_DATE_TIME.format(endTime)));
		jsonSchedulerJob.put("previousFireTime", (previousFireTime == null ? "-" : FORMAT_DATE_TIME.format(previousFireTime)));
		jsonSchedulerJob.put("nextFireTime", (nextFireTime == null ? "-" : FORMAT_DATE_TIME.format(nextFireTime)));
		jsonSchedulerJob.put("storageType", (storageType == null ? "-" : storageType.toString()));

		return jsonSchedulerJob;
	}

	public static List<JSONObject> getJSONSchedulerJobsList(List<SchedulerResponse> schedulerResponses) {

		List<JSONObject> jsonSchedulerJobsList = new ArrayList<JSONObject>();

		for (SchedulerResponse scheduler : schedulerResponses) {
			jsonSchedulerJobsList.add(getJSONSchedulerJob(scheduler));
		}
		return jsonSchedulerJobsList;
	}

	public static SchedulerJobBean getSchedulerJob(SchedulerResponse schedulerResponse) {
		TriggerState triggerState = SchedulerEngineHelperUtil.getJobState(schedulerResponse);
		Date startTime = SchedulerEngineHelperUtil.getStartTime(schedulerResponse);
		Date endTime = SchedulerEngineHelperUtil.getEndTime(schedulerResponse);
		Date previousFireTime = SchedulerEngineHelperUtil.getPreviousFireTime(schedulerResponse);
		Date nextFireTime = SchedulerEngineHelperUtil.getNextFireTime(schedulerResponse);
		StorageType storageType = schedulerResponse.getStorageType();

		SchedulerJobBean schedulerJobBean = new SchedulerJobBeanImpl();
		schedulerJobBean.setJobName(schedulerResponse.getJobName());
		schedulerJobBean.setGroupName(schedulerResponse.getGroupName());
		schedulerJobBean.setTriggerState(triggerState == null ? "-" : triggerState.toString());
		schedulerJobBean.setStartTime(startTime == null ? "-" : FORMAT_DATE_TIME.format(startTime));
		schedulerJobBean.setEndTime(endTime == null ? "-" : FORMAT_DATE_TIME.format(endTime));
		schedulerJobBean.setPreviousFireTime(previousFireTime == null ? "-" : FORMAT_DATE_TIME.format(previousFireTime));
		schedulerJobBean.setNextFireTime(nextFireTime == null ? "-" : FORMAT_DATE_TIME.format(nextFireTime));
		schedulerJobBean.setStorageType(storageType == null ? "-" : storageType.toString().trim());
		return schedulerJobBean;
	}

	public static List<SchedulerJobBean> getSchedulerJobsList(List<SchedulerResponse> schedulerResponses) {
		List<SchedulerJobBean> schedulerJobBeans = new ArrayList<SchedulerJobBean>();
		for (SchedulerResponse scheduler : schedulerResponses) {
			schedulerJobBeans.add(getSchedulerJob(scheduler));
		}
		return schedulerJobBeans;
	}

	public static void scheduleJobServiceAction(String action) throws SchedulerException {
		//Log Info messages
		log(LOGLEVEL.INFO, action + LOG_ACTION_MSG);
		
		if (action.equals(START)) {
			SchedulerEngineHelperUtil.start();
		} else if (action.equals(SHUTDOWN)) {
			SchedulerEngineHelperUtil.shutdown();
		}
	}

	public static void scheduleJobAction(UploadPortletRequest uploadRequest, String action, int index) throws SchedulerException, ParseException {
		String jobName;
		String groupName;
		String storageTypeText;
		StorageType storageType;
		
		//Log Info messages
		log(LOGLEVEL.INFO, action + LOG_ACTION_MSG);
		
		if(!action.equals(REFRESH)) {
			if(index >= 0) {
				for(int i=0; i<=index; i++) {
					jobName = (String) uploadRequest.getParameter(JOB_NAME + i);
					groupName = (String) uploadRequest.getParameter(GROUP_NAME + i);
					storageTypeText = (String) uploadRequest.getParameter(STORAGE_TYPE + i);
					storageType = StorageType.valueOf(storageTypeText);
					
					if(action.equals(PAUSE)) {
						SchedulerEngineHelperUtil.pause(jobName, groupName, storageType);
					} else if(action.equals(RESUME)) {
						SchedulerEngineHelperUtil.resume(jobName, groupName, storageType);
					} else if (action.equals(UNSCHEDULE)) {
						SchedulerEngineHelperUtil.unschedule(jobName, groupName, storageType);
					} else if (action.equals(DELETE)) {
						SchedulerEngineHelperUtil.delete(jobName, groupName, storageType);
					} else if (action.equals(UPDATE)) {
						String startDate = (String) uploadRequest.getParameter(START_DATE);
						String endDate = (String) uploadRequest.getParameter(END_DATE);
						String cronExpression = (String) uploadRequest.getParameter(CRON_EXPRESSION);
						Trigger trigger = new CronTrigger(jobName, groupName, FORMAT_DATE.parse(startDate), FORMAT_DATE.parse(endDate), cronExpression);
						SchedulerEngineHelperUtil.update(trigger, storageType);
					}
				}
			}
		}
	}

	public static void getSchedulerJobs(PortletRequest request)
			throws SchedulerException {
		// Scheduler List
		List<SchedulerResponse> schedulerJobs = SchedulerEngineHelperUtil.getScheduledJobs();
		List<SchedulerJobBean> schedulerJobBeans = getSchedulerJobsList(schedulerJobs);
		request.setAttribute("schedulerJobsList", schedulerJobBeans);
		request.setAttribute("count", schedulerJobBeans.size());
	}
	
	public static List<SchedulerJobBean> subList(List<SchedulerJobBean> list, Integer start, Integer end){
		if(list != null) {
			return ListUtil.subList(list, start, end);
		} else {
			return new ArrayList<SchedulerJobBean>();
		}
	}
	
	public static OrderByComparator getSchedulerJobCoparator(String orderByCol, String orderByType) {
		boolean orderByAsc = false;
		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}
		
		OrderByComparator orderByComparator = null;
		
		if (orderByCol.equalsIgnoreCase("shortName")) {
			orderByComparator = new JobNameComparator(orderByAsc);
		} else if (orderByCol.equalsIgnoreCase("shortGroup")) {
			orderByComparator = new GroupNameComparator(orderByAsc);
		} else if (orderByCol.equalsIgnoreCase("state")) {
			orderByComparator = new StateComparator(orderByAsc);
		} else if (orderByCol.equalsIgnoreCase("startTime")) {
			orderByComparator = new StartTimeComparator(orderByAsc);
		} else if (orderByCol.equalsIgnoreCase("endTime")) {
			orderByComparator = new EndTimeComparator(orderByAsc);
		} else if (orderByCol.equalsIgnoreCase("previousFireTime")) {
			orderByComparator = new PreviousFireTimeComparator(orderByAsc);
		} else if (orderByCol.equalsIgnoreCase("nextFireTime")) {
			orderByComparator = new NextFireTimeComparator(orderByAsc);
		} else if (orderByCol.equalsIgnoreCase("storageType")) {
			orderByComparator = new StorageTypeComparator(orderByAsc);
		}
		
		return orderByComparator;
	}

	public static enum LOGLEVEL {
		DEBUG, INFO, WARN
	}

	/**
	 * A utility method to log info/debug messages
	 * 
	 * @param logLevel
	 * @param message
	 */
	private static void log(LOGLEVEL logLevel, String message) {

		switch (logLevel) {
		case DEBUG:
			if (_log.isDebugEnabled()) {
				_log.debug(message);
			}
			break;
		case INFO:
			if (_log.isInfoEnabled()) {
				_log.info(message);
			}
			break;
		default:
			if (_log.isInfoEnabled()) {
				_log.info(message);
			}
			break;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(QuartzSchedulerUtil.class);
	
	private static final String START = "start";
	private static final String SHUTDOWN = "shutdown";
	private static final String PAUSE = "pause";
	private static final String RESUME = "resume";
	private static final String UNSCHEDULE = "unschedule";
	private static final String UPDATE = "update";
	private static final String DELETE = "delete";
	private static final String REFRESH = "refresh";

	private static final String JOB_NAME = "jobName_";
	private static final String GROUP_NAME = "groupName_";
	private static final String STORAGE_TYPE = "storageType_";
	private static final String START_DATE = "startDate";
	private static final String END_DATE = "endDate";
	private static final String CRON_EXPRESSION = "cronExpression";
	
	private static final String LOG_ACTION_MSG = " action will be processed.";
}
