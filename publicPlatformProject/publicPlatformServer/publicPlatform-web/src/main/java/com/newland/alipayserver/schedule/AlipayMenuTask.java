package com.newland.alipayserver.schedule;

import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.newland.alipayService.service.MenuService;

/**
 * 微信菜单定时同步
 * 
 * @author hongye
 * @since 2014-3-12
 */
@Service("alipayMenuTask")
public class AlipayMenuTask {
	private static final Logger logger = LoggerFactory.getLogger(AlipayMenuTask.class);
	
	@Resource(name = "menuService")
	private MenuService menuService;
	
	public void runTask() throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append("服务窗菜单定时同步定时任务开始运行... 时间：" + new Date() + "\n");
		try{
			menuService.addMenu();
		}catch(Exception e){
			sb.append(e.toString());
			throw e;
		}finally{
			sb.append("服务窗菜单定时同步定时定时任务运行结束... 时间：" + new Date());
			logger.info(sb.toString());
		}
	}

}
