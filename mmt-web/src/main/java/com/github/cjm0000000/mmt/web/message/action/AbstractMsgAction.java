package com.github.cjm0000000.mmt.web.message.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.github.cjm0000000.mmt.shared.message.local.LocalMsgBean;
import com.github.cjm0000000.mmt.shared.message.local.persistence.LocalMsgBeanRepository;
import com.github.cjm0000000.mmt.web.common.AdminNavAction;
import com.github.cjm0000000.mmt.web.common.MMTAction;
import com.github.cjm0000000.mmt.web.common.paging.Pagination;
import com.github.cjm0000000.mmt.web.system.bean.User;

/**
 * 通用消息管理
 * 
 * @author lemon
 * @version 1.0
 * 
 */
@SessionAttributes(MMTAction.TOKEN)
public abstract class AbstractMsgAction extends AdminNavAction {
	private static final byte LEVEL1 = 1;
	private static final byte LEVEL2 = 2;
	private static final byte LEVEL3 = 3;
	@Autowired
	protected LocalMsgBeanRepository msgBeanMapper;

	/**
	 * 获取消息级别
	 * @return
	 */
	protected abstract byte getLevel();
	
	/**
	 * 获取每页数量
	 * @return
	 */
	protected abstract byte getPageSize();
	
	/**
	 * 获取结果集数量
	 * @param cust_id
	 * @return
	 */
	protected abstract int getResultCount(int cust_id);
	
	/**
	 * 对结果集特殊处理
	 * @param msgList
	 */
	protected abstract void obtainResult(List<LocalMsgBean> msgList);
	
	/**
	 * 获取消息列表
	 * @param cust_id
	 * @param page
	 * @param pageSize
	 * @return
	 */
	protected List<LocalMsgBean> getMsgList(int cust_id, int page, int pageSize){
		if(getLevel() == LEVEL1)
			return msgBeanMapper.getL1List(cust_id, 0, pageSize);
		else if(getLevel() == LEVEL2)
			return msgBeanMapper.getL2List(cust_id, (page - 1) * pageSize, pageSize);
		else if(getLevel() == LEVEL3)
			return msgBeanMapper.getL3List((page - 1) * pageSize, pageSize);
		return null;
	}
	
	/**
	 * 显示操作主页面
	 * @param user
	 * @param page
	 * @return
	 */
	public final ModelAndView showIndex(User user, int page) {
		// 获取Main数据
		List<LocalMsgBean> msgList = getMsgList(user.getCust_id(), page, getPageSize());
		obtainResult(msgList);
		if(getLevel() == LEVEL1){
			return getListResult(user.getRole_id(), getMainViewName(), msgList);
		}else{
			Pagination pg = new Pagination(page, getPageSize(), getResultCount(user.getCust_id()));
			return getListResultByPagination(pg, user.getRole_id(), getMainViewName(), msgList);
		}
	}
	
	/**
	 * 显示添加或者编辑客户信息的页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "add-edit-page", method = RequestMethod.GET)
	public ModelAndView addOrEditPage(
			@RequestParam(value = "id", required = false, defaultValue = "0") long id) {
		LocalMsgBean mb = null;
		if(id > 0)
			mb = msgBeanMapper.getMsg(id, getLevel());
		if(null == mb)
			mb = new LocalMsgBean();
		String title = getAddEditTitle(id);
		Map<String, Object>	param = new HashMap<>();
		param.put("msg", mb);
		param.put("title", title);
		return new ModelAndView("manage/message/msg-add-edit", "param", param);
	}
	
	/**
	 * 跳转到主页
	 * @return
	 */
	@RequestMapping("list")
	public String index() {
		return "redirect:/webservices/message/level"+getLevel()+"/list/1";
	}
	
	/**
	 * 删除消息
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String delete(String id) {
		if (id == null || "".equals(id))
			return sendJSONError("删除失败： 信息不存在。");
		int result = msgBeanMapper.deleteMsg(id, getLevel());
		if (0 != result)
			return sendJSONMsg("删除成功。");
		else
			return sendJSONError("删除失败。");
	}
	
	/**
	 * 获取添加编辑页面的标题
	 * @param id
	 * @return
	 */
	private String getAddEditTitle(long id){
		StringBuilder sb = new StringBuilder();
		if(id > 0){
			sb.append("编辑");
			if(getLevel() == 2) sb.append("二级");
			else sb.append("通用");
		}else{
			sb.append("添加");
			if(getLevel() == 2) sb.append("二级");
			else sb.append("通用");
		}
		return sb.toString();
	}
	
	/**
	 * 获取主页模板名称
	 * @return
	 */
	private String getMainViewName(){
		return "list";
	}
}