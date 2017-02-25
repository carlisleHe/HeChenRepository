<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/common/meta.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>身边的兴业银行</title>
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<style type="text/css">
body,html,#allmap {width: 100%;height: 100%;overflow: hidden;margin: 0;}
</style>
<!-- <script src="http://api.map.baidu.com/api?v=1.3" type="text/javascript"></script> -->
<script type="text/javascript"
	src="http://api.map.baidu.com/api?v=1.5&ak=jr7TMaTwGiQVRXTkvll34s6h"></script>
<script type="text/javascript">
(function(){function load_script(xyUrl,callback){var head=document.getElementsByTagName("head")[0];var script=document.createElement("script");script.type="text/javascript";script.src=xyUrl;script.onload=script.onreadystatechange=function(){if((!this.readyState||this.readyState==="loaded"||this.readyState==="complete")){callback&&callback();script.onload=script.onreadystatechange=null;if(head&&script.parentNode){head.removeChild(script)}}};head.insertBefore(script,head.firstChild)}function transMore(points,type,callback){var xyUrl="http://api.map.baidu.com/ag/coord/convert?from="+type+"&to=4&mode=1";var xs=[];var ys=[];var maxCnt=20;var send=function(){var url=xyUrl+"&x="+xs.join(",")+"&y="+ys.join(",")+"&callback=callback";load_script(url);xs=[];ys=[]};for(var index in points){if(index%maxCnt==0&&index!=0){send()}xs.push(points[index].lng);ys.push(points[index].lat);if(index==points.length-1){send()}}}window.BMap=window.BMap||{};BMap.Convertor={};BMap.Convertor.transMore=transMore})();
</script>
</head>
<body>
	<div id="allmap"></div>
	<script type="text/javascript">
		var flag='<s:property value="name"/>'
		//从参数里获取位置值
		var loc_name = '<s:property value="name"/>';
		var loc_loc = '<s:property value="address"/>';
		var loc_tel = '<s:property value="contact"/>'
		var my_loc = '我的位置'
		var destlng = <s:property value="destlng"/>;
		var destlat = <s:property value="destlat"/>;
		var mylng = <s:property value="mylng"/>;
		var mylat = <s:property value="mylat"/>;

		var status = <s:property value="status"/>==1?"有效":"无效";
		var prefInfo = '<s:property value="prefInfo" escape="false"/>';
		var endDate = '<s:property value="endDate"/>';
		
		
		var content = '<div style="width:190px;white-space:normal;margin:0;line-height:20px;padding:2px;">'
			+ '商户名称：' + loc_name + '<br/>'
			+ '商户地址：' + loc_loc + '<br/>'
			+ '商户电话：<a href="tel://' + loc_tel + '" target="_blank">'+ loc_tel +'</a><br/><br/>'
			+ '<a href="javascript:showPrefInfo()" >优惠信息</a><br/><br/>'
			
			+'选项：<a href="javascript:walking()">步行</a>'
			+'&nbsp;&nbsp;&nbsp;<a href="javascript:driving()">驾车</a>'
			+'&nbsp;&nbsp;&nbsp;<a href="javascript:searchATM()">ATM</a>'
			+'</div>';
		
		var prefInfo = '优惠细则：<div style="width:190px;overflow:scroll; height:120px;white-space:normal;margin:0;line-height:20px;padding:2px;">'
				
			+ '' + prefInfo + '</div><br/>'			
			+ '商户状态：' + status + '<br/>'
			+ '截止日期：' + endDate + '<br/><br/>'
			+ '<a href="javascript:showInfo()" >返回</a><br/>';		
			
			
		
		var map = new BMap.Map("allmap");
		
		var myloc;
		var dest;
		
		function showPrefInfo(){
			var infoWindow = new BMap.InfoWindow(prefInfo, {
				  width : 190,     // 信息窗口宽度
				  title : "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;优惠信息" , // 信息窗口标题
				  enableMessage:true,//设置允许信息窗发送短息
				  message: loc_name+ ',地址:'+loc_loc+',电话:'+loc_tel
			});  // 创建信息窗口对象
	
			var marker2 = new BMap.Marker(dest);
			map.addOverlay(marker2);
			marker2.openInfoWindow(infoWindow); //开启信息窗口
			marker2.addEventListener("click", function(){this.openInfoWindow(infoWindow);});
		}
		
		
		function showInfo(){
			var infoWindow = new BMap.InfoWindow(content, {
				  width : 190,     // 信息窗口宽度
				  title : null , // 信息窗口标题
				  enableMessage:true,//设置允许信息窗发送短息
				  message: loc_name+ ',地址:'+loc_loc+',电话:'+loc_tel
			});  // 创建信息窗口对象
	
			var marker2 = new BMap.Marker(dest);
			map.addOverlay(marker2);
			marker2.openInfoWindow(infoWindow); //开启信息窗口
			marker2.addEventListener("click", function(){this.openInfoWindow(infoWindow);});
		}
		
		function callback(xyResults){
			myloc = new BMap.Point(xyResults[0].x, xyResults[0].y);
			dest = new BMap.Point(xyResults[1].x, xyResults[1].y)
			//设置目标点为中心
			map.centerAndZoom(myloc, 18);
			map.addControl(new BMap.NavigationControl());  //添加默认缩放平移控件
			map.addControl(new BMap.ScaleControl({anchor: BMAP_ANCHOR_BOTTOM_RIGHT})); // 比例尺控件右下
			
			var marker = new BMap.Marker(myloc);
			map.addOverlay(marker);
			var label = new BMap.Label(my_loc, {
				 position: myloc,
				 offset   : new BMap.Size(20, -10)    //设置文本偏移量
			});
			marker.setLabel(label);
		
			showInfo();
		};

		var points = [new BMap.Point(mylng,mylat),new BMap.Point(destlng,destlat)];
		BMap.Convertor.transMore(points,2,callback);
		
		function walking(){
			map.clearOverlays();
			map.centerAndZoom(myloc, 18);
			var walking = new BMap.WalkingRoute(map, {
				renderOptions : {
					map : map,
					autoViewport : true
				}
			});
			walking.search(myloc, dest);
		}
		
		function driving(){
			map.clearOverlays();
			map.centerAndZoom(myloc, 18);
			var driving = new BMap.DrivingRoute(map, {
				renderOptions : {
					map : map,
					autoViewport : true
				}
			});
			driving.search(myloc, dest);
		}	
		
		function searchATM(){
			//本地搜索
			map.clearOverlays();
			map.centerAndZoom(myloc, 18);
			var local = new BMap.LocalSearch(map, {
				  renderOptions: {map: map, panel: "r-result"}
				});

			local.searchInBounds("ATM", map.getBounds());
			var marker = new BMap.Marker(myloc);
			map.addOverlay(marker);
			var label = new BMap.Label(my_loc, {
				 position: myloc,
				 offset   : new BMap.Size(20, -10)    //设置文本偏移量
			});
			marker.setLabel(label);
		}
	</script>
</body>
</html>