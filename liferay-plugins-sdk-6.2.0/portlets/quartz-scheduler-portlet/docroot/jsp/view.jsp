<%--
    /*
    * Copyright (C) 2005-2014 Rivet Logic Corporation.
    *
    * This program is free software; you can redistribute it and/or
    * modify it under the terms of the GNU General Public License
    * as published by the Free Software Foundation; version 2
    * of the License.
    *
    * This program is distributed in the hope that it will be useful,
    * but WITHOUT ANY WARRANTY; without even the implied warranty of
    * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
    * GNU General Public License for more details.
    *
    * You should have received a copy of the GNU General Public License
    * along with this program; if not, write to the Free Software
    * Foundation, Inc., 51 Franklin Street, Fifth Floor,
    * Boston, MA 02110-1301, USA.
    */
--%>
<%@page import="java.util.Collections"%>
<%@page import="com.liferay.portal.kernel.util.OrderByComparator"%>
<%@page import="com.rivetlogic.quartz.bean.SchedulerJobBean"%>
<%@page import="java.util.List"%>
<%@page import="com.rivetlogic.quartz.util.QuartzSchedulerUtil"%>
<%@include file="init.jsp" %>

<liferay-ui:success key="rivet_schedule_success" message="rivet.schedule.success"/>
<liferay-ui:error key="rivet_schedule_error" message="rivet.schedule.error" />

<%
	
	List<SchedulerJobBean> schedulerJobBeans = (List<SchedulerJobBean>)  request.getAttribute("schedulerJobsList");
	
	String orderByCol = "";

	String orderByType = "";

	orderByCol = (String)request.getAttribute("orderByCol");

	orderByType = (String)request.getAttribute("orderByType");

	if (orderByCol != null || orderByType != null) {
		OrderByComparator orderByComparator = QuartzSchedulerUtil.getSchedulerJobCoparator(orderByCol, orderByType);
		Collections.sort(schedulerJobBeans, orderByComparator);
	}
 	
%>
<portlet:actionURL var="jobActionURL" name="jobAction"></portlet:actionURL>
<div id="schedulerManager">
	<div id="schedulerJobsContainer">
		<aui:form action="${jobActionURL}">
			<input id="index" type="hidden" value="-1"/>
			<fieldset>
				<legend>Scheduler Job Service</legend>
				<button id="shutdown_p" type="button" value="Shutdown">Shutdown</button>
			</fieldset>
			<fieldset>
				<div class="scheduler-job-actions">
					<button id="resume" type="button" value="Resume">Resume</button>
	    			<button id="pause" type="button" value="Pause">Pause</button>
				</div>
				<div class="refresh">
					<button id="refresh" type="button" value="Refresh">Refresh</button>
				</div>
			</fieldset>
			<div id="shutdownPopup"></div>
		</aui:form>
		<liferay-ui:search-container id="schedulerJobListContainer" delta="10"
			emptyResultsMessage="portlet.plan.details.eligibility.empty" orderByCol="<%= orderByCol %>" orderByType="<%= orderByType %>">
			<liferay-ui:search-container-results results="<%= QuartzSchedulerUtil.subList(schedulerJobBeans, searchContainer.getStart(), searchContainer.getEnd()) %>"
				total="${count}" />
				
			<liferay-ui:search-container-row
				className="com.rivetlogic.quartz.bean.impl.SchedulerJobBeanImpl"
				modelVar="schedulerJobBean">
		
				<liferay-ui:search-container-column-text name="">
						<input type="checkbox"/>
				</liferay-ui:search-container-column-text>
				
				<liferay-ui:search-container-column-text name="short-name" orderable="<%= true %>" orderableProperty="shortName">
		                ${schedulerJobBean.shortName}
		                <input id="fullJobName" type="hidden" value="${schedulerJobBean.jobName}"/>
		        </liferay-ui:search-container-column-text>
		        <liferay-ui:search-container-column-text name="group-name"  orderable="<%= true %>" orderableProperty="shortGroup">
		                ${schedulerJobBean.shortGroup}
		                <input id="fullGroupName" type="hidden" value="${schedulerJobBean.groupName}"/>
		        </liferay-ui:search-container-column-text>
		        <liferay-ui:search-container-column-text name="state"  orderable="<%= true %>" orderableProperty="state">
		                ${schedulerJobBean.triggerState}
		        </liferay-ui:search-container-column-text>
		        <liferay-ui:search-container-column-text name="start-time"  orderable="<%= true %>" orderableProperty="startTime">
		                ${schedulerJobBean.startTime}
		        </liferay-ui:search-container-column-text>
		        <liferay-ui:search-container-column-text name="end-time"  orderable="<%= true %>" orderableProperty="endTime">
		                ${schedulerJobBean.endTime}
		        </liferay-ui:search-container-column-text>
		        <liferay-ui:search-container-column-text name="previous-fire-time"  orderable="<%= true %>" orderableProperty="previousFireTime">
		                ${schedulerJobBean.previousFireTime}
		        </liferay-ui:search-container-column-text>
		        <liferay-ui:search-container-column-text name="next-fire-time"  orderable="<%= true %>" orderableProperty="nextFireTime">
		                ${schedulerJobBean.nextFireTime}
		        </liferay-ui:search-container-column-text>
		        <liferay-ui:search-container-column-text name="storage-type"  orderable="<%= true %>" orderableProperty="storageType">
		                ${schedulerJobBean.storageType}
		        </liferay-ui:search-container-column-text>
			</liferay-ui:search-container-row>
			
			<liferay-ui:search-iterator/>
		
		</liferay-ui:search-container>
	</div>
</div>
