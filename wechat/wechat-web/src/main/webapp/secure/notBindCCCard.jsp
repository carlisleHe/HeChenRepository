<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/meta.jsp"%>
<html>
<head>
<title><s:text name="APP_NAME"/></title>
<%@ include file="/common/header.jsp"%>
<style type="text/css">
.btnbox{padding: 10px; width:auto;}
.nxtbt{
	margin-top: 10px; /*change*/
	width:100%;
	height:40px;
	background:#05be01;
	color:#fff;
	font-size: 18px;
	border:none;
	border-radius:5px;
	border:1px solid #209f1e;
}
</style>
</head>

<body class="yhkbg">
				
   <form action="redPacket!bindCard.do" method="post">		
	  <fieldset style="border-color:#e4e2df">
           <legend> 领取红包：</legend>
           <br/>
           <div style="color:#878787">
                                    对不起，本活动暂只针对微信银行信用卡绑定用户，请先绑定。
           </div>
           <br/>
       </fieldset>
        <div class="btnbox">
				<s:token></s:token>
           		<input type="submit" class="nxtbt" value="点击绑定"  />
           </div>
    </form>
	
</body>
</html>