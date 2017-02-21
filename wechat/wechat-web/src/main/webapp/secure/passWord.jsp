<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/meta.jsp"%>
<html>
<head>
<title><s:text name="APP_NAME"/></title>
<%@ include file="/common/header.jsp"%>
<%@ include file="/common/encrypt.jsp"%>
<script type="text/javascript" src="${ctx}/resources/js/hsm_cli.js"></script>
<script>
$(function() {
	$('#acctpwd').keyup(function() {
		var limitV = $(this).val();	
		$(this).val(limitV.replace(/[^0-9]/g, ""));

	})
})
	function beforeSubmit() {
	    var acctType = $("#acctType").val();
		var acct = $("#acct").val();
		var acctpwd = $("#acctpwd").val();
		var promptId = $("#promptId").val();
		  if(acctpwd.search("[^ \t]") == -1 ){
		    	msg("亲，要输入密码哦");
		    	$('#acctpwd').val('');
		    	return false;
		    }
		    if(acctpwd.search("[^0-9]") != -1){
		    	msg("亲，密码要为数字哦");
		    	$('#acctpwd').val('');
				$('#acctpwd').focus();
		    	return false;
		    }
		    if(acctpwd.length!=6){
		    	msg("亲，密码要为六位数字");
		    	$('#acctpwd').val('');
		    	return false;
		    }
		    if(!IssimplePwd(acctpwd,acct)){
		    	if(acctType==2){
				  msg("您输入的查询密码过于简单，请重置");
				  $('#acctpwd').val('');
			      return false;
		    	}
		    	 msg("您输入的取款密码过于简单，请至本行任一营业网点修改");
				  $('#acctpwd').val('');
			      return false;
				 
			  }
		    if(promptId!=""&&(promptId.length!=6||/[^\d\w]+/.test(promptId))){
		    	msg("亲，推荐人ID为6位字母或数字");
		    	$("#promptId").val('');
		    	return false;
		    }
		$("#eacctpwd").val(EncryptAPin(acctpwd));
		
	}
</script>
</head>

<body>
	<div class="wrap">
		<s:form id="psform" action="authorization!auth.do" namespace="" method="post">
			<div class="tit1">亲，输完密码，我们就可以在一起了</div>
			<ul class="lisbox">
			      <style>
				   .ft_size4{display:inline-block;width:67px}
				   </style>
				<li>
					<span class="bdmc ft_size4">卡类型</span>
					<input type="text" readonly class="txtc"  value="<s:text name='CARD_NAME_%{authCardView.acctType}'/>">
				</li>
				<li >
				   <s:if test="authCardView.acctType==2">
						<span class="bdmc ft_size4">查询密码</span> 
					</s:if>
					<s:else>
						<span class="bdmc ft_size4">取款密码</span> 
					</s:else>
					<input type="password" class="txtc" id="acctpwd" maxlength="6" placeholder="六位有效数字" value="">
					<input type="hidden" id="eacctpwd" name="authCardView.passWord"/>
					<s:hidden id="acct"  value="%{authCardView.acctNo}"></s:hidden>
					<s:hidden id="acctType"  value="%{authCardView.acctType}"></s:hidden>
				</li>
				<li class="last">
					<span class="bdmc ft_size4">推广人</span>
					<input type="text" class="txtc" value="<s:property value = 'authCardView.promptId'/>" placeholder = "选填，六位字符" name = "authCardView.promptId" id="promptId"/>
				</li>
			</ul>
			<div class="btnbox">
				<s:token></s:token>
				<s:submit value="绑定" cssClass="nxtbt" onclick="return beforeSubmit();"></s:submit>
			</div>
			<div class="foot"></div>
		</s:form>
		
	</div>
    <%@include file="/common/foot.jsp"%>
</body>
</html>