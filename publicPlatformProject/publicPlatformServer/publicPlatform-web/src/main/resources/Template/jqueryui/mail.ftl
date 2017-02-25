<script type="text/javascript">
	var result;
	function sendEmail(){
                var email=document.getElementById('${parameters.id?default("hSendEmail")}').value;
                 var reg=new RegExp("(^[a-zA-Z0-9_\.\-]+\@([a-zA-Z0-9\-]+\.)+[a-zA-Z0-9]{2,4}$)|^$");
                // var reg = new RegExp("<@s.property value='@com.opensymphony.xwork2.validator.validators.EmailValidator@emailAddressPattern'/>");
                 if(email==""){
                    jalert('Email地址不能为空');
                    return false;
                }
                if(!reg.test(email)){
                    jalert('Email格式不对！');
                    return false;
                }
        var btnSendEmail = $("#btnSendEmail");
        if (btnSendEmail.prop("disabled")) {
			return false;
		}
		btnSendEmail.prop("disabled",true).css("color","grey");
		$.ajax({
            url: '${parameters.ctx?default("/pers/main")}/base/sendEmail.do',
            data: {"email":document.getElementById('${parameters.id?default("hSendEmail")}').value,"timestamp":new Date().getTime()},
            type:'POST',
            success:function(msg){
				if(typeof(msg.error) == "undefined" || msg.error == false){
					emailBackCount();
				}else{
					jalert(msg.msg);
					btnSendEmail.prop("disabled",false).css("color","blue");
				}
            },
            error:function(){
				jalert("服务器超时，请重试！");
				btnSendEmail.prop("disabled",false).css("color","blue");
            },
            timeout:60000
        })
	}
	var emailCount=30;
    function emailBackCount(){
		var btnSendEmail = $("#btnSendEmail");
		btnSendEmail.prop("disabled",true).css("color","grey");
		if(emailCount==0){
			btnSendEmail.html("<@s.text name='${parameters.buttonValue}'/>");
			btnSendEmail.prop("disabled",false).css("color","blue");
			emailCount=30;
		}else{
			btnSendEmail.html("<@s.text name='${parameters.sentMsg}'/>("+emailCount+")");
			emailCount--;
			window.setTimeout("emailBackCount()",1000);
		}
    }
</script>
<p<#rt/>
<#if parameters.pClass??>
 class="${parameters.pClass}"<#rt/>
</#if>
>
<label<#rt/>
<#if parameters.labelClass??>
 class="${parameters.labelClass}"<#rt/>
</#if>
><@s.text name="${parameters.labelValue}"/></label>
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
 name="hSendEmail"/>
 <a id="btnSendEmail" onclick="sendEmail();" href="javascript:void(0)"><@s.text name="${parameters.buttonValue}"/></a>
</p>