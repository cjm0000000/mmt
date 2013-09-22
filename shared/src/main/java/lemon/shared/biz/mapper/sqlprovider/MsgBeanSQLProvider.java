package lemon.shared.biz.mapper.sqlprovider;

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
	 * 
	 * @return
	 */
	public String addMsgSQL(final Map<String, Object> paramMap) {
		System.out.println(paramMap);
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
}
