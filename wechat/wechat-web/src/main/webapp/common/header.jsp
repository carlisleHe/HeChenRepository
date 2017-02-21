<%@ page language="java"  pageEncoding="utf-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<meta name="viewport" content="user-scalable=no,initial-scale=1.0, maximum-scale=1.0"/>
<meta name="apple-mobile-web-app-capable" content="yes"/>
 <meta name="format-detection" content="telephone=no">
<meta name="apple-mobile-web-app-status-bar-style" content="black-translucent"/>
<meta name="apple-touch-fullscreen" content="yes"/>
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/css.css"/>
<script type="text/javascript" src="${ctx}/resources/js/zepto.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript">
var height;
var position;
$(function() {
	height = $(document).height();	
	position = document.documentElement.clientHeight/2;	
});
function msg(msg){
	$('#tczz').show().css('height',height);
    $("#tccnr").html(msg);
 
	if(msg=="绑定成功"){
	   $(".tcctit").html("成功提示");
	   $("#tccnr").attr("style" ,"text-align:center" );
	}else{
	  $(".tcctit").html("错误提示");
	}
		
	$('#tccbox').show().css({
		'top': position -($('#tccbox').height()/2),
		'left':($(document).width()/2)-($('#tccbox').width()/2)
	});
	
}
function tanGb(){
	var msg = $("#tccnr").html();
	if(msg=="绑定成功"){
		 var appId = $("#appId").val();
		 var openId = $("#openId").val();
	     document.location.href="${ctx}/bankCardManager!bankCardList.do?appId="+appId+"&openId="+openId;	
	}else{
		$('.tczz').hide();
		$('.tccbox').hide();
	}
}

document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
	WeixinJSBridge.call('hideToolbar');
});
</script>
<s:actionerror/>
<s:actionmessage/>  