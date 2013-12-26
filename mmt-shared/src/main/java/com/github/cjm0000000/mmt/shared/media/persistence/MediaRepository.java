package com.github.cjm0000000.mmt.shared.media.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.scripting.defaults.RawLanguageDriver;
import org.springframework.stereotype.Repository;

import com.github.cjm0000000.mmt.core.service.ServiceType;
import com.github.cjm0000000.mmt.shared.media.Media;
import com.github.cjm0000000.mmt.shared.media.MediaSync;
import com.github.cjm0000000.mmt.shared.media.MediaSyncLog;

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
	@Lang(RawLanguageDriver.class)
	int addMedia(Media media);
	
	/**
	 * add media synchronize
	 * @param ms
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	int addMediaSync(MediaSync ms);
	
	/**
	 * add media sync log
	 * @param log
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	int addMediaSyncLog(MediaSyncLog log);
	
	/**
	 * delete media synchronize 
	 * @param media_id
	 * @param serviceType
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	int deleteMediaSync(@Param("m_id") long media_id,
			@Param("service_type") ServiceType serviceType);
	
	/**
	 * delete media(s) by id(s)
	 * @param ids
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	int deleteMedia(String[] ids);
	
	/**
	 * Get media by id
	 * @param id
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	Media getMedia(long id);
	
	/**
	 * get media synchronize
	 * @param media_id (Require)
	 * @param service_type (Optional)
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	List<MediaSync> getMediaSync(@Param("m_id") long media_id,
			@Param("service_type") ServiceType service_type);
	
	/**
	 * Get media synchronize
	 * @param media_ids
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	List<MediaSync> getMediaSyncs(long[] media_ids);
	
	/**
	 * update media
	 * @param media
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	int updateMedia(Media media);
	
	/**
	 * find media list by cust_id and display_name(if not null)
	 * @param cust_id
	 * @param display_name
	 * @param start
	 * @param limit
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	List<Media> list(@Param("cust_id") int cust_id,
			@Param("display_name") String display_name,
			@Param("start") int start, @Param("limit") int limit);
	
	/**
	 * Get media(s) count
	 * @param cust_id
	 * @param display_name
	 * @return
	 */
	@Lang(RawLanguageDriver.class)
	int mediaCount(@Param("cust_id") int cust_id,
			@Param("display_name") String display_name);
}
