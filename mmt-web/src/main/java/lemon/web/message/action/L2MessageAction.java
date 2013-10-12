package lemon.web.message.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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
 * 二级消息管理
 * 
 * @author lemon
 * @version 1.0
 * 
 */
@Controller
@RequestMapping("message/level2")
public final class L2MessageAction extends AdminNavAction {
	@Autowired
	private MsgBeanMapper msgBeanMapper;
	private static final String LEVEL = "2";

	/**
	 * 跳转到Level2页面
	 * @return
	 */
	@RequestMapping("list")
	public String list() {
		return "redirect:list/1";
	}
	
	/**
	 * 显示L2消息页面
	 * @param page
	 * @param session
	 * @return
	 */
	@RequestMapping("list/{page}")
	public ModelAndView list(@PathVariable("page") int page, HttpSession session) {
		//获取Main视图名称
		String mainViewName = getMainViewName(Thread.currentThread().getStackTrace()[1].getMethodName());
		//获取用户角色
		User user = (User) session.getAttribute(TOKEN);
		if(null == user)
			sendError("请先登录。");
		//获取导航条数据
		Map<String, Object> resultMap = buildNav(user.getRole_id());
		//获取Main数据
		List<MsgBean> l2_list = msgBeanMapper.getL2List(user.getCust_id(), (page - 1) * PAGESIZE, PAGESIZE);
		int l2_count = msgBeanMapper.getL2Count(user.getCust_id());
		resultMap.put("mainViewName", mainViewName);
		resultMap.put("msgList", l2_list);
		resultMap.put("rsCnt", l2_count);
		resultMap.put("currentPage", page);
		resultMap.put("PAGESIZE", PAGESIZE);
		return new ModelAndView(VIEW_MANAGE_HOME_PAGE, "page", resultMap);
	}

	/**
	 * 保存一级信息
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
		if(msgBeanMapper.getL2Msg(user.getCust_id(), msg.getKey()) != null && msg.getId() <= 0)
			return BS3UI.danger("保存失败: 关键字" + msg.getKey() + "已经存在。");
		msg.setCust_id(user.getCust_id());
		int result = 0;
		if(msg.getId() <= 0){
			result = msgBeanMapper.addMsg(msg, LEVEL);
		}else{
			result = msgBeanMapper.updateMsg(msg, LEVEL);
		}
		if(result != 0)
			return BS3UI.success("保存成功。");
		else
			return BS3UI.danger("保存失败。");
	}
	
	/**
	 * 显示添加或者编辑客户信息的页面
	 * @param cust_id
	 * @return
	 */
	@RequestMapping(value="add-edit-page")
	public ModelAndView addOrEditPage(int id) {
		MsgBean mb = null;
		if(id > 0)
			mb = msgBeanMapper.getMsg(id, LEVEL);
		if(null == mb)
			mb = new MsgBean();
		return new ModelAndView(getAddEditView(), "msg", mb);
	}
	
	/**
	 * 删除客户信息
	 * @param cust_id
	 * @return
	 */
	@RequestMapping(value = "delete", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String delete(String id) {
		System.out.println("ID="+id);
		if (id == null || "".equals(id))
			return BS3UI.success("删除失败： 信息不存在。");
		int result = msgBeanMapper.deleteMsg(id, LEVEL);
		if (0 != result)
			return BS3UI.success("删除成功。");
		else
			return BS3UI.danger("删除失败。");
	}
	
	@Override
	public String getMenuURL() {
		return "message/level2";
	}
	
	//FIXME 去掉XStream的JSON支持
	
}