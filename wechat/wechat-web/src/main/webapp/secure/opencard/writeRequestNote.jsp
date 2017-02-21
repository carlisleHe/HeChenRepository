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
function beforeSubmit() {
	    var applyId = $("#applyId").val();
	    var telephone = $("#telephone").val();
	    var identify = $("#identify").val();
	    if(applyId == ""){
	   			 msg("申请号不能为空");
		    	return false;
	    }
	    ///[^\d]+/.test(applyId) ||
	    if( applyId.length<5){
	   	    msg("申请号输入不正确");
		  	return false;
	    }
	    if(telephone == ""){
	   			msg("手机号码不能为空");
		    	return false;
	    }
	    if(telephone.length!=11){
		    	msg("手机号输入不正确");
		    	$('#telephone').val('');
		    	return false;
		 } 
	    if(identify == ""){
	   			 msg("短信验证码不能为空");
		    	return false;
	    }
	     if(identify.length!=6){
		    	msg("短信验证码输入不正确");
		    	$('#identify').val('');
		    	return false;
		 } 
		 return true;
	}
	//${ctx}/
	$(function(){
				var timeCount = 90;
				var changeInterval = null;
				var currentTitle = null;
				//var sequence = 0;
				//var code ="";
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
										changeInterval = setInterval(changeBtnText, 1000);
									}else{
									    msg(result.message); 
									  //code = result.code;//测试用
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
						if($('#telephone').val() == ""){
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
									     //code = result.code;
										changeInterval = setInterval(changeBtnText, 1000);
									}else{
									   msg(result.message); 
									   //code = result.code;//测试用
									  //$('#identify').val(code);//测试用
									}
					   }, "json");
						
				});
		});	

		jQuery(document).ready(function(){
			$('#identify').val("");
			// 焦点移开立马校验
			jQuery("#psform").validationEngine();
		});
		
</script>
</head>
<body>
	<s:form id="psform" action="opencard!updateUserRequest.do" namespace="" method="post">
		<div class="topbar"> 远程开户 </div>
		<div class="p-con liucheng">
				<div><img src="resources/images/liucheng1.png"></div>
				<span class="on">填写申请信息</span>
				<span>上传照片</span>
				<span>填写开户信息</span>
				<span>视频验证</span>
		</div>
		<div class="tit1">请输入以下信息</div>
		<ul class="lisbox">
		  <li><span class="bdmc ft_size3">申请号:</span>
		   <input id="applyId" type="text" class="txtc"  placeholder="输入申请号" name="applyinfo.applyId" value="<s:property value='applyinfo.applyId'/>"/>
		  </li>
		  <li><span class="bdmc ft_size3">手机号码:</span>
		   <input id="telephone" type="text" class="validate[required,custom[phone]] text-input txtc"  placeholder="输入手机号码" name="applyinfo.mobile" value="<s:property value='applyinfo.mobile'/>"/>
		  </li>
		  <li><span class="bdmc ft_size3">短信验证码:</span>
		    <s:textfield type="text" cssClass="txtc yzm-txt" name="identify" id="identify" placeholder="六位有效数字"></s:textfield>
			<span id="sendAgainSpan">
				<a id="getidentifycode" href="javascript:void(0)"  >获取验证码</a>
			</span>
		  </li>
		  <%-- 
		   <li class="last" ><span class="bdmc ft_size3">序号:</span>
		  	 <input type="text" id="sequence" class="txtc" placeholder="显示短信验证码序号" readonly/>
		  </li>
		  --%>
		</ul>
		<div class="btnbox">
		  <s:submit value="下一步" cssClass="nxtbt" onclick='return beforeSubmit();'/>
		</div>
	 </s:form>
	   <%@include file="/common/foot.jsp"%>
</body>
</html>