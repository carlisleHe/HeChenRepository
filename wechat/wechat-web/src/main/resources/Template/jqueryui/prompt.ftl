<#if parameters.content?? &&""!=parameters.content?trim>
    <h5 class="title" ><@s.text name="helps"/></h5>
    <div class="text-info-box">
          ${parameters.content}
    </div>
</#if>
<div class="help_bottom"><@s.if test="'blank' eq #parameters.decorator[0]"></@s.if><@s.else><a href="/pers/main/customer/functionMap.do" id="func-map">功能地图</a></@s.else><span id="pageNo">页面编号： <@s.property value="#request.pageId" /><#if parameters.pageCode??>${parameters.pageCode}</#if></span></div>
