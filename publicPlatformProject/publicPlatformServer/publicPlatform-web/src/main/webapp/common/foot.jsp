<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
 <div id="posit_wxy" class="copyright"><img src="${ctx}/resources/images/copyright.png"alt=""/></div>
<div class="tczz" id="tczz"> </div>
<div class="tccbox" id="tccbox">
  <div class="tcctit"></div>
  <s:hidden id="appId"  value="%{appId}"></s:hidden>
  <s:hidden id="openId"  value="%{openId}"></s:hidden>
  <div id="tccnr" class="tccnr"></div>
  <div class="tccbtn">
    <input type="button" onClick="tanGb()" class="btn con" value="确认">
  </div>
</div>