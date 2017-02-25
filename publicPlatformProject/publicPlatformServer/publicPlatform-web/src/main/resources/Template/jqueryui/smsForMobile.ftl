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
            var inputArea=$("#"+"${parameters.id}");
            $("#"+"${parameters.id}").focus(function() {
            	if(myCount>0){
					inputArea.attr("placeholder","请输入");
				}
			}); 
			$("#"+"${parameters.id}").blur(function() {
				if(myCount>0&&myCount<120){
					inputArea.attr("placeholder","短信已发送("+myCount+"s)");
				}else{
					inputArea.attr("placeholder","超时,请点击重新获取");
				}
				
			}); 
        })
    var inputArea=$("#"+"${parameters.id}");
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
            url: '<@s.property value="#attr.ctx"/>/base/sendSms.do',
            <#if parameters.telPhoneId??>
			 data: {"telPhone":document.getElementById('${parameters.telPhoneId?default("hSendSms")}').value,"timestamp":new Date().getTime()},
			</#if>
            type:'POST',
            success:function(msgInfo){
				if(typeof(msgInfo.error) == "undefined" || msgInfo.error == false){
                    smsHasSended = true;
                    if (myCount == 120 || myCount <= 0) {
                    	myCount=120;
                    	inputArea.attr("placeholder","短信已发送(120s)");
                    	
                    	objTimer = window.setInterval("backwardCount()",1000);
						$("#btnSendSms").removeClass("nbt").addClass("hbt");
                    } else {
                    	myCount=120;
                    }
				}else{
					msg(msgInfo.msg); 
					btnSendSms.prop("disabled",false).removeClass("hbt").addClass("nbt");
				}
            },
            error:function(){
				msg("服务器超时，请重试！");
				btnSendSms.prop("disabled",false).removeClass("hbt").addClass("nbt");
            },
            timeout:30000
        })
        return false;
	}
    function backwardCount(){
		var btnSendSms = $("#btnSendSms");
		var inputArea=$("#"+"${parameters.id}");
		//var lefttime = $("#lefttime");
		btnSendSms.prop("disabled",true).removeClass("nbt").addClass("hbt");
		if(myCount==0||leftCount=="0"){
			//lefttime.html("（已失效）");
			
			inputArea.attr("placeholder","超时,请点击重新获取");
			btnSendSms.val("<@s.text name='${parameters.btnResendValue}'/>");
			btnSendSms.prop("disabled",false).removeClass("hbt").addClass("nbt");
			myCount=120;
			leftCount="6";
			window.clearInterval(objTimer);
		}else{
			inputArea.attr("placeholder","短信已发送("+myCount+"s)");
			if (myCount > 60) {
				//btnSendSms.html("已发送");
				//btnSendSms.css("color","grey").css("text-decoration","none");
			} else {
				btnSendSms.val("<@s.text name='${parameters.btnResendValue}'/>");
				btnSendSms.prop("disabled",false).removeClass("hbt").addClass("nbt");
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
<input type="button" id="btnSendSms"  class="hbt" onclick="${parameters.onClick}" ondblclick="${parameters.onClick}" value="${parameters.buttonValue}">
<#rt/>
</p>