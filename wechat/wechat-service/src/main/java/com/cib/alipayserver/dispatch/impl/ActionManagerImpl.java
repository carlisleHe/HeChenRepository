package com.cib.alipayserver.dispatch.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.stereotype.Service;

import com.cib.alipayserver.dispatch.Action;
import com.cib.alipayserver.dispatch.ActionManager;
@Service("actionManager")
public class ActionManagerImpl implements ActionManager, InitializingBean, BeanFactoryAware {
	
	private Map<String, String> actionMap = new HashMap<String, String>();

	private Logger logger = LoggerFactory.getLogger(getClass());
	@Override
	public Action getAction(String key) {
		if(StringUtils.isBlank(key)){
			return null;
		}
		key=StringUtils.lowerCase(key);
		String name = this.actionMap.get(key);
		if (StringUtils.isBlank(name)) return null;
		return (Action)this.factory.getBean(name);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Map<String, Action> map = BeanFactoryUtils.beansOfTypeIncludingAncestors((ListableBeanFactory)factory, Action.class);
		if (map != null){
			for (Map.Entry<String, Action> entry: map.entrySet()){
				Action action = entry.getValue();
				String value = entry.getKey();
				if (action != null){
					logger.info("装入Action: [" + action.getActionKey() + "] beanName:[" + value + "]");
					actionMap.put(StringUtils.lowerCase(action.getActionKey()), value);
				}
			}
		}
	}
	BeanFactory factory;
	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		factory = beanFactory;
	}


}
