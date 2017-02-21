<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/meta.jsp"%>
<html>
<head>
<title><s:text name="APP_NAME"/></title>
<%@ include file="/common/header.jsp"%>
<script>
	$(function() {
		$('#reset').click(function() {
			$('#cardId').val('');
			$('#cardId').focus();
			$('#reset').hide();
			return false;
		});

		$('#cardId').keyup(function() {
			if ($(this).val() != '') {
				$('#reset').show();
			}
		});
		
		$('#cardId').blur(function() {
			
			var limitV = $(this).val();		
			$(this).val(limitV.replace(/[^0-9]/g, ""));

			var formatV = $(this).val();
			var re = /(\d{6})(-?\d+)/;
			if(formatV.length ==16){
				re = /(\d{4})(-?\d+)/;
			}
			while (re.test(formatV)) {
				formatV = formatV.replace(re, "$1 $2");
			}
			var returnV;
			if(formatV.length >20 )
				returnV = formatV.substring(0,20);
			else
				returnV = formatV;
			$(this).val(returnV);
		});
	});

	function beforeSubmit(){
		
	    var accountNo = $("#cardId").val().replace(/\ /g,"");
	    $("#accountNo").val(accountNo);
	    var certNo = $("#certNo").val();
	    if ( accountNo.search("[^ \t]") == -1 ){
	    		msg("亲，要输入卡号哦");
	    		return false;
	    }
	    if(certNo.search("[^ \t]") == -1 ){
	    	msg("亲，要输入证件号哦");
	    	return false;
	    }
	    if(accountNo.search("[^0-9]") != -1){
	    	msg("亲，卡号一定要输数字哦");
	    	return false;
	    }
	   // if($("#cardId").val().replace(/\ /g,"").length<10||$("#cardId").val().replace(/\ /g,"").length>18||$("#cardId").val().replace(/\ /g,"").length==17){
	    //	msg("卡号不是兴业账号");
	   // 	return false;
	  //  }
	    if(certNo.length<6){
	    	msg("亲，证件号输错啦");
	    	return false;
	    }
			return true;
	}
	
    function refreshYard(yardIMG) { //换验证码
		yardIMG.src = "${ctx}/AddInImage?imageId?=" + new Date().getTime();
	}
	
	  
	
</script>

</head>

<body >
	<div class="wrap">
		<s:form id="CardForm" action="bankCardManager!addAcct.do"  namespace=""  method="post">
			<div class="tit1">亲，请告诉我您要绑定的银行卡信息</div>
			<ul class="lisbox">
				<li>
				   <style>
				   .ft_size3{display:inline-block;width:57px}
				   .txts{height:24px;border:none;outline:none;font-size: 16px;font-weight: bold;color:#000;}
				   </style>
					<span class="bdmc ft_size3">卡号</span> 
					<s:textfield id="cardId"  cssClass="txtc" maxlength="20" name="authCardView.acctNoInput"   placeholder="理财卡或信用卡卡号"></s:textfield> <img style="display:none" class="reset" id="reset" src="resources/images/reset.png" width="14" alt="" />
				   <input type="hidden" id="accountNo" name="authCardView.acctNo" />
				</li>
				<s:if test="#session.needCaptcha==1">
					<li>
						<span class="bdmc ft_size3">证件号</span> 
						<s:textfield  cssClass="txtc" name="authCardView.certNo" id="certNo" placeholder="身份证可只输入后6位"> </s:textfield>
					</li>
					<li class="last">
						<span class="bdmc ft_size3">附加码</span> 
						<s:textfield cssClass="txts" name="captchafield" id="yard" size="5" maxlength="4" ></s:textfield>
						<img id="yardIMG" height="24" style="padding-left:5px;vertical-align: middle; _padding-bottom: 5px;"
						<s:if test="#session.needCaptcha==1">
							src="${ctx}/AddInImage?imageId='<%=new java.util.Date()%>' "
						</s:if>/> 
						<a href="#" onclick="refreshYard(document.getElementById('yardIMG'));" tabindex=-1 ">换一张</a>
					</li>
				</s:if><s:else>
					<li class ="last">
						<span class="bdmc ft_size3">证件号</span> 
						<s:textfield  cssClass="txtc" name="authCardView.certNo" id="certNo" placeholder="身份证可只输入后6位"> </s:textfield>
					</li>					
				</s:else>
			</ul>
			<div class="btnbox">
				<s:token></s:token>
				<s:submit value="下一步" cssClass="nxtbt" onclick="return beforeSubmit();"></s:submit>
			</div>
			<div class="foot"></div>
		</s:form>
	</div>
    <%@include file="/common/foot.jsp"%>
</body>
</html>