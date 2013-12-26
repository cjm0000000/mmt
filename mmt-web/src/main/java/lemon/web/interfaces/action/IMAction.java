package lemon.web.interfaces.action;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lemon.shared.api.MmtAPI;
import lemon.shared.message.MsgManager;
import lemon.shared.message.metadata.IMessage;
import lemon.shared.message.metadata.Message;
import lemon.shared.message.metadata.MsgType;
import lemon.shared.message.metadata.TextMessage;
import lemon.web.base.AdminNavAction;
import lemon.web.base.MMTAction;
import lemon.web.base.paging.Pagination;
import lemon.web.system.bean.User;
import lemon.web.system.mapper.SystemConfigMapper;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.github.cjm0000000.mmt.core.service.ServiceType;
import com.github.cjm0000000.mmt.shared.access.ReturnCode;
import com.github.cjm0000000.mmt.weixin.config.persistence.WeiXinConfigRepository;
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
	@Autowired @Qualifier("weiXinAPI")
	private MmtAPI wxAPI;
	@Autowired
	private WeiXinConfigRepository wxConfigMapper;
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
	@RequestMapping(value="list/{page}", method=RequestMethod.GET)
	public ModelAndView list(@PathVariable("page") int page,
			@RequestParam(required = false) ServiceType service_type,
			@RequestParam(required = false) String msgType, ModelMap model) {
		User user = (User) model.get(TOKEN);
		//获取Main视图名称
		String operation = Thread.currentThread().getStackTrace()[1].getMethodName();
		//获取Main数据
		List<IMessage> list = msgManager.getRecvMsgList(user.getCust_id(),
				service_type, convertMsgType(service_type, msgType), (page - 1) * PAGESIZE, PAGESIZE);
		processMsgContent(list);
		Pagination pg = new Pagination(page, PAGESIZE,
				msgManager.getRecvMsgCnt(user.getCust_id(), service_type,
						convertMsgType(service_type, msgType)), generateFilterString(service_type, msgType));
		ModelAndView mv = getListResultByPagination(pg, user.getRole_id(), operation, list);
		mv.addObject("serviceTypes", systemConfigMapper.getItems(GROUP_FOR_SERVICE_TYPE));
		mv.addObject("msgTypes", 	 systemConfigMapper.getItems(GROUP_FOR_MSG_TYPE));
		mv.addObject("service_type", service_type);
		mv.addObject("msgType",		 msgType);
		return mv;
	}
	
	
	/**
	 * 回复消息
	 * @param msgId
	 * @param repMsgType
	 * @param content
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="send", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String sendMsg(long msgId, String repMsgType, String content,ModelMap model){
		if (msgId == 0 || StringUtils.isBlank(repMsgType)
				|| StringUtils.isBlank(content))
			return sendJSONError("请求参数不正确。");
		Message msg = msgManager.getRecvMsgDetail(msgId);
		if(msg == null)
			return sendJSONError("消息不存在。");
		Message sendMsg = generateFormattedMsg(repMsgType, content, msg.getFromUserName());
		User user = (User) model.get(TOKEN);
		ReturnCode rCode = wxAPI.sendMsg(wxConfigMapper.get(user.getCust_id()), sendMsg);
		if(rCode.getErrcode() == 0)
			return sendJSONMsg("发送成功。");
		return sendJSONError("发送失败：" + rCode.getErrmsg());
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
	 * 处理消息内容
	 * @param list
	 */
	private void processMsgContent(List<IMessage> list){
		Set<String> msgTypes = new HashSet<>(8);
		long[] msgIds = new long[list.size()];
		IMessage tempMsg;
		for (int i=0; i < list.size(); i++) {
			tempMsg = list.get(i);
			if(tempMsg == null)
				continue;
			msgTypes.add(tempMsg.getMsgType());//提取类型
			msgIds[i] = tempMsg.getId();//提取ID
		}
		//获取内容
		for (String msgType : msgTypes) {
			if(null == msgType)
				continue;
			switch (msgType) {
			case MsgType.IMAGE:

				break;
			case MsgType.LINK:

				break;
			case MsgType.LOCATION:

				break;
			case MsgType.TEXT:
				List<TextMessage> textMsgList = msgManager.getRecvTextMsgList(msgIds);
				for (int i=0; i<list.size(); i++) {
					Outer:
					for (TextMessage textMessage : textMsgList) {
						if(list.get(i).getId() == textMessage.getId()){
							list.get(i).setContent(textMessage.getContent());
							break Outer;
						}
					}
				}
				break;
			case MsgType.VIDEO:

				break;
			case MsgType.AUDIO:

				break;
			case MsgType.VOICE:

				break;
			}
		}
	}
	
	/**
	 * 生成格式化的消息(用户主动消息)
	 * @param service_type
	 * @param msgType
	 * @param content
	 * @return
	 */
	private Message generateFormattedMsg(String msgType, String content, String toUser){
		switch (msgType) {
		case MsgType.TEXT:
			TextMessage msg = new TextMessage();
			msg.setToUserName(toUser);
			msg.setContent(content);
			return msg;
		default:
			break;
		}
		return null;
	}
	
	public static void main(String[] args){
		System.out.println(1>>3);
	}
	
}
