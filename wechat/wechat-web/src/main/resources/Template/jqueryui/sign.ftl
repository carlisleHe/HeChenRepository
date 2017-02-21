<input type="hidden" name="srcData" Id="SRC_DATA" value="${parameters.signdata?default("")}" />
<input type=hidden name="signData" Id="SIGN_DATA" />
<div id="myAnchor"></div>
<script type="text/javascript">
     var obFlag =false;
     
     function isHasActive(){
	       try{
		       new ActiveXObject("CertKitAx.BlockHouse.1");
			   document.getElementById('myAnchor').innerHTML='<OBJECT classid="clsid:33FE9A3A-55F6-4B55-A473-D19A59C89E28"'+
			   ' codeBase="${parameters.ctx?default("")}/resources/certtoolkit/CertKitAx.cab#version=1,6,0,1"'+
			   ' id="signcontrol"'+
			   ' style="VISIBILITY: hidden"'+
			   ' width="170"'+
			   ' height="42"'+
			   ' ></OBJECT>';
			    obFlag=true;
	      }catch(e){
	           obFlag=false;  
	      } 
     }
     
     isHasActive();
     
<#if parameters.clientCN??>
	function selectDefaultCert(){
        try{
            var control = document.getElementById("signcontrol");
			if(control!=null){
			     selectCert("${parameters.clientCN?default("null")}");
			}
        }catch(e){
            error_report(e.name,e.message);
        }
    }
    selectDefaultCert();
</#if>
function _clientSign(srcData){
    var control = document.getElementById("signcontrol");
    try{
    	if (selected == false){
    		selectCert(null);
    	}else{
        	control.CFCA_GetCertCN(true)
        }
    }catch(e){
        selectCert(null);
    }
    var signData = control.CFCA_SignData(srcData);
    return signData;
}
var selected = true;
function selectCert(cn){
    var control = document.getElementById("signcontrol");
if(control!=null){

}
    if (cn == null || selected == false){
        control.CFCA_SelectUserCerts(true);
    }else{
        control.CFCA_InitCertAppContext(true, cn);
    }
}
function error_report(err_name,err_message)
{
    var err_info;
    err_info = "错误:" + err_name + "\n错误信息:" + err_message;
    alert("错误:" + err_name + "\n错误信息:" + err_message);
    if (event) event.returnValue = false;
}
function collectSignData(){
<#if parameters.customer>
    
<#else>
	var childrens = document.getElementsByTagName("INPUT");
    var srcData = "";
    for (var i = 0 ; i < childrens.length ; i++){
        if (childrens[i].type == "hidden"){
            if (childrens[i].id == "SIGN"){
                srcData += childrens[i].name + "=" + childrens[i].value;
                if ( (i + 1) < childrens.length) srcData += ";";
            }
        }
    }
	
</#if>
    return srcData;
}
function clientSign(){
    try{
        new ActiveXObject("CertKitAx.BlockHouse.1");
        if(obFlag==false){
            document.getElementById('myAnchor').innerHTML='<OBJECT classid="clsid:33FE9A3A-55F6-4B55-A473-D19A59C89E28"'+
			   ' codeBase="${parameters.ctx?default("")}/resources/certtoolkit/CertKitAx.cab#version=1,6,0,1"'+
			   ' id="signcontrol"'+
			   ' style="VISIBILITY: hidden"'+
			   ' width="170"'+
			   ' height="42"'+
			   ' ></OBJECT>';
        }
        var signData = document.getElementById("SIGN_DATA");
        var srcData = document.getElementById("SRC_DATA");
        signData.value = _clientSign(srcData.value);
        return true;
    }catch(e){
        var errnum= e.number; 
       selected = false;
       obFlag= false;
       //alert("错误代号："+errnum);
        switch(errnum){
              case -1:{
                jPageDialog('温馨提示',"${parameters.ctx?default("")}/commons!noinst.do",480,180);
                event.returnValue = false;
                return false;
              }
              case -2:{
               jPageDialog('温馨提示',"${parameters.ctx?default("")}/commons!noinst.do",480,180);
                event.returnValue = false;
                return false;
              }
              case -4:{
               jPageDialog('温馨提示',"${parameters.ctx?default("")}/commons!noinst.do",480,180);
                event.returnValue = false;
                return false;
              }
              case -6:{
               jPageDialog('温馨提示',"${parameters.ctx?default("")}/commons!nodown.do",480,150);
                event.returnValue = false;
                return false;
              }
              case -2146827850:{
              	jPageDialog('温馨提示',"${parameters.ctx?default("")}/commons!nodown.do",480,150);
                event.returnValue = false;
                return false;
              }
              default:{
              	jPageDialog('温馨提示',"${parameters.ctx?default("")}/commons!nodown.do",480,150);
               	//jalert("证书签名失败，错误原因：[" + errnum + ":" + e.message + "]");
               	event.resutValue = false;
               	return false;
              }
        }
    }
}
</script>