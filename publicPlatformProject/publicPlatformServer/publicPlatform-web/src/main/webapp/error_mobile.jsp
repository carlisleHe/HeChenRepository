<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/meta.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<meta name="viewport" content="user-scalable=no,initial-scale=1.0, maximum-scale=1.0"/>
<meta name="apple-mobile-web-app-capable" content="yes"/>
<meta name="format-detection" content="telephone=no">
<meta names="apple-mobile-web-app-status-bar-style" content="black-translucent"/>
<meta name="apple-touch-fullscreen" content="yes"/>
<title></title>
 <style>
 /*设计给的css太乱了，没时间整理，只能放在这里 */
 .content{min-height:100%;height:auto!important;height:100%;position:relative;}
.icon_error{background:url(${ctx}/resources/images/err.png) no-repeat;width:279px;height:220px;margin:50px auto 0;}
.error_words{padding:25px 30px 0;line-height:24px;text-align:center;}
 </style>
</head>
<body style="background-color:#f0f0f2">
<div class="content">
	<div class="icon_error"></div>
	<p class="error_words"><s:property value="#request.GLOABL_ERROR_MSG" escape="false" /></p>
</div>
</body>
</html>