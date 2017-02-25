<script type="text/javascript">
        $(function(){
             $("[name='${parameters.name}']").addClass("validate[custom[number2],required]");
        })
</script>
<input type="text" onpaste="return false;" onfocus="this.style.imeMode='disabled'" oncontextmenu="return false;" name="${parameters.name}" alt="amount" <#rt/>
<#if parameters.id??>
 id="${parameters.id}"<#rt/>
</#if>
<#if parameters.cssStyle??>
 style="${parameters.cssStyle}"<#rt/>
</#if>
<#if parameters.cssClass??>
 class="${parameters.cssClass}"<#rt/>
</#if>
<#if parameters.onkeyup??>
 onkeyup="${parameters.onkeyup}"<#rt/>
</#if>
<#if parameters.value??>
 value="${parameters.value}"<#rt/>
</#if>
<#if parameters.maxlength??>
 maxlength="${parameters.maxlength}"<#rt/>
</#if>
/>