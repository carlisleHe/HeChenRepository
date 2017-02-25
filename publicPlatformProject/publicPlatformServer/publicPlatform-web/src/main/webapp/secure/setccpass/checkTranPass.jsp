<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/meta.jsp"%>
<html>
<head>
<title><s:text name="APP_NAME"/></title>
<%@ include file="/common/header.jsp"%>
<%@ include file="/common/encrypt.jsp"%>
<%--<link rel="stylesheet"  href="resources/css/css.css" type="text/css"/> --%>
<script type="text/javascript"  src="${ctx}/resources/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/hsm_cli.js"></script>

<script>
	
function beforeSubmit() {
		var re = /(\d{6})/;
	    var tranPass = $("#tranPass").val();
	     if(tranPass == ""){
	   		 msg("交易密码不能为空");
		    return false;
	    }
	     if(tranPass.length != 6){
	   		msg("交易密码输入不正确");
		   return false;
	    }
	    if( !re.test(tranPass)){
	  	    msg("交易密码输入不正确");
		  	return false;
	    }
	  $("#eacctpwd").val(EncryptAPin(tranPass));
	}
		
</script>
</head>
<body>
	<s:form id="psform" action="creditCardPasswordManager.do" namespace="" method="post">
		<div class="topbar">查询密码设置 </div>
		<ul class="lisbox">
		  <li><span class="bdmc ft_size3">信用卡　：</span>
		 	 <s:property value='confPass.acctNoInput'/>
		  </li>
		  <li><span class="bdmc ft_size3">证件号码：</span>
		  	<s:property value='confPass.inputCertNo'/>
		  </li>
		  <li><span class="bdmc ft_size3">交易密码：</span>
			 <input id="tranPass" type="password" class="txtc" placeholder="六位有效数字"  maxlength="6"/>
			 <input type="hidden" id="eacctpwd" name="confPass.tranPassword"/>
		  </li>
		</ul>
		<div class="btnbox">
		  <s:submit value="下一步" cssClass="nxtbt" onclick="return beforeSubmit();" method="checkTranPass"/>
		  <s:submit value="未设置交易密码，请点击设置" cssClass="calbtn" method="tranPass" onclick="$.blockUI({ message: '请稍后！' });"/>
		</div>
	 </s:form>
	   <%@include file="/common/foot.jsp"%>
</body>
</html>