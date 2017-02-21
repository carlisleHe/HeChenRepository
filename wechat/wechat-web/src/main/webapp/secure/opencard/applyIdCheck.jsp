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
		 <div class="topbar">修改开户申请</div>
		<div class="p-con">
			<p class="text-left">开户申请单不存在或开户申请不允许修改！</p>
		</div>
		<div class="btnbox">
		     <input type="button" class="nxtbt" value="关闭" onclick="WeixinJSBridge.call('closeWindow');" />
		</div>
    <%@include file="/common/foot.jsp"%>
</body>
</html>
