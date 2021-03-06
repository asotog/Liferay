<%--
	/**
	 * Copyright (C) 2005-2014 Rivet Logic Corporation.
	 * 
	 * This program is free software; you can redistribute it and/or modify it under
	 * the terms of the GNU General Public License as published by the Free Software
	 * Foundation; version 3 of the License.
	 * 
	 * This program is distributed in the hope that it will be useful, but WITHOUT
	 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
	 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
	 * details.
	 * 
	 * You should have received a copy of the GNU General Public License along with
	 * this program; if not, write to the Free Software Foundation, Inc., 51
	 */
--%>

<%@page import="com.rivetlogic.speech.bean.CommandBean"%>
<%@page import="java.util.List"%>
<%@page import="com.rivetlogic.speech.util.SpeechUtil"%>
<%@include file="init.jsp" %>
<%
	List<CommandBean> commandBeans = (List<CommandBean>) request.getAttribute("command_list");
%>
<aui:fieldset label="command-list">
	<liferay-ui:search-container delta="10" emptyResultsMessage="no-commands-were-found">
		<liferay-ui:search-container-results 
			results="<%= SpeechUtil.subList(commandBeans, searchContainer.getStart(), searchContainer.getEnd()) %>" 
			total="${commandCount}"/>
		<liferay-ui:search-container-row className="com.rivetlogic.speech.bean.impl.CommandBeanImpl" modelVar="command">
			<liferay-ui:search-container-column-text name="command_key" cssClass="command-key-column">
				${command.commandKey}
			</liferay-ui:search-container-column-text>
			<liferay-ui:search-container-column-text name="command_value" cssClass="command-value-column">
				<p class="break-text">${command.commandValue}</p>
			</liferay-ui:search-container-column-text>
			<liferay-ui:search-container-column-text cssClass="action-column">
				<liferay-ui:icon-menu>
					<portlet:renderURL var="editURL">
						<portlet:param name="mvcPath" value="/html/speech/edit.jsp" />
						<portlet:param name="command_key" value="${command.commandKey}"/>
						<portlet:param name="command_value" value="${command.commandValue}"/>
					</portlet:renderURL>
					<liferay-ui:icon image="edit" url="${editURL}"/>
					<portlet:actionURL name="deleteAction" var="deleteURL">
						<portlet:param name="command_key" value="${command.commandKey}"/>
						<portlet:param name="command_value" value="${command.commandValue}"/>
					</portlet:actionURL>
					<liferay-ui:icon-delete url="${deleteURL}"/>
				</liferay-ui:icon-menu>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>
		<liferay-ui:search-iterator/>
	</liferay-ui:search-container>
</aui:fieldset>