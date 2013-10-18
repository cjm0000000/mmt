package lemon.web.message.action;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import lemon.shared.robotmsg.bean.RobotMsgBean;
import lemon.web.system.bean.User;

/**
 * 三级消息管理
 * 
 * @author lemon
 * @version 1.0
 * 
 */
@Controller
@RequestMapping("message/level3")
public final class L3MessageAction extends MessageAction {

	/**
	 * 显示L2消息列表
	 * @param page
	 * @param session
	 * @return
	 */
	@RequestMapping("list/{page}")
	public ModelAndView list(@PathVariable("page") int page, HttpSession session) {
		return showIndex(session, page);
	}

	/**
	 * 保存三级信息
	 * @param msg
	 * @param br
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String save(@Valid RobotMsgBean msg, BindingResult br, HttpSession session) {
		User user = (User) session.getAttribute(TOKEN);
		if(null == user)
			sendError("请先登录。");
		if(msg == null)
			return sendJSONError("保存失败： 信息格式不正确。");
		if(br.hasErrors())
			return sendJSONError(br.getFieldError().getDefaultMessage());
		if(msgBeanMapper.getL3MsgStrictly(msg.getKey()) != null && msg.getId() <= 0)
			return sendJSONError("保存失败: 关键字" + msg.getKey() + "已经存在。");
		msg.setCust_id(user.getCust_id());
		int result = 0;
		if(msg.getId() <= 0){
			result = msgBeanMapper.addMsg(msg, getLevel());
		}else{
			result = msgBeanMapper.updateMsg(msg, getLevel());
		}
		if(result != 0)
			return sendJSONMsg("保存成功。");
		else
			return sendJSONError("保存失败。");
	}
	
	@Override
	public String getMenuURL() {
		return "message/level3";
	}

	@Override
	protected byte getLevel() {
		return 3;
	}

	@Override
	protected byte getPageSize() {
		return PAGESIZE;
	}

	@Override
	protected String getMainViewName() {
		return getMainViewName("list");
	}

	@Override
	protected int getResultCount(int cust_id) {
		return msgBeanMapper.getL3Count();
	}

	@Override
	protected void obtainResult(List<RobotMsgBean> msgList) {
		
	}
}