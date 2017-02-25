package com.newland.alipayserver.web.interceptor;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.text.CharacterIterator;
import java.text.MessageFormat;
import java.text.StringCharacterIterator;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.StrutsConstants;
import org.apache.struts2.StrutsStatics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.intensoft.exception.AppBizException;
import com.intensoft.exception.AppRTException;
import com.intensoft.exception.locale.ThrowableTranslate;
import com.intensoft.exception.logger.SimpleThrowableLogger;
import com.intensoft.exception.logger.ThrowableLogger;
import com.newland.alipayserver.web.actions.BaseAction;
import com.newland.alipayserver.web.annotation.ExecuteResult;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.ValidationAware;
import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.opensymphony.xwork2.util.TextParseUtil;
import com.opensymphony.xwork2.validator.ValidationException;

/**
 * 异常拦截器
 */
public class ExceptionInterceptor implements Interceptor {

    private static final long serialVersionUID = 6404872932457673950L;
    /**
     * 异常统计记录（用于统计异常情况）
     * 记录格式：ncid={0},oid={1},sysTrackId={2},exceptType={3},exceptCode={4},exceptMsg={5}
     */
    private static Logger statistLogger = LoggerFactory.getLogger(ExceptionInterceptor.class);
    /**
     * 系统日志记录
     */
    private static Logger logger = LoggerFactory.getLogger(ExceptionInterceptor.class.getName());
    /**
     * 异常日志统一记录（增加唯一编号便于异常定位）
     */
    private ThrowableLogger throwableLogger = new SimpleThrowableLogger();
    /**
     * 此处可以考虑扩展为从资源文件获取,借以实现国际化.
     */
    public static final String ERROR_SYS = "error.sys";
    public static final String ERROR_SQL = "error.sql";
    public static final String ERROR_APP = "error.app";
    public static final String ORGIN_ACTION = "ORGIN_ACTION";
    public static final String ORGIN_URL = "ORGIN_URL";
    public static final String ORGIN_NAMESPACE = "ORGIN_NAMESPACE";
    public static final String GLOABL_ERROR_MSG = "GLOABL_ERROR_MSG";
    static char[] hex = "0123456789ABCDEF".toCharArray();
    private String defaultEncoding = "UTF-8";

    private String uploadSizeExceed;
    @Inject(StrutsConstants.STRUTS_I18N_ENCODING)
    public void setDefaultEncoding(String val) {
        this.defaultEncoding = val;
    }

    /**
     * 在action中要定义与方法同名的result，如果action返回该result,否则返回全局异常页。
     */
    public String intercept(ActionInvocation invocation) throws Exception {
        ServletContext servletContext = ServletActionContext.getServletContext();
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        ThrowableTranslate throwableTranslate = (ThrowableTranslate) ctx.getBean("throwableTranslate");

        /**
         * 扩展操作写在catch里面。
         */
        ActionSupport action = (ActionSupport) invocation.getAction();
        ValidationException validationException = null;
        String msg = "",traceid = "",errcode = "";
        String execMethodName = invocation.getProxy().getMethod();
        Method execMethod = action.getClass().getMethod(execMethodName);

        ExecuteResult excResult = execMethod.getAnnotation(ExecuteResult.class);
        boolean froceGoGloableError=false;
        try {
            return invocation.invoke();
        } catch (SQLException e) {
            traceid = throwableLogger.logError(logger, e.getMessage(), e);
            errcode = e.getErrorCode()!=0 ? Integer.toString(e.getErrorCode()) : "";
           // msg = throwableTranslate.getDescription(e, action.getLocale());
            msg = e.getMessage();

            
            //统计异常记录
            statistLogger.error(this.recordStatistException(traceid, e.getClass().getName(), String.valueOf(e.getErrorCode()), e.getMessage()));
        } catch (AppRTException e) {
            traceid = throwableLogger.logError(logger, e.getMessage(), e);
            errcode = e.getCode();
           /* if(StringUtils.isBlank(e.getCode()))
            {
            	msg =e.getMessage();
            }else{
            	 msg = throwableTranslate.getDescription(e, action.getLocale());
                 if("UNKNOWN_EXP".equals(msg))//假如没翻译的
                 {
                    msg = e.getMessage();
                 }
            }*/
            msg = e.getMessage();

            //统计异常记录
            statistLogger.error(this.recordStatistException(traceid, e.getClass().getName(), e.getCode(), e.getMessage()));
        } catch (AppBizException e) {
            traceid = throwableLogger.logError(logger, e.getMessage(), e);
           /* if(e instanceof CoreNativeException) {
            	errcode = ((CoreNativeException)e).getNativeCode().toString();
            }else
            	errcode = e.getCode();
           // logger.debug("error1111="+traceid+",原因："+e.getMessage(), e);
            msg = throwableTranslate.getDescription(e, action.getLocale());*/
            msg = e.getMessage();
            /*logger.error("error="+traceid+",原因："+msg, e);
            if(StringUtils.isBlank(e.getCode())||(e instanceof CoreSystException))
            {
            	msg = e.getMessage();
            }else{
            	 msg = throwableTranslate.getDescription(e, action.getLocale());
                 if("UNKNOWN_EXP".equals(msg))//假如没翻译的
                 {
                    msg = e.getMessage();
                 }
            }*/
            //统计异常记录
            statistLogger.error(this.recordStatistException(traceid, e.getClass().getName(), e.getCode(), e.getMessage()));
        } catch (ValidationException e) {
            validationException = e;
            msg = e.getMessage();
            //统计异常记录
            statistLogger.error(this.recordStatistException("", e.getClass().getName(), e.getLocalizedMessage(), e.getMessage()));
        } catch (IllegalArgumentException e) {
            traceid = throwableLogger.logError(logger, e.getMessage(), e);
            msg = e.getMessage();
            
            //统计异常记录
            statistLogger.error(this.recordStatistException(traceid, e.getClass().getName(), e.getLocalizedMessage(), e.getMessage()));
        }
        catch (NullPointerException e)
        {
        	
            traceid = throwableLogger.logError(logger, e.getMessage(), e);
            msg = action.getText("SYSTEM_BUSY_ERROR");

            //统计异常记录
            statistLogger.error(this.recordStatistException(traceid, e.getClass().getName(), e.getLocalizedMessage(), e.getMessage()));
        }
        catch (Exception e) {
            traceid = throwableLogger.logError(logger, e.getMessage(), e);
            msg = action.getText("SYSTEM_BUSY_ERROR");

            //统计异常记录
            statistLogger.error(this.recordStatistException(traceid, e.getClass().getName(), e.getLocalizedMessage(), e.getMessage()));
        }
        uploadSizeExceed=action.getText("struts.messages.upload.size.exceed");
        String result = BaseAction.GLOBAL_ERROR;
        if (excResult == null) {
            if (invocation.getProxy().getConfig().getResults().containsKey(execMethodName)) {
                result = execMethodName;
            }
        } else {
            String excResultName = excResult.name();
            try {
                excResultName = TextParseUtil.translateVariables(excResultName, invocation.getStack());
            } catch (Exception ex) {
                logger.debug("计算错误页面结果出错", ex);
                excResultName = BaseAction.GLOBAL_ERROR;
            }
            if (excResultName.equals(BaseAction.GLOBAL_JSON_ERROR)) {
                result = BaseAction.GLOBAL_JSON_ERROR;
            } else if (invocation.getProxy().getConfig().getResults().containsKey(excResultName)) {
                result = excResultName;
            }
        }
        ServletActionContext.getRequest().setAttribute("EXCEPTION_GLOBAL_ERROR", result);
        if (result.equals(BaseAction.GLOBAL_JSON_ERROR)) {
        } else {
            if (action instanceof ValidationAware) {
                // generate json
                if (validationException != null) {
                    ValidationAware validationAware = (ValidationAware) action;
                    msg = msg + getMsg(validationAware);
                }
            }
        }
        //如果是调用方法上有   @ExecuteResult(name = ExecuteResult.JSON_ERROR)
        //grid 请求时候使用 @ExecuteResult(name = ExecuteResult.JSON_ERROR,code=ExecuteResult.GRID_EXCEPTION_CODE)注释，表示返回为JSON类型的异常信息
        if (result.equals(BaseAction.GLOBAL_JSON_ERROR)) {        	 
            return jsonResult(invocation, msg,composeErrcode(traceid,errcode), validationException, excResult.code());
        }
        if(msg!=null)
        {
        	msg=msg.replace("\"", "\\\"");
        }
        if(froceGoGloableError)
        {
            result=BaseAction.GLOBAL_ERROR;
        }        
        if (!result.equals(BaseAction.GLOBAL_ERROR)) {
            action.addActionError(msg);
            action.addActionError(composeErrcode(traceid,errcode));
        } else {
        	//msg += composeErrcode(traceid,errcode);
            action.clearActionErrors();
            action.clearMessages();
            ServletActionContext.getRequest().setAttribute(GLOABL_ERROR_MSG, msg);
        }

//         ServletActionContext.getRequest().setAttribute(ORGIN_URL,ServletActionContext.getRequest().getRequestURL());
        String queryString = ServletActionContext.getRequest().getQueryString();
        String reqUrl = ServletActionContext.getRequest().getRequestURI() + ((queryString == null) ? "" : ("?" + queryString));
        ServletActionContext.getRequest().setAttribute(ORGIN_URL, reqUrl);//暂时没发现哪里有用
        ServletActionContext.getRequest().setAttribute(ORGIN_ACTION, invocation.getProxy().getActionName());
        ServletActionContext.getRequest().setAttribute(ORGIN_NAMESPACE, invocation.getProxy().getNamespace());

        org.apache.struts2.convention.annotation.Action a = execMethod.getAnnotation(org.apache.struts2.convention.annotation.Action.class);
        if (a != null) {
            String[] params = a.params();
            String url = ServletActionContext.getRequest().getRequestURI();
            url = url.substring(0, url.lastIndexOf("/"));
            if (params != null && params.length > 1) {
                ServletActionContext.getRequest().setAttribute(params[0], url + "/" + params[1]);
            }
        }
        return result;
    }
    
    private String composeErrcode(String traceid,String errcode){
    	StringBuffer sb = new StringBuffer();
        if (StringUtils.isNotBlank(traceid)) {
            sb.append("(");
            sb.append(traceid);
            sb.append(")");
        }
        sb.append("-[");
        sb.append(errcode);
        sb.append("]"); 
        return sb.toString();
    }

    private String jsonResult(ActionInvocation invocation, String msg, String errcode,ValidationException e, int code) {
        try {
            ActionContext actionContext = invocation.getInvocationContext();

            HttpServletRequest request = (HttpServletRequest) actionContext.get(StrutsStatics.HTTP_REQUEST);
            HttpServletResponse response = (HttpServletResponse) actionContext.get(StrutsStatics.HTTP_RESPONSE);

            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.setContentType("application/json;charset=" + defaultEncoding);
            if (code > 0) {
                response.setStatus(code);
            }
            ServletOutputStream sos = response.getOutputStream();

            StringBuilder sb = new StringBuilder();
            sb.append("{ ");
            sb.append("\"error\":true,");
            sb.append("\"msg\":");
            sb.append("\"");
            if (e != null) {
                msg = "Validation error!";
            }
            if (msg == null) {
                msg = "";
            }
            sb.append(escapeJSON(msg));
            sb.append("\",");
            sb.append("\"errcode\":\""+errcode+"\"");
            if (e != null) {
                Object action = invocation.getAction();

                if (action instanceof ValidationAware) {
                    // generate json
                    ValidationAware validationAware = (ValidationAware) action;
                    {
                        sb.append(",");
                    }
                    sb.append(buildResponse(validationAware));
                }
            }
            sb.append("}");
            sos.write(sb.toString().getBytes(defaultEncoding));
            sos.close();
        } catch (Exception ex) {
            logger.warn("出错啦", ex);
        }
        return Action.NONE;
    }

    protected String buildResponse(ValidationAware validationAware) {

        StringBuilder sb = new StringBuilder();

        if (validationAware.hasErrors()) {
            //action errors
            if (validationAware.hasActionErrors()) {
                sb.append("\"errors\":");
                sb.append(buildArray(validationAware.getActionErrors()));
            }

            //field errors
            if (validationAware.hasFieldErrors()) {
                if (validationAware.hasActionErrors()) {
                    sb.append(",");
                }
                sb.append("\"fieldErrors\": {");
                Map<String, List<String>> fieldErrors = validationAware.getFieldErrors();
                for (Map.Entry<String, List<String>> fieldError : fieldErrors.entrySet()) {
                    sb.append("\"");
                    //if it is model driven, remove "model." see WW-2721
                    sb.append(validationAware instanceof ModelDriven ? fieldError.getKey().substring(6)
                            : fieldError.getKey());
                    sb.append("\":");
                    sb.append(buildArray(fieldError.getValue()));
                    sb.append(",");
                }
                //remove trailing comma, IE creates an empty object, duh
                sb.deleteCharAt(sb.length() - 1);
                sb.append("}");
            }
        }

        /*response  like:
         * {
         *      "error":true
         *      "errors": ["this", "that"],
         *      "fieldErrors": {
         *            field1: "this",
         *            field2: "that"
         *      }
         * }
         */
        return sb.toString();
    }

    private String buildArray(Collection<String> values) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (String value : values) {
            sb.append("\"");
            sb.append(escapeJSON(value));
            sb.append("\",");
        }
        if (values.size() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append("]");
        return sb.toString();
    }

    private String getMsg(ValidationAware e) {
        StringBuilder sb = new StringBuilder();

        if ((e.getActionErrors() != null) && (e.getActionErrors().size() > 0)) {
            sb.append("");
            sb.append(buildArrayString(e.getActionErrors()));
        }
        if ((e.getFieldErrors() != null) && (e.getFieldErrors().size() > 0)) {
            sb.append("fieldErrors: [");
            Map<String, List<String>> fieldErrors = e.getFieldErrors();
            for (Map.Entry<String, List<String>> fieldError : fieldErrors.entrySet()) {
                sb.append("[");
                sb.append(fieldError.getKey());
                sb.append("]:");
                sb.append(buildArrayString(fieldError.getValue()));
                sb.append(",");
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append("]");
        }
        return sb.toString();
    }

    private String buildArrayString(Collection<String> values) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (String value : values) {
            if(value.indexOf("the request was rejected because its size")>=0)
            {
                value=uploadSizeExceed;
            }
            sb.append(escapeJSON(value));
            sb.append(",");
        }
        if (values.size() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append("]");
        return sb.toString();
    }

//    private String getMsg(String traceid, String i18n, String code) {
//        StringBuffer sb = new StringBuffer();
//        sb.append(i18n);
//        if (StringUtils.isNotBlank(traceid)) {
//            sb.append("(");
//            sb.append(traceid);
//            sb.append(")");
//        }
//        sb.append("-[");
//        sb.append(code);
//        sb.append("]");
//        return sb.toString();
//    }

    public void destroy() {
    }

    public void init() {
    }

    private String escapeJSON(Object obj) {
        StringBuilder sb = new StringBuilder();

        CharacterIterator it = new StringCharacterIterator(obj.toString());

        for (char c = it.first(); c != CharacterIterator.DONE; c = it.next()) {
            if (c == '"') {
                sb.append("\\\"");
            } else if (c == '\\') {
                sb.append("\\\\");
            } else if (c == '/') {
                sb.append("\\/");
            } else if (c == '\b') {
                sb.append("\\b");
            } else if (c == '\f') {
                sb.append("\\f");
            } else if (c == '\n') {
                sb.append("\\n");
            } else if (c == '\r') {
                sb.append("\\r");
            } else if (c == '\t') {
                sb.append("\\t");
            } else if (Character.isISOControl(c)) {
                sb.append(unicode(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * Represent as unicode
     * @param c character to be encoded
     */
    private String unicode(char c) {
        StringBuilder sb = new StringBuilder();
        sb.append("\\u");

        int n = c;
        if (n == 0) {
            return "";
        }
        for (int i = 0; i < 4; ++i) {
            int digit = (n & 0xf000) >> 12;

            sb.append(hex[digit]);
            n <<= 4;
        }
        return sb.toString();
    }

    /**
     * 记录格式函数
     */
    private String recordStatistException(String sysTrackId, String exceptType, String exceptCode, String exceptMsg) {
        String recordFormat = "sysTrackId={0}|exceptType={1}|exceptCode={2}|exceptMsg={3}";
        return MessageFormat.format(recordFormat,sysTrackId, exceptType, exceptCode, exceptMsg);
    }
}

