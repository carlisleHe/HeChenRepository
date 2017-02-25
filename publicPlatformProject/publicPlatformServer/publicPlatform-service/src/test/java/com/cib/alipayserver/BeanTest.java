package com.cib.alipayserver;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations = {"classpath:alipay-context.xml"})
public class BeanTest extends AbstractJUnit4SpringContextTests { 

}
