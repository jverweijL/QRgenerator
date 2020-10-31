<%@ page import="com.liferay.demo.qr.portlet.configuration.QRCodeGeneratorPortletConfiguration" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
    QRCodeGeneratorPortletConfiguration portletInstanceConfiguration = portletDisplay.getPortletInstanceConfiguration(QRCodeGeneratorPortletConfiguration.class);
    String url = portletInstanceConfiguration.url();
    pageContext.setAttribute("url",url);
    String match = portletInstanceConfiguration.match();
    pageContext.setAttribute("match",match);
    String replace = portletInstanceConfiguration.replace();
    pageContext.setAttribute("replace",replace);
    String foregroundColor = portletInstanceConfiguration.foregroundColor();
    pageContext.setAttribute("foregroundColor",foregroundColor);
    String backgroundColor = portletInstanceConfiguration.backgroundColor();
    pageContext.setAttribute("backgroundColor",backgroundColor);
%>
