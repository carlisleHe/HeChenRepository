<div class="flowbox" id="${parameters.id?default('flow')}">
    <ul class="flow">
        <#list parameters.steps as step>
            <#rt/><li class="<#if step_has_next>page_item<#else>last</#if> <#if step_index==0>first</#if>" id="${parameters.id?default('flow')}_${step_index+1}" style="width:${parameters.width}"><span>${step_index+1}.&nbsp;<@s.text name="${step.value}"/></span></li><#rt/>
        </#list>
    </ul>
</div>

 