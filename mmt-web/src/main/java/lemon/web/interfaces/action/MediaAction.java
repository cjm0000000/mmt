package lemon.web.interfaces.action;

import java.util.List;

import javax.validation.Valid;

import lemon.shared.media.Media;
import lemon.shared.media.persistence.MediaRepository;
import lemon.shared.toolkit.idcenter.IdWorkerManager;
import lemon.web.base.AdminNavAction;
import lemon.web.base.MMTAction;
import lemon.web.base.paging.Pagination;
import lemon.web.system.bean.User;

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
	@Autowired
	private MediaRepository mediaRepository;

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
	 * 显示添加或者编辑多媒体信息的页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "add-edit-page", method = RequestMethod.GET)
	public ModelAndView addOrEditPage(long id) {
		Media media = null;
		if(id > 0)
			media = mediaRepository.getMedia(id);
		if(null == media)
			media = new Media();
		return new ModelAndView(getAddEditView(), "media", media);
	}

	@Override
	public String getMenuURL() {
		return "interface/media";
	}
	

}