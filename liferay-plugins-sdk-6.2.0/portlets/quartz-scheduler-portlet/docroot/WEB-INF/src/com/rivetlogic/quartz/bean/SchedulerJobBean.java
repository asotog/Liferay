/**
 * 
 */
package com.rivetlogic.quartz.bean;

import java.io.Serializable;

/**
 * @author steven.barba
 *
 */
public interface SchedulerJobBean extends Serializable {
	public String getJobName();
	public void setJobName(String jobName);

	public String getGroupName();
	public void setGroupName(String groupName);

	public String getTriggerState();
	public void setTriggerState(String triggerState);

	public String getStartTime();
	public void setStartTime(String startTime);

	public String getEndTime();
	public void setEndTime(String endTime);
	
	public String getPreviousFireTime();
	public void setPreviousFireTime(String previousFireTime);

	public String getNextFireTime();
	public void setNextFireTime(String nextFireTime);

	public String getStorageType();
	public void setStorageType(String storageType);
	
	public String getShortName();
	public String getShortGroup();
}
