package com.cib.alipayserver.schedule;

import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.newland.wechat.proxy.service.AlipayNodeService;

/**
 * 服务器健康检查定时器
 * 
 * @author hongye
 * @since 2014-7-25
 */
@Service("serverHealthExaminationTask")
public class ServerHealthExaminationTask {
private static final Logger logger = LoggerFactory.getLogger(ServerHealthExaminationTask.class);
	
	@Resource(name = "alipayNodeService")
	private AlipayNodeService alipayNodeService;
	
	public void runTask() throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append("钱包服务器健康检查定时任务开始运行... 时间：" + new Date() + "\n");
		try{
			alipayNodeService.failAlipayList();
		}catch(Exception e){
			sb.append(e.toString());
			throw e;
		}finally{
			sb.append("钱包服务器健康检查定时定时任务运行结束... 时间：" + new Date());
			logger.info(sb.toString());
		}
	}

}
