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
	  <div class="topbar">提示</div>
	  <s:form id="psform" action="creditCardPasswordManager.do" namespace="" method="post">
			<div class="p-con">
				<p class="text-left"> 查询密码未设置请先设置查询密码。</p>
			</div>
			<div class="btnbox">
		  		<s:submit value="设置查询密码并绑定" cssClass="nxtbt"/>
		    </div>
	  </s:form>
    <%@include file="/common/foot.jsp"%>
</body>
</html>
