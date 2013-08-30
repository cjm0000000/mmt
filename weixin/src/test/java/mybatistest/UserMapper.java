package mybatistest;

import org.apache.ibatis.annotations.Select;

public interface UserMapper {

	@Select("SELECT name FROM user WHERE name=#{name}")
	User get(String name);
}
