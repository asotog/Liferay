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
public class PreviousFireTimeComparator extends OrderByComparator {

	private static final long serialVersionUID = -4403619283278634299L;

	private boolean asc;
	
	public PreviousFireTimeComparator() {
		this(false);
	}
	
	public PreviousFireTimeComparator(boolean asc) {
		this.asc = asc;
	}

	@Override
	public int compare(Object arg0, Object arg1) {
		
		boolean value = false;
		SchedulerJobBean jobBean0 = (SchedulerJobBean) arg0;
		SchedulerJobBean jobBean1 = (SchedulerJobBean) arg1;
		
		Date previousFireTime0 = null;
		Date previousFireTime1 = null;
		String previousFire0 = jobBean0.getPreviousFireTime();
		String previousFire1 = jobBean1.getPreviousFireTime();
		if(!previousFire0.trim().equals("-") && !previousFire1.trim().equals("-")) {
			try {
				previousFireTime0 = QuartzSchedulerUtil.FORMAT_DATE_TIME.parse(previousFire0);
				previousFireTime1 = QuartzSchedulerUtil.FORMAT_DATE_TIME.parse(previousFire1);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			value = previousFireTime0.after(previousFireTime1);
		} else {
			if(!previousFire0.trim().equals("-")) {
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
