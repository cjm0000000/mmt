package lemon.yixin.dao;

import java.util.List;

import lemon.yixin.bean.YiXinConfig;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
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
	@Select("SELECT A.cust_id, A.yx_account, A.token, A.timestamp, A.biz_class, A.subscribe_msg, A.appid, A.secret, A.api_url FROM yixin_config A WHERE A.cust_id=#{cust_id}")
	public YiXinConfig get(int cust_id);
	/**
	 * Get all active configures
	 * @return
	 */
	@Select("SELECT A.cust_id, A.yx_account, A.token, A.timestamp, A.biz_class, A.subscribe_msg, A.appid, A.secret, A.api_url FROM yixin_config A, customer C, customer_service S WHERE A.cust_id=C.cust_id AND A.cust_id=S.cust_id AND C.status='AVAILABLE' AND S.status='AVAILABLE' AND s.service='YIXIN'")
	public List<YiXinConfig> availableList();
	/**
	 * Add YiXin configure
	 * @param config
	 */
	@Insert("INSERT INTO yixin_config(cust_id,yx_account,token,subscribe_msg,biz_class,appid,secret,api_url) SELECT #{cust_id},#{yx_account},#{token},#{subscribe_msg},#{biz_class},#{appid},#{secret},#{api_url}")
	public void save(YiXinConfig config);
	
	/**
	 * Delete YiXin configure
	 * @param cust_id
	 */
	@Delete("DELETE FROM yixin_config WHERE cust_id=#{cust_id}")
	public void delete(int cust_id);
	
	/**
	 * update YiXin configure
	 * @param config
	 */
	@Update("UPDATE yixin_config A SET A.yx_account=#{yx_account},A.token=#{token},A.subscribe_msg=#{subscribe_msg} WHERE A.cust_id=#{cust_id}")
	public void update(YiXinConfig config);
}
