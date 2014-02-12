package com.github.cjm0000000.mmt.web.test.system;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.cjm0000000.mmt.web.system.bean.UserConfig;
import com.github.cjm0000000.mmt.web.system.persistence.UserConfigRepository;
import com.github.cjm0000000.mmt.web.test.AbstractWebTester;

public class UserConfigRepo_Test extends AbstractWebTester{
	@Autowired
	private UserConfigRepository userConfigMapper;
	private final int user_id = -11;
	
	@Test
	public void deleteItem(){
		UserConfig item = addItem(user_id,"testkey", "testvalue");
		userConfigMapper.deleteItem(user_id, item.getKey());
		item = userConfigMapper.getItem(user_id, item.getKey());
		assertNull(item);
	}
	
	@Test
	public void deleteItems(){
		UserConfig item1 = addItem(user_id,"testkey1", "testvalue1");
		UserConfig item2 = addItem(user_id,"testkey2", "testvalue2");
		userConfigMapper.deleteItems(user_id);
		item1 = userConfigMapper.getItem(user_id, item1.getKey());
		assertNull(item1);
		item2 = userConfigMapper.getItem(user_id, item2.getKey());
		assertNull(item2);
	}
	
	@Test
	public void getItems(){
		userConfigMapper.deleteItems(user_id);
		addItem(user_id,"testkey1", "testvalue1");
		addItem(user_id,"testkey2", "testvalue2");
		List<UserConfig> list = userConfigMapper.getItems(user_id);
		assertEquals(2, list.size());
		userConfigMapper.deleteItems(user_id);
	}
	
	private UserConfig addItem(int user_id,String key,String value){
		UserConfig item = new UserConfig();
		item.setUser_id(user_id);
		item.setKey(key);
		item.setValue(value);
		userConfigMapper.addItem(item);
		return item;
	}
}
