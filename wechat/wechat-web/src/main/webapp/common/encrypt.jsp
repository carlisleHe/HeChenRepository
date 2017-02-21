<%@ page language="java"  pageEncoding="utf-8"%>
<%@ page import="com.newland.wechatServer.HsmAdapter" %>
<%@ page import="org.apache.struts2.ServletActionContext" %>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@ page import="org.springframework.web.context.WebApplicationContext" %>
<%
ServletContext servletContext = ServletActionContext.getServletContext();
WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(servletContext);
HsmAdapter hsm = (HsmAdapter) wac.getBean("hsmAdapter");
%>
<script type="text/javascript">
var pk = '<%=hsm.getRsaPublicKey()%>';
</script>