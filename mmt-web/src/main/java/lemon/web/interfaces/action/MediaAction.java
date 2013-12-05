package lemon.web.interfaces.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;

import lemon.shared.MmtException;
import lemon.shared.api.MmtAPI;
import lemon.shared.config.MMTCharset;
import lemon.shared.file.FileManager;
import lemon.shared.media.Media;
import lemon.shared.media.MediaSync;
import lemon.shared.media.persistence.MediaRepository;
import lemon.shared.service.ServiceType;
import lemon.shared.toolkit.idcenter.IdWorkerManager;
import lemon.web.base.AdminNavAction;
import lemon.web.base.MMTAction;
import lemon.web.base.paging.Pagination;
import lemon.web.global.MMT;
import lemon.web.system.bean.SystemConfig;
import lemon.web.system.bean.User;
import lemon.web.system.mapper.SystemConfigMapper;
import lemon.web.toolkit.ArchiveManager;
import lemon.weixin.config.bean.WeiXinConfig;
import lemon.weixin.config.mapper.WXConfigMapper;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 * 多媒体管理
 * 
 * @author lemon
 * @version 1.1
 * 
 */
@Controller
@RequestMapping("/interface/media")
@SessionAttributes(MMTAction.TOKEN)
public final class MediaAction extends AdminNavAction {
	/** 多媒体类型 */
	private static final String MEDIA_TYPE_GROUP = "MEDIA_TYPE";
	/** 文件检查规则 */
	private static Map<String,Integer> verifyPool = null;
	@Autowired
	private MediaRepository mediaRepository;
	@Autowired
	private SystemConfigMapper systemConfigMapper;
	@Resource(name="localSystemFileManager")
	private FileManager fileManager;
	@Autowired
	private WXConfigMapper wxConfigMapper;
	@Resource(name="weiXinAPI")
	private MmtAPI weixinApi;
	
	static{
		initVerifyRules();
	}

	/**
	 * 显示多媒体列表首页
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list() {
		return "redirect:/webservices/interface/media/list/1";
	}
	
	/**
	 * 分页显示多媒体列表
	 * @param page
	 * @param key
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "list/{page}", method = RequestMethod.GET)
	public ModelAndView list(@PathVariable("page") int page,
			@RequestParam(value = "k", required = false) String key,
			ModelMap model) {
		User user = (User) model.get(TOKEN);
		//获取operation
		String operation = Thread.currentThread().getStackTrace()[1].getMethodName();
		//获取Main数据
		List<Media> mediaList = mediaRepository.list(user.getCust_id(),key,(page - 1) * PAGESIZE, PAGESIZE);
		processSync(mediaList);
		Pagination pg = new Pagination(page, PAGESIZE,
				mediaRepository.mediaCount(user.getCust_id(), key),
				(key == null || "".equals(key)) ? null : "k=" + key);
		ModelAndView mv = getListResultByPagination(pg, user.getRole_id(), operation, mediaList);
		mv.addObject("k", key);
		return mv;
	}
	
	/**
	 * 删除多媒体信息
	 * @param media_id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String delete(String media_id) {
		if (media_id == null || "".equals(media_id))
			return sendJSONError("多媒体信息删除失败： 资源不存在。");
		int result = mediaRepository.deleteMedia(media_id.split(","));
		if (0 != result)
			return sendJSONMsg("多媒体信息删除成功。");
		else
			return sendJSONError("多媒体信息删除失败。");
	}
	
	/**
	 * 显示上传多媒体信息的页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "show-upload", method = RequestMethod.GET)
	public ModelAndView showUpload(ModelMap model) {
		User user = (User) model.get(TOKEN);
		String operation = "showUpload";
		List<SystemConfig> list = systemConfigMapper.getItems(MEDIA_TYPE_GROUP);
		return getListResult(user.getRole_id(), operation, list);
	}
	
	/**
	 * 上传文件
	 * @param file
	 * @param wxSync
	 * @param yxSync
	 * @param media_type
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "upload", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	public String upload(@RequestParam("media_file") MultipartFile file,
			@RequestParam(value = "upload-wx", required = false) Object wxSync,
			@RequestParam(value = "upload-yx", required = false) Object yxSync,
			@RequestParam("media_type") String media_type, ModelMap model) {
		if(file.isEmpty())
			return sendJSONError("文件不能为空。");
		String fileName = obtainFileName(file);
		//Verify file suffix
		String suffix = getSuffix(fileName);
		Integer maxSize = verifyPool.get(media_type + "_" + suffix);
		if(maxSize == null)
			return sendJSONError("文件格式不支持。");
		if(toKB(file.getSize()) > maxSize)
			return sendJSONError("文件大小超出限制[" + maxSize + "KB]。");
		byte[] bytes = null;
		try {
			bytes = file.getBytes();
		} catch (IOException e) {
			sendJSONError("文件上传失败。");
		}
		User user = (User) model.get(TOKEN);
		
		//save file
		Media media = new Media();
		media.setCust_id(user.getCust_id());
		media.setDisplay_name(fileName);
		media.setId(IdWorkerManager.getIdWorker(Media.class).getId());
		media.setMedia_path(ArchiveManager.getPrivateFilePath(user.getCust_id()));
		media.setMedia_type(media_type);
		media.setMedia_size((int) (file.getSize()/1000));
		media.setReal_name(UUID.randomUUID().toString() + "." + suffix);
		int result = mediaRepository.addMedia(media);
		
		if(result  == 0)
			return sendJSONError("文件上传失败。");
		//保存到本地
		fileManager.writeFile(MMT.getUploadFileRoot() + media.getMedia_path(), media.getReal_name(), bytes);
		if(wxSync != null){
			String res = syncFile2APIServer(user, media, bytes);
			if(res != null)
				return res;
		}
		return sendJSONMsg("上传成功。");
	}
	
	/**
	 * 把服务器上的多媒体同步到API server
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "sync", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	public String syncFile(@RequestParam(defaultValue="0")long media_id, ServiceType service_type,
			ModelMap model) {
		if(media_id <=0 || service_type == null)
			return sendJSONError("参数错误。");
		Media media = mediaRepository.getMedia(media_id);
		if(media == null)
			return sendJSONError("多媒体资源不存在。");
		//读取磁盘文件
		byte[] bytes;
		try {
			bytes = fileManager.readFile(MMT.getUploadFileRoot() + media.getMedia_path(), media.getReal_name());
		} catch (MmtException e) {
			return sendJSONError("无法读取多媒体文件。");
		}
		//同步
		User user = (User) model.get(TOKEN);
		String res = syncFile2APIServer(user, media, bytes);
		if(res != null)
			return res;
		return sendJSONMsg("同步成功。");
	}

	@Override
	public String getMenuURL() {
		return "interface/media";
	}
	
	/**
	 * 初始化文件后缀规则
	 */
	private static void initVerifyRules(){
		verifyPool = new HashMap<>();
		verifyPool.put("image_jpg", 128);
		verifyPool.put("voice_amr", 256);
		verifyPool.put("voice_mp3", 256);
		verifyPool.put("video_mp4", 1024);
		verifyPool.put("thumb_jpg", 64);
	}
	
	/**
	 * 获取文件名后缀
	 * @param fileName
	 * @return
	 */
	private String getSuffix(String fileName){
		return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
	}
	
	/**
	 * 转KB
	 * @param _byte
	 * @return
	 */
	private int toKB(long _byte){
		return (int) (_byte/1024);
	}
	
	/**
	 * 处理文件名
	 * @param file
	 * @return
	 */
	private String obtainFileName(MultipartFile file){
		try {
			return new String(file.getOriginalFilename().getBytes("iso-8859-1"), MMTCharset.LOCAL_CHARSET);
		} catch (UnsupportedEncodingException e1) {
			return file.getOriginalFilename();
		}
	}
	
	/**
	 * 合并同步信息到Media
	 * @param list
	 */
	private void processSync(final List<Media> list){
		if(list == null || list.size() == 0)
			return;
		final long[] media_ids = new long[list.size()];
		for (int i = 0; i < list.size(); i++) 
			media_ids[i] = list.get(i).getId();
		
		List<MediaSync> syncList = mediaRepository.getMediaSyncs(media_ids);
		
		for (Media media : list) 
			for (MediaSync mediaSync : syncList) 
				if(media.getId() == mediaSync.getM_id())
					processMediaDetails(media, mediaSync);
	}
	
	/**
	 * 处理Detail字段
	 * @param m
	 * @param ms
	 */
	private void processMediaDetails(Media m, MediaSync ms){
		Set<MediaSync> set = m.getSyncDetails();
		if(set == null)
			set = new HashSet<>(4);//注意：当service_type增加的时候需要修改这个初始化参数
		set.add(ms);
		if(m.getSyncDetails() == null)
			m.setSyncDetails(set);
	}
	
	/**
	 * Sync file to API server
	 * @param user
	 * @param media
	 * @param bytes
	 * @return
	 */
	private String syncFile2APIServer(User user, Media media, byte[] bytes){
		WeiXinConfig cfg = wxConfigMapper.get(user.getCust_id());
		if(cfg  == null)
			return sendJSONError("请先配置微信接口。");
		JSONObject resObj = weixinApi.uploadMedia(cfg, media.getMedia_type(), bytes, media.getDisplay_name());
		if(resObj.get("errcode") != null)
			return sendJSONError("上传到微信服务器出错[errcode=" + resObj.get("errcode")
					+ ",errmsg=" + resObj.get("errmsg") + "]");
		if(!media.getMedia_type().equals(resObj.get("type")))
			return sendJSONError("与微信服务器同步出错。");
		//存到同步表
		MediaSync ms = new MediaSync();
		ms.setCreated_at(resObj.getInt("created_at"));
		ms.setCust_id(user.getCust_id());
		//微信文件过期时间是三天
		ms.setExpire_time(ms.getCreated_at() + 3 * 24 * 3600);
		ms.setId(IdWorkerManager.getIdWorker(MediaSync.class).getId());
		ms.setM_id(media.getId());
		if("thumb".equals(media.getMedia_type()))
			ms.setMedia_id(resObj.getString("thumb_media_id"));
		else
			ms.setMedia_id(resObj.getString("media_id"));
		ms.setService_type(ServiceType.WEIXIN);
		//清理Sync
		int result = mediaRepository.deleteMediaSync(media.getId(), ServiceType.WEIXIN);
		result = mediaRepository.addMediaSync(ms);
		if(result == 0)
			return sendJSONError("文件上传成功，但是同步到微信服务器失败。");
		return null;//success
	}
	
}