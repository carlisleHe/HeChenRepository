<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/meta.jsp"%>
<html>
<head>
<title><s:text name="APP_NAME"/></title>
<%@ include file="/common/header.jsp"%>
<link rel="stylesheet" href="${ctx}/resources/css/validationEngine.jquery.css" type="text/css"/>
<script src="${ctx}/resources/js/jquery-1.10.2.min.js"></script>
<script src="${ctx}/resources/js/jquery.validationEngine.js" type="text/javascript" charset="utf-8"></script>
<script src="${ctx}/resources/js/languages/jquery.validationEngine-en.js"></script>
<script>
  var scrolltop = $(document).scrollTop();//获取当前窗口距离页面顶部高度 
	function beforeSubmit() {
	    var cardId = $("#cardId").val();
	    var telephone = $("#telephone").val();
	    var identify = $("#identify").val();
	    if(cardId == ""){
	   			 msg("身份证号码不能为空");
		    	return false;
	    }
	    if(cardId.length!=18){
		    	msg("必须是二代身份证");
		    	return false;
		} 
	    if(telephone == ""){
	   			msg("手机号码不能为空");
		    	return false;
	    }
	     if(telephone.length!=11){
		    	msg("手机号输入不正确");
		    	return false;
		 } 
	     if(identify == ""){
	   			 msg("短信验证码不能为空");
		    	return false;
	    }
	    if(identify.length!=6){
		    	msg("短信验证码输入不正确");
		    	return false;
		} 
		 return true;
	}
	
	$(function(){
				$('#identify').val("");
				var timeCount = 90;
				var changeInterval = null;
				var currentTitle = null;
				//var sequence = 0;
				//var code = "";
				var changeBtnText = function(){
					if(timeCount==0){
						clearInterval(changeInterval);
						timeCount = 90;
						$('#sendAgainSpan').bind("click",function(){$.post("${ctx}/opencard!sendIdentifyCode.do",
						       {telephone:$('#telephone').val()},
								function(result){
									if(result.isSuccess=="success"){
									    $('#sendAgainSpan').unbind("click"); //移除click
									    //sequence = result.sequence;
									    //code = result.code;
										changeInterval = setInterval(changeBtnText, 1000);
									}else{
									     msg(result.message); 
									    // code = result.code;//测试用
									    //$('#identify').val(code);//测试用
									    
									}
					   }, "json");}); //绑定click
						$('#sendAgainSpan').html(
							'<a href="javascript:void(0)">获取验证码</a>'
						);
						return;
					}
					$('#sendAgainSpan').html('重新获取（'+timeCount+'）');
					//$('#identify').val(code);
					//$('#sequence').val('D'+sequence);
					timeCount --;
				};
				$('#sendAgainSpan').click(function(){
						if($('#telephone').val()== ""){
							msg("手机号不能为空");
							return false;
						}
						 if($('#telephone').val().length!=11){
		    				msg("手机号输入不正确");
		    				return false;
		   				 } 
						$.post("${ctx}/opencard!sendIdentifyCode.do",
						        {telephone:$('#telephone').val()},
								function(result){
									if(result.isSuccess=="success"){
									    $('#sendAgainSpan').unbind("click"); //移除click
									    //sequence = result.sequence;
									   // code = result.code;
										changeInterval = setInterval(changeBtnText, 1000);
									}else{
									    msg(result.message);
									   // code = result.code;//测试用
									    //$('#identify').val(code);//测试用
				                       	//$('#sequence').val('D'+sequence);//测试用
									}
					   }, "json");
						
				});
		});	
		//查看风险提示界面
		function riskPrompt(){
		  	$("#riskPrompt").show();
			$("#psform").hide();
			$("#modifyApply").hide();
		  	document.getElementById('riskPrompt').scrollIntoView();
		}
		
		//填写身份信息界面
		function writeInfo(){
			$("#psform").show();
			$("#modifyApply").show();
			$("#riskPrompt").hide();
		}
	
		jQuery(document).ready(function(){
		     $("#riskPrompt").hide();//隐藏风险提示界面
			// binds form submission and fields to the validation engine
			jQuery("#psform").validationEngine();
		});
</script>
</head>
<body>
	<s:form id="psform" action="opencard!isOpenCard.do" namespace="" method="post">
		<div class="topbar"> 远程开户 </div>
		<div class="p-con liucheng">
				<div><img src="${ctx}/resources/images/liucheng1.png"></div>
				<span class="on">填写身份信息</span>
				<span>上传照片</span>
				<span>填写开户信息</span>
				<span>视频验证</span>
		</div>
		<div class="tit1">请输入以下信息</div>
		<ul class="lisbox">
		  <li><span class="bdmc ft_size3">身份证号码:</span>
		   <input id="cardId" type="text" class="validate[required,custom[idcret]] text-input txtc"  placeholder="输入身份证号码" name="applyinfo.certNo" value="<s:property value='applyinfo.certNo'/>"/>
		  </li>
		  <li><span class="bdmc ft_size3">手机号码:</span>
		   <input id="telephone" type="text" class="validate[required,custom[phone]] text-input txtc"  placeholder="输入手机号码" name="applyinfo.mobile" value="<s:property value='applyinfo.mobile'/>"/>
		  <div ><span class="xwtx">请务必使用本人手机号进行申请。</span><a href="javascript:riskPrompt();">重要提示</a></div>
		  </li>
		  <li><span class="bdmc ft_size3">短信验证码:</span>
		    <s:textfield type="text" cssClass="txtc yzm-txt" name="identify" id="identify" placeholder="六位有效数字"></s:textfield>
			<span id="sendAgainSpan">
				<a href="javascript:void(0)" >获取验证码</a>
			</span>
			
		  </li>
		  <div class="p-con lcsm">
			<p class="text-left">
		  	提示：已持有兴业银行借记卡的客户暂不能申请。</p>
		</div>
		  <%-- 
		   <li  class="last"><span class="bdmc ft_size3">序号:</span>
		  	 <input type="text" id="sequence" class="txtc" placeholder="显示短信验证码序号" readonly/>
		  </li>
		  --%>
		</ul>
		<input type="hidden" id="operaterType" name="operatertype" value='1'/>
		<div class="btnbox">
		  <s:submit value="下一步" cssClass="nxtbt" onclick='return beforeSubmit();'/>
		</div>
	 </s:form>
	 <s:form id="modifyApply" action="opencard!setpWrite.do" namespace="" method="post">
		 <input type="hidden" id="operaterType" name="operatertype" value='2'/>
		 <div class="stepbox">
		 	  <s:submit value="修改开户申请" cssClass="calbtn"/>
		 </div>
	</s:form> 
	
	<!-- ---------  兴业银行风险提示  -------------- -->
 	<div id="riskPrompt">
		<div class="topbar">远程开户</div>
			<div class="p-con">
				<h2><span>重要提示</span></h2>
				<p class="text-left">
					您所填写的手机号码将会用于开通短信口令功能，短信口令是您办理电子银行转账等交易的验证手段！为了您的资金安全，请务必使用本人手机号码进行申请，切勿使用他人手机号码申请。
				</p>
			</div>
			<div class="btnbox">
			  <input type="button" value="返回" class="nxtbt" onclick="writeInfo();" />
		</div>
	</div>
	<%@include file="/common/foot.jsp"%>
</body>
</html>