<script type="text/javascript"><#rt/>
jQuery(window).load(function(){<#rt/>
 jQuery("#${parameters.id}").jqGrid({
<#if parameters.adjustMode??>adjustMode:${parameters.adjustMode?string("true","false")},</#if><#rt/>
<#if parameters.autowidth??>autowidth:${parameters.autowidth?string("true","false")},</#if><#rt/>
<#if parameters.captionKey??>caption:'<@s.text name="${parameters.captionKey}"/>',<#else><#if parameters.caption??>caption:'${parameters.caption}',</#if></#if><#rt/>
<#if parameters.datatype??>datatype:'${parameters.datatype}',</#if>
<#if parameters.height??>height:'${parameters.height}',</#if>
<#if parameters.loadComplete??>loadComplete:${parameters.loadComplete},</#if><#rt/>
<#if parameters.loadError??>loadError:${parameters.loadError},<#else>loadError:defaultLoadError,</#if><#rt/>
<#if parameters.loadonce??>loadonce:${parameters.loadonce?string("true","false")},</#if><#rt/>
<#if parameters.multiSave??>multiSave:${parameters.multiSave?string("true","false")},</#if><#rt/>
<#if parameters.multiselect??>multiselect:${parameters.multiselect?string("true","false")},</#if><#rt/>
<#if parameters.onSelectAll??>onSelectAll:${parameters.onSelectAll},</#if>
<#if parameters.onSelectRow??>onSelectRow:${parameters.onSelectRow},</#if>
<#if parameters.pager??>pager:'#${parameters.pager}',</#if>
<#if parameters.prmNames??>prmNames:${parameters.prmNames},</#if>
<#if parameters.radio??>radio:${parameters.radio?string("true","false")},</#if><#rt/>
<#if parameters.rowNum??>rowNum:${parameters.rowNum},</#if><#rt/>
<#if parameters.rownumbers??>rownumbers:${parameters.rownumbers?string("true","false")},</#if><#rt/>
<#if parameters.rownumWidth??>rownumWidth:'${parameters.rownumWidth}',</#if><#rt/>
<#if parameters.rowList??>rowList:${parameters.rowList},</#if><#rt/>
<#if parameters.totalUnknown??>totalUnknown:${parameters.totalUnknown?string("true","false")},</#if><#rt/>
<#if parameters.treedatatype??>treedatatype:${parameters.treedatatype},</#if><#rt/>
<#if parameters.treeGrid??>treeGrid:${parameters.treeGrid},</#if><#rt/>
<#if parameters.treeGridModel??>treeGridModel:${parameters.treeGridModel},</#if><#rt/>
<#if parameters.treeIcons??>treeIcons:${parameters.treeIcons},</#if><#rt/>
<#if parameters.treeReader??>treeReader:${parameters.treeReader},</#if><#rt/>
<#if parameters.tree_root_level??>tree_root_level:${parameters.tree_root_level},</#if><#rt/>
<#if parameters.shrinkToFit??>shrinkToFit:${parameters.shrinkToFit},</#if><#rt/>
<#if parameters.url??>url:'${parameters.url}',</#if><#rt/>
<#if parameters.viewrecords??>viewrecords:${parameters.viewrecords?string("true","false")},</#if><#rt/>
<#if parameters.width??>width:'${parameters.width}',</#if>
<#lt/>colModel:[  
<#list parameters.gridColumns as gridColumn>
 {<#lt/>name:'${gridColumn.name?html}'<#rt/>
  <#if gridColumn.align??><#lt/>,align:'${gridColumn.align}'</#if><#rt/>
  <#if gridColumn.fixed??><#lt/>,fixed:${gridColumn.fixed}</#if><#rt/>
 <#if gridColumn.formatoptions??><#lt/>,formatoptions:${gridColumn.formatoptions}</#if><#rt/>
 <#if gridColumn.formatter??><#lt/>,formatter:${gridColumn.formatter}</#if><#rt/>
  <#if gridColumn.hidden??><#lt/>,hidden:${gridColumn.hidden}</#if><#rt/>
  <#if gridColumn.width??><#lt/>,width:${gridColumn.width}</#if><#rt/>
  <#if gridColumn.sorttype??><#lt/>,sorttype:${gridColumn.sorttype}</#if><#rt/>
  <#if gridColumn.sortable??><#lt/>,sortable:${gridColumn.sortable}</#if>}<#rt/>
  <#rt/>
 <#if gridColumn_has_next>,
 </#if>
</#list>
    ],
colNames:[<#list parameters.gridColumns as gridColumn>
  <#rt/><#if gridColumn.titleKey??>'<@s.text name="${gridColumn.titleKey}"/>'<#else><#if gridColumn.title??>'${gridColumn.title}'<#else>'unname'</#if></#if><#rt/>
 <#if gridColumn_has_next>,<#rt/>
 </#if>
</#list>]
     });
 });
</script>