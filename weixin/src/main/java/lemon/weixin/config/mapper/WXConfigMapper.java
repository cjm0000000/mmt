package lemon.weixin.config.mapper;

import java.util.List;

import lemon.weixin.config.bean.WeiXinConfig;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.scripting.defaults.RawLanguageDriver;
import org.springframework.stereotype.Repository;

/**
 * Repository of WeiXin configures
 * @author lemon
 * @version 1.0
 *
 */
@Repository
public interface WXConfigMapper {
	
	/**
	 * Get a WeiXin configure
	 * @param cust_id
	 * @return
	 */
	@Select("SELECT A.cust_id, A.wx_account, A.token, A.timestamp, A.biz_class, A.subscribe_msg, A.welcome_msg, A.appid, A.secret, A.api_url, A.account_type FROM weixin_config A WHERE A.cust_id=#{cust_id}")
	@Lang(RawLanguageDriver.class)
	WeiXinConfig get(int cust_id);
	
	/**
	 * Get all active configures
	 * @return
	 */
	@Select("SELECT A.cust_id, A.wx_account, A.token, A.timestamp, A.biz_class, A.subscribe_msg, A.welcome_msg, A.appid, A.secret, A.api_url, A.account_type FROM weixin_config A, customer C, customer_service S WHERE A.cust_id=C.cust_id AND A.cust_id=S.cust_id AND C.status='AVAILABLE' AND S.status='AVAILABLE' AND S.service='WEIXIN'")
	@Lang(RawLanguageDriver.class)
	List<WeiXinConfig> availableList();
	
	/**
	 * Add WeiXin configure
	 * @param config
	 * @return
	 */
	@Insert("INSERT INTO weixin_config(cust_id,wx_account,token,subscribe_msg,welcome_msg,biz_class,appid,secret,api_url,account_type) SELECT #{cust_id},#{wx_account},#{token},#{subscribe_msg},#{welcome_msg},#{biz_class},#{appid},#{secret},#{api_url},#{account_type}")
	@Lang(RawLanguageDriver.class)
	int save(WeiXinConfig config);
	
	/**
	 * Delete WeiXin configure
	 * @param cust_id
	 * @return
	 */
	@Delete("DELETE FROM weixin_config WHERE cust_id=#{cust_id}")
	@Lang(RawLanguageDriver.class)
	int delete(int cust_id);
	
	/**
	 * update WeiXin configure
	 * @param config
	 * @return
	 */
	@Update("UPDATE weixin_config A SET A.wx_account=#{wx_account},A.subscribe_msg=#{subscribe_msg},A.welcome_msg=#{welcome_msg},A.account_type=#{account_type},A.appid=#{appid},A.secret=#{secret} WHERE A.cust_id=#{cust_id}")
	@Lang(RawLanguageDriver.class)
	int update(WeiXinConfig config);
	
	/**
	 * check if exists configure with the given api_url
	 * @param api_url
	 * @return
	 */
	@Select("SELECT COUNT(1) FROM weixin_config A WHERE A.api_url=#{api_url}")
	@Lang(RawLanguageDriver.class)
	int checkConfig(String api_url);
}
