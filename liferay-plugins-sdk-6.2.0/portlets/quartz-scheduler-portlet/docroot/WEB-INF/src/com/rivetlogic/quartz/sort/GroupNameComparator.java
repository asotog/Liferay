/**
 * 
 */
package com.rivetlogic.quartz.sort;

import com.liferay.portal.kernel.util.OrderByComparator;
import com.rivetlogic.quartz.bean.SchedulerJobBean;

/**
 * @author steven.barba
 *
 */
public class GroupNameComparator extends OrderByComparator {

	private static final long serialVersionUID = -6611037824577956159L;

	private boolean asc;
	
	public GroupNameComparator () {
		this(false);
	}
	
	public GroupNameComparator(boolean asc) {
		this.asc = asc;
	}
	
	@Override
	public int compare(Object arg0, Object arg1) {
		SchedulerJobBean jobBean0 = (SchedulerJobBean) arg0;
		SchedulerJobBean jobBean1 = (SchedulerJobBean) arg1;
		
		int value =jobBean0.getShortGroup().toLowerCase().compareTo(jobBean1.getShortGroup().toLowerCase());
		
		if(asc) {
			return value;
		} else {
			return -value;
		}
	}

}
