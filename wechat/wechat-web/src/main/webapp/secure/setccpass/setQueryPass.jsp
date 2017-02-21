<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/meta.jsp"%>
<html>
<head>
<title><s:text name="APP_NAME"/></title>
<%@ include file="/common/header.jsp"%>
<%@ include file="/common/encrypt.jsp"%>
<script type="text/javascript" src="${ctx}/resources/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/hsm_cli.js"></script>

<script>
function beforeSubmit() {
		var re = /(\d{6})/;
	    var pass1 = $("#pass1").val();
	    var pass2 = $("#pass2").val();
	    if(pass1 == ""){
	   		msg("查询密码不能为空");
		    	return false;
	    }
	    if(pass2 == ""){
	   		msg("再输一次不能为空");
		    return false;
	    }
	    if(!re.test(pass1)){
	   	    msg("查询密码输入不正确");
	   	    $('#pass1').val('');
		  	return false;
	    }
	    if(!IssimplePwd2(pass1)){
			 msg("您输入的查询密码过于简单，请重置");
			 $('#pass1').val('');
			 $('#pass1').val('');
		     return false;
      	 }
	    if(pass1 != pass2){
	    	msg("两次输入的查询密码不一致");
	    	$('#pass2').val('');
	    	return false;
	    }
	    $("#eacctpwd").val(EncryptAPin(pass1));
	}
		
</script>
</head>
<body>
	<s:form id="psform" action="creditCardPasswordManager.do" namespace="" method="post">
		<div class="topbar"> 查询密码设置 </div>
		<div class="tit1">请设置您的查询密码</div>
		<ul class="lisbox">
		   <li><span class="bdmc ft_size3">查询密码：</span>
		    <input id="pass1" type="password" class="txtc" placeholder="请输入查询密码"  maxlength="6"/>
		    <input type="hidden" id="eacctpwd" name="confPass.queryPassword"/>
		  </li>
		  <li><span class="bdmc ft_size3">再输一次：</span>
		    <input id="pass2" type="password" class="txtc"  placeholder="再输一次查询密码"  maxlength="6"/>
		  </li>
		  <div class="p-con lcsm">
			<p class="text-left">
		  	温馨提示：『查询密码』是您进行账户查询及管理时使用的密码。为了您用卡安全查询密码请勿与交易密码相同。</p>
		  </div>
		</ul>
		<div class="btnbox">
		  <s:submit value="确认并完成绑定" cssClass="nxtbt" onclick="return beforeSubmit();" method="confQueryPass"/>
		  <s:submit value="上一步" cssClass="calbtn"  method="lastTranPass"/>
		</div>
	 </s:form>
	   <%@include file="/common/foot.jsp"%>
</body>
</html>