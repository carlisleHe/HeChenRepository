<#if parameters.content??>
    <#if parameters.i18n>
        <@s.text name="${parameters.content}"/>
    <#else>
        ${parameters.content}
    </#if>
</#if>

