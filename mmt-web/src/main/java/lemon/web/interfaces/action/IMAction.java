package lemon.web.interfaces.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import lemon.shared.message.MsgManager;
import lemon.shared.message.metadata.Message;
import lemon.shared.message.metadata.MsgType;
import lemon.shared.service.ServiceType;
import lemon.web.base.AdminNavAction;
import lemon.web.base.MMTAction;
import lemon.web.base.paging.Pagination;
import lemon.web.system.bean.User;
import lemon.web.system.mapper.SystemConfigMapper;

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
	@Autowired
	private SystemConfigMapper systemConfigMapper;
	private static final String GROUP_FOR_SERVICE_TYPE 	= "SERVICE_TYPE";
	private static final String GROUP_FOR_MSG_TYPE 		= "MESSAGE_TYPE";
	
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
				service_type, convertMsgType(service_type, msgType), (page - 1) * PAGESIZE, PAGESIZE);
		List<IMessage> result = processCreateTime(list);
		list = null;
		processMsgContent(result);
		Pagination pg = new Pagination(page, PAGESIZE,
				msgManager.getRecvMsgCnt(user.getCust_id(), service_type,
						convertMsgType(service_type, msgType)), generateFilterString(service_type, msgType));
		ModelAndView mv = getListResultByPagination(pg, user.getRole_id(), operation, result);
		mv.addObject("serviceTypes", systemConfigMapper.getItems(GROUP_FOR_SERVICE_TYPE));
		mv.addObject("msgTypes", 	 systemConfigMapper.getItems(GROUP_FOR_MSG_TYPE));
		mv.addObject("service_type", service_type);
		mv.addObject("msgType",		 msgType);
		return mv;
	}

	@Override
	protected String getMenuURL() {
		return "interface/im";
	}
	
	/**
	 * 转换MsgType
	 * @param service_type
	 * @param msgType
	 * @return
	 */
	private String convertMsgType(ServiceType service_type, String msgType){
		if(msgType == null)
			return null;
		if("yuyin".equals(msgType)){
			if(service_type == null)
				return "ALL";
			if(ServiceType.WEIXIN.equals(service_type))
				return MsgType.VOICE;
			if(ServiceType.YIXIN.equals(service_type))
				return MsgType.AUDIO;
		}
		return msgType;
	}
	
	/**
	 * 拼接查询条件
	 * @param service_type
	 * @param msgType
	 * @return
	 */
	private String generateFilterString(ServiceType service_type, String msgType) {
		if (service_type == null && msgType == null)
			return "";
		StringBuilder sb = new StringBuilder();
		if (service_type != null && !"".equals(service_type))
			sb.append("&service_type=").append(service_type);
		if (msgType != null && !"".equals(msgType))
			sb.append("&msgType=").append(msgType);
		if (sb.length() > 0)
			return sb.substring(1, sb.length());
		return "";
	}
	
	/**
	 * 处理创建时间
	 * @param list
	 */
	private List<IMessage> processCreateTime(List<Message> list){
		if(list == null || list.size() == 0)
			return null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for (Message iMessage : list) {
			IMessage iMsg = (IMessage) iMessage;
			iMsg.setCreateTime4display(sdf.format(new Date(iMessage.getCreateTime() * 1000)));
		}
		return null;
	}
	
	/**
	 * 处理消息内容
	 * @param list
	 */
	private void processMsgContent(List<IMessage> list){
		//TODO 处理消息内容
	}
	
}
