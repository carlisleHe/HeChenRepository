<#if (actionMessages?exists && actionMessages?size > 0)>
	<script type="text/javascript">
        $(document).ready(function(){
           msg("${actionMessages[0]}");
        });
	</script>
</#if>
