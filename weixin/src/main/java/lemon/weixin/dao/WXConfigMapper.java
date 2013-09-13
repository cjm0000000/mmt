package lemon.weixin.dao;

import java.util.List;

import lemon.weixin.bean.WeiXinConfig;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
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
	@Select("SELECT A.cust_id, A.wx_account, A.token, A.timestamp, A.biz_class, A.subscribe_msg, A.appid, A.secret, A.api_url FROM weixin_config A WHERE A.cust_id=#{cust_id}")
	public WeiXinConfig get(int cust_id);
	/**
	 * Get all active configures
	 * @return
	 */
	@Select("SELECT A.cust_id, A.wx_account, A.token, A.timestamp, A.biz_class, A.subscribe_msg, A.appid, A.secret, A.api_url FROM weixin_config A, customer C, customer_service S WHERE A.cust_id=C.cust_id AND A.cust_id=S.cust_id AND C.status='AVAILABLE' AND S.status='AVAILABLE' AND S.service='WEIXIN'")
	public List<WeiXinConfig> availableList();
	/**
	 * Add WeiXin configure
	 * @param config
	 */
	@Insert("INSERT INTO weixin_config(cust_id,wx_account,token,subscribe_msg,biz_class,appid,secret,api_url) SELECT #{cust_id},#{wx_account},#{token},#{subscribe_msg},#{biz_class},#{appid},#{secret},#{api_url}")
	public void save(WeiXinConfig config);
	
	/**
	 * Delete WeiXin configure
	 * @param cust_id
	 */
	@Delete("DELETE FROM weixin_config WHERE cust_id=#{cust_id}")
	public void delete(int cust_id);
	
	/**
	 * update WeiXin configure
	 * @param config
	 */
	@Update("UPDATE weixin_config A SET A.wx_account=#{wx_account},A.token=#{token},A.subscribe_msg=#{subscribe_msg} WHERE A.cust_id=#{cust_id}")
	public void update(WeiXinConfig config);
}
