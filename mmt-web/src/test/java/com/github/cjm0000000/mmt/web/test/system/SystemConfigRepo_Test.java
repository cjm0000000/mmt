package com.github.cjm0000000.mmt.web.test.system;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.cjm0000000.mmt.web.system.bean.SystemConfig;
import com.github.cjm0000000.mmt.web.system.persistence.SysConfigRepository;
import com.github.cjm0000000.mmt.web.test.AbstractWebTester;

public class SystemConfigRepo_Test extends AbstractWebTester{
	@Autowired
	private SysConfigRepository systemConfigMapper;
	
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
