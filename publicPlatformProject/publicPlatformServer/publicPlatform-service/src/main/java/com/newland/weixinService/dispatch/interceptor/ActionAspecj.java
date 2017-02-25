package com.newland.weixinService.dispatch.interceptor;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.newland.weixinService.dispatch.Action;
import com.newland.weixinService.session.WeixinSession;
/**
 * Action 执行拦截器
 * 完成Action变量自动装配
 * @author dvlp
 *
 */
public class ActionAspecj {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private Set<String> methods = new HashSet<String>();
	
	public void doAfter(JoinPoint jp) {  
        logger.debug("log Ending method: "  
                + jp.getTarget().getClass().getName() + "."  
                + jp.getSignature().getName());  
        if (methods.contains(jp.getSignature().getName())){
        	Action action = (Action)jp.getTarget();
        	WeixinSession ses = null;
        	Object[] objs = jp.getArgs();
        	if (objs != null){
        		for (Object obj : objs){
        			if (obj instanceof WeixinSession){
        				ses = (WeixinSession)obj;
        				break;
        			}
        		}
        	}
        	if (ses == null) return;
        	this.unpopulator(action, ses);
        }
    }  
  
  
    public void doBefore(JoinPoint jp) {  
        System.out.println("log Begining method: "  
                + jp.getTarget().getClass().getName() + "."  
                + jp.getSignature().getName());  
        if (methods.contains(jp.getSignature().getName())){
        	Action action = (Action)jp.getTarget();
        	WeixinSession ses = null;
        	Object[] objs = jp.getArgs();
        	if (objs != null){
        		for (Object obj : objs){
        			if (obj instanceof WeixinSession){
        				ses = (WeixinSession)obj;
        				break;
        			}
        		}
        	}
        	if (ses == null) return;
        	this.populator(action, ses);
        }
    }  
  
    public void doThrowing(JoinPoint jp, Throwable ex) {  
        System.out.println("method " + jp.getTarget().getClass().getName()  
                + "." + jp.getSignature().getName() + " throw exception");  
        System.out.println(ex.getMessage()); 
        if (methods.contains(jp.getSignature().getName())){
        	Action action = (Action)jp.getTarget();
        	WeixinSession ses = null;
        	Object[] objs = jp.getArgs();
        	if (objs != null){
        		for (Object obj : objs){
        			if (obj instanceof WeixinSession){
        				ses = (WeixinSession)obj;
        				break;
        			}
        		}
        	}
        	if (ses == null) return;
        	this.unpopulator(action, ses);
        }
    }


	public Set<String> getMethods() {
		return methods;
	}


	public void setMethods(Set<String> methods) {
		this.methods = methods;
	}
	
	public static final String SCOPE_MAP = "SCOPE_MAP";
	
	private void populator(Action action, WeixinSession ses){
		Map<String, Object> map = ses.getValue(SCOPE_MAP);
		if (map == null){
			map = new HashMap<String, Object>();
			ses.addValue(SCOPE_MAP, map);
		}
		try {
			BeanUtilsBean.getInstance().populate(action, map);
		} catch (Exception e) {
			logger.error("恢复属性失败", e);
		}
		
	}
	
	private void unpopulator(Action action, WeixinSession ses){
		Map<String, Object> map = ses.getValue(SCOPE_MAP);
		if (map == null){
			map = new HashMap<String, Object>();
			ses.addValue(SCOPE_MAP, map);
		}
		Field[] fields = action.getClass().getDeclaredFields();
		for (Field field:fields){
			try {
				logger.debug("缓存: [" + field.getName() + "]");
				map.put(field.getName(), this.getProperty(action, field));
			} catch (Exception e) {
				logger.error("缓存属性失败", e);
			}
		}
	}
	
	private Object getProperty(Object obj, Field field) throws Exception{
		boolean accessible = field.isAccessible();
		field.setAccessible(true);
		Object temp = field.get(obj);
		field.setAccessible(accessible);
		return temp;
	}

}
