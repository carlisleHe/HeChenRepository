<#assign FUNID = stack.findValue("FUNID")/>
<fieldset class="ui-widget ui-widget-content ui-corner-all" style="margin-top: 15px">
    <legend style="margin-left: 15px;"><@s.text name="helps"/></legend>
    <div  id="prompt_results" style="padding:5px">
      <#if parameters.content??>${parameters.content}</#if>
    </div>
</fieldset>
