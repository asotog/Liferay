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
public class StateComparator extends OrderByComparator {

	private static final long serialVersionUID = -284650427609551461L;

	private boolean asc;
	
	public StateComparator() {
		this(false);
	}
	
	public StateComparator(boolean asc) {
		this.asc = asc;
	}

	@Override
	public int compare(Object arg0, Object arg1) {
		SchedulerJobBean jobBean0 = (SchedulerJobBean) arg0;
		SchedulerJobBean jobBean1 = (SchedulerJobBean) arg1;
		
		int value =jobBean0.getTriggerState().toLowerCase().compareTo(jobBean1.getTriggerState().toLowerCase());
		
		if(asc) {
			return value;
		} else {
			return -value;
		}
	}

}
