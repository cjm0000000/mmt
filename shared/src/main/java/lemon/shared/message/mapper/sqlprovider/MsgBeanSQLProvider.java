package lemon.shared.message.mapper.sqlprovider;

import java.util.Map;

import lemon.shared.MmtException;

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
			throw new MmtException("level不能为空");
		if (!"1".equals(level) && !"2".equals(level) && !"3".equals(level))
			throw new MmtException("消息库找不到: level=" + level);
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
			throw new MmtException("level不能为空");
		if (!"1".equals(level) && !"2".equals(level) && !"3".equals(level))
			throw new MmtException("消息库找不到: level=" + level);
		return new SQL() {
			{
				UPDATE("mmt_biz_l" + level);
				SET("`key`=#{msg.key}");
				SET("value=#{msg.value}");
				WHERE("id=#{msg.id}");
			}
		}.toString();
	}
	
	/**
	 * 提供deleteMsg的SQL
	 * @param paramMap
	 * @return
	 */
	public String deleteMsgSQL(final Map<String, Object> paramMap) {
		final String level = (String) paramMap.get("level");
		final String id = (String) paramMap.get("id");
		if (level == null )
			throw new MmtException("level不能为空");
		if (!"1".equals(level) && !"2".equals(level) && !"3".equals(level))
			throw new MmtException("消息库找不到: level=" + level);
		return new SQL() {
			{
				DELETE_FROM("mmt_biz_l" + level);
				WHERE("id IN (" + id + ")");
				
			}
		}.toString();
	}
	
	/**
	 * 提供getMsg的SQL
	 * @param paramMap
	 * @return
	 */
	public String getMsgSQL(final Map<String, Object> paramMap) {
		final String level = (String) paramMap.get("level");
		if (level == null )
			throw new MmtException("level不能为空");
		if (!"1".equals(level) && !"2".equals(level) && !"3".equals(level))
			throw new MmtException("消息库找不到: level=" + level);
		return new SQL() {
			{
				SELECT("id, `key`, value");
				if (!"3".equals(level))
					SELECT("cust_id");
				FROM("mmt_biz_l" + level);
				WHERE("id=#{id}");
			}
		}.toString();
	}
}
