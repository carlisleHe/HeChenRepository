<script language="javascript" src="${parameters.compath?default("")}/resources/js/hsm_cli.js" ></script>
<script type="text/javascript">
	pk = "${parameters.pk}";
</script>
<#if parameters.hasCookie>
	<#if parameters.svalue?if_exists == "activex">
		<div id="com">
			<OBJECT classid="clsid:8BE80FD3-B35E-CD48-1179-1B592DDEDDA7" codebase="${parameters.compath?default("")}/resources/js/CIB.cab#version=1,0,0,1" id="hsmcli"   name="hsmcli"  style="display:none"></OBJECT>
		</div>
		<input type="hidden" id="comType" value="activex">
	<#else>
		<div id="com">
			<Applet id="hsmcli" name="hsmcli"  vspace="0" width="0" height="0" code="UnionBrowserRSAMDL.class" Archive="${parameters.compath?default("")}/resources/js/hsmcli.jar" style="position:absolute"> </Applet>
		</div>
		<input type="hidden" id="comType" value="applet">
	</#if>
<#else>
<div id="com">
	<Applet id="hsmcli" name="hsmcli"  vspace="0" width="0" height="0" code="UnionBrowserRSAMDL.class" Archive="${parameters.compath?default("")}/resources/js/hsmcli.jar" style="position:absolute"> </Applet>
</div>
<input type="hidden" id="comType" value="applet">
</#if>
<input type="hidden" id="compath" value="${parameters.compath?default("")}">
</p>
