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
           <legend> 温馨提示：</legend>
           <br/>
           <div style="color:#878787">
                                    对不起，您未绑定理财卡，本活动需要绑定兴业银行理财卡
           </div>
          <div class="btnbox">
				<s:token></s:token>
           		<input type="submit" class="nxtbt" value="请点击绑定"  />
           </div>
       </fieldset>
    </form>
	
</body>
</html>