package lemon.web.interfaces.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.validation.Valid;

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
import org.springframework.validation.BindingResult;
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
		Pagination pg = new Pagination(page, PAGESIZE,
				mediaRepository.mediaCount(user.getCust_id(), key),
				(key == null || "".equals(key)) ? null : "k=" + key);
		ModelAndView mv = getListResultByPagination(pg, user.getRole_id(), operation, mediaList);
		mv.addObject("k", key);
		return mv;
	}
	

	/**
	 * 保存多媒体信息
	 * @param media
	 * @param br
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "save", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String save(@Valid Media media, BindingResult br, ModelMap model) {
		if(media == null)
			return sendJSONError("数据格式不正确。");
		if(br.hasErrors())
			return sendJSONError(br.getFieldError().getDefaultMessage());
		User user = (User) model.get(TOKEN);
		int result = 0;
		media.setCust_id(user.getCust_id());
		if(media.getId() <= 0){
			media.setId(IdWorkerManager.getIdWorker(Media.class).getId());
			result = mediaRepository.addMedia(media);
		}else
			result = mediaRepository.updateMedia(media);
		if(result != 0)
			return sendJSONMsg("多媒体信息保存成功。");
		else
			return sendJSONError("多媒体信息保存失败。");
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
		media.setReal_name(UUID.randomUUID().toString() + "." + suffix);
		int result = mediaRepository.addMedia(media);
		
		if(result  == 0)
			return sendJSONError("文件上传失败。");
		//保存到本地
		fileManager.writeFile(MMT.getUploadFileRoot() + media.getMedia_path(), media.getReal_name(), bytes);
		if(wxSync != null){
			WeiXinConfig cfg = wxConfigMapper.get(user.getCust_id());
			JSONObject resObj = weixinApi.uploadMedia(cfg, media_type, bytes, fileName);
			if(resObj.get("errcode") != null)
				return sendJSONError("上传到微信服务器出错[errcode="+resObj.get("errcode")+",errmsg="+resObj.get("errmsg")+"]");
			if(!media_type.equals(resObj.get("type")))
				return sendJSONError("与微信服务器同步出错。");
			//存到同步表
			MediaSync ms = new MediaSync();
			ms.setCreated_at(resObj.getLong("created_at"));
			ms.setCust_id(user.getCust_id());
			//微信文件过期时间是三天
			ms.setExpire_time(ms.getCreated_at() + 3 * 24 * 3600);
			ms.setId(IdWorkerManager.getIdWorker(MediaSync.class).getId());
			ms.setM_id(media.getId());
			if("thumb".equals(media_type))
				ms.setMedia_id(resObj.getString("thumb_media_id"));
			else
				ms.setMedia_id(resObj.getString("media_id"));
			ms.setService_type(ServiceType.WEIXIN);
			result = mediaRepository.addMediaSync(ms);
			if(result == 0)
				return sendJSONError("文件上传成功，但是同步到微信服务器失败。");
		}
		return sendJSONMsg("上传成功。");
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
	 * 
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
}