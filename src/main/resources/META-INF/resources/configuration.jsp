<%@ taglib prefix="asui" uri="http://liferay.com/tld/aui" %>
<%@ page import="com.liferay.portal.kernel.util.Constants" %>
<%@ page import="java.lang.invoke.ConstantCallSite" %>
<%@ page import="com.liferay.portal.kernel.util.Constants" %>

<%@ include file="/init.jsp"%>

<liferay-portlet:actionURL portletConfiguration="<%=true%>" var="configurationActionURL"/>
<liferay-portlet:renderURL portletConfiguration="<%=true%>" var="configurationRenderURL"/>

<aui:form action="<%=configurationActionURL%>" method="post" name="fm" class="form">
    <aui:input name="<%= Constants.CMD%>" type="hidden" value="<%= Constants.UPDATE%>"/>
    <aui:input name="redirect" type="hidden" value="<%=configurationRenderURL%>"/>

    <div class="portlet-configuration-body-content">
        <div class="container-fluid">
            <p>Do whatever you want todo here.</p>
            <aui:fieldset>
                <aui:input name="preferences--url--" label="Define static url" value="${url}"></aui:input>
            </aui:fieldset>
            <aui:fieldset>
                <aui:input name="preferences--match--" label="Regex for matching" value="${match}"></aui:input>
                <aui:input name="preferences--replace--" label="Replacement" value="${replace}"></aui:input>
            </aui:fieldset>
            <aui:fieldset>
                <aui:input name="preferences--foregroundColor--" label="Foreground Color" value="${foregroundColor}"></aui:input>
            </aui:fieldset>
            <aui:fieldset>
                <aui:input name="preferences--backgroundColor--" label="Background Color" value="${backgroundColor}"></aui:input>
            </aui:fieldset>
        </div>
    </div>

    <aui:button-row>
        <aui:button cssClass="btn-lg" type="submit"/>
    </aui:button-row>
</aui:form>

<!-- https://www.masteringliferay.com/members/learning-portal/videos/lesson/-/play/making-your-portlets-configurable -->