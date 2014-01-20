/**
 * 
 */
package com.rivetlogic.quartz.sort;

import java.text.ParseException;
import java.util.Date;

import com.liferay.portal.kernel.util.OrderByComparator;
import com.rivetlogic.quartz.bean.SchedulerJobBean;
import com.rivetlogic.quartz.util.QuartzSchedulerUtil;

/**
 * @author steven.barba
 *
 */
public class NextFireTimeComparator extends OrderByComparator {

	private static final long serialVersionUID = -4403619283278634299L;

	private boolean asc;
	
	public NextFireTimeComparator() {
		this(false);
	}
	
	public NextFireTimeComparator(boolean asc) {
		this.asc = asc;
	}

	@Override
	public int compare(Object arg0, Object arg1) {
		
		boolean value = false;
		SchedulerJobBean jobBean0 = (SchedulerJobBean) arg0;
		SchedulerJobBean jobBean1 = (SchedulerJobBean) arg1;
		
		Date nextFireTime0 = null;
		Date nextFireTime1 = null;
		String nextFire0 = jobBean0.getNextFireTime();
		String nextFire1 = jobBean1.getNextFireTime();
		if(!nextFire0.trim().equals("-") && !nextFire1.trim().equals("-")) {
			try {
				nextFireTime0 = QuartzSchedulerUtil.FORMAT_DATE_TIME.parse(nextFire0);
				nextFireTime1 = QuartzSchedulerUtil.FORMAT_DATE_TIME.parse(nextFire1);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			value = nextFireTime0.after(nextFireTime1);
		} else {
			if(!nextFire0.trim().equals("-")) {
				value = true;
			}
		}
		
		if(asc) {
			return value ? 1 : -1;
		} else {
			return value ? -1 : 1;
		}
	
	}

}
