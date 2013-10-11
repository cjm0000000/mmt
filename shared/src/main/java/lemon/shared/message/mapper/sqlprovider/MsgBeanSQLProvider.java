package lemon.shared.message.mapper.sqlprovider;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

/**
 * MsgBean动态SQL生成器
 * 
 * @author lemon
 * @version 1.0
 * 
 */
public class MsgBeanSQLProvider {

	/**
	 * 提供addMsg的SQL
	 * @param paramMap
	 * @return
	 */
	public String addMsgSQL(final Map<String, Object> paramMap) {
		final String level = (String) paramMap.get("level");
		if (level == null)
			throw new NullPointerException("消息库找不到。");
		return new SQL() {
			{
				INSERT_INTO("mmt_biz_l" + level);
				VALUES("`key`,value", "#{msg.key},#{msg.value}");
				if (!level.equals("3"))
					VALUES("cust_id", "#{msg.cust_id}");
			}
		}.toString();
	}
	
	/**
	 * 提供updateMsg的SQL
	 * @param paramMap
	 * @return
	 */
	public String updateMsgSQL(final Map<String, Object> paramMap) {
		final String level = (String) paramMap.get("level");
		if (level == null)
			throw new NullPointerException("消息库找不到。");
		return new SQL() {
			{
				UPDATE("mmt_biz_l" + level);
				SET("`key`=#{msg.key}");
				SET("value=#{msg.value}");
				WHERE("id=#{msg.id}");
			}
		}.toString();
	}
}
