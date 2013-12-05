package lemon.shared.test.media;

import static org.junit.Assert.*;

import java.util.List;
import java.util.UUID;

import lemon.shared.media.Media;
import lemon.shared.media.MediaSync;
import lemon.shared.media.MediaSyncLog;
import lemon.shared.media.persistence.MediaRepository;
import lemon.shared.service.ServiceType;
import lemon.shared.toolkit.EqualsUtil;
import lemon.shared.toolkit.idcenter.IdWorkerManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@RunWith(JUnit4.class)
public class MediaRepoTest {
	private MediaRepository mediaRepository;
	private ApplicationContext acx;
	private static final int CUST_ID = -5743;

	@Before
	public void init() {
		String[] resource = { "classpath:spring-db.xml",
				"classpath:spring-dao.xml", "classpath:spring-service.xml" };
		acx = new ClassPathXmlApplicationContext(resource);
		mediaRepository = acx.getBean(MediaRepository.class);
		assertNotNull(mediaRepository);
	}
	
	@Test
	public void testMedia(){
		String display_name = "中文.xls";
		String media_path = "/usr/local";
		String media_type = "image";
		int media_size = 743;
		String real_name = UUID.randomUUID().toString() + ".jpeg";
		//Test add
		Media media = newMedia(display_name, media_path, media_type,
				media_size, real_name);
		assertNotEquals(0, mediaRepository.addMedia(media));
		//Test get
		Media m2 = mediaRepository.getMedia(media.getId());
		m2.setTimestamp(null);
		assertTrue(EqualsUtil.deepEquals(media, m2));
		//Test update
		String update_display_name = "更新后.xml";
		m2.setDisplay_name(update_display_name);
		assertNotEquals(0, mediaRepository.updateMedia(m2));
		m2 = mediaRepository.getMedia(m2.getId());
		assertEquals(update_display_name, m2.getDisplay_name());
		//Test delete
		mediaRepository.deleteMedia(new String[]{String.valueOf(m2.getId())});
		m2 = mediaRepository.getMedia(m2.getId());
		assertNull(m2);
		//Test list
		display_name = UUID.randomUUID().toString();
		media = newMedia(display_name, media_path, media_type, media_size,
				real_name);
		assertNotEquals(0, mediaRepository.addMedia(media));
		List<Media> list = mediaRepository.list(CUST_ID, display_name, 0, 10);
		assertNotNull(list);
		int count = mediaRepository.mediaCount(CUST_ID, display_name);
		assertEquals(count, list.size());
	}
	
	@Test
	public void mediaSync(){
		Media media = newMedia("rquest.xml", "/usr/bin", "video", 142, UUID
				.randomUUID().toString());
		assertNotEquals(0, mediaRepository.addMedia(media));
		ServiceType st = ServiceType.OTHER;
		MediaSync ms = newMediaSync(media, st);
		assertNotEquals(0, mediaRepository.addMediaSync(ms));
		assertNotEquals(0, mediaRepository.deleteMediaSync(ms.getM_id(), st));
		List<MediaSync> list = mediaRepository.getMediaSync(media.getId(), st);
		assertNotNull(list);
		assertEquals(0, list.size());
	}
	
	/**
	 * Test for method getMediaSyncs
	 */
	@Test
	public void getMediaSyncs(){
		Media media1 = newMedia("media1.jpg", "/usr/local", "image", 67, UUID.randomUUID().toString());
		assertNotEquals(0, mediaRepository.addMedia(media1));
		Media media2 = newMedia("media2.jpg", "/usr/local", "image", 33, UUID.randomUUID().toString());
		assertNotEquals(0, mediaRepository.addMedia(media2));
		Media media3 = newMedia("media3.jpg", "/usr/local", "image", 120, UUID.randomUUID().toString());
		assertNotEquals(0, mediaRepository.addMedia(media3));
		
		ServiceType weixin = ServiceType.WEIXIN;
		ServiceType yixin = ServiceType.YIXIN;
		
		MediaSync ms1_weixin = newMediaSync(media1, weixin);
		assertNotEquals(0, mediaRepository.addMediaSync(ms1_weixin));
		MediaSync ms1_yixin = newMediaSync(media1, yixin);
		assertNotEquals(0, mediaRepository.addMediaSync(ms1_yixin));
		
		MediaSync ms2_yixin = newMediaSync(media2, yixin);
		assertNotEquals(0, mediaRepository.addMediaSync(ms2_yixin));
		
		MediaSync ms3_weixin = newMediaSync(media3, weixin);
		assertNotEquals(0, mediaRepository.addMediaSync(ms3_weixin));
		
		long[] media_ids = { media1.getId(), media2.getId(), media3.getId() };
		List<MediaSync> list = mediaRepository.getMediaSyncs(media_ids);
		assertNotNull(list);
		assertEquals(list.size(), 4);
	}
	
	/**
	 * Test for method addMediaSyncLog
	 */
	@Test
	public void addMediaSyncLog(){
		Media media = newMedia("log.jpg", "/www/ear", "image", 100, UUID.randomUUID()+".jpg");
		assertNotEquals(0, mediaRepository.addMedia(media));
		MediaSyncLog log = new MediaSyncLog();
		log.setCust_id(CUST_ID);
		log.setId(IdWorkerManager.getIdWorker(MediaSyncLog.class).getId());
		log.setMedia_id(media.getId());
		log.setResult("{\"type\":\"thumb\",\"thumb_media_id\":\"lWtZrS9a3ucVshCTFC3bGYDVoMgpiHGGjzhch0jeM7zQneNy2AR9HENU4U1EUf9_\",\"created_at\":1386227649}");
		log.setService_type(ServiceType.WEIXIN);
		assertNotEquals(0, mediaRepository.addMediaSyncLog(log));
	}
	
	/**
	 * New a media object
	 * @param display_name
	 * @param media_path
	 * @param media_type
	 * @param media_size
	 * @param real_name
	 * @return
	 */
	private Media newMedia(String display_name, String media_path,
			String media_type, int media_size, String real_name) {
		Media media = new Media();
		media.setId(IdWorkerManager.getIdWorker(Media.class).getId());
		media.setCust_id(CUST_ID);
		media.setDisplay_name(display_name);
		media.setMedia_path(media_path);
		media.setMedia_type(media_type);
		media.setMedia_size(media_size);
		media.setReal_name(real_name);
		return media;
	}
	
	/**
	 * New a media sync object
	 * @param media
	 * @param st
	 * @return
	 */
	private MediaSync newMediaSync(Media media,ServiceType st){
		MediaSync ms = new MediaSync();
		ms.setCreated_at((int) (System.currentTimeMillis()/1000));
		ms.setCust_id(media.getCust_id());
		ms.setExpire_time((int) (System.currentTimeMillis()/1000) + 7200);
		ms.setId(IdWorkerManager.getIdWorker(MediaSync.class).getId());
		ms.setM_id(media.getId());
		ms.setMedia_id(UUID.randomUUID().toString());
		ms.setService_type(st);
		return ms;
	}
}
