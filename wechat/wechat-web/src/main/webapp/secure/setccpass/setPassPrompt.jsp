<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/meta.jsp"%>
<html>
<head>
<title><s:text name="APP_NAME"/></title>
<%@ include file="/common/header.jsp"%>
<script src="${ctx}/resources/js/jquery-1.10.2.min.js"></script>

<script>
function beforeSubmit() {
	    var num3 = $("#num3").val();
	   
	    if(num3 == ""){
	   		msg("背面签名栏未尾3位不能为空");
		    	return false;
	    }
	    if(num3.length!=3){
	   		msg("背面签名栏未尾3位输入不正确");
		    return false;
	    }
	}
		
</script>
</head>
<body>
	<s:form id="psform" action="creditCardPasswordManager!queryAndTranPass.do" namespace="" method="post">
		<div class="topbar"> 密码设置 </div>
		<div class="tit1">您尚未设置交易密码和查询密码，为了后续用卡方便，请按以下流程进行设置。</div>
		<ul class="lisbox">
		   <li><span class="bdmc ft_size3">信用卡：</span>
		   	  <s:property value="confPass.acctNoInput"/>
		   </li>
		   <li><span class="bdmc ft_size3">背面签名栏未尾3位：</span>
		   	<input id="num3" type="text" class="txtc"  name="confPass.sign" placeholder="输入背面签名栏未尾3位"  maxlength="3"  value="<s:property value='confPass.sign'/>"/>
		   </li>
		   <div class="p-con lcsm">
			  <p class="text-left">
		  	       温馨提示：『查询密码』是您进行账户查询及管理时使用的密码。『交易密码』是您在消费或者提取现金时使用的密码。为了您用卡安全，查询密码请勿与交易密码相同。</p>
			</div>
		</ul>
		<div class="btnbox">
		  <s:submit value="下一步" cssClass="nxtbt" onclick="return beforeSubmit();"/>
		</div>
	 </s:form>
	   <%@include file="/common/foot.jsp"%>
</body>
</html>