package com.github.cjm0000000.shared.test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 基于Spring测试套件的基础类
 * @author lemon
 * @version 1.1
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring-db.xml", "classpath:spring-dao.xml",
		"classpath:spring-service.xml" })
public abstract class AbstractTester {
	protected static final int CUST_ID = -5743;
}
