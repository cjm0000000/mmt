package lemon.shared.message.local.persistence.sqlprovider;

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
public class LocalMsgBeanSQLProvider {

	/**
	 * 提供addMsg的SQL
	 * @param paramMap
	 * @return
	 */
	public String addMsgSQL(final Map<String, Object> paramMap) {
		final int level = (int) paramMap.get("level");
		if (level != 1 && level != 2 && level != 3)
			throw new MmtException("消息库找不到: level=" + level);
		return new SQL() {
			{
				INSERT_INTO("msg_repo_l" + level);
				VALUES("`key`,value", "#{msg.key},#{msg.value}");
				if (level != 3)
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
		final int level = (int) paramMap.get("level");
		if (level != 1 && level != 2 && level != 3)
			throw new MmtException("消息库找不到: level=" + level);
		return new SQL() {
			{
				UPDATE("msg_repo_l" + level);
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
		final String id = (String) paramMap.get("id");
		final int level = (int) paramMap.get("level");
		if (level != 1 && level != 2 && level != 3)
			throw new MmtException("消息库找不到: level=" + level);
		return new SQL() {
			{
				DELETE_FROM("msg_repo_l" + level);
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
		final int level = (int) paramMap.get("level");
		if (level != 1 && level != 2 && level != 3)
			throw new MmtException("消息库找不到: level=" + level);
		return new SQL() {
			{
				SELECT("id, `key`, value");
				if (level != 3)
					SELECT("cust_id");
				FROM("msg_repo_l" + level);
				WHERE("id=#{id}");
			}
		}.toString();
	}
}
