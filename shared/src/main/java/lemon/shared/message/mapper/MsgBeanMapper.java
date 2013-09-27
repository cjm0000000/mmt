package lemon.shared.message.mapper;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.scripting.defaults.RawLanguageDriver;
import org.springframework.stereotype.Repository;

import lemon.shared.message.bean.MsgBean;
import lemon.shared.message.mapper.sqlprovider.MsgBeanSQLProvider;

/**
 * MsgBeanMapper Repository
 * 
 * @author lemon
 * @version 1.0
 * 
 */
@Repository
public interface MsgBeanMapper {
	
	/**
	 * 添加MsgBean
	 * @param msg
	 * @param level
	 */
	@InsertProvider(type = MsgBeanSQLProvider.class, method = "addMsgSQL")
	@Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "msg.id")
	void addMsg(@Param("msg") MsgBean msg, @Param("level") String level);

	/**
	 * 根据KEY获取一级消息<br>
	 * 严格匹配
	 * 
	 * @param cust_id
	 * @param key
	 * @return
	 */
	@Select("SELECT A.id,A.cust_id,A.`key`,A.`value` FROM mmt_biz_l1 A WHERE A.cust_id=#{cust_id} AND A.`key`=#{key}")
	@Lang(RawLanguageDriver.class)
	MsgBean getL1Msg(@Param("cust_id") int cust_id, @Param("key") String key);

	/**
	 * 根据KEY获取二级消息<br>
	 * 模糊匹配
	 * 
	 * @param cust_id
	 * @param key
	 * @return
	 */
	@Select("SELECT A.id,A.cust_id,A.`key`,A.`value` FROM mmt_biz_l2 A WHERE A.cust_id=#{cust_id} AND A.`key` LIKE CONCAT('%',#{key},'%') ORDER BY A.`key` LIMIT 1")
	@Lang(RawLanguageDriver.class)
	MsgBean getL2Msg(@Param("cust_id") int cust_id, @Param("key") String key);

	/**
	 * 获取通用消息<br>
	 * 模糊匹配
	 * 
	 * @param cust_id
	 * @param key
	 * @return
	 */
	@Select("SELECT A.id,A.`key`,A.`value` FROM mmt_biz_l3 A WHERE A.`key` LIKE CONCAT('%',#{key},'%') ORDER BY A.`key` LIMIT 1")
	@Lang(RawLanguageDriver.class)
	MsgBean getL3Msg(@Param("key") String key);
}
