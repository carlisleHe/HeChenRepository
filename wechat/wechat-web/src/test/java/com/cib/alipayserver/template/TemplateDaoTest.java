package com.cib.alipayserver.template;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.cib.alipayserver.BeanTest;
import com.newland.wechatServer.template.dao.TemplateDao;
import com.newland.wechatServer.template.model.TempConf;
import com.newland.wechatServer.template.model.TempConfExt;
import com.newland.wechatServer.template.model.Template;

public class TemplateDaoTest extends BeanTest {
	@Autowired
	TemplateDao templdateDao;
	@Test
	public void test(){
		Template temp = new Template();
		temp.setId("00");
		temp.setCreateTime(new Date());
		temp.setUpdateTime(new Date());
		temp.setDescript("测试");
		temp.setTemplateId("08238478");
		Set<TempConf> set = new HashSet<TempConf>();
		temp.setTempConfs(set);
		TempConf conf = new TempConf();
		conf.setContent("尊敬的{ownerName}:您的兴业银行理财卡{accountNo}{currencyName}账户发生如下交易");
		conf.setDescript("title");
		conf.setKeyword(TempConf.KEYWORD_TITLE);
		conf.setTemplate(temp);
		conf.setUpdateTime(new Date());
		set.add(conf);
		conf = new TempConf();
		conf.setContent("{transDate,date,yyyy年MM月dd日  HH:mm}");
		conf.setDescript("time");
		conf.setKeyword(TempConf.KEYWORD_TIME);
		conf.setTemplate(temp);
		conf.setUpdateTime(new Date());
		set.add(conf);
		conf = new TempConf();
		conf.setContent("{transType}");
		conf.setDescript("type");
		conf.setKeyword(TempConf.KEYWORD_TYPE);
		conf.setTemplate(temp);
		conf.setUpdateTime(new Date());
		set.add(conf);
		conf = new TempConf();
		conf.setContent("{symbol}{transAmount,number,#,##0.00} {currencyUnit}");
		conf.setDescript("number");
		conf.setKeyword(TempConf.KEYWORD_NUMBER);
		conf.setTemplate(temp);
		conf.setUpdateTime(new Date());
		set.add(conf);
		conf = new TempConf();
		conf.setContent("{balance,number,#,##0.00} {currencyUnit}");
		conf.setDescript("balance");
		conf.setKeyword(TempConf.KEYWORD_BALANCE);
		conf.setTemplate(temp);
		conf.setUpdateTime(new Date());
		set.add(conf);
		conf = new TempConf();
		conf.setContent("如有疑问，请咨询95561。");
		conf.setDescript("remark");
		conf.setKeyword(TempConf.KEYWORD_REMARK);
		conf.setTemplate(temp);
		conf.setUpdateTime(new Date());
		set.add(conf);
		Set<TempConfExt> exts = new HashSet<TempConfExt>();
		conf.setExts(exts);
		TempConfExt ext = new TempConfExt();
		ext.setContent("扩展");
		ext.setKeyword("extend");
		ext.setTempConf(conf);
		ext.setUpdateTime(new Date());
		exts.add(ext);
		this.templdateDao.save(temp);
		
	}
	@Test
	public void testQuery(){
		Template temp = this.templdateDao.findById("00");
		System.err.println(temp + " - " + temp.getTempConfs());
	}

}
