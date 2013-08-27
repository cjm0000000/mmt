package lemon.web.system.mapper;

import java.util.List;

import lemon.web.system.bean.UserConfig;

import org.springframework.stereotype.Repository;

/**
 * 用户参数
 * 
 * @author 张连明
 * @date May 14, 2012 10:18:34 AM
 */
@Repository
public interface UserConfigMapper {
	UserConfig getItem(UserConfig obj);

	List<UserConfig> getItems(int user_id);

	void saveItem(UserConfig obj);

	void deleteItem(UserConfig obj);
}
