package com.cib.alipayserver.web.result;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.StrutsConstants;
import org.apache.struts2.StrutsStatics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Result;
import com.opensymphony.xwork2.inject.Inject;

public class GlobalJsonErrorResult implements Result {

    static char[] hex = "0123456789ABCDEF".toCharArray();
	private static final Logger LOG = LoggerFactory
			.getLogger(GlobalJsonErrorResult.class);


   private StringBuffer json = new StringBuffer("");
   private String defaultEncoding = "UTF-8";

   @Inject(StrutsConstants.STRUTS_I18N_ENCODING)
   public void setDefaultEncoding(String val) {
       this.defaultEncoding = val;
   }

   public void execute(ActionInvocation invocation) throws Exception {
       ActionContext actionContext = invocation.getInvocationContext();

       HttpServletRequest request = (HttpServletRequest) actionContext.get(StrutsStatics.HTTP_REQUEST);
       HttpServletResponse response = (HttpServletResponse) actionContext.get(StrutsStatics.HTTP_RESPONSE);

       try {
           response.setHeader("Pragma", "no-cache");
           response.setHeader("Cache-Control", "no-cache");
           response.setDateHeader("Expires", 0);
           response.setContentType("application/json;charset=" + defaultEncoding);
           ServletOutputStream sos = response.getOutputStream();

           String errorMsg=  actionContext.getValueStack().findValue("errorMsg").toString();
           if(errorMsg==null)errorMsg="";
           this.add("{\"error\":true,");
           this.add("msg", errorMsg);
           this.add("}");
           sos.write(this.json.toString().getBytes(defaultEncoding));
           sos.close();
       } catch (Exception ex) {
             LOG.warn("出错啦", ex);
       }

   }

     private void add(String name, Object value, boolean hasData) {

       this.add('"');
       this.add(name);
       this.add("\":");
       this.string(value);
       if (hasData) {
           this.add(',');
       }
   }

   private void add(String name, Object value) {
       this.add(name, value, true);
   }

   private void add(Object obj) {
       this.json.append(obj);
   }

   private void escapseString(Object obj) {
       CharacterIterator it = new StringCharacterIterator(obj.toString());

       for (char c = it.first(); c != CharacterIterator.DONE; c = it.next()) {
           if (c == '"') {
               this.add("\\\"");
           } else if (c == '\\') {
               this.add("\\\\");
           } else if (c == '/') {
               this.add("\\/");
           } else if (c == '\b') {
               this.add("\\b");
           } else if (c == '\f') {
               this.add("\\f");
           } else if (c == '\n') {
               this.add("\\n");
           } else if (c == '\r') {
               this.add("\\r");
           } else if (c == '\t') {
               this.add("\\t");
           } else if (Character.isISOControl(c)) {
               this.unicode(c);
           } else {
               this.add(c);
           }
       }
   }

   private void string(Object obj) {
       this.add('"');

       escapseString(obj);

       this.add('"');
   }

   /**
    * Represent as unicode
    *
    * @param c
    *            character to be encoded
    */
   private void unicode(char c) {
       this.add("\\u");

       int n = c;

       for (int i = 0; i < 4; ++i) {
           int digit = (n & 0xf000) >> 12;

           this.add(hex[digit]);
           n <<= 4;
       }
   }

}
