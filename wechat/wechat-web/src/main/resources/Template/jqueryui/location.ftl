<#if parameters.resp??>
<#if stack.findValue(parameters.resp)??>
<#assign reps = stack.findValue(parameters.resp)/>
<@s.i18n name="menu">
<#if reps.currentBreadCrumb[0] != "">
<p id="crumbs" class="curr_pos clearfix">
   <strong class="left"><@s.text name="menu.location"/>：</strong>
    <span style="background:none;border: 0" class="left">
    <#list reps.currentBreadCrumb as item>
        <#if item != "">
            <#assign component = reps.breadCrumbComponent[item] />
            <#if component??>
            <span class="<#if item_has_next>node<#else>highLight</#if>" ><@s.text name="${component.title}"/></span>
            </#if>
             <#if item_has_next>&gt;</#if>
        </#if>
    </#list>
    </span>
</p>
</#if>
</@s.i18n>
</#if>
</#if>