package lemon.test.web.system;

import static org.junit.Assert.*;

import java.util.List;

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
	public void testAll(){
		String group = "TG0000000000000001";
		SystemConfig item1 = addItem(group ,"testkey1", "testvalue1");
		addItem(group ,"testkey2", "testvalue2");
		addItem(group ,"testkey3", "testvalue3");
		addItem(group ,"testkey4", "testvalue4");
		List<SystemConfig> list = systemConfigMapper.getItems(group);
		assertNotNull(list);
		assertEquals(4, list.size());
		int result = systemConfigMapper.deleteItem(group,item1.getKey());
		assertNotEquals(0, result);
		item1 = systemConfigMapper.getItem(group,item1.getKey());
		assertNull(item1);
		result = systemConfigMapper.deleteItems(group);
		assertNotEquals(0, result);
		list = systemConfigMapper.getItems(group);
		assertEquals(0, list.size());
	}
	
	@After
	public void destory(){
		acx.close();
	}
	
	private SystemConfig addItem(String group, String key,String value){
		SystemConfig item = new SystemConfig();
		item.setKey(key);
		item.setValue(value);
		item.setGroup(group);
		int result = systemConfigMapper.addItem(item);
		assertNotEquals(0, result);
		return item;
	}
}
