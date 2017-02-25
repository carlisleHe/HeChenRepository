<script type="text/javascript">
    var myCount=${parameters.leftTime};
    var leftCount=${parameters.leftCount};
	var objTimer;
	var labelValue='<@s.text name="${parameters.labelValue}"/>';
	labelValue=labelValue.replace(/\:|\：|[ ]/g,"");
        var smsHasSended=false;
	<#if !parameters.seqence??||parameters.seqence=="">
	myCount=0;
	</#if>
        $(function(){
        	<#if parameters.sendLater == false>
             $("#"+"${parameters.id}").addClass("validate[custom[onlyNumber],required,codeLength[${parameters.maxLength}]]");
            <#else>
             $("#"+"${parameters.id}").addClass("validate[custom[onlyNumber],required,codeLength[${parameters.maxLength}],funcCall[hasSendSms]]");
            </#if>
             <#if parameters.telPhoneId??>
             	$("#"+"${parameters.telPhoneId}").attr("alt","mobile").setMask({selectCharsOnFocus:false});
             </#if>
        })
	function sendSms(){
                <#if parameters.telPhoneId??>
                    var telPhoneValue=document.getElementById('${parameters.telPhoneId?default("hSendSms")}').value;
                    var reg=new RegExp("(^[0-9]+$)|^$");
                    if(telPhoneValue==""){
                        jalert('手机号不能为空');
                        return false;
                    }
                    if(!reg.test(telPhoneValue)){
                         jalert('手机号必须是数字');
                        return false;
                    }
                    if(telPhoneValue.length!=11){
                        jalert('手机号必须是11位');
                        return false;
                    }
                </#if>
		var btnSendSms = $("#btnSendSms");
		if (btnSendSms.prop("disabled")) {
			return false;
		}
		btnSendSms.prop("disabled",true);
		$.ajax({
			//async:false, 
            <#if parameters.fromOut == false>
            url: '${parameters.ctx?default("/pers/main")}/base/sendSms.do',
            <#else>
            url: '${parameters.ctx?default("/pers/main")}/base/sendOutSms.do',
            </#if>
            <#if parameters.telPhoneId??>
			 data: {"telPhone":document.getElementById('${parameters.telPhoneId?default("hSendSms")}').value,"timestamp":new Date().getTime()},
			</#if>
            type:'POST',
            success:function(msg){
				if(typeof(msg.error) == "undefined" || msg.error == false){
                               if(msg.showSeqence){
                                    $("#seqenceInfo").html("（请输入您手机收到的顺序号为"+msg.seqence+"的6位数"+labelValue+"）");
                                }
                    smsHasSended = true;
                    if (myCount == 120 || myCount <= 0) {
                    	myCount=120;
                    	$("#lefttime").html("（剩余有效时间120秒）");
                    	objTimer = window.setInterval("backwardCount()",1000);
                    	btnSendSms.html("已发送").css("color","grey");
                    } else {
                    	myCount=120;
                    }
				}else{
					jalert(msg.msg);
					btnSendSms.prop("disabled",false).css("color","blue");
				}
            },
            error:function(){
				jalert("服务器超时，请重试！");
				btnSendSms.prop("disabled",false).css("color","blue");
            },
            timeout:30000
        })
        return false;
	}
    function backwardCount(){
		var btnSendSms = $("#btnSendSms");
		var lefttime = $("#lefttime");
		btnSendSms.prop("disabled",true);
		if(myCount==0||leftCount=="0"){
			lefttime.html("（已失效）");
			$("#seqenceInfo").html("");
			btnSendSms.html("<@s.text name='${parameters.btnResendValue}'/>");
			btnSendSms.prop("disabled",false).css("color","blue").css("text-decoration","underline");
			myCount=120;
			leftCount="6";
			window.clearInterval(objTimer);
		}else{
			lefttime.html("（剩余有效时间" + myCount + "秒）");
			if (myCount > 30) {
				btnSendSms.html("已发送");
				btnSendSms.css("color","grey").css("text-decoration","none");
			} else {
				btnSendSms.html("<@s.text name='${parameters.btnResendValue}'/>");
				btnSendSms.prop("disabled",false).css("color","blue").css("text-decoration","underline");
			}
			myCount--;
		}
    }
    <#if parameters.sendLater&&parameters.seqence??&&parameters.seqence!="">
        smsHasSended=true;
        $(document).ready(function(){
    	objTimer = window.setInterval("backwardCount()",1000);
    })
    </#if>
    <#if parameters.sendLater == false>
    $(document).ready(function(){
    	objTimer = window.setInterval("backwardCount()",1000);
    })
    </#if>
    <#if parameters.sendLater == true>
    function hasSendSms(){
    	var smsVal = $("#${parameters.id?default('hSendSms')}").val();
    	if(smsVal != undefined && $.trim(smsVal) != "" ){
	    	if (smsHasSended==false) {
	    		return "* 请先<@s.text name='${parameters.buttonValue}'/>";
	    	}
    	}
    }
  	</#if>
</script>
<p<#rt/>
<#if parameters.pClass??>
 class="${parameters.pClass}"<#rt/>
</#if>
><label<#rt/>
<#if parameters.labelClass??>
 class="${parameters.labelClass}"<#rt/>
</#if>
>
<#if parameters.noStar == false>
<span class="highlight">*</span><#rt/>
</#if>
<@s.text name="${parameters.labelValue}"/></label>
<input type="text" id="${parameters.id?default("")}"<#rt/>
<#if parameters.inputClass??>
 class="${parameters.inputClass}"<#rt/>
</#if>
<#if parameters.inputValue??>
 value="${parameters.inputValue}"<#rt/>
</#if>
<#if parameters.maxLength??>
 maxlength="${parameters.maxLength}"<#rt/>
</#if>
 name="hsendSms"/>
<#if parameters.receiptBusiness == false>
<span id="lefttime"></span><#rt/>
</#if>
<a id="btnSendSms" href="#" class="linka" onclick="${parameters.onClick}" ondblclick="${parameters.onClick}"><@s.text name="${parameters.buttonValue}"/></a><#rt/>
<#if parameters.receiptBusiness == false>
<span id="seqenceInfo"><#rt/>
<#if parameters.noSeqence == false&&parameters.seqence??&&parameters.seqence!="">
<script type="text/javascript">
document.write("（请输入您手机收到的顺序号为${parameters.seqence}的6位数"+labelValue+"）");
</script><#rt/>
</#if>
</span><#rt/>
</#if>
 <#if parameters.freeInfo>
 <span style="color:#1D6402;"><@s.text name="sms_free_info"/></span>
 </#if>
</p>