<#assign version=1000>
<#if parameters.model_login??>
    <#if !stack.findValue("#model_login_css_included")??><#t/>
        <link href="${base}/resources/css/login.css?${version}" rel="stylesheet" type="text/css" media="all"/>
        <#assign temporaryVariable = stack.setValue("#model_login_css_included", "true") /><#t/>
    </#if>
</#if><#t/>
<#if parameters.model_main??>
    <#if !stack.findValue("#model_main_css_included")??><#t/>
            <link href="${base}/resources/css/${parameters.skin}/core.css?${version}" rel="stylesheet" type="text/css" media="all"/>
            <!--[if lte IE 7]>
            <link href="${base}/resources/css/${parameters.skin}/patch.css?${version}" rel="stylesheet" type="text/css" media="all"/>
            <![endif]-->
        <#assign temporaryVariable = stack.setValue("#model_main_css_included", "true") /><#t/>
    </#if>
</#if><#t/>
<#if parameters.model_menu??>
    <#if !stack.findValue("#model_menu_css_included")??><#t/>
	<link href="${base}/resources/css/${parameters.skin}/menu.css?${version}" rel="stylesheet" type="text/css" media="all"/>
        <#assign temporaryVariable = stack.setValue("#model_menu_css_included", "true") /><#t/>
    </#if>  
</#if><#t/>
<#if parameters.model_form??>
    <#if !stack.findValue("#model_form_css_included")??><#t/>
        <link href="${base}/resources/css/${parameters.skin}/jqform.css?${version}" rel="stylesheet" type="text/css" media="all"/>
        <#assign temporaryVariable = stack.setValue("#model_form_css_included", "true") /><#t/>
    </#if>
</#if><#t/>
<#if parameters.model_ui??>
    <#if !stack.findValue("#model_ui_css_included")??><#t/>
	<link rel="stylesheet" href="${base}/resources/css/${parameters.skin}/jquery-ui.css?${version}" type="text/css"/>
        <#assign temporaryVariable = stack.setValue("#model_ui_css_included", "true") /><#t/>
    </#if>
</#if><#t/>
<#if parameters.model_grid??>
    <#if !stack.findValue("#model_grid_css_included")??><#t/>
	<link rel="stylesheet" href="${base}/resources/css/${parameters.skin}/jqgrid.css?${version}" type="text/css"/>
        <#assign temporaryVariable = stack.setValue("#model_grid_css_included", "true") /><#t/>
    </#if>
</#if><#t/>

<#if parameters.model_calculator??>
    <#if !stack.findValue("#model_calculator_css_included")??><#t/>
	<link rel="stylesheet" href="${base}/resources/js/calculator/jquery.calculator.css?${version}" type="text/css"/>
        <#assign temporaryVariable = stack.setValue("#model_calculator_css_included", "true") /><#t/>
    </#if>
</#if><#t/>

<#if parameters.model_login??>
    <#if !stack.findValue("#model_login_js_included")??><#t/>  
	<script type="text/javascript" src="${base}/resources/js/core.js?${version}"></script>
        <!--[if  IE 6]>
		<script type="text/javascript">try{document.execCommand("BackgroundImageCache",false,true);}catch(e){}</script>
	<![endif]-->
        <#assign temporaryVariable = stack.setValue("#model_login_js_included", "true") /><#t/>
    </#if>
</#if><#t/>
<#if parameters.model_main??>
    <#if !stack.findValue("#model_main_js_included")??><#t/>
        <script type="text/javascript" src="${base}/resources/js/core.js?${version}"></script>
        <script type="text/javascript" src="${base}/resources/js/i18n/i18n-${parameters.i18n}.js?${version}"></script>
        <!--[if  IE 6]>
        <script type="text/javascript">try{document.execCommand("BackgroundImageCache",false,true);}catch(e){}</script>
        <![endif]-->
        <#assign temporaryVariable = stack.setValue("#model_main_js_included", "true") /><#t/>
    </#if>
</#if><#t/>
 <#if parameters.model_menu??>
    <#if !stack.findValue("#model_menu_js_included")??><#t/>
	<script type="text/javascript" src="${base}/resources/js/menu.js?${version}"></script>
        <#assign temporaryVariable = stack.setValue("#model_menu_js_included", "true") /><#t/>
    </#if>  
</#if><#t/>
 <#if parameters.model_form??>
    <#if !stack.findValue("#model_form_js_included")??><#t/>
    	<script type="text/javascript" src="${base}/resources/js/jqform.js?${version}"></script>
        <#assign temporaryVariable = stack.setValue("#model_form_js_included", "true") /><#t/>
    </#if>
</#if><#t/>
<#if parameters.model_ui??>
    <#if !stack.findValue("#model_ui_js_included")??><#t/>
        <script type="text/javascript" src="${base}/resources/js/jqui.js?${version}"></script>
        <#assign temporaryVariable = stack.setValue("#model_ui_js_included", "true") /><#t/>
    </#if>
</#if><#t/>
<#if parameters.model_grid??>
    <#if !stack.findValue("#model_grid_js_included")??><#t/>
        <script type="text/javascript" src="${base}/resources/js/jqgrid.js?${version}"></script>
        <#assign temporaryVariable = stack.setValue("#model_grid_js_included", "true") /><#t/>
    </#if>
</#if><#t/>
 <#if parameters.model_mask??>
    <#if !stack.findValue("#model_mask_js_included")??><#t/>
	<script type="text/javascript" src="${base}/resources/js/jqmask.js?${version}"></script>
        <#assign temporaryVariable = stack.setValue("#model_mask_js_included", "true") /><#t/>
    </#if>
</#if><#t/>
 <#if parameters.model_mydialog??>
    <#if !stack.findValue("#model_mydialog_js_included")??><#t/>
	<script type="text/javascript" src="${base}/resources/js/mydialog.js?${version}"></script>
        <#assign temporaryVariable = stack.setValue("#model_mydialog_js_included", "true") /><#t/>
    </#if>
</#if><#t/>
<#if parameters.model_calculator??>
    <#if !stack.findValue("#model_calculator_js_included")??><#t/>
	<script type="text/javascript" src="${base}/resources/js/calculator/jquery.calculator.js?${version}"></script>
	<script type="text/javascript" src="${base}/resources/js/calculator/jquery.calculator-${parameters.i18n}.js?${version}"></script>
        <#assign temporaryVariable = stack.setValue("#model_calculator_js_included", "true") /><#t/>
    </#if>
</#if><#t/>
