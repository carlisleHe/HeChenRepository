<%@ page language="java"  pageEncoding="utf-8"%>
<%@ page import="com.cib.weixinserver.web.actions.JsSdkAction" %>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@ page import="org.springframework.web.context.WebApplicationContext" %>
<%
String  appId= (String)request.getSession().getAttribute("appId");  
session.setAttribute("appId",appId);
String  timestamp= (String)request.getSession().getAttribute("timestamp");  
session.setAttribute("timestamp",timestamp);
String  nonceStr= (String)request.getSession().getAttribute("nonceStr");  
session.setAttribute("nonceStr",nonceStr);
String  signature= (String)request.getSession().getAttribute("signature");  
session.setAttribute("signature",signature);
%>
<script type="text/javascript">
var appId = '<%=appId%>';
var timestamp = '<%=timestamp%>';
var nonceStr = '<%=nonceStr%>';
var signature = '<%=signature%>';

</script>