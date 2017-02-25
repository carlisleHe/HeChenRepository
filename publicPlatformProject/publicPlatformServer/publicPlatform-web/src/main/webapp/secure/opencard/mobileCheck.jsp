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
	<s:form id="psform" action="opencard!setpWrite.do" namespace="" method="post">
		 <div class="topbar">修改开户申请</div>
		<div class="p-con">
			<p class="text-left">您填写的手机号和开户申请时所填写的手机号不一致，请重新填写。</p>
		</div>
		<div class="btnbox">
		  <s:submit value="返回" cssClass="nxtbt" onclick='return beforeSubmit();'/>
		</div>
	 </s:form>
    <%@include file="/common/foot.jsp"%>
</body>
</html>
