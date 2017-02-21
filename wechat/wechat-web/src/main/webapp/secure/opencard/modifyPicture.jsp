<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/meta.jsp"%>
<html>
<head>
<title><s:text name="APP_NAME"/></title>
<%@ include file="/common/header.jsp"%>
<%@ include file="/common/signature.jsp"%>
<script src="${ctx}/resources/js/jquery-1.10.2.min.js"></script>
<script src="${ctx}/resources/js/zepto.min.js"></script>
<script src="https://res.wx.qq.com/open/js/jweixin-1.1.0.js"></script>
<!-- 后台弹出提示框定位 -->
<s:if test='actionMessages.size>0'>
	<script type="text/javascript">
        $(document).ready(function(){
           document.getElementById('psform').scrollIntoView();
        });
	</script>
</s:if>

<script>
var position;

$(function() {
	position = document.documentElement.clientHeight/2;
})
function msg(msg){
	$('#tczz').show().css('height',height);
    $("#tccnr").html(msg);
 
	 document.getElementById('psform').scrollIntoView();
	 $(".tcctit").html("错误提示");
	
	$('#tccbox').show().css({
		'top':position-($('#tccbox').height()/2),
		'left':($(document).width()/2)-($('#tccbox').width()/2)
	});
}
function tanGb(){
	var msg = $("#tccnr").html();
	if(msg=="绑定成功"){
		 var appId = $("#appId").val();
		 var openId = $("#openId").val();
	     document.location.href="${ctx}/authorization!list.do?appId="+appId+"&openId="+openId;	
	}else{
		$('.tczz').hide();
		$('.tccbox').hide();
	}
}

document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
	WeixinJSBridge.call('hideToolbar');
});
</script>
</head>

<body>
	<s:form id="psform" action="opencard!modifyPicture.do" namespace="" method="post">
		<div class="topbar"> 远程开户 </div>
		<div class="p-con liucheng">
				<div><img src="resources/images/liucheng2.png"></div>
				<span>填写身份信息</span>
				<span class="on">上传照片</span>
				<span>填写开户信息</span>
				<span>视频验证</span>
		</div>
        <div class="tit2">拍摄身份证请使用本人二代身份证原件！</div>
		<div class="p-con">
			<div id="camera-thumb2">
	      	  <img id="previewImg1" class="camera-thumb1" src="${ctx}/jssdk!imgPic1.do?imageId='<%=new java.util.Date()%>' " name="imgPath1">
	      	  <input type="hidden" id="customerImg1" name="customerImg1"/>
	      	 </div>
			点击上图拍摄二代身份证正面
		</div>
		<div class="p-con">
			<div id="camera-thumb2">
			 	<img id="previewImg2" src="${ctx}/jssdk!imgPic2.do?imageId='<%=new java.util.Date()%>'" name="imgPath2">
			 	<input type="hidden" id="customerImg2" name="customerImg2"/>
			 </div>
			点击上图拍摄二代身份证反面
		</div>
		<div class="p-con">
			<div id="camera-thumb3">
				 <img id="previewImg3" src="${ctx}/jssdk!imgPic3.do?imageId='<%=new java.util.Date()%>'" name="imgPath3" >
				 <input type="hidden" id="customerImg3" name="customerImg3"/>
			 </div>
			点击上图拍摄免冠头像
		</div>
		<div class="p-con">
			<p class="text-left">请务必确保所拍摄的三张照片清晰可见，如需修改，请直接点击图片重新拍摄。</p>
		</div>
		<div class="btnbox">
		 	<s:submit  value="上传图片" cssClass="nxtbt" onclick='return beforeSubmit();'  /><%--method="picture" --%>
		</div>
		</s:form>
		<s:form action="opencard!setpWrite.do" namespace="" method="post">
		    <div class="stepbox">
		 		<s:submit value="上一步" cssClass="calbtn"/>
		 	</div>
		</s:form> 
	<script>
		    /*
		   * 注意：
		   * 1. 所有的JS接口只能在公众号绑定的域名下调用，公众号开发者需要先登录微信公众平台进入“公众号设置”的“功能设置”里填写“JS接口安全域名”。
		   * 2. 如果发现在 Android 不能分享自定义内容，请到官网下载最新的包覆盖安装，Android 自定义分享接口需升级至 6.0.2.58 版本及以上。
		   * 3. 常见问题及完整 JS-SDK 文档地址：http://mp.weixin.qq.com/wiki/7/aaa137b55fb2e0456bf8dd9148dd613f.html
		   *
		   * 开发中遇到问题详见文档“附录5-常见错误及解决办法”解决，如仍未能解决可通过以下渠道反馈：
		   * 邮箱地址：weixin-open@qq.com
		   * 邮件主题：【微信JS-SDK反馈】具体问题
		   * 邮件内容说明：用简明的语言描述问题所在，并交代清楚遇到该问题的场景，可附上截屏图片，微信团队会尽快处理你的反馈。
		   */
		 // alert(location.href.split('#')[0]);
		  wx.config({
		      debug: false,  //true测试
		      appId: appId,
		      timestamp: timestamp,
		      nonceStr:  nonceStr,
		      signature: signature,
		      jsApiList: [
		        // 所有要调用的 API 都要加到这个列表中
	      		'chooseImage',
		   		'previewImage',
		    	'uploadImage',
		    	'downloadImage',
		    	'closeWindow',
		    	'hideOptionMenu'
		      ]
		  });
		    
  		  wx.ready(function () {
			
			//拍照、并上传图片
		    function camera(j){
	    	      wx.chooseImage({
		          sourceType:['camera'],  // 拍照
		          sizeType: ['original'], // 原图
		          success: function (res) {
		             // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
		             var localIds = res.localIds; 
		             images.localId = localIds;
		             $("#previewImg"+j).attr('src',localIds[0]);   
		            if (images.localId.length == 0) {
		                msg('请先拍照');
		                return;
		            }
		            var i=0;
		            function upload() {
		                wx.uploadImage({
	                             localId: images.localId[i],
	                             success: function (res) {
	                                 //msg("上传成功");
	                                 var serverId = res.serverId; 
	                                 $("#customerImg"+j).val(serverId);   
	                             },
	                             fail: function (res) {
	                                msg("图片上传失败,请重新上传");
	                            }
	                   });
		            }
		            upload();
		          },
		            fail:function(res){
		                    msg("拍照失败,请重新拍照");
		            }
		        });
		    }
		     wx.checkJsApi({
			      jsApiList: [
			        'chooseImage',
				    'previewImage',
				    'uploadImage',
			        'downloadImage',
				    'closeWindow',
				    'hideOptionMenu'
			      ],
			      success: function (res) {
						$("#previewImg1").click(function(){
						       camera(1);
							});
							
					
						$("#previewImg2").click(function(){
						       camera(2);
							});
							
							
						$("#previewImg3").click(function(){
						   		camera(3);
						});
				},
			     fail:function(){
			     	msg('微信客户端版本过低，请升级至6.1及以上版本！');
			     }  
			 });
	  }); 
  	  // 5.1 拍照,图片存放位置
      var images = {
        localId: [],
        serverId: []
      };
  	  function beforeSubmit() {
 			return true;
  	 }

	</script>
	<%@include file="/common/foot.jsp"%>
</body>
</html>
