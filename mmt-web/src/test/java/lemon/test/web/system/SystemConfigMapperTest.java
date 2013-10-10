package lemon.test.web.system;

import static org.junit.Assert.*;

import lemon.web.system.bean.SystemConfig;
import lemon.web.system.mapper.SystemConfigMapper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@RunWith(JUnit4.class)
public class SystemConfigMapperTest {
	private AbstractApplicationContext acx;
	private SystemConfigMapper systemConfigMapper;
	
	@Before
	public void init(){
		String[] resource = { "classpath:spring-db.xml",
				"classpath:spring-dao.xml", "classpath:spring-service.xml" };
		acx = new ClassPathXmlApplicationContext(resource);
		systemConfigMapper = acx.getBean(SystemConfigMapper.class);
		assertNotNull(systemConfigMapper);
	}
	
	@Test
	public void deleteItem(){
		SystemConfig item = addItem("testkey", "testvalue");
		int result = systemConfigMapper.deleteItem(item.getKey());
		assertNotEquals(0, result);
		item = systemConfigMapper.getItem(item.getKey());
		assertNull(item);
	}
	
	@After
	public void destory(){
		acx.close();
	}
	
	private SystemConfig addItem(String key,String value){
		SystemConfig item = new SystemConfig();
		item.setKey(key);
		item.setValue(value);
		int result = systemConfigMapper.addItem(item);
		assertNotEquals(0, result);
		return item;
	}
}
