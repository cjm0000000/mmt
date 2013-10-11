package lemon.web.message.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import lemon.shared.message.bean.MsgBean;
import lemon.shared.message.mapper.MsgBeanMapper;
import lemon.web.base.AdminNavAction;
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
public final class L1MessageAction extends AdminNavAction {
	@Autowired
	private MsgBeanMapper msgBeanMapper;
	private static final int MAX_LEVEL1_SIZE = 10;

	/**
	 * 跳转到Level1页面
	 * @return
	 */
	@RequestMapping("list")
	public String show() {
		return "redirect:show";
	}
	
	/**
	 * 显示L1消息页面
	 * @param cust_name
	 * @param session
	 * @return
	 */
	@RequestMapping("show")
	public ModelAndView show(HttpSession session) {
		//获取Main视图名称
		String mainViewName = "message/level1";
		//获取用户角色
		User user = (User) session.getAttribute(TOKEN);
		if(null == user)
			sendError("请先登录。");
		//获取导航条数据
		Map<String, Object> resultMap = buildNav(user.getRole_id());
		//获取Main数据
		List<MsgBean> l1_list = msgBeanMapper.getL1List(user.getCust_id(), 0, MAX_LEVEL1_SIZE);
		if(l1_list.size() < 10)
			for (int i = l1_list.size(); i < 10; i++) 
				l1_list.add(null);
		System.out.println(l1_list);
		resultMap.put("mainViewName", mainViewName);
		resultMap.put("msgList", l1_list);
		return new ModelAndView(VIEW_MANAGE_HOME_PAGE, "page", resultMap);
	}

	/**
	 * 保存一级信息
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String save(String json) {
		System.out.println(json);
		if(json == null)
			return BS3UI.warning("保存失败： 信息格式不正确。");
		int result = 0;
		//TODO 解析JSON
		List<MsgBean> msgList = null;
		for (MsgBean msgBean : msgList) {
			if(msgBean.getId() <= 0)
				msgBeanMapper.addMsg(msgBean, "1");
			else
				msgBeanMapper.addMsg(msgBean, "1");
		}
		if(result != 0)
			return BS3UI.success("保存成功。");
		else
			return BS3UI.danger("保存失败。");
	}
	
	@Override
	public String getMenuURL() {
		return "message/level1";
	}
	
}