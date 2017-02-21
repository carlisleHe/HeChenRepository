<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/meta.jsp"%>
<html>
<head>
<title><s:text name="APP_NAME"/></title>
<%@ include file="/common/header.jsp"%>
<%-- <script src="js/jquery-1.10.2.min.js"></script>--%>
<script>

	function openCard(){
		 $("#operaterType").val(1);
		 return true;
	}
	
	function modifyApply(){
		 $("#operaterType").val(2);
		 return true;
	}
</script>
</head>
<body>
	<s:form id="psform" action="opencard!oauth.do" namespace="" method="post">
		<div class="topbar"> 远程开户 </div>
		<div class="p-con lcsm">
			<h2 class="text-left"><span>开户流程说明</span></h2>
			<p class="liuchengtu text-left">
			填写身份信息 <br>
			身份证正反面、上半身正面拍照上传<br>
			填写开户资料<br>
			<span class="linebox"></span>
			添加微信好友并进行视频验证<br>
			开户</p>
		</div>
		<div class="p-con lcsm">
			<p class="text-left">
		  	 提示：请准备好二代身份证原件及支持拍照功能的手机，建议在3G/4G、Wifi网络环境下进行开卡操作。</p>
		</div>
		<div class="btnbox">
		  <input type="hidden" id="operaterType" name="operatertype" />
		  <s:submit value="现在开户" cssClass="nxtbt" onclick='openCard();'></s:submit>
		  <s:submit value="修改开户申请" cssClass="calbtn" onclick='modifyApply();'></s:submit>
		</div>
	</s:form>
	<%@include file="/common/foot.jsp"%>
</body>
</html>
