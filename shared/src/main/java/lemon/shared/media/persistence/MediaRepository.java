package lemon.shared.media.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import lemon.shared.media.Media;
import lemon.shared.media.MediaSync;
import lemon.shared.service.ServiceType;

/**
 * Media repository
 * @author lemon
 * @version 1.1
 *
 */
@Repository
public interface MediaRepository {
	/**
	 * add media
	 * @param media
	 * @return
	 */
	int addMedia(Media media);
	
	/**
	 * add media synchronize
	 * @param ms
	 * @return
	 */
	int addMediaSync(MediaSync ms);
	
	/**
	 * delete media synchronize 
	 * @param m_id
	 * @param serviceType
	 * @return
	 */
	int deleteMediaSync(@Param("m_id") long id,
			@Param("service_type") ServiceType serviceType);
	
	/**
	 * delete media(s) by id(s)
	 * @param ids
	 * @return
	 */
	int deleteMedia(String[] ids);
	
	/**
	 * Get media by id
	 * @param id
	 * @return
	 */
	Media getMedia(long id);
	
	/**
	 * update media
	 * @param media
	 * @return
	 */
	int updateMedia(Media media);
	
	/**
	 * find media list by cust_id and display_name(if not null)
	 * @param cust_id
	 * @param display_name
	 * @param start
	 * @param limit
	 * @return
	 */
	List<Media> list(@Param("cust_id") int cust_id,
			@Param("display_name") String display_name,
			@Param("start") int start, @Param("limit") int limit);
}
