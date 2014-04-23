<%@include file="init.jsp" %>
<%
PortletPreferences preference = renderRequest.getPreferences();
String keyWord = preference.getValue("key_word", StringPool.BLANK);

String commandKey = ParamUtil.get(request, "command-key", StringPool.BLANK);
String commandValue = ParamUtil.get(request, "command-value", StringPool.BLANK);
System.out.println("command-key:" + commandKey + " command-value: " + commandValue);
%>
<!-- MESSAGES -->
<liferay-ui:success key="success-msg" message="add-command-success-msg"/>

<!-- ACTION URL -->
<portlet:actionURL name="addCommandAction" var="addCommandURL"/>
<portlet:actionURL name="backAction" var="backURL"/>

<!-- FORM -->
<aui:form action="${addCommandURL}">
	<aui:fieldset column="true" label="add-command">
		<aui:input name="key_word" helpMessage="key_word_help_msg" value="<%= keyWord %>">
			<aui:validator name="maxLength">
                	'15'
            </aui:validator>
		</aui:input>
		<aui:fieldset column="false">
			<aui:input name="command_key" required="true" helpMessage="command_key_help_msg" value="<%= commandKey %>">
				<aui:validator name="maxLength">
                	'15'
            	</aui:validator>
			</aui:input>
			<aui:input name="command_value" required="true" helpMessage="command_value_help_msg" value="<%= commandValue %>"/>
		</aui:fieldset>
	</aui:fieldset>
	<aui:button-row>
		<aui:button type="button" value="Back" onClick="${backURL}"/>
		<aui:button type="submit" value="Add"/>
	</aui:button-row>
</aui:form>

