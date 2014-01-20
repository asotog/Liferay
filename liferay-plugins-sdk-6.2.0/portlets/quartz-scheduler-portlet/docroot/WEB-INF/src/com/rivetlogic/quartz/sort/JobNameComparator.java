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
public class JobNameComparator extends OrderByComparator {

	private static final long serialVersionUID = 5419351872248414916L;
	
	private boolean asc;
	
	public JobNameComparator() {
		this(false);
	}
	
	public JobNameComparator(boolean asc) {
		this.asc = asc;
	}

	@Override
	public int compare(Object arg0, Object arg1) {
		SchedulerJobBean jobBean0 = (SchedulerJobBean) arg0;
		SchedulerJobBean jobBean1 = (SchedulerJobBean) arg1;
		
		int value =jobBean0.getShortName().toLowerCase().compareTo(jobBean1.getShortName().toLowerCase());
		
		if(asc) {
			return value;
		} else {
			return -value;
		}
	}

}
