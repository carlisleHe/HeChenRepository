package com.cib.alipayserver.util;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class JUnitUtils {
	private static Boolean isRunningTest;
	/**
	 * 检测是否在运行单元测试
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Boolean IsRunningTest() {
		if (null == isRunningTest) {
			StackTraceElement[] stackTrace = Thread.currentThread()
					.getStackTrace();
			List statckList = Arrays.asList(stackTrace);
			for (Iterator i = statckList.iterator(); i.hasNext();) {
				String stackString = i.next().toString();
				if (stackString.lastIndexOf("junit.runner") > -1) {
					isRunningTest = true;
					return isRunningTest;
				}
			}
			isRunningTest = false;
			return isRunningTest;
		} else {
			return isRunningTest;
		}
	}
}
