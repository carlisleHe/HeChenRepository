<#if (actionErrors?exists && actionErrors?size > 0)>
	<script type="text/javascript">
  $(function(){
	
	$(document).ready(
		function() {
		msg("${actionErrors[0]}");
	})
})
	</script>

</#if>
