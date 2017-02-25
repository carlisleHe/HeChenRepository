<script type="text/javascript">
    $(function() {
		var id = "${tag.value}";
		var bef = parseInt(id)-1;
		$("#${parameters.flowId?default('flow')}_"+bef).addClass("before_current");
        $("#${parameters.flowId?default('flow')}_"+id).addClass("current");
        if($("#${parameters.flowId?default('flow')}_"+id).hasClass("first")){
			$("#${parameters.flowId?default('flow')}_"+id).addClass("current_first");
		};
		if($("#${parameters.flowId?default('flow')}_"+id).hasClass("last")){
			$("#${parameters.flowId?default('flow')}_"+id).addClass("current_last");
		};
    });
</script>