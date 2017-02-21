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

	});
});
	function beforeSubmit() {
	var acct = $("#acct").val();
	var acctType = $("#acctType").val();
	var acctpwd = $("#acctpwd").val();
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
		$("#eacctpwd").val(EncryptAPin(acctpwd));
		return true;
	}
	  function toBack(){
          document.location='${ctx}/bankCardManager!showMod.do';
          $.blockUI({ message: '请稍后！' });
          return false;
      }
</script>
</head>

<body>
	<div class="wrap">
		<s:form id="psform" action="bankCardManager!openNotify.do" namespace="" method="post">
			<div class="tit1">开通微信动户通知：</div>
			<ul class="lisbox">

			  <li>
			  		<span class="bdmc" style="display:inline-block;width:67px">卡号</span> 
			  		<!-- <span class="bdmc"></span> -->
			       <input type="text" readonly class="txtc"  value="<s:property value="selAuthCardView.acctNoView"/>">
			  </li>
			  <li class="last">
					<s:if test="selAuthCardView.acctType==2">
						<span class="bdmc" style="display:inline-block;width:67px">查询密码</span> 
					</s:if>
					<s:else>
						<span class="bdmc" style="display:inline-block;width:67px">取款密码</span> 
					</s:else>
					<input type="password" class="txtc" id="acctpwd" maxlength="6" placeholder="六位有效数字" value="">
					<input type="hidden" id="eacctpwd" name="selAuthCardView.passWord" />
					<s:hidden id="acct"  value="%{selAuthCardView.acctNo}"></s:hidden>
					<s:hidden id="acctType"  value="%{selAuthCardView.acctType}"></s:hidden>
				</li>
			</ul>
			
			<div class="btnbox">
				<s:submit value="确认开通" cssClass="nxtbt" onclick="return beforeSubmit();"></s:submit>
				<s:submit value="取消"  onclick="return toBack();"  cssClass="calbtn"></s:submit>
			</div>
	     </s:form>
			<div class="foot"></div>

	</div>
    <%@include file="/common/foot.jsp"%>
</body>
</html>