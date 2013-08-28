package lemon.weixin.dao;

import java.util.List;

import lemon.weixin.bean.WeiXinConfig;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * Repository of WeiXin configures
 * @author lemon
 *
 */
@Repository
public interface WeiXinConfigMapper {
	/**
	 * Get a WeiXin configure
	 * @param cust_id
	 * @return
	 */
	@Select("SELECT A.cust_id, A.wx_account, A.token, A.timestamp FROM weixin_config A WHERE A.cust_id=#{cust_id}")
	public WeiXinConfig get(int cust_id);
	/**
	 * Get all active configures
	 * @return
	 */
	@Select("SELECT A.cust_id, A.wx_account, A.token, A.timestamp FROM weixin_config A, customer C WHERE A.cust_id=C.cust_id AND C.status='1'")
	public List<WeiXinConfig> activeList();
	/**
	 * Add WeiXin configure
	 * @param config
	 */
	@Insert("INSERT INTO weixin_config(cust_id,wx_account,token) SELECT #{cust_id},#{wx_account},#{token}")
	public void save(WeiXinConfig config);
	
	/**
	 * Delete WeiXin configure
	 * @param cust_id
	 */
	@Delete("DELETE FROM weixin_config WHERE cust_id=#{cust_id}")
	public void delete(int cust_id);
}
