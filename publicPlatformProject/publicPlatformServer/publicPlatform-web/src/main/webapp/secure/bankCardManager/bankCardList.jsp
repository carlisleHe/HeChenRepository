<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/common/meta.jsp" %>
<html>
<head>
<title><s:text name="APP_NAME"/></title>
<%@ include file="/common/header.jsp"%>
</head>
<body class="yhkbg">
	<ul class="wdyhk">
	 <s:iterator var="ac" value="#request.authCardViewList" status="st">
			<li>
				 <a href="bankCardManager!show.do?selectIndex=<s:property value="#st.index"/>"  class="<s:if test="#ac.acctType==1">cxk</s:if><s:else>xyk</s:else>">
					<h3>兴业银行</h3>
					
					<h4><s:text name="CARD_NAME_%{#ac.acctType}"/></h4>
					<div class="kahao"><s:property value="#ac.acctNoView" /></div>
					<s:if test="#ac.defaultCard">
						<div class="mrk"></div>
					</s:if>
				</a>
			</li> 
	 </s:iterator>
		<li class="tjyhk">
			<a href="bankCardManager!toAddCard.do" class="yhklink" >
				<div class="wztjyhk">添加银行卡</div>
			</a>
		</li>
	</ul>
	<div class="tczz" id="tczz"> </div>
	<div class="tccbox" id="tccbox">
	  <div class="tcctit">错误提示</div>
	  <div class="tccnr" id="tccnr"></div>
	  <div class="tccbtn">
	    <input type="button" onClick="tanGb()" class="btn con" value="确认">
	  </div>
	</div>
</body>
</html>