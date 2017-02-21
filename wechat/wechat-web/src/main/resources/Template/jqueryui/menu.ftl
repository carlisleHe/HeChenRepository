<#if parameters.resp??>
<#assign reps = stack.findValue(parameters.resp)/>
<@s.i18n name="menu">
<div id="menu-container">
<ul id="primary-nav" class="menu">
    <#list reps.topMenus as mc>
    <li class="top" id="li${mc.name}">
        <a id="${mc.name}" <#if mc.url == '#'>target="_self"</#if><#if mc.target?starts_with("_blank")>target="_blank"</#if> class="l1" href="<#if mc.url != '#'>${reps.contextPath}</#if>${mc.url?default('#')}"><@s.text name="${mc.title}"/></a>
        <#if (mc.components?exists && mc.components?size > 0) >
        <ul id="ul${mc.name}" class="sub-menu">
            <#list mc.components as subItem>
            <li id="li${subItem.name}" class="second <#if (subItem.components?exists && subItem.components?size > 0) >arrow-down</#if>">
                <a <#if subItem.url?starts_with("#")>target="_self"</#if><#if subItem.target?starts_with("_blank")>target="_blank"</#if> href="<#if subItem.url != '#'>${reps.contextPath}</#if>${subItem.url?default('#')}" id="${subItem.name}" class="l2"><@s.text name="${subItem.title}"/></a>
                <#if (subItem.components?exists && subItem.components?size > 0) >
                <ul id="ul${subItem.name}" class="down-menu">
                    <#list subItem.components as downItem>
                    <li id="li${downItem.name}" class="third <#if (downItem.components?exists && downItem.components?size > 0) >arrow-right</#if>">
                        <a <#if downItem.url?starts_with("#")>target="_self"</#if><#if downItem.target?starts_with("_blank")>target="_blank"</#if> href="<#if downItem.url != '#'>${reps.contextPath}</#if>${downItem.url?default('#')}" id="${downItem.name}" class="l3"><@s.text name="${downItem.title}"/></a>
                        <#if (downItem.components?exists && downItem.components?size > 0) >
                        <ul id="ul${downItem.name}" class="poll-menu">
                            <#list downItem.components as pullDownItem>
                            <li id="li${pullDownItem.name}" class="last"><a <#if pullDownItem.url?starts_with("#")>target="_self"</#if><#if pullDownItem.target?starts_with("_blank")>target="_blank"</#if> href="${reps.contextPath}${pullDownItem.url?default('#')}" id="${pullDownItem.name}" class="l4"><@s.text name="${pullDownItem.title}"/></a></li>
                            </#list>
			            </ul>
                        </#if>
                    </li>
                    </#list>
                </ul>
                </#if>
            </li>
            <li class="sec-split"/>
            </#list>
        </ul>
        </#if>
    </li>
    </#list>
</ul>
</div></@s.i18n>
</#if>