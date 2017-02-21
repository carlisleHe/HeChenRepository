<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/meta.jsp"%>
<html>
<head>
<meta charset="utf-8">
<title><s:text name="APP_NAME"/></title>
<%@ include file="/common/header.jsp"%>
<%@ include file="/common/encrypt.jsp"%>
<link rel="stylesheet"  href="${ctx}/resources/css/css.css" type="text/css"/>
<link rel="stylesheet" href="${ctx}/resources/css/validationEngine.jquery.css" type="text/css"/>
<link rel="stylesheet" href="${ctx}/resources/css/mobiscroll.custom-2.6.3.min.css" type="text/css" />
<script type="text/javascript" src="${ctx}/resources/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jquery.validationEngine.js" charset="utf-8"></script>
<script type="text/javascript" src="${ctx}/resources/js/languages/jquery.validationEngine-en.js" charset="utf-8"></script>
<script type="text/javascript" src="${ctx}/resources/js/hsm_cli.js" charset="utf-8"></script>
<script type="text/javascript" src="${ctx}/resources/js/mobiscroll.custom-2.6.3.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${ctx}/resources/js/zepto.min.js" charset="utf-8"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<meta name="viewport" content="user-scalable=no,initial-scale=1.0, maximum-scale=1.0"/>
<meta name="apple-mobile-web-app-capable" content="yes"/>
<meta name="format-detection" content="telephone=no">
<meta name="apple-mobile-web-app-status-bar-style" content="black-translucent"/>
<meta name="apple-touch-fullscreen" content="yes"/>


<!-- 后台弹出提示框定位 -->
<s:if test='actionMessages.size>0'>
	<script type="text/javascript">
        $(document).ready(function(){
           document.getElementById('psform').scrollIntoView();
        });
	</script>
</s:if>

<script type="text/javascript">

var height;
var position;
var useContracthHeight;
var appraisalHeight ;
$(function() {
	useContracthHeight = $('#useContract').height();
    height = $(document).height() - useContracthHeight;	
	position = document.documentElement.clientHeight/2;
})
function msg(msg){
	$('#tczz').show().css('height',height);
    $("#tccnr").html(msg);
 
	if(msg=="绑定成功"){
	   $(".tcctit").html("成功提示");
	   $("#tccnr").attr("style" ,"text-align:center" );
	}else{
	  document.getElementById('psform').scrollIntoView();
	  $(".tcctit").html("错误提示");
	}
	$('#tccbox').show().css({
		'top':position-($('#tccbox').height()/2),
		'left':($(document).width()/2)-($('#tccbox').width()/2)
	});
}
function tanGb(){
	var msg = $("#tccnr").html();
	if(msg=="绑定成功"){
		 var appId = $("#appId").val();
		 var openId = $("#openId").val();
	     document.location.href="${ctx}/authorization!list.do?appId="+appId+"&openId="+openId;	
	}else{
		$('.tczz').hide();
		$('.tccbox').hide();
	}
}
document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
	WeixinJSBridge.call('hideToolbar');
});

$(document).ready(function() {

	//回显风险评估答案
	var finRiskAns = $('#finRiskAns').val();
	if(finRiskAns != ""){
		//$("#already").text("已评估，可重新评估");
		var array = ["A","B","C","D","E","F"];
		var length = finRiskAns.toString().length;
		var i = 1;
		for(;i<=length;i++){
			var val = finRiskAns.toString().substring(i-1,i);
			var radioLength = $("input[name=checkbox"+i+"][type='radio']").length;
			var j = 1;
			for(;j<=radioLength;j++){
				if(val == array[j-1]){
					$("#checkbox"+i+"-"+j).parent("span").addClass("checked");
					break;
				}
			}
		}
	};
	//网银类型
     if($('#usertype').val()==0){
     	$('#blx_button').addClass("on");
     	$('#blx').show();
     	$('#jyx').hide();
     }else{
     	$('#jyx_button').addClass("on");
     	$('#blx').hide();
     	$('#jyx').show();
     }
    $(".yinhangtaocan span").click(function() {  
        $(".yinhangtaocan span").removeClass("on");
        $(this).addClass("on");
        $(".yinghangtaocanlei p").hide().eq($(this).index()).show();
        $("#usertype").val($(this).attr("index"));
        return false;  
    });
      
    var now = new Date();
    var year = now.getFullYear();
    var month = now.getMonth();
    var date = now.getDate();
  
	//日期控件
   $('#test_default').mobiscroll(
			{ 
			    minDate: new Date(year, month, date),
			    maxDate: new Date(year+20,month,date),
		    	preset : 'date',
				theme: 'default', 
				mode: 'clickpick', 
				display: 'modal', 
				lang: 'zh',
				dateFormat:'yymmdd',
			    dateOrder:'yymmdd',
				endYear:9999 //结束年份
			 }
	);
			 
	jQuery("#psform").validationEngine();//焦点移开立马校验
	$("#useContract").hide();//隐藏合约界面

  	 $(".check-radio dd").click(function(){
  			$(this).siblings().children("span").removeAttr("class") && $(this).children("span").attr("class","checked");
  			$(this).children("input[type='radio']").attr("checked","checked");
  	});
 });  
 
 	//查看合约界面
	function useContract(){
	    $("#useContract").show();
		$("#psform").hide();
		  document.getElementById('useContract').scrollIntoView();
	}
	
	//填写用户信息界面
	function writeInfo(){
		$("#psform").show();
		$("#useContract").hide();
	}
	
	//校验字符串长度（中文2，英文1）
	function checkLength(str){
		 var realLength = 0, len = str.length,charCode = -1;  
         for (var i = 0; i < len; i++) {  
            charCode = str.charCodeAt(i);  
            if (charCode >= 0 && charCode <= 128) realLength += 1;  
            else realLength += 2;  
         } 
         return realLength;
	
	}
	 
	 //提交校验
	 function beforeSubmit(){

		  var paypas1 = $("#paypas1").val();
	 	  var drawpas1 = $("#drawpas1").val();
	 	  var username = $("#username").val(); 
	 	  var useremail = $("#useremail").val();
	 	  var useradd = $("#useradd").val();
	 	  var userweixin = $("#userweixin").val();
	      if($("#test_default").val() == ""){
   	  			msg("身份证到期日期不能为空");
	     		return false;
	      }
	      if(username == ""){
   	  			msg("姓名不能为空");
	     		return false;
	      }
	      var len = checkLength(username);
	      if(len > 30){
   	  			msg("姓名过长");
	     		return false;
	      }
	      if(useremail == ""){
   	  			msg("电子邮箱不能为空");
	     		return false;
	      }
	       if(useremail.length > 30){
   	  			msg("电子邮箱过长");
	     		return false;
	      }
	      if(useradd == ""){
   	  			msg("家庭地址不能为空");
	     		return false;
	     }
	      len = checkLength(useradd);
	      if(len >60){
   	  			msg("家庭地址过长");
	     		return false;
	     }
	     if(userweixin == ""){
   	  			msg("微信号不能为空");
	     		return false;
	     }
	      len = checkLength(userweixin);
	      if(len >40){
   	  			msg("微信号过长");
	     		return false;
	     }
	      if($("#bankNo").val()==-1){
      			msg("请选择分行名称");
      			return false;
      	 } 
      	  if($("#nodeNo").val()==-1){
      			msg("请选择网点名称");
      			return false;
      	 }
      	 if(drawpas1 == ""){
      			msg("取款密码不能为空");
      			return false;
      	 }
      	  if(paypas1 == ""){
      			msg("支付密码不能为空");
      			return false;
      	 }
      	 if(!IssimplePwd2(drawpas1)){
			  msg("您输入的取款密码过于简单，请重置");
			  $('#drawpas1').val('');
			  $('#drawpas2').val('');
		      return false;
      	 }
      	 if(!IssimplePwd2(paypas1)){
			  msg("您输入的支付密码过于简单，请重置");
			  $('#paypas1').val('');
			  $('#paypas2').val('');
		      return false;
      	 } 
      	 if(drawpas1 !=$('#drawpas2').val()){
			  msg("两次输入的取款密码不一致，请重新输入");
			  $('#drawpas2').val('');
		      return false;
      	 }
      	 if(paypas1 != $('#paypas2').val()){
			  msg("两次输入的支付密码不一致，请重新输入");
			  $('#paypas2').val('');
		      return false;
      	 } 
	   var array = ["A","B","C","D","E","F"];
	   var i = 1;
	   var finRiskAns = "";
	   for(;i<=9;i++){
	     	var radioLength = $("input[name=checkbox"+i+"][type='radio']").length;
	     	var j = 1;
	    	for(;j<=radioLength;j++){
	    		if($("#checkbox"+i+"-"+j).parent("span").hasClass("checked")){
	    			$("#appraisal"+i).val(array[j-1]);
	    		}
	    	}
	   }
	    for(var i=1;i<=9;i++){
	    	if($("#appraisal"+i).val()== ""){
	    		msg("第"+i+"题未选");
	    		return false;
	    	}
	    	finRiskAns+=$("#appraisal"+i).val();
	    }
	    $("#finRiskAns").val(finRiskAns);
	 	//$("#psform").show();
		//$("#appraisal").hide();
		//$("#already").text("已评估，可重新评估");
		
		if(!$("#button-agree").prop("checked")){
   	  			msg("请同意借记卡领用合约");
	     		return false;
	     }
	    $("#netpayPassword").val(EncryptAPin(paypas1));
	    $("#password").val(EncryptAPin(drawpas1));
	    
	    return true;
	     
	 }
  
	 //异步响应函数    
    function search(para){   
//${ctx}/
       	$.post("${ctx}/opencard!geBankNode.do",
			        {bankNo:para},
					function(result){
						var nodeNo=document.getElementById("nodeNo");  
						nodeNo.length=0;   //先将下拉列表框清空   
						
						if(result.isSuccess){
							var bandanodes = result.bankNodes;
							var count = bandanodes.length;
						
				            for (var i = 0; i <count; i++){    
				                nodeNo.options.add(new Option(bandanodes[i].cnNodeName, bandanodes[i].nodeNo));   //将结果循环添加到下拉列表中   
				            } 
						}else{
						    nodeNo.options.add(new Option("==请选择网点名称==","-1"));
							msg(result.message);
						}
			}, "json");  
    } 
 
   
	 //下拉框改变事件  
	 function changeBankNote(){

	 	var nodeNo=document.getElementById("nodeNo");   

	    nodeNo.length = 0;   
	    if($('#bankNo').val() == -1){
	      nodeNo.options.add(new Option("==请选择网点名称==","-1"));   
	    }else{   
	         search($('#bankNo').val());   
	     }    
	    return;      
	 }
	 //保存风险评估
	 function stepSaveRiskAns(){

	  var array = ["A","B","C","D","E","F"];
	   var i = 1;
	   var finRiskAns = "";
	   for(;i<=9;i++){
	     	var radioLength = $("input[name=checkbox"+i+"][type='radio']").length;
	     	var j = 1;
	    	for(;j<=radioLength;j++){
	    		if($("#checkbox"+i+"-"+j).parent("span").hasClass("checked")){
	    			$("#appraisal"+i).val(array[j-1]);
	    		}
	    	}
	   }
	   
	    for(var i=1;i<=9;i++){
	    	if($("#appraisal"+i).val()== ""){
	    		$("#appraisal"+i).val(" ");
	    	}
	    	finRiskAns+=$("#appraisal"+i).val();
	    }
	    $("#finRiskAns").val(finRiskAns);

	 }
</script>
<s:actionmessage/>
</head>
<body>
	<!-- ---------  填写身份信息  -------------- -->
	<s:form id="psform" action="opencard.do" namespace="" method="post">
		<div class="topbar"> 远程开户 </div>
		<div class="p-con liucheng">
				<div><img src="${ctx}/resources/images/liucheng3.png"></div>
				<span>填写身份信息</span>
				<span>上传照片</span>
				<span class="on">填写开户信息</span>
				<span>视频验证</span>
		</div>
		<div class="tit1">请输入您的个人资料</div>
		<ul class="lisbox">
		  <li><span class="bdmc">身份证号码:</span>
		  	<s:property value="applyinfo.certNo"/>
		  </li>
		   <li><span class="bdmc">身份证到期日期:</span>
		    <input id="test_default" type="text" class="txtc" placeholder="请选择日期" name="applyinfo.certDueDate" value="<s:property value='applyinfo.certDueDate'/>"/>
		  </li>
		  <li><span class="bdmc">姓名:</span>
		    <input id="username" type="text" class="txtc" placeholder="请输入您的名字" name="applyinfo.custName" value="<s:property value='applyinfo.custName'/>"/>
		  </li>
		  <li><span class="bdmc">电子邮箱:</span>
		    <input id="useremail" type="text" placeholder="请输入常用邮箱地址"  class="validate[required,custom[email]] text-input txtc" name="applyinfo.email" value="<s:property value='applyinfo.email'/>"/>
		  </li>
		  <li><span class="bdmc">家庭地址:</span>
		  <textarea id="useradd" name="applyinfo.homeAddr" class="jtdz" placeholder="输入家庭地址" row="2" cols="15"><s:property value='applyinfo.homeAddr'/></textarea>
		  <div class="txt-info">（提示：此地址将作为银行卡收件地址）</div>
		  </li>
		  <li class="last"><span class="bdmc">微信号:</span>
		    <input type="text" class="txtc txtb" placeholder="微信号/QQ/手机号" id="userweixin" name="applyinfo.wxName" value="<s:property value='applyinfo.wxName'/>"/>
		   <%--  <span class="ico_zy"></span>--%>
		    <div class="txt-info">（提示：兴业银行客服人员将添加您为微信好友，并与您进行视频通话以便对您的身份进行核查，请务必确保正确填写微信号，否则将导致开卡失败）</div>
		  </li>
		</ul>
		<div class="tit1">请选择开户网点</div>
		<ul class="lisbox">
			<li>
				  <span class="bdmc">分行: </span>
                   	<s:select id="bankNo"  name="applyinfo.brNo" cssClass="lisbox-select" list="banks"  listValue="cnBankName" listKey="bankNo" headerKey="-1"
                   	headerValue="==请选择分行名称==" onchange="changeBankNote()"/>
		   </li>
		   <li>
		  		 <span class="bdmc ">网点:</span>
		   		  <select name="applyinfo.subBrNo" id="nodeNo">
		   		    <s:if test="applyinfo.subBrNo == -1">
		   		    	  <option value="-1">==请选择网点名称==</option>   
		   		    </s:if>
		   		    <s:if test='applyinfo.subBrNo != undefined'>  
		   		     	<s:iterator var="bn" value="#request.bankNodes" status="st">
		   		     		<s:if test='applyinfo.subBrNo == #bn.nodeNo'> 
		   		   		 		<option value="<s:property value='#bn.nodeNo'/>" selected="true"><s:property value="#bn.cnNodeName"/></option>  
		   		   		 	</s:if> 
		   		   		 	<s:else>
		   		   		 		<option value="<s:property value='#bn.nodeNo'/>"><s:property value="#bn.cnNodeName"/></option>  
		   		   		 	</s:else>
		   		   		 </s:iterator>
		   		    </s:if>
		   		    <s:else>
		   		  		  <option value="-1">==请选择网点名称==</option>   
		   		    </s:else>
				 </select> 
		   </li>
		</ul>
		<div class="tit1">请选择电子银行套餐（二选一）</div>
		<div class="p-con yhtc">
			<div class="yinhangtaocan">
				<span index="0" id="blx_button" class="thicker">便利型</span>
				<span index="1" id="jyx_button" class="thicker">精英型</span>
		    	<input type="hidden" id="usertype" name="applyinfo.mealContent" value="<s:property value='applyinfo.mealContent'/>"/>
			</div>
			<div class="yinghangtaocanlei">
			<p class="text-left" id="blx">
			个人网银：任意转账（日限额20万）、缴费<br>
			手机银行：任意转账（日限额5万）、缴费<br>
			网上支付：日限额5千
			</p>
			<p class="text-left" id="jyx">
			个人网银：任意转账（日限额100万）、缴费<br>
			手机银行：任意转账（日限额20万）、缴费<br>
			网上支付：日限额5万
			</p>
			</div>
		</div>
		<div class="tit1">请设置银行卡取款密码</div>
		<ul class="lisbox" id="qkps">
		  <li><span class="bdmc">银行卡取款密码:</span> 
		    <input type="password" class="validate[required,custom[password]] text-input txtc txtb" placeholder="请输入取款密码" id="drawpas1"/>
		    <input type="hidden" id="password" name="applyinfo.password"/>
		  </li>
		  <li class="last"><span class="bdmc">确认取款密码:</span>
		    <input type="password" class="validate[required,custom[password]] text-input txtc txtb" placeholder="再次输入取款密码" id="drawpas2" />
		  </li>
		  <div class="txt-err"></div>
		</ul>
		<div class="tit1">请设置网上支付密码</div>
		<ul class="lisbox" id="zfps">
		  <li><span class="bdmc">网上支付密码:</span> 
		    <input type="password" class="validate[required,custom[password]] text-input txtc txtb"  placeholder="请输入支付密码" id="paypas1"/>
		    <input type="hidden" id="netpayPassword" name="applyinfo.netpayPassword"/>
		  </li>
		  <li class="last"><span class="bdmc">确认支付密码:</span>
		    <input type="password"  class="validate[required,custom[password]] text-input txtc txtb" placeholder="再次输入支付密码" id="paypas2"/>
		  </li>
		  <div class="txt-err"></div>
		</ul>
	
		
		<!-- ---------  理财风险评估  -------------- -->
			<div class="tit1">请进行理财风险评估：</div>
		
		<input type="hidden" id="appraisal1" name="riskAns.ans1"/>
		<input type="hidden" id="appraisal2" name="riskAns.ans2" />
		<input type="hidden" id="appraisal3" name="riskAns.ans3"/>
		<input type="hidden" id="appraisal4" name="riskAns.ans4" />
		<input type="hidden" id="appraisal5" name="riskAns.ans5" />
		<input type="hidden" id="appraisal6" name="riskAns.ans6"/>
		<input type="hidden" id="appraisal7" name="riskAns.ans7"/>
		<input type="hidden" id="appraisal8" name="riskAns.ans8"/>
		<input type="hidden" id="appraisal9" name="riskAns.ans9"/>
		<input type="hidden" id="finRiskAns" name="applyinfo.finRiskAns" value="<s:property value='applyinfo.finRiskAns'/>"/>
	
		<div class="p-con">
			<p class="text-left">请协助回答下列问题。评估结果将有助于分析您对金融工具及投资目标的相关风险的态度。该风险评估问卷的有效期为一年，超过一年请您通过柜面、网上银行或手机银行等方式重新评估自身风险承受能力。此外，若发生可能影响自身风险承受能力（尤其是本问卷题目涉及的各项内容）的情况，请您通过柜面或网上银行方式主动重新评估自身风险承受能力。</p>
		</div>
			<div class="p-con">
			<dl class="check-radio">
				<dt>1、您的年龄是</dt>
				<dd><span class=""><label for="checkbox1-1">A.高于65岁（含）(0分)</label><input type="radio" id="checkbox1-1" name="checkbox1" value="A"/></span></dd>
		        <dd><span class=""><label for="checkbox1-2">B.51-64岁(2分)</label><input type="radio" id="checkbox1-2" name="checkbox1" value="B"/></span></dd>
		        <dd><span class=""><label for="checkbox1-3">C.18-30岁(4分)</label><input type="radio" id="checkbox1-3" name="checkbox1" value="C"/></span></dd>
		        <dd><span class=""><label for="checkbox1-4">D.31-50岁(6分)</label><input type="radio" id="checkbox1-4" name="checkbox1" value="D"/></span></dd>
			</dl>
		</div>
		<div class="p-con">
			<dl class="check-radio">
				<dt>2、 您的家庭年收入（折合人民币）为</dt>
				<dd><span class=""><label for="checkbox2-1">A.10万元（含）以下(0分)</label><input type="radio" id="checkbox2-1" name="checkbox2" value="A"/></span></dd>
		        <dd><span class=""><label for="checkbox2-2">B.10-30万元（含）(2分)</label><input type="radio" id="checkbox2-2" name="checkbox2" value="B"/></span></dd>
		        <dd><span class=""><label for="checkbox2-3">C.30-100万元（含）(4分)</label><input type="radio" id="checkbox2-3" name="checkbox2" value="C"/></span></dd>
		        <dd><span class=""><label for="checkbox2-4">D.100万元以上(6分)</label><input type="radio" id="checkbox2-4" name="checkbox2" value="D"/></span></dd>
			</dl>
		</div>
		<div class="p-con">
			<dl class="check-radio">
				<dt>3、在您的家庭年收入中，计划用于金融投资（储蓄存款除外）的比例为</dt>
				<dd><span class=""><label for="checkbox3-1">A.10%（含）以下(0分)</label><input type="radio" id="checkbox3-1" name="checkbox3" value="A"/></span></dd>
		        <dd><span class=""><label for="checkbox3-2">B.10%-25%（含）(2分)</label><input type="radio" id="checkbox3-2" name="checkbox3" value="B"/></span></dd>
		        <dd><span class=""><label for="checkbox3-3">C.25%-50%（含）(4分)</label><input type="radio" id="checkbox3-3" name="checkbox3" value="C"/></span></dd>
		        <dd><span class=""><label for="checkbox3-4">D.50%以上(6分)</label><input type="radio" id="checkbox3-4" name="checkbox3" value="D"/></span></dd>
			</dl>
		</div>
		<div class="p-con">
			<dl class="check-radio">
				<dt>4、您的投资经验可以被描述为</dt>
				<dd><span class=""><label for="checkbox4-1">A.基本无投资经验。仅有银行存款经历(0分)</label><input type="radio" id="checkbox4-1" name="checkbox4" value="A"/></span></dd>
		        <dd><span style="word-warp:break-word;word-break:break-all"><label for="checkbox4-2">B.有限投资经验。除拥有银行存款外，还投资过国债、货币市场基金、债券型基金和银行保本型理财产品等低风险金融产品(2分)</label><input type="radio" id="checkbox4-2" name="checkbox4" value="B"/></span></dd>
		        <dd><span class=""><label for="checkbox4-3">C. 一般投资经验。除上述金融产品外，还投资过混合型基金、信托融资类银行理财产品等中等风险的金融产品(4分)</label><input type="radio" id="checkbox4-3" name="checkbox4" value="C"/></span></dd>
		        <dd><span class=""><label for="checkbox4-4">D.丰富投资经验。除上述金融产品外，还投资过外汇、股票、股票型基金等较高风险的金融产品。投资经验丰富，并倾向于自己作出投资决定(6分)</label><input type="radio" id="checkbox4-4" name="checkbox4" value="D"/></span></dd>
			</dl>
		</div>
		<div class="p-con">
			<dl class="check-radio">
				<dt>5、您有多少年投资股票、基金、外汇、金融衍生产品等风险投资品的经验？</dt>
				<dd><span class=""><label for="checkbox5-1">A.没有经验(0分)</label><input type="radio" id="checkbox5-1" name="checkbox5" value="A"/></span></dd>
		        <dd><span class=""><label for="checkbox5-2">B.少于2年（含）(2分)</label><input type="radio" id="checkbox5-2" name="checkbox5" value="B"/></span></dd>
		        <dd><span class=""><label for="checkbox5-3">C.2至5年（含）(4分)</label><input type="radio" id="checkbox5-3" name="checkbox5" value="C"/></span></dd>
		        <dd><span class=""><label for="checkbox5-4">D.5年以上(6分)</label><input type="radio" id="checkbox5-4" name="checkbox5" value="D"/></span></dd>
			</dl>
		</div>
		<div class="p-con">
			<dl class="check-radio">
				<dt>6、您对风险的态度是</dt>
				<dd><span class=""><label for="checkbox6-1">A.厌恶风险，不希望本金损失，希望获得稳定回报(0分)</label><input type="radio" id="checkbox6-1" name="checkbox6" value="A"/></span></dd>
		        <dd><span class=""><label for="checkbox6-2">B.可以承受有限风险，保守投资，愿意承担一定幅度的收益波动(2分)</label><input type="radio" id="checkbox6-2" name="checkbox6" value="B"/></span></dd>
		        <dd><span class=""><label for="checkbox6-3">C.可以承受较高风险，寻求较高收益，愿意为此承担有限本金损失(4分)</label><input type="radio" id="checkbox6-3" name="checkbox6" value="C"/></span></dd>
		        <dd><span class=""><label for="checkbox6-4">D.不介意风险，愿意承担为期较长期间的投资的负面波动(6分)</label><input type="radio" id="checkbox6-4" name="checkbox6" value="D"/></span></dd>
			</dl>
		</div>
		<div class="p-con">
			<dl class="check-radio">
				<dt>7、您的投资目的与收益期望值是</dt>
				<dd><span class=""><label for="checkbox7-1">A.在本金安全的情况下，投资收益必须达到我的最低要求，例如同期定期存款收益(0分)</label><input type="radio" id="checkbox7-1" name="checkbox7" value="A"/></span></dd>
		        <dd><span class=""><label for="checkbox7-2">B.在本金安全或者本金损失可能性极低的情况下，我愿意接受投资收益适当的波动，以便有可能获得大于同期存款的收益(2分)</label><input type="radio" id="checkbox7-2" name="checkbox7" value="B"/></span></dd>
		        <dd><span class=""><label for="checkbox7-3">C.在本金安全或者有较大保障的情况下，我愿意接受投资收益的波动，以便有可能获得大于同期存款的收益(4分)</label><input type="radio" id="checkbox7-3" name="checkbox7" value="C"/></span></dd>
		        <dd><span class=""><label for="checkbox7-4">D.愿意承担一定风险，以平衡的方式，寻求一定的资金收益和成长性(6分)</label><input type="radio" id="checkbox7-4" name="checkbox7" value="D"/></span></dd>
		        <dd><span class=""><label for="checkbox7-5">E.为获得一定的投资回报，愿意承担投资产品市值较大波动导致本金损失的风险(8分)</label><input type="radio" id="checkbox7-5" name="checkbox7" value="E"/></span></dd>
		        <dd><span class=""><label for="checkbox7-6">F.以获得高投资回报为目的，愿意承担投资产品市值高波动性而导致本金大比例损失的风险(10分)</label><input type="radio" id="checkbox7-6" name="checkbox7" value="F"/></span></dd>
			</dl>
		</div>
		<div class="p-con">
			<dl class="check-radio">
				<dt>8、您计划的投资期限是多久</dt>
				<dd><span class=""><label for="checkbox8-1">A.半年（含）以下，暂时对空闲资金进行保值操作，对其流动性要求极高(0分)</label><input type="radio" id="checkbox8-1" name="checkbox8" value="A"/></span></dd>
		        <dd><span class=""><label for="checkbox8-2">B.半年至1年（含），我可能会随时动用投资资金，对其流动性要求较高(2分)</label><input type="radio" id="checkbox8-2" name="checkbox8" value="B"/></span></dd>
		        <dd><span class=""><label for="checkbox8-3">C.1至2年（含），为获得满意的收益，我短期内不会动用投资资金(4分)</label><input type="radio" id="checkbox8-3" name="checkbox8" value="C"/></span></dd>
		        <dd><span class=""><label for="checkbox8-4">D.2年以上，为达到理想目标，我会持续地进行投资(6分)</label><input type="radio" id="checkbox8-4" name="checkbox8" value="D"/></span></dd>
			</dl>
		</div>
		<div class="p-con">
			<dl class="check-radio">
				<dt>9、您投资的产品价值出现何种程度的波动时，您会采取止损措施？</dt>
				<dd><span class=""><label for="checkbox9-1">A.本金无损失，但收益未达到预期(0分)</label><input type="radio" id="checkbox9-1" name="checkbox9" value="A"/></span></dd>
		        <dd><span class=""><label for="checkbox9-2">B.本金10%（含）以内的亏损(2分)</label><input type="radio" id="checkbox9-2" name="checkbox9" value="B"/></span></dd>
		        <dd><span class=""><label for="checkbox9-3">C.本金10%-30%（含）的亏损(4分)</label><input type="radio" id="checkbox9-3" name="checkbox9" value="C"/></span></dd>
		        <dd><span class=""><label for="checkbox9-4">D.本金30%-50%（含）的亏损(6分)</label><input type="radio" id="checkbox9-4" name="checkbox9" value="D"/></span></dd>
		        <dd><span class=""><label for="checkbox9-5">E.本金50%以上的亏损(8分)</label><input type="radio" id="checkbox9-5" name="checkbox9" value="E"/></span></dd>
			</dl>
		</div>
		<div class="p-con">
			<p class="text-left">
				投资者声明：<br>
				本风险评估测试完全由投资者本人独立、自主、谨慎完成，评估内容及结果为本人真实意愿表达无误。
			</p>
		</div>
	
		
		<div class="btnbox button-agree">
		       <label for="button-agree">我已认真阅读并同意<a href="javascript:useContract();" class="text-ablue">《借记卡领用合约》</a></label>
		       <input type="checkbox" id="button-agree"/>
		       <span class="checkbox"></span>
		</div>
		<div class="btnbox">
		   <s:submit id="posit" value="下一步" cssClass="nxtbt" onclick='return beforeSubmit();' method="writeInfo"/>
		   <s:submit value="上一步" cssClass="calbtn"  onclick='return stepSaveRiskAns();' method="setStepModifyPic"/> 
		</div>
	</s:form>
	
	<!-- ---------  兴业银行借记卡领用合约  -------------- -->
 	<div id="useContract">
		<div class="topbar">远程开户</div>
			<div class="p-con">
				<h2><span>兴业银行借记卡领用合约</span></h2>
				<h6><span>（2015年4月版）</span></h6>
				<hr width="100%" size="1">
				<p class="text-left">
					兴业银行（以下简称甲方）与兴业银行借记卡申领人（以下简称乙方）就乙方向甲方申领使用借记卡，在知悉并愿意共同遵守《兴业银行借记卡章程》（以下简称《章程》）前提下，就借记卡的申领、使用等事宜达成本合约。乙方在《兴业银行个人账户开户及综合服务申请表》上签名即视为乙方已知悉、理解并同意共同遵守《章程》及本合约。
			 		<br><br>
					<b>第一条 借记卡性质</b>
			 		<br><br>
					（一）借记卡是甲方发行的具有转账结算、存取现金等功能的支付工具，同时也是个人综合理财工具，不具备透支功能。
					 <br>
					（二）借记卡按信息载体分为磁条卡、IC卡和磁条IC复合卡，其中IC卡和磁条IC复合卡统称为IC卡。
					 <br>
					（三）IC卡的有效期为八年，磁条卡的有效期为二十年。有效期截至卡面标注年月的最后一天。有效期届满前，乙方应及时办理换领手续。
					 <br>
					（四）乙方同意借记卡中的人民币活期账户自动设置为个人银行结算账户，保证其使用遵守《人民币银行结算账户管理办法》及金融监管机构其他账户管理的有关规定。
					 <br>
                                                     （五）乙方借记卡内存款按照中国人民银行的有关规定计付利息。
					 <br><br>
					 <b>第二条 申请</b>
			 		 <br><br>
			 		（一）乙方保证向甲方提供的开户证件符合《个人存款账户实名制规定》的规定，并保证所填写的个人资料是真实和合法的。若由代办人代为开立借记卡，则代办人保证向甲方提供的借记卡申请人、本人证件及相关文件均符合《个人存款账户实名制规定》的规定，保证所填写的借记卡申请人和代办人资料是真实和合法的，保证借记卡申请人遵守本《领用合约》的规定。
					 <br>
					（二）乙方同意甲方收集、使用乙方个人资料。非出于法律、法规、司法机关等有权机构的要求或为乙方提供与借记卡服务有关的目的，甲方不得将资料披露给未经乙方同意的第三方。
					 <br>
					（三）为保障乙方资金安全，乙方留存在甲方的个人信息发生变动时，应及时前往甲方营业网点办理个人信息变更手续。否则，由此造成业务不能及时处理等后果，由乙方自行承担相关责任。
					 <br>
					（四）为保证借记卡在使用时被顺利受理，乙方收妥卡片时应立即在卡片背面的签名条签名。
					 <br>
					（五）乙方不得出租、出借、出售甲方发行的借记卡和网盾、令牌等安全认证工具，否则乙方将承担由此产生的后果、损失以及相应的法律责任。
			 		 <br><br>
					 <b>第三条 账户管理</b>
			 		 <br><br>
			 		 （一）借记卡资金支取方式为凭密码支取，密码为甲方识别乙方身份的唯一依据，所有通过密码发出的交易指令均视为乙方本人办理。
					 <br>
					（二）乙方开户后自动享有通存通兑、柜面通、刷卡消费、卡内转账、电子银行等功能。若甲方对借记卡新推出其他服务功能，除甲方规定需要到甲方柜台办理确认的以外，乙方已申请的借记卡自动享有该项服务功能。
					 <br>
					（三）乙方应妥善保管卡片及账号、密码等资料，不得将借记卡转借他人使用。因乙方泄露个人资料、密码或将账户转借他人使用造成的风险及损失由乙方承担。
					 <br>
					（四）乙方办理支付结算时，应持审慎的态度，妥善保管和使用票据及结算凭证，审验票据和结算凭证上的签章以及个人有效身份证件。甲方以善意且符合规定的操作程序审查票据、结算凭证及个人有效身份证件，未能识别出伪造、变造的票据、结算凭证或身份证件而错误付款，不属于票据法第五十七条规定的“重大过失”，甲方不再承担受委托付款的责任，给乙方造成的损失由乙方自行承担，甲方不承担民事责任。
					 <br>
					（五）乙方借记卡遗失、被盗，或交易密码泄露、被改、遗忘时，乙方应尽快向甲方申请挂失或密码重置。若挂失前或密码重置前或挂失失效后（口头挂失五日内有效）资金被他人盗用、支取，由乙方自行承担可能造成的一切损失。
					 <br>
					（六）乙方可到兴业银行任一营业网点办理借记卡销户。销户前应取消借记卡的所有代理业务关系，且卡内的所有账户应全部支取，若有未结清账户则不允许销户。销户后，借记卡无法办理任何业务，其中销户前芯片信息可以正常读取的IC卡，由甲方将卡片剪角后交还乙方；销户前芯片信息无法正常读取的IC卡，由甲方收回并做销毁处理；磁条卡交还乙方，由乙方自行剪角作废。兴业e卡仅在申领并启用实物卡的情况下方可销户。
					 <br>
					（七）乙方同意甲方保留对所有账户余额为零且两年内无乙方主动发起账务类交易的借记卡予以销户的权利。
					 <br>
					（八）乙方应定期通过柜台、网上银行、手机银行、电话银行、自助设备、对账单、对账折等与甲方核对账务。账务核对实行对账回单默认制度，即在乙方收到账务信息后3个月内未提供对账回单或确认信息，视同乙方对甲方提供的账务记录无异议。
					 <br>
					（九）如在甲方批处理日切期间，乙方通过自助渠道办理相关业务，发生实际日期与甲方账务日期不一致的情况，以甲方的账务日期为准。
			 		 <br><br>
					 <b>第四条 主要功能</b>
			 		 <br><br>
			 		 （一）电子现金
						1、电子现金适用于小额快速支付业务，是IC卡特有的功能，具备脱机消费、圈存、充值和查询等功能，支持接触式和非接触式交易。
						2、电子现金账户余额上限为1000元人民币，账户不得透支、不计付利息、不提取现金、不挂失。
						3、电子现金账户内资金只可用于脱机消费。乙方可通过甲方营业网点、“银联”标识ATM、自助设备等办理充值、圈存交易，将资金转入电子现金账户。借记卡内活期主账户为电子现金默认的绑定账户。
						4、如卡片丢失，电子现金账户内的资金损失由乙方承担；如卡片损坏，乙方应及时办理换卡或销户。如办理换卡，电子现金账户内的资金将在乙方办理换卡领取新卡12个自然日后转入新借记卡内的补登账户；如办理销户，电子现金账户内的资金将在乙方办理销户12个自然日转入乙方指定的甲方发行的借记卡内活期主账户。退还金额以12个自然日后的清算余额为准。
						5、“第4点”所述退还资金以及电子现金消费退货资金将转入借记卡内的补登账户。乙方可通过甲方渠道办理补登圈存，将资金转入电子现金账户。
					 <br>
					（二）ATM取款和转账
						1、乙方可持借记卡在境内外贴有“银联”标识的ATM上办理取款。境内默认每日限额2万元（含）人民币；境外默认每日限额等值1万元（含）人民币。
						2、乙方可持借记卡申请开通ATM转账功能。开通后可在甲方以及部分贴有“银联”标识的ATM上办理转账业务。可接受转账的银行范围以银联电子支付服务有限公司公布的内容为准。每日转账金额不得超过5万元人民币。
				     <br>
					（三）刷卡消费
						1、乙方可持借记卡在境内外贴有“银联”标识的POS机刷卡消费。若因通讯异常、银行卡组织系统故障、银行卡组织商户培训和管理等非甲方原因造成刷卡消费障碍，甲方不承担责任。
						2、乙方可持借记卡办理小额免签消费，即在中国银联指定的商户内（主要为便利店、超市、快餐、停车场等类型商户），对消费金额在200元以下（含）的交易可免除在签购单上签名（仍需要输入密码）。
						3、乙方应在确保获得商品或服务的情况下，办理刷卡消费。在输入交易密码前，应仔细核对消费金额。交易完成后，应在签购单上签名，并妥善保管签购单。对于小额免签消费，无需在签购单上签名，可根据需要向商户索取签购单。
						4、乙方在办理刷卡消费时，如商户要求多次（2次及以上）输入交易密码，应确认每笔交易的结果，避免多次交易重复扣款。
						5、乙方与商户发生交易纠纷由双方自行解决，甲方只有协助调查的义务而不承担其他责任。
					 <br>
					（四）卡内转账
						乙方可通过网上银行、手机银行、电话银行等渠道办理借记卡内定活期等账户间的转账。
					 <br>
					（五）柜面通
						乙方可凭借记卡在当地加入“柜面通”系统的银行网点办理存取款等业务，乙方同意遵守当地柜面通业务管理的有关规定。
					 <br>
					（六）信用卡关联还款
						1、乙方委托甲方按信用卡账单周期将应还款项的全部或预定部分从借记卡活期账户转入信用卡账户。由于乙方借记卡活期账户余额不足造成还款不成功，乙方自行承担责任。乙方指定的信用卡必须为甲方所发行。
	                 <br>				
					（七）预约转账
						1、预约转账是指乙方委托甲方按约定金额、时间等条件向事先约定的账户转账，并按约定的条件终止转账。
						2、乙方应准确指定转入账户、转入账户开户银行和转账金额等内容，并保证所填写内容真实、准确。预约转出账户内应保持足够的余额，如因乙方指定账户错误或处于非正常状态或因账户余额不足等原因造成风险或损失，由乙方自行承担责任。
					 <br>
					（八）固话终端转账
						乙方在甲方营业网点申请开通固话终端转账功能后，可通过固定电话支付终端上办理跨行转账业务。可接受转账的银行范围以银联电子支付服务有限公司公布的内容为准。
					 <br>
					（九）银联在线支付
						乙方在甲方营业网点申请开通银联在线支付功能后，可通过不具备读卡功能的自助交易终端完成对交易涉及资金进行支付的业务，包括互联网、手机、数字机顶盒等渠道。
					 <br>
					（十）短信口令
						1、短信口令是指乙方在使用甲方网上银行、手机银行、电话银行办理转账汇款、缴费、支付等业务时，甲方系统自动发送到乙方指定签约手机号的一次性随机密码。
						2、乙方登录网上银行可使用短信口令自助开通、关闭或修改网上银行、手机银行、电话银行的转账、支付、缴费等功能及限额。
						3、乙方必须保证签约短信口令的手机号为乙方本人名下手机号。否则由此造成的损失和风险由乙方承担。
						4、若由于通信异常或不可抗力因素造成乙方未能及时收到短信或短信信息错误，甲方不承担责任。
					 <br>
					（十一）网盾
						网盾是用于存放互联网银行客户身份标志并对客户发出的互联网银行交易指令进行数字加密和数字签名的安全认证工具。
					 <br>
					（十二）令牌
						令牌是与电子银行客户绑定的动态口令认证客户端，是通过一次一密的方式对客户发出的电子银行交易进行确认的一种安全认证工具。
					 <br>
					（十三）精灵信使
						1、精灵信使是甲方利用移动通信运营商的网络为乙方提供的短信金融业务，在账户资金发生变动、预约转账自动扣款失败、交易密码输错时即会收到手机短信通知，动户通知金额起点暂定为100元。目前仅支持人民币活期账户资金变动通知。乙方还可使用网上银行、手机银行、电话银行等自助设置余额超限通知、基金净值通知、贵金属行情通知等短信服务。
						2、乙方必须指定接收短信的手机和手机持有人，接收人可以为本人或他人。若乙方指定的相关信息有误，由此产生的问题甲方不承担相关责任。
						3、乙方同意甲方利用精灵信使服务向乙方发送通知、通告或其他服务信息。
						4、乙方同意精灵信使仅作为乙方账户信息参考，不作为最终对账依据或存款证明。若由于系统、通信异常等不可抗力因素造成乙方未能及时收到短信或短信信息错误，甲方不承担责任。
					 <br>
					（十四）网上支付
						1、乙方凭借记卡可开通网上银行、手机银行等渠道的网上支付功能，并在经甲方认证的互联网商户办理电子支付。一切由于商品质量、送货服务等引起的争议均由乙方及商户自行协商解决，甲方只有协助调查的义务而不承担其他责任。
						2、乙方在办理网上支付业务时，应妥善保管账号、密码、安全认证工具等，若因非甲方原因造成账号、密码、短信口令、网盾、令牌等泄露或遗失等，由乙方承担风险和损失。
					 <br>
					（十五）转账汇款
						1、转账汇款按照是否只能向事先约定的账户转账分为定向转账和任意转账。定向转账和任意转账在同一渠道（网上银行、手机银行、电话银行）不能同时开通。
						2、定向转账是指乙方经过与甲方约定，可通过网上银行、手机银行、电话银行向约定的兴业银行或他行账户转账。定向转账需要事先通过甲方柜台或凭借短信口令、网盾等安全认证工具在甲方指定的电子银行渠道开通并设定转入账户。交易时须使用短信口令、网盾、令牌等工具进行安全认证。乙方必须准确指定约定转入账号、户名和日转账限额等内容，若由于乙方指定错误造成风险或损失，由乙方自行承担责任。
						3、任意转账是指通过电子银行渠道向任意的兴业银行或他行账户转账。任意转账需要事先通过甲方柜台或凭借短信口令、网盾等安全认证工具在甲方指定的电子银行渠道开通。交易时须使用短信口令、网盾、令牌等工具进行安全认证。
					 <br>
					（十六）自助缴费
						1、乙方成功申请自助缴费功能后，可通过网上银行、手机银行、电话银行自助缴纳公共事业费等。
						2、乙方成功开通代缴费功能后，同意委托甲方按约定周期代扣账户资金代缴公共事业费等。
						3、缴费项目以甲方当地机构公布的或网上银行、手机银行、电话银行提供的为准。
					 <br>
					（十七）智能通知存款
						1、智能通知存款分为便捷型和收益型两款产品。“智能通知存款（便捷型）”是指将理财卡内人民币活期账户自动关联为“智能通知存款（便捷型）”账户，系统根据账户余额变动情况，对达到5万元的资金智能选择一天或七天的通知存款类型进行计息。“智能通知存款（收益型）”是采用“智能通知存款（收益型）”专户，按乙方约定自动将人民币活期账户中扣除预留金额后，超过5万元（含）的存款以千元整数倍转入专户，并按实际存款天数自动选择合适的存期及通知利率。
						2、“智能通知存款（便捷型）”以每季度末月20日为结息日，“智能通知存款（收益型）”满七天自动结息和转存，转存时将乙方活期账户内可用余额扣除预留金额后的千元整数倍资金转入通知账户，与当期本金以及通知利息计入下期通知存款本金。
						3、智能通知存款业务开办期间，甲方按照监管部门的相关政策，调整计结息方式或相关业务规定，无须事先征得乙方同意。
						4、乙方在开通收益型智能通知存款功能后，若乙方办理ATM取款、第三方存款银转证、POS消费以及信用卡自动还款交易时，发生活期账户余额不足，不足部分将自动从智能通知存款账户中支取。
					 <br>
					（十八）基金代销、外汇买卖、贵金属买卖、理财产品等其他功能请参照甲方相关规定办理。
			 		<br><br>
					<b>第五条 电子银行</b>
			 		<br><br>
			 		（一）乙方同意甲方为乙方提供网上银行、手机银行、电话银行等电子银行渠道服务。甲方可以根据业务需要增加、取消或调整电子银行渠道功能，除甲方要求需要到甲方网点办理确认或使用网上银行进行确认（凭短信口令或网盾办理）的功能外，已开立的账户自动享受其余的功能服务。
					 <br>
					（二）通过电子银行办理转账及支付业务时，乙方可以在相关规定的额度范围内自行设定限额，不同渠道（网上银行、手机银行、电话银行等）限额设置相互独立，互不影响。
					 <br>
					（三）乙方应按照甲方有关规定、操作流程办理电子银行业务。若因乙方拨打或登录非甲方认证的电子银行、下载非甲方认证的相关软件、在非安全地点或环境登录或以不符合甲方规定的流程登录电子银行造成风险或损失，由乙方自行承担责任。
					 <br>
					（四）乙方已知悉电子银行业务存在风险，乙方应妥善保管并谨慎使用账号、密码、安全认证工具、其他个人信息等信息资料。如因非甲方原因造成乙方的账号、密码、安全认证工具、其他个人信息泄露或遗失的，由乙方承担由此引发的风险和损失。
					 <br>
					（五）乙方申请手机银行服务时所使用的手机号应为本人名下号码。在事先得到手机号机主同意的情况下，乙方可以使用非本人名下的手机号码申请甲方提供的手机银行服务，但由此造成的损失和风险由乙方承担。
					 <br>
					（六）乙方对其在甲方留存的手机号发出的服务指令承担一切民事责任。乙方及其指定人必须妥善保管并正确使用指定手机号进行操作，所有使用上述手机号进行的操作均视为乙方及其指定人本人所为，如绑定的手机号或乙方的手机被他人借用或盗用，由此产生的全部风险和损失由乙方承担。
					 <br>
					（七）乙方经电子工具及互联网络和甲方电子银行系统连接相互传送各种符号、数字、字母等形式表达履行本协议意思的电子数据信息（包括但不限于客户指令、业务交易结果等），具有与书面合同等同的效力。
					<br><br>
					<b>第六条收费项目与收费标准</b>
			 		<br><br>	
			 		（一）乙方同意甲方根据《兴业银行零售客户服务价目表》、《政府指导价、政府定价价目表》（以甲方网站公布的为准）所列的项目和标准收取费用。当有关法律、法规和规章等发生变更时，或因经营需要，甲方将对收费项目和收费标准做出相应的调整，并于执行前依法按照规定提前发出公告。若乙方不愿接受公告内容的，应在公告内容正式实施前向甲方提出申请变更或终止相关服务，否则视为同意接受公告的各项内容。
                     <br>	                                 
                                                     （二）借记卡默认开通的功能中，部分功能涉及收费，以甲方网站公布的《兴业银行零售客户服务价目表》、《政府指导价、政府定价价目表》为准。
			 		<br><br>
					<b>第七条 免责条款</b>
			 		<br><br>	
			 		（一）在业务办理过程中，由于以下原因，导致甲方未能执行、未能及时执行或未能正确执行乙方交易指令等，甲方无须承担责任。
						1、乙方账户余额或信用额度不足；
						2、乙方账户内资金被法定有权机构冻结或扣划；
						3、乙方之行为是出于欺诈或其他非法目的；
						4、乙方发出的交易指令内容不明确或缺乏必要的交易信息，或未正确按照甲方规定交易的操作规则和程序发出交易指令；
						5、发生网络、通信、供电等非甲方原因造成的系统故障及其他不可抗力等原因导致交易无法进行或交易终止。
					 <br>
					（二）如因监管机构调整业务有关规定或遇到经济、突发事件等不可抗力导致本合约第四条所列各项业务变更或取消，甲方可按新规定执行或终止该业务，无需征得乙方同意。
					 <br>
					（三）乙方若违反本协议及相关规定造成的资金损失由乙方自行承担。
			 		<br><br>
					<b>第八条</b>
			 		<br><br>	
			 		乙方办理借记联名卡、兴业e卡等业务时，除需遵守以上条款外，同时还需遵守相关银行卡的管理规定。
			 		<br><br>
					<b>第九条 </b>
			 		<br><br>	
			 		 本合约及各项业务管理规定如有修改、调整，甲方将通过营业网点或网站等进行公告。公告期满后，修改后的合约、业务管理规定即为生效。在公告期内，乙方可以选择是否继续使用借记卡，乙方因对合约、业务管理规定的修改有异议而不继续使用借记卡的，可向甲方提出申请变更或终止相关服务或销卡申请，否则视为同意接受合约、业务管理规定的各项内容。
			 		<br><br>
					<b>第十条 </b>
			 		<br><br>
			 		凡因本合约引发的争议，甲乙双方应通过友好协商解决，协商不成的，任何一方均有权向被告住所地人民法院提起诉讼。
			 		<br><br>
					<b>第十一条 </b>
			 		<br><br>	
			 		本合约适用中华人民共和国有关法律和中国人民银行、中国银行业监督管理委员会等监管部门的有关规定，未尽事宜按照银行监管部门的有关规定及其它相关政策法规执行。
			 		<br><br>
					<b>第十二条 </b>
			 		<br><br>	
			 		本合约自乙方在《兴业银行个人账户开户及综合服务申请表》上签字，并经甲方核发借记卡之日起生效。
				</p>
			</div>
			<div class="btnbox">
			  <input type="button" value="返回" class="nxtbt" onclick="writeInfo();" />
		</div>
	</div>

	<%@include file="/common/foot.jsp"%>
</body>
</html>