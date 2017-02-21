<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/meta.jsp"%>
<html>
<head>
<title><s:text name="APP_NAME"/></title>
<%@ include file="/common/header.jsp"%>
<script>
document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
    // 通过下面这个API隐藏底部导航栏
    WeixinJSBridge.call('hideToolbar');
});

var opType;
function tanChu(obj,msg,type) {
	opType = type;
	$('.tczz').show().css('height', $(document).height());
	$("#tccnr2").html(msg);
	$(obj).show().css({
		'top' : ($(document).height() / 2) - ($('.tccbox').height() / 2),
		'left' : ($(document).width() / 2) - ($('.tccbox').width() / 2)
	});
	
}
function tan(obj) {
	$('.tczz').hide();
	$(obj).hide();
	if(opType==1){
		$("#closeform").unbind('submit').submit();
	}
	if(opType==2){
		$("#defaultform").unbind('submit').submit();
	}
	if(opType==3){
		$("#notifyform").unbind('submit').submit();
	}
}
function tant(){
	$("#notifyform").unbind('submit').submit();
}
function tanCN(obj) {
	$('.tczz').hide();
	$(obj).hide();
}
</script>
</head>

<body>
	<div class="wrap">
			<div style="padding-top: 20px;"></div>
			<ul class="lisbox">
				<li>
					<span class="bdico">
						<img src="resources/images/usico.png" alt="" />
					</span>
					<span class="bdzd fl"><s:property value="selAuthCardView.acctName"/></span>&nbsp;<span class="hswz zswc"><s:property value="selAuthCardView.acctNoSub"/></span> 
					<s:if test="selAuthCardView.defaultCard">
						<span class="zswz hswz">默认<s:text name="CARD_NAME_%{selAuthCardView.acctType}"/></span>
					</s:if>
					<s:else>
						<span class="zswz hswz"><s:text name="CARD_NAME_%{selAuthCardView.acctType}"/></span>
					</s:else>
				</li>
				<s:if test="selAuthCardView.acctType==2">
					<li>
					<span class="bdico">
						<img src="resources/images/bqyh.png" alt="" />
					</span>
				</s:if>
				<s:else>
					<li class="last">
					<span class="bdico">
						<img src="resources/images/yeico.png" alt="" />
					</span>
				</s:else>
					<span class="bdzd fl"><s:text name="BILL_NAME_%{selAuthCardView.acctType}"/></span>
					<s:if test="selAuthCardView.acctType==2">
					<div class="fr">
						<span class="zswz"><s:property value="%{getText('format.money',{selAuthCardView.billAmountCNY})}"/></span><span class="zswz hswz">人民币</span><br>
						<span class="zswz"><s:property value="%{getText('format.money',{selAuthCardView.billAmountUSD})}"/></span><span class="zswz hswz">美元</span>
					</div>
					</s:if>
					<s:else><span class="zswz"><s:property value="%{getText('format.money',{selAuthCardView.balance})}"/>元</span></s:else>
				</li>
				<s:if test="selAuthCardView.acctType==2">
				<li class="last">
					<span class="bdico">
						<img src="resources/images/dqhk.png" alt="" />
					</span>
					<span class="bdzd fl">到期还款日</span>
					<span class="zswz hswz">每个月<s:property value="%{getText('day_format',{selAuthCardView.endRefundDate})}"/>号</span>
				</li>
				</s:if>
			</ul>
			<ul class="lisbox ljlist">
				<s:form id="closeform" action="authorization!close.do" namespace="" method="post">
				<s:if test="selAuthCardView.acctType==2 && selAuthCardView.defaultCard">
					<li class="last">
					</li>
				</s:if>
				<s:else>
				</s:else>
					<li>
						<s:token></s:token>
						<a href="#"  onClick="tanChu('.tc2','亲，您确定了要解除绑定吗？说好在一起的，我在这里，等您回来！',1)">
							<span class="bdico">
								<img src="resources/images/jcbd.png"   alt="" />
							</span>
							<span class="bdzd fl">解除和微信号绑定</span>
							<span class="zswz hswz">
								<img src="resources/images/ljico.png"  alt="" />
							</span>
						</a>
					</li>
				</s:form>
				<s:if test="selAuthCardView.defaultCard">
				</s:if>
				<s:else>
					<s:form id="defaultform" action="authorization!defaultCard.do" namespace="" method="post">
					<s:if test="selAuthCardView.acctType==2">
						<li class="last">
							<a href="#" onClick="tanChu('.tc2','设置为默认信用卡后，您可直接在微信对话界面通过菜单查询该账户信息，您确定将此卡设置成默认信用卡吗？',2)">
								<span class="bdico">
									<img src="resources/images/mrico.png" alt="" />
								</span>
								<span class="bdzd fl">设置为默认信用卡</span>
								<span class="zswz hswz">
									<img src="resources/images/ljico.png" alt="" />
								</span>
							</a>
						</li>
					</s:if>
					<s:else>
						<li>
							<a href="#" onClick="tanChu('.tc2','设置为默认理财卡后，您可直接在微信对话界面通过菜单查询该账户信息，您确定将此卡设置成默认理财卡吗？',2)">
								<span class="bdico">
									<img src="resources/images/mrico.png" alt="" />
								</span>
								<span class="bdzd fl">设置为默认理财卡</span>
								<span class="zswz hswz">
									<img src="resources/images/ljico.png" alt="" />
								</span>
							</a>
						</li>
					</s:else>
						</s:form>
				</s:else>
				<s:form id="notifyform" action="authorization!authNotify.do" namespace="" method="post">
					<li class="last">
					
					<s:if test="selAuthCardView.NotifyType==00 || selAuthCardView.NotifyType==02">
						<a href="#"  onClick="tanChu('.tc2','亲，我知道，您已厌倦了尘世的喧嚣，可是，您确定要关闭微信动户通知享受孤独吗？',3)">
					</s:if>
					<s:else>
						<a href="#" onClick="tant()">
                    </s:else>
                    <s:token></s:token>
							<span class="bdico">
								<img src="resources/images/tzico.png" alt="" />
							</span>
							<span class="bdzd fl">
								<s:if test="selAuthCardView.NotifyType==00 || selAuthCardView.NotifyType==02">关闭</s:if><s:else>开通</s:else>微信动户通知 	
							</span>
							<span class="zswz">
								<img src="resources/images/ljico.png" alt="" />
							</span>
						</a>
					</li>
				</s:form>
			</ul>
	   		<s:form id="showform" action="authorization!list.do" namespace="" method="post">
			<div class="btnbox">
				<s:submit value="返回" cssClass="nxtbt"></s:submit>
			</div>
			<div class="foot"></div>
		</s:form>
	</div>
		<div class="tccbox tc2">
		  <div class="tcctit">信息提示</div>
		  <div class="tccnr" id="tccnr2"></div>
		  <div class="tccbtn">
		    <input type="button" onClick="tanCN('.tc2')" class="btn cal" value="有点犹豫">
		    <input type="button" onClick="tan('.tc2')" class="btn con" value="非常确定">
		  </div>
		</div>
    <%@include file="/common/foot.jsp"%>
</body>
</html>