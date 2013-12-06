package lemon.yixin.test.base;

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
		"file:../shared/src/test/resources/spring-db.xml",
		"file:../shared/src/test/resources/spring-dao.xml",
		"file:../shared/src/test/resources/spring-service.xml" })
public abstract class BaseYiXinTest {
	protected static final int CUST_ID = -5743;
}
