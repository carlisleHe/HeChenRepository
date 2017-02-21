<p class="form-p">
<@s.text name="select.search_cond"/>
<select id="searchCond">
	<option value="0"><@s.text name="option.alias"/></option>
	<option value="1" selected="selected"><@s.text name="option.name"/></option>
	<option value="2"><@s.text name="option.acct_no"/></option>
</select>
<@s.text name="input.search_word"/><@s.textfield id="searchWord"/>&nbsp;
<input type="button" value="<@s.text name='btn.search'/>" onclick="reflesh();"/>
</p>
<script type="text/javascript">
function reflesh() {
	$("#${parameters.gridId}").setGridParam({datatype:'json'});
	$("#${parameters.gridId}").appendPostData({"searchCond":$("#searchCond").val(),"searchWord":$("#searchWord").val()});
	<#if parameters.type == "TYPE_CUST">
		$("#${parameters.gridId}").setGridParam({url:'${parameters.ctx}/transfer/cussentAccountMgr!transferList.do'});
	</#if>
	<#if parameters.type == "TYPE_CONT">
		$("#${parameters.gridId}").setGridParam({url:'${parameters.ctx}/transfer/contractAcctMgr!search.do'});
	</#if>
	$("#${parameters.gridId}").trigger("reloadGrid");
}
</script>