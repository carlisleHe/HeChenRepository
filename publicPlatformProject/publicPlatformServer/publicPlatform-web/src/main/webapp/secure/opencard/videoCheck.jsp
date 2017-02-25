<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/meta.jsp"%>
<html>
	<head>
		<title><s:text name="APP_NAME"/></title>
		<%@ include file="/common/header.jsp"%>
		<script type="text/javascript" src="${ctx}/resources/js/jquery-1.10.2.min.js"></script>
		<%-- <link rel="stylesheet"  href="resources/css/css.css" type="text/css"/>--%>
		<%-- <script src="resources/js/jquery-1.10.2.min.js"></script>--%>
		<script>
			jQuery(document).ready(function(){
			     $("#lookInfo").hide();//隐藏开通功能界面
			});
			//查看开通功能界面
			function look(){
			  	$("#lookInfo").show();
				$("#videoInfo").hide();
			  	document.getElementById('lookInfo').scrollIntoView();
			}
			
			//填写身份信息界面
			function writeInfo(){
				$("#videoInfo").show();
				$("#lookInfo").hide();
			}
		
		</script>
	</head>

	<body>
	<div id="videoInfo">
			<div class="topbar"> 远程开户 </div>
			<div class="p-con liucheng">
					<div><img src="${ctx}/resources/images/liucheng4.png"></div>
					<span>填写身份信息</span>
					<span>上传照片</span>
					<span>填写开户信息</span>
					<span class="on">视频验证</span>
			</div>
			<div class="p-con">
		    	<div><img src="${ctx}/resources/images/success.png"></div>
		    	<br/>
				<span class="green"><b>开户申请提交成功！</b></span>
				<br/><br/>
				<p class="text-left">
					工作人员将在9:00至18:00期间与您联系，并要求添加您为微信好友，通过微信视频通话的方式进一步确认您的身份，在此期间请您保持电话畅通。<br/><br/>您所申请的借记卡将开通以下功能，<a href="javascript:look();">点击查看</a>。
				</p>
			</div>
			<div class="btnbox">
			  <input type="button" value="关闭" class="nxtbt" onclick="WeixinJSBridge.call('closeWindow');" />
			</div>
	</div>
	<!-- ---------  开通以下功能提示  -------------- -->
 	<div id="lookInfo">
		<div class="topbar">远程开户</div>
			<div class="p-con">
				<h2><span>您申请的借记卡将开通以下功能</span></h2>
				<p class="text-left">
					<s:if test="applyinfo.mealContent==0">
						1、个人网银：任意转账（日限额20万）、缴费<br>
						2、手机银行：任意转账（日限额5万）、缴费<br>
						3、网上支付：日限额5千
					</s:if>
					<s:else>
						1、个人网银：任意转账（日限额100万）、缴费<br>
						2、手机银行：任意转账（日限额20万）、缴费<br>
						3、网上支付：日限额5万
					</s:else>
	                    <br/>
	                                                     4、理财产品购买
	                    <br/>
	                                                     5、短信口令（申请手机号：<s:property value='applyinfo.mobile'/>）
	                    <br/>                                 
                                                              <br/> 注：短信口令是您办理电子银行转账等交易的验证手段！为了您的资金安全，开通短信口令请务必使用本人手机号码，切勿使用他人手机号码开通此功能。短信口令内容请切勿告诉他人，工作人员不会向您索取，请勿泄露。

				</p>
			</div>
			<div class="btnbox">
			  <input type="button" value="返回" class="nxtbt" onclick="writeInfo();" />
		</div>
	</div>
		<%@include file="/common/foot.jsp"%>
	</body>
</html>
