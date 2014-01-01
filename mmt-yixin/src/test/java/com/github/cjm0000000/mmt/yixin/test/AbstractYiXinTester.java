package com.github.cjm0000000.mmt.yixin.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 基于Spring测试套件的基础类
 * 
 * @author lemon
 * @version 1.1
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
		"file:../mmt-shared/src/test/resources/spring-db.xml",
		"file:../mmt-shared/src/test/resources/spring-dao.xml",
		"file:../mmt-shared/src/test/resources/spring-service.xml" })
public abstract class AbstractYiXinTester {
	protected static final int CUST_ID = -5743;
	
	/**
	 * default test case
	 */
	protected abstract void defaultCase();
	
	@Test
	public void run(){
		defaultCase();
	}
}
