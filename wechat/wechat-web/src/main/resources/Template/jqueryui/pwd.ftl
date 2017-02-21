<#if parameters.needPwd>
<script type="text/javascript">
        $(function(){
             $("#${parameters.id?default("")}").addClass("validate[keyboard,custom[onlyNumber],custom[simplePwd],required,codeLength[${parameters.maxLength}]] left(22)");
        })
	pk = "${parameters.pk}";
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
>
<#if parameters.noStar == false>
<span class="highlight">*</span><#rt/>
</#if>
<@s.text name="${parameters.labelValue}"/>
</label>
<#if parameters.fromOut == false>
<input type="password" autocomplete="off" id="${parameters.id?default("")}" onpaste="return false;" oncontextmenu="return false;" onblur="if(this.value!=''){document.getElementById('hide${parameters.id?default("")}').value = parent.EncryptAPin(this.value);}"<#rt/>
<#else>
<input type="password" autocomplete="off" id="${parameters.id?default("")}" onpaste="return false;" oncontextmenu="return false;" onblur="if(this.value!=''){document.getElementById('hide${parameters.id?default("")}').value = EncryptAPin(this.value);}"<#rt/>
</#if>
<#if parameters.inputClass??>
 class="${parameters.inputClass}"<#rt/>
</#if>
<#if parameters.maxLength??>
 maxlength="${parameters.maxLength}"<#rt/>
</#if>
/>
<input type='hidden' id='hide${parameters.id?default("")}' name='${parameters.name?default("")}'/>
</p>
</#if>