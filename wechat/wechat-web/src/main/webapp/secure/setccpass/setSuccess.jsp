<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/meta.jsp"%>
<html>
<head>
<title><s:text name="APP_NAME"/></title>
<%@ include file="/common/header.jsp"%>
<script src="${ctx}/resources/js/jquery-1.10.2.min.js"></script>
<script>
</script>
</head>
<body>
	  <div class="topbar">绑定成功</div>
	<div class="p-con">
		<%--content 1)查询密码,2)查询密码和交易密码 --%>
		<p class="text-left"> 您的信用卡*<s:property value="acctno"/><s:property value="content"/>设置成功,并已完成与微信银行绑定。</p>
	</div>
	<div class="btnbox">
	<input type="button" class="nxtbt" value="返回" onclick="WeixinJSBridge.call('closeWindow');" />
	</div>
    <%@include file="/common/foot.jsp"%>
</body>
</html>
