package lemon.shared.test.media;

import static org.junit.Assert.*;

import java.util.List;
import java.util.UUID;

import lemon.shared.media.Media;
import lemon.shared.media.MediaSync;
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
		String real_name = UUID.randomUUID().toString() + ".jpeg";
		//Test add
		Media media = newMedia(display_name, media_path, media_type, real_name);
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
		media = newMedia(display_name, media_path, media_type, real_name);
		assertNotEquals(0, mediaRepository.addMedia(media));
		List<Media> list = mediaRepository.list(CUST_ID, null, 0, 10);
		assertNotNull(list);
		assertNotEquals(0, list.size());
	}
	
	@Test
	public void mediaSync(){
		Media media = newMedia("rquest.xml", "/usr/bin", "video", UUID.randomUUID().toString());
		assertNotEquals(0, mediaRepository.addMedia(media));
		MediaSync ms = new MediaSync();
		ms.setCreated_at(System.currentTimeMillis());
		ms.setCust_id(media.getCust_id());
		ms.setExpire_time(System.currentTimeMillis() + 3600);
		ms.setId(IdWorkerManager.getIdWorker(MediaSync.class).getId());
		ms.setM_id(media.getId());
		ms.setMedia_id(UUID.randomUUID().toString());
		ms.setService_type(ServiceType.OTHER);
		assertNotEquals(0, mediaRepository.addMediaSync(ms));
		assertNotEquals(0, mediaRepository.deleteMediaSync(ms.getM_id(), ServiceType.OTHER));
		List<MediaSync> list = mediaRepository.getMediaSync(media.getId(), ServiceType.OTHER);
		assertNotNull(list);
		assertEquals(0, list.size());
	}
	
	/**
	 * New a media object
	 * @param display_name
	 * @param media_path
	 * @param media_type
	 * @param real_name
	 * @return
	 */
	private Media newMedia(String display_name, String media_path,
			String media_type, String real_name) {
		Media media = new Media();
		media.setId(IdWorkerManager.getIdWorker(Media.class).getId());
		media.setCust_id(CUST_ID);
		media.setDisplay_name(display_name);
		media.setMedia_path(media_path);
		media.setMedia_type(media_type);
		media.setReal_name(real_name);
		return media;
	}
}
