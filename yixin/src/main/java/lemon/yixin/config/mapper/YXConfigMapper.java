package lemon.yixin.config.mapper;

import java.util.List;

import lemon.yixin.config.bean.YiXinConfig;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.scripting.defaults.RawLanguageDriver;
import org.springframework.stereotype.Repository;

/**
 * Repository of YiXin configures
 * @author lemon
 * @version 1.0
 *
 */
@Repository
public interface YXConfigMapper {
	
	/**
	 * Get a YiXin configure
	 * @param cust_id
	 * @return
	 */
	@Select("SELECT A.cust_id, A.yx_account, A.token, A.timestamp, A.biz_class, A.subscribe_msg, A.welcome_msg, A.appid, A.secret, A.api_url FROM yixin_config A WHERE A.cust_id=#{cust_id}")
	@Lang(RawLanguageDriver.class)
	YiXinConfig get(int cust_id);
	
	/**
	 * Get all active configures
	 * @return
	 */
	@Select("SELECT A.cust_id, A.yx_account, A.token, A.timestamp, A.biz_class, A.subscribe_msg, A.welcome_msg, A.appid, A.secret, A.api_url FROM yixin_config A, customer C, customer_service S WHERE A.cust_id=C.cust_id AND A.cust_id=S.cust_id AND C.status='AVAILABLE' AND S.status='AVAILABLE' AND S.service_type='YIXIN'")
	@Lang(RawLanguageDriver.class)
	List<YiXinConfig> availableList();
	
	/**
	 * Add YiXin configure
	 * @param config
	 * @return
	 */
	@Insert("INSERT INTO yixin_config(cust_id,yx_account,token,subscribe_msg,welcome_msg,biz_class,appid,secret,api_url) VALUES (#{cust_id},#{yx_account},#{token},#{subscribe_msg},#{welcome_msg},#{biz_class},#{appid},#{secret},#{api_url})")
	@Lang(RawLanguageDriver.class)
	int save(YiXinConfig config);
	
	/**
	 * Delete YiXin configure
	 * @param cust_id
	 * @return
	 */
	@Delete("DELETE FROM yixin_config WHERE cust_id=#{cust_id}")
	@Lang(RawLanguageDriver.class)
	int delete(int cust_id);
	
	/**
	 * update YiXin configure
	 * @param config
	 * @return
	 */
	@Update("UPDATE yixin_config A SET A.yx_account=#{yx_account},A.subscribe_msg=#{subscribe_msg}, A.welcome_msg=#{welcome_msg}, A.secret=#{secret}, A.appid=#{appid} WHERE A.cust_id=#{cust_id}")
	@Lang(RawLanguageDriver.class)
	int update(YiXinConfig config);
	
	/**
	 * check if exists configure with the given api_url
	 * @param api_url
	 * @return
	 */
	@Select("SELECT COUNT(1) FROM yixin_config A WHERE A.api_url=#{api_url}")
	@Lang(RawLanguageDriver.class)
	int checkConfig(String api_url);
}
