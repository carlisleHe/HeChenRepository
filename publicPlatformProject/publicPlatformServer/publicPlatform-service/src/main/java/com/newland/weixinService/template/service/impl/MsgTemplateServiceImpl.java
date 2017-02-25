package com.newland.weixinService.template.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.utils.CloneUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.intensoft.cache.annotation.CacheMode;
import com.intensoft.cache.annotation.FieldParameter;
import com.intensoft.cache.annotation.MethodCache;
import com.intensoft.cache.annotation.NetbankAopProxy;
import com.intensoft.exception.AppRTException;
import com.newland.base.common.AppExCode;
import com.newland.base.common.Const;
import com.newland.weixinService.exception.WeixinException;
import com.newland.weixinService.exception.WeixinExceptionType;
import com.newland.weixinService.template.dao.TemplateDao;
import com.newland.weixinService.template.model.ColumnDefine;
import com.newland.weixinService.template.model.MsgTemplate;
import com.newland.weixinService.template.model.TempConf;
import com.newland.weixinService.template.model.TempConfExt;
import com.newland.weixinService.template.model.Template;
import com.newland.weixinService.template.service.MsgTemplateService;
@NetbankAopProxy
public class MsgTemplateServiceImpl implements MsgTemplateService {
	private final Logger logger = LoggerFactory.getLogger(MsgTemplateService.class);
	
	@Resource(name = "templateDao")
	private TemplateDao templateDao;
	
	private Map<String, Map<String, MsgTemplate>> templateMap;


	@Override
	@MethodCache(expireTime=60*60,fields={@FieldParameter(paramIndex=0)})
	public MsgTemplate findById(String id) {
		return this.findById(id, Const.DEFAULT_MSG_TEMPLATE);
	}



	/**
	 * 数据库加载模板数据
	 * @throws WeixinException
	 * @throws CloneNotSupportedException
	 */
	private void loadTemplate() throws WeixinException, CloneNotSupportedException {
		if (templateMap == null) templateMap = new HashMap<String, Map<String, MsgTemplate>>();
		List<Template> list = this.templateDao.queryAll();
		if (CollectionUtils.isEmpty(list)) throw new WeixinException(WeixinExceptionType.TEMPLATE_NOTFOUNT);
		for (Template temp : list){
			Map<String, Set<TempConfExt>> exts = new HashMap<String, Set<TempConfExt>>();
			MsgTemplate def = new MsgTemplate();
			def.setTemplateId(temp.getTemplateId());
			def.setTopcolor(temp.getTopColor());
			def.setUrl(temp.getUrl());
			Set<TempConf> confs = temp.getTempConfs();
			//modify by xuzz at 2014-08-14
			//跟绍全讨论过，认为真实操作过程当中很有可能模板的配置还没有加入数据库，所以这里做了兼容性的操作，不再以异常的形式返回
			if (CollectionUtils.isEmpty(confs)) {
				continue;
			}
			Map<String, ColumnDefine> columns = new HashMap<String, ColumnDefine>();
			for (TempConf conf : confs){
				if ("1".equals(conf.getConfType())) {
					def.putAddition(conf.getKeyword(), conf.getContent());
					continue;
				}
				ColumnDefine define = new ColumnDefine();
				define.setColor(conf.getColor());
				define.setKey(conf.getKeyword());
				ContentWrapper wrapper = new ContentWrapper(conf.getContent());
				define.setValue(wrapper.getContent());
				define.setPropertyNames(wrapper.getProps());
				columns.put(conf.getKeyword(),define);
				if ("1".equals(conf.getAddtion()) && CollectionUtils.isEmpty(conf.getExts()) == false){
					exts.put(conf.getKeyword(), conf.getExts());
				}
			}
			def.setColumns(getColumnDefines(def, columns));
			Map<String, MsgTemplate> msgMaps = new HashMap<String, MsgTemplate>();
			msgMaps.put(Const.DEFAULT_MSG_TEMPLATE, def);
			for (Map.Entry<String, Set<TempConfExt>> entry:exts.entrySet()){
				for (TempConfExt ext:entry.getValue()){
					MsgTemplate msgTemp = (MsgTemplate)CloneUtils.clone(def);
					ColumnDefine define = columns.get(entry.getKey());
					ColumnDefine newdef = null;
					if (define != null) newdef = (ColumnDefine)CloneUtils.clone(define);
					newdef.setColor(define.getColor());
					newdef.setKey(define.getKey());
					ContentWrapper wrapper = new ContentWrapper(ext.getContent());
					newdef.setValue(wrapper.getContent());
					newdef.setPropertyNames(wrapper.getProps());
					columns.put(entry.getKey(),newdef);
					msgTemp.setColumns(getColumnDefines(msgTemp, columns));
					msgMaps.put(ext.getKeyword(), msgTemp);
					columns.put(entry.getKey(), define);
				}
			}
			templateMap.put(temp.getId(), msgMaps);
		}
	}
	
	private List<ColumnDefine> getColumnDefines(MsgTemplate template, Map<String, ColumnDefine> columns){
		List<ColumnDefine> list = new ArrayList<ColumnDefine>();
		Map<String, ColumnDefine> temp = new HashMap<String, ColumnDefine>();
		temp.putAll(columns);
		for (Map.Entry<String, ColumnDefine> entry:columns.entrySet()){
			ColumnDefine define = entry.getValue();
			for (String prop:define.getPropertyNames()){
				if (columns.get(prop) != null){
					ColumnDefine cd = temp.get(prop);
					template.putAddition(cd.getKey(), revertLineBreak(cd.getValue()));
				}
			}
		}
		for (Map.Entry<String, ColumnDefine> entry:temp.entrySet()){
			list.add(entry.getValue());
		}
		return list;
	}
	
	private static class ContentWrapper{
		List<String> props;
		String content;
		public ContentWrapper(String resource){
			this.content = resource;
			init();
		}
		private void init() {
			Pattern pattern = Pattern.compile("\\{(.+?)[^\\}]\\}");
			Matcher matcher = pattern.matcher(content);
			List<String> list = new ArrayList<String>();
			while(matcher.find()){
			   String temp = matcher.group(0);
			   temp = temp.replaceAll("\\{", "");
			   temp = temp.replaceAll("\\}", "");
			   String[] nodes = StringUtils.split(temp, ",");
			   if (nodes.length > 0) list.add(nodes[0]);
			}
			props = list;
			for (int i = 0 ; i < list.size(); i ++){
				content = content.replaceAll("\\{" + list.get(i), "{" + i );
			}
		}
		public String[] getProps() {
			return props.toArray(new String[props.size()]);
		}

		public String getContent() {
			//对换行符进行特殊处理，这里也就限制了模板的内容不能有\n非换行用的类似的字符
			 content = revertLineBreak(content);
			 return content;
		}
	}
	/**
	 * 还原换行符(替换"\\n"为"\n")，解决数据符查询出来被驱动或应用转义掉的问题
	 * @param content
	 * @return
	 */
	public static String revertLineBreak(String content){
		String regex = "\\\\n";
		String replacement = "\n";
		if(StringUtils.isBlank(content)){
			return content;
		}
		return content.replaceAll(regex, replacement);
	}

	public TemplateDao getTemplateDao() {
		return templateDao;
	}

	public void setTemplateDao(TemplateDao templateDao) {
		this.templateDao = templateDao;
	}

	@Override
	@MethodCache(expireTime = 3600, cacheMode = CacheMode.BY_ARGS_VALUE, fields = {
			@FieldParameter(paramIndex = 0), @FieldParameter(paramIndex = 1) })
	public MsgTemplate findById(String templateId, String alias) {
		try{
			loadTemplate();
			Map<String, MsgTemplate> map = templateMap.get(templateId);
			if (CollectionUtils.isEmpty(map)) return null;
			MsgTemplate temp =  map.get(alias);
			if (temp == null) return map.get(Const.DEFAULT_MSG_TEMPLATE);
			return temp;
		}
		catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new AppRTException(AppExCode.TEMPLATE_LOAD_ERR, String.format("动户通知模板加载失败！原因：[%s]", e.getMessage()));
		}
		
	}

}
