package lemon.web.message.action;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import lemon.shared.message.bean.MsgBean;
import lemon.web.system.bean.User;
import lemon.web.ui.BS3UI;

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
	 * @param session
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String save(MsgBean msg, HttpSession session) {
		User user = (User) session.getAttribute(TOKEN);
		if(null == user)
			sendError("请先登录。");
		if(msg == null)
			return BS3UI.warning("保存失败： 信息格式不正确。");
		if(msgBeanMapper.getL3Msg(msg.getKey()) != null && msg.getId() <= 0)
			return BS3UI.danger("保存失败: 关键字" + msg.getKey() + "已经存在。");
		msg.setCust_id(user.getCust_id());
		int result = 0;
		if(msg.getId() <= 0){
			result = msgBeanMapper.addMsg(msg, getLevel());
		}else{
			result = msgBeanMapper.updateMsg(msg, getLevel());
		}
		if(result != 0)
			return BS3UI.success("保存成功。");
		else
			return BS3UI.danger("保存失败。");
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
	protected void obtainResult(List<MsgBean> msgList) {
		
	}
}