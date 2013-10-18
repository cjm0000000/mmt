package lemon.yixin.fans.mapper;

import lemon.yixin.fans.bean.YiXinFans;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * YiXin fans repository
 * @author lemon
 * @version 1.0
 *
 */
@Repository
public interface YXFansMapper {
	/**
	 * save YiXin fans
	 * @param fans
	 */
	@Insert("INSERT INTO yixin_fans(yxid,cust_id,nick_name,status) SELECT #{yxid},#{cust_id},#{nick_name},'AVAILABLE'")
	@Options(useGeneratedKeys=true,keyColumn="id",keyProperty="id")
	void saveFans(YiXinFans fans);
	
	/**
	 * get YiXin fans by yxid and cust_id
	 * @param cust_id
	 * @param yxid
	 * @return
	 */
	@Select("SELECT A.id,A.yxid,A.cust_id,A.nick_name,A.status,date_format(A.timestamp,'%Y-%m-%d %H:%i') timestamp FROM yixin_fans A WHERE A.cust_id=#{cust_id} AND A.yxid=#{yxid}")
	YiXinFans getFans(@Param("cust_id") int cust_id, @Param("yxid") String yxid);
	
	/**
	 * update YiXin fans
	 * @param fans
	 */
	@Update("UPDATE yixin_fans A SET A.nick_name=#{nick_name},A.status=#{status} WHERE A.id=#{id}")
	void updateFans(YiXinFans fans);
}
