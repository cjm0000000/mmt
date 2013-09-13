package lemon.weixin.dao;

import lemon.weixin.bean.WeiXinFans;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * WeiXin fans repository
 * @author lemon
 * @version 1.0
 *
 */
@Repository
public interface WXFansMapper {
	/**
	 * save WeiXin fans
	 * @param fans
	 */
	@Insert("INSERT INTO weixin_fans(wxid,cust_id,nick_name,status) SELECT #{wxid},#{cust_id},#{nick_name},'AVAILABLE'")
	@Options(useGeneratedKeys=true,keyColumn="id",keyProperty="id")
	void saveFans(WeiXinFans fans);
	
	/**
	 * get WeiXin fans by wxid and cust_id
	 * @param cust_id
	 * @param wxid
	 * @return
	 */
	@Select("SELECT A.id,A.wxid,A.cust_id,A.nick_name,A.status,date_format(A.timestamp,'%Y-%m-%d %H:%i') timestamp FROM weixin_fans A WHERE A.cust_id=#{cust_id} AND A.wxid=#{wxid}")
	WeiXinFans getFans(@Param("cust_id") int cust_id, @Param("wxid") String wxid);
	
	/**
	 * update WeiXin fans
	 * @param fans
	 */
	@Update("UPDATE weixin_fans A SET A.nick_name=#{nick_name},A.status=#{status} WHERE A.id=#{id}")
	void updateFans(WeiXinFans fans);
}
