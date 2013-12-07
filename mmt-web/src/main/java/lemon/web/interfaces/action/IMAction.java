package lemon.web.interfaces.action;

import java.util.List;

import lemon.shared.message.MsgManager;
import lemon.shared.message.metadata.Message;
import lemon.shared.service.ServiceType;
import lemon.web.base.AdminNavAction;
import lemon.web.base.MMTAction;
import lemon.web.base.paging.Pagination;
import lemon.web.system.bean.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
/**
 * 即时消息管理
 * 
 * @author lemon
 * @version 1.1
 * 
 */
@Controller
@RequestMapping("/interface/im")
@SessionAttributes(MMTAction.TOKEN)
public class IMAction extends AdminNavAction {
	@Autowired
	private MsgManager msgManager;

	/**
	 * 显示消息记录首页
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list() {
		return "redirect:/webservices/interface/im/list/1";
	}
	
	/**
	 * 查询消息记录
	 * @param page
	 * @param service_type
	 * @param msgType
	 * @param model
	 * @return
	 */
	@RequestMapping(value="list/{page}",method=RequestMethod.GET)
	public ModelAndView list(@PathVariable("page") int page,
			@RequestParam(required = false) ServiceType service_type,
			@RequestParam(required = false) String msgType, ModelMap model) {
		User user = (User) model.get(TOKEN);
		//获取Main视图名称
		String operation = Thread.currentThread().getStackTrace()[1].getMethodName();
		//获取Main数据
		List<Message> list = msgManager.getRecvMsgList(user.getCust_id(),
				service_type, msgType, (page - 1) * PAGESIZE, PAGESIZE);
		Pagination pg = new Pagination(page, PAGESIZE,
				msgManager.getRecvMsgCnt(user.getCust_id(), service_type, msgType));
		return getListResultByPagination(pg, user.getRole_id(), operation, list);
	}

	@Override
	protected String getMenuURL() {
		return "interface/im";
	}
	
}
