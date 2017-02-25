package com.newland.base.util;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class SafeThreadPoolTaskExecutor extends ThreadPoolTaskExecutor
		implements DisposableBean {

	private static final long serialVersionUID = 6778852824270009051L;

	private boolean isShuttingDown = false;

	@Override
	public void destroy() {
		setShuttingDown(true);
		try {
			// 暂停五秒种
			Thread.sleep(1000 * 5);
		} catch (InterruptedException e) {
		}
		int count = super.getActiveCount();
		while (count > 0) {
			logger.info("还有[" + count + "]个任务正在执行中，稍后再执行关闭...");
			try {
				// 暂停五秒种
				Thread.sleep(1000 * 5);
			} catch (InterruptedException e) {
			}
			count = super.getActiveCount();
		}
		logger.info("安全线程池服务关闭.");
	}


	public boolean isShuttingDown() {
		return isShuttingDown;
	}


	public void setShuttingDown(boolean isShuttingDown) {
		this.isShuttingDown = isShuttingDown;
	}

	public boolean isShuttingdown() {
		return isShuttingDown;
	}
	
}
