/**
 * 
 */
package com.rivetlogic.quartz.bean.impl;

import com.rivetlogic.quartz.bean.SchedulerJobBean;

/**
 * @author steven.barba
 *
 */
public class SchedulerJobBeanImpl implements SchedulerJobBean {
	private static final long serialVersionUID = -8744907046781772258L;
	
	protected String jobName;
	protected String groupName;
	protected String triggerState;
	protected String startTime;
	protected String endTime;
	protected String previousFireTime;
	protected String nextFireTime;
	protected String storageType;
	
	/**
	 * @return the jobName
	 */
	public String getJobName() {
		return jobName;
	}
	/**
	 * @param jobName the jobName to set
	 */
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}
	/**
	 * @param groupName the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	/**
	 * @return the triggerState
	 */
	public String getTriggerState() {
		return triggerState;
	}
	/**
	 * @param triggerState the triggerState to set
	 */
	public void setTriggerState(String triggerState) {
		this.triggerState = triggerState;
	}
	/**
	 * @return the startTime
	 */
	public String getStartTime() {
		return startTime;
	}
	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	/**
	 * @return the endTime
	 */
	public String getEndTime() {
		return endTime;
	}
	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	/**
	 * @return the previousFireTime
	 */
	public String getPreviousFireTime() {
		return previousFireTime;
	}
	/**
	 * @param previousFireTime the previousFireTime to set
	 */
	public void setPreviousFireTime(String previousFireTime) {
		this.previousFireTime = previousFireTime;
	}
	/**
	 * @return the nextFireTime
	 */
	public String getNextFireTime() {
		return nextFireTime;
	}
	/**
	 * @param nextFireTime the nextFireTime to set
	 */
	public void setNextFireTime(String nextFireTime) {
		this.nextFireTime = nextFireTime;
	}
	/**
	 * @return the storageType
	 */
	public String getStorageType() {
		return storageType;
	}
	/**
	 * @param storageType the storageType to set
	 */
	public void setStorageType(String storageType) {
		this.storageType = storageType;
	}
	
	public String getShortName() {
		if(jobName != null && !jobName.isEmpty()) {
			return jobName.substring(jobName.lastIndexOf(".")).substring(1);
		} else {
			return null;
		}
	}
	
	public String getShortGroup() {
		if(groupName != null && !groupName.isEmpty()) {
			return groupName.substring(groupName.lastIndexOf(".")).substring(1);
		} else {
			return null;
		}
	}
}
