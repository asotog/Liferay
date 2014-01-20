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
public class StartTimeComparator extends OrderByComparator {

	private static final long serialVersionUID = -4403619283278634299L;

	private boolean asc;
	
	public StartTimeComparator() {
		this(false);
	}
	
	public StartTimeComparator(boolean asc) {
		this.asc = asc;
	}

	@Override
	public int compare(Object arg0, Object arg1) {
		
		boolean value = false;
		SchedulerJobBean jobBean0 = (SchedulerJobBean) arg0;
		SchedulerJobBean jobBean1 = (SchedulerJobBean) arg1;
		
		Date startTime0 = null;
		Date startTime1 = null;
		String start0 = jobBean0.getStartTime();
		String start1 = jobBean1.getStartTime();
		if(!start0.trim().equals("-") && !start1.trim().equals("-")) {
			try {
				startTime0 = QuartzSchedulerUtil.FORMAT_DATE_TIME.parse(start0);
				startTime1 = QuartzSchedulerUtil.FORMAT_DATE_TIME.parse(start1);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			value = startTime0.after(startTime1);
		} else {
			if(!start0.trim().equals("-")) {
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
