<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/meta.jsp"%>
<%@ taglib uri="http://www.cib.com.cn/tags" prefix="cib" %>
<html>
<head>
<title><s:text name="APP_NAME"/></title>
<%@ include file="/common/header.jsp"%>
<%@ include file="/common/encrypt.jsp"%>
<script type="text/javascript" src="${ctx}/resources/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/hsm_cli.js"></script>
<!-- 后台弹出提示框定位 -->
<s:if test='actionMessages.size>0'>
	<script type="text/javascript">
        $(document).ready(function(){
           document.getElementById('psform').scrollIntoView();
        });
	</script>
</s:if>


<script>

function beforeSubmit() {
		var re = /(\d{6})/;
	    var queryPass1 = $("#queryPass1").val();
	    var tranPass1 = $("#tranPass1").val();
	    var identify = $("#identify").val();
	    var num3 = $("#num3").val();

	    if(num3 == ""){
	   		msg("背面签名栏未尾3位不能为空");
		    	return false;
	    }
	    if(num3.length!=3){
	   		msg("背面签名栏未尾3位输入不正确");
		    return false;
	    }
	    if(queryPass1 == ""){
      		msg("查询密码不能为空");
      		return false;
      	 }
      	if(tranPass1 == ""){
      		msg("交易密码不能为空");
      		return false;
      	 }
      	 if(identify == ""){
      		 msg("短信口令不能为空");
      		 return false;
      	 }
      	 if(!re.test(queryPass1)){
	   	    msg("查询密码输入不正确");
	   	    $('#queryPass1').val('');
		  	return false;
	    }
	    if(!re.test(tranPass1)){
	   	    msg("交易密码输入不正确");
	   	    $('#tranPass1').val('');
		  	return false;
	    }
      	 if(!IssimplePwd2(queryPass1)){
			 msg("您输入的查询密码过于简单，请重置");
			 $('#queryPass1').val('');
			 $('#queryPass2').val('');
		     return false;
      	 }
      	 if(!IssimplePwd2(tranPass1)){
			 msg("您输入的交易密码过于简单，请重置");
			 $('#tranPass1').val('');
			 $('#tranPass2').val('');
		     return false;
      	 } 
      	 if(queryPass1 !=$('#queryPass2').val()){
			 msg("两次输入的查询密码不一致，请重新输入");
			 $('#queryPass2').val('');
		     return false;
      	 }
      	 if(tranPass1 != $('#tranPass2').val()){
		     msg("两次输入的交易密码不一致，请重新输入");
			 $('#tranPass2').val('');
		     return false;
      	 } 
      	$("#queryPass").val(EncryptAPin(queryPass1));
	    $("#tranPass").val(EncryptAPin(tranPass1));
		
	}
	$(function(){
			var countDown = $("#countDown").val();
			if(countDown==-1){
				var timeCount = 120;
			}else{
				$("#identify").val("");
				var timeCount = 120-countDown;
			}
			var changeInterval = null;
			var falg = false;
			var changeBtnText = function(){
				$("#countDown").val(120-timeCount);
				if(timeCount==0){//清定时器
					document.getElementById("identify").setAttribute("placeholder",'超时，请"重新获取"');
					return;
				}else if(timeCount==60){//添加样式，绑定click
					$('#recover').bind("click",function(){$.post("${ctx}/confccpass!sendIdentifyCode.do",
					    	function(msg){
					    		if(typeof(msg.error) == "undefined" || msg.error == false){
								    $('#recover').unbind("click"); //移除click
								    $("#recover").removeClass("nbt").addClass("hbt");
								    clearInterval(changeInterval);
								    timeCount = 120;
									changeInterval = setInterval(changeBtnText, 1000);
								}else{
								    msg(msg.message); 
								}
				   }, "json");}); //绑定click
				  $("#recover").removeClass("hbt").addClass("nbt");
				}
				if(falg){
					document.getElementById("identify").setAttribute("placeholder","请输入");
				}else{
					document.getElementById("identify").setAttribute("placeholder","短信已发送("+timeCount+"s)");
				}
				
				timeCount --;
		};
		if(timeCount<=60){
			$('#recover').bind("click",function(){$.post("${ctx}/confccpass!sendIdentifyCode.do",
					    	function(msg){
								if(typeof(msg.error) == "undefined" || msg.error == false){
								    $('#recover').unbind("click"); //移除click
								    $("#recover").removeClass("nbt").addClass("hbt");
								    clearInterval(changeInterval);
								    timeCount = 120;
									changeInterval = setInterval(changeBtnText, 1000);
								}else{
								    msg(msg.msg); 
								}
				   }, "json");}); //绑定click
			 $("#recover").removeClass("hbt").addClass("nbt");
		}else{
			$('#recover').unbind("click"); //移除click
		}
		changeInterval = setInterval(changeBtnText, 1000);
		
		$('#identify').focus(function() { 
				falg = true;
		}); 
		$('#identify').blur(function() { 
				falg = false;
		}); 
	});

</script>
</head>
<body>
	<s:form id="psform" action="confccpass.do" namespace="" method="post">
		<div class="topbar">信用卡密码设置 </div>
		<div class="tit1">您尚未设置查询密码和交易密码，为了后续用卡方便，请按以下流程进行设置并完成绑定。</div>
		<ul class="lisbox">
		   <li><span class="bdmc ft_size3">信用卡：</span>
		   	  <s:property value="confPass.acctNoInput"/>
		   </li>
		   <li class="last"><span class="bdmc ft_size3">卡背面签名栏末三位：</span>
		   	<input id="num3" type="text" class="txtc"  name="confPass.sign" placeholder="请输入末三位"  maxlength="3"  value="<s:property value='confPass.sign'/>"/>
		   </li>
		</ul>
		  <div class="tit1">请输入您手机号 <s:property value="confPass.formMobile"/>收到的6位短信口令</div>
		  <ul class="lisbox" id="dxkl">
		  <li  class="last"><span class="bdmc ft_size3">短信口令：</span>
		    <s:textfield  type="text" cssClass="txtc txtbt" name="identify" id="identify"  maxlength="6"></s:textfield>
			<input type=button value="重新获取" class="hbt" id="recover"/>
		  </li>
		</ul>
		<div class="tit1">请设置您的查询密码和交易密码：</div>
		<ul class="lisbox" id="cxmm">
		  <li><span class="bdmc">查询密码:</span> 
		    <input type="password" class="txtc txtb" placeholder="请设置查询密码" id="queryPass1"  maxlength="6"/>
		    <input type="hidden" id="queryPass" name="confPass.queryPassword"/>
		  </li>
		  <li><span class="bdmc">再输一次:</span>
		    <input type="password" class="txtc txtb" placeholder="再输一次查询密码" id="queryPass2"  maxlength="6"/>
		  </li>
		 <li><span class="bdmc">交易密码:</span> 
		    <input type="password" class="txtc txtb"  placeholder="请设置交易密码" id="tranPass1"  maxlength="6"/>
		    <input type="hidden" id="tranPass" name="confPass.tranPassword"/>
		  </li>
		  <li><span class="bdmc">再输一次:</span>
		    <input type="password"  class="txtc txtb" placeholder="再输一次交易密码" id="tranPass2"  maxlength="6"/>
		  </li>
		   <input type="hidden" id="countDown" name="countDown"  value="<s:property value='countDown'/>"/>
		  	<div class="p-con lcsm">
			 	 <p class="text-left">
			  	       温馨提示：查询密码是您进行账户查询及管理时使用的密码，交易密码是您在消费或提取现金时使用的密码，为了您用卡安全，查询密码请勿与交易密码相同。
			  	 </p>
		  	 </div>
		</ul>		
		<div class="btnbox">
		  <s:submit value="确认并完成绑定" cssClass="nxtbt" onclick="return beforeSubmit();" method="confQueryAndTranPass"/>
		  <s:submit value="上一步" cssClass="calbtn"  method="lastTranPass"/>
		</div>   
	 </s:form>
	   <%@include file="/common/foot.jsp"%>
</body>
</html>