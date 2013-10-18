package lemon.web.message.action;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import lemon.shared.robotmsg.bean.RobotMsgBean;
import lemon.web.system.bean.User;
import lemon.web.ui.BS3UI;

/**
 * 一级消息管理
 * 
 * @author lemon
 * @version 1.0
 * 
 */
@Controller
@RequestMapping("message/level1")
public final class L1MessageAction extends MessageAction {

	/**
	 * 跳转到Level1页面
	 * @return
	 */
	@RequestMapping("list")
	@Override
	public String index() {
		return "redirect:show";
	}
	
	@RequestMapping(value="show")
	@Override
	public ModelAndView addOrEditPage(HttpSession session, Integer id) {
		return showIndex(session, 1);
	}

	/**
	 * 保存一级信息
	 * @param session
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String save(HttpSession session, String json) {
		User user = (User) session.getAttribute(TOKEN);
		if(null == user)
			sendError("请先登录。");
		if(json == null)
			return BS3UI.warning("保存失败： 信息格式不正确。");
		// 解析JSON,转Java集合
		Collection<RobotMsgBean> msgList = json2collection(json);
		//去空去重复
		Set<RobotMsgBean> set = grep(msgList);
		//清理
		msgList.clear();
		//数据入库
		int result = 0;
		for (RobotMsgBean msgBean : set) {
			msgBean.setCust_id(user.getCust_id());
			if(msgBean.getId() <= 0)
				result = msgBeanMapper.addMsg(msgBean, getLevel());
			else
				result = msgBeanMapper.updateMsg(msgBean, getLevel());
		}
		if(result != 0)
			return BS3UI.success("保存成功。");
		else
			return BS3UI.danger("保存失败。");
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String delete(String id) {
		return "暂时不支持删除。";
	}
	
	@Override
	public String getMenuURL() {
		return "message/level1";
	}
	
	@Override
	protected byte getLevel() {
		return 1;
	}

	@Override
	protected byte getPageSize() {
		return 10;
	}

	@Override
	protected String getMainViewName() {
		return "message/level1";
	}

	@Override
	protected int getResultCount(int cust_id) {
		return 0;
	}

	@Override
	protected void obtainResult(List<RobotMsgBean> msgList) {
		if(msgList.size() < 10)
			for (int i = msgList.size(); i < 10; i++) 
				msgList.add(null);
	}
	
	/**
	 * 检测MsgBean为空
	 * @param mb
	 * @return
	 */
	private boolean isBlank(RobotMsgBean mb) {
		if (null == mb)
			return true;
		if (mb.getCust_id() == 0) {
			if ("".equals(mb.getKey().trim())
					|| "".equals(mb.getValue().trim()))
				return true;
		}
		return false;
	}
	
	/**
	 * JSON转Java集合
	 * @param json
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Collection<RobotMsgBean> json2collection(String json) {
		JSONArray jsonArray = JSONArray.fromObject(json);
		return JSONArray.toCollection(jsonArray, RobotMsgBean.class);
	}
	
	/**
	 * 过滤结果集<br>
	 * 1. 去空
	 * 2. 去重
	 * @param msgList
	 * @return
	 */
	private Set<RobotMsgBean> grep(Collection<RobotMsgBean> msgList){
		Set<RobotMsgBean> set 	= new HashSet<RobotMsgBean>(msgList.size());
		Set<RobotMsgBean> temp 	= new HashSet<RobotMsgBean>(msgList.size());
		for (RobotMsgBean msgBean : msgList) {
			if (isBlank(msgBean))
				continue;
			if(msgBean.getId() > 0)
				set.add(msgBean);
			else
				temp.add(msgBean);
		}
		for (RobotMsgBean mb : temp)
			set.add(mb);
		temp.clear();
		return set;
	}
}