package lemon.web.message.action;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import lemon.shared.message.local.LocalMsgBean;
import lemon.shared.toolkit.idcenter.IdWorkerManager;
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
	@Override
	@RequestMapping("list")
	public String index() {
		return "redirect:show";
	}
	
	/**
	 * 显示首页
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "show", method = RequestMethod.GET)
	public ModelAndView addOrEditPage(
			@RequestParam(value = "id", required = false, defaultValue = "0") long id,
			ModelMap model) {
		User user = (User) model.get(TOKEN);
		return showIndex(user, 1);
	}

	/**
	 * 保存一级信息
	 * @param json
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "save", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String save(@RequestParam String json, ModelMap model) {
		if(json == null || "".equals(json))
			return BS3UI.warning("保存失败： 消息格式不正确。");
		User user = (User) model.get(TOKEN);
		// 解析JSON,转Java集合
		Collection<LocalMsgBean> msgList = json2collection(json);
		//去空去重复
		Set<LocalMsgBean> set = grep(msgList);
		//清理
		msgList.clear();
		//数据入库
		int result = 0;
		for (LocalMsgBean msgBean : set) {
			msgBean.setCust_id(user.getCust_id());
			if(msgBean.getId() <= 0){
				msgBean.setId(IdWorkerManager.getIdWorker(LocalMsgBean.class).getId());
				result = msgBeanMapper.addMsg(msgBean, getLevel());
			}else
				result = msgBeanMapper.updateMsg(msgBean, getLevel());
		}
		if(result != 0)
			return BS3UI.success("保存成功。");
		else
			return BS3UI.danger("保存失败。");
	}
	
	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
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
	protected int getResultCount(int cust_id) {
		return 0;
	}

	@Override
	protected void obtainResult(List<LocalMsgBean> msgList) {
		if(msgList.size() < 10)
			for (int i = msgList.size(); i < 10; i++) 
				msgList.add(null);
	}
	
	/**
	 * 检测MsgBean为空
	 * @param mb
	 * @return
	 */
	private boolean isBlank(LocalMsgBean mb) {
		if (null == mb)
			return true;
		if ("".equals(mb.getKey().trim()) && "".equals(mb.getValue().trim())){
			if(mb.getId() > 0)//remove from database
				msgBeanMapper.deleteMsg(String.valueOf(mb.getId()), getLevel());
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
	private Collection<LocalMsgBean> json2collection(String json) {
		JSONArray jsonArray = JSONArray.fromObject(json);
		return JSONArray.toCollection(jsonArray, LocalMsgBean.class);
	}
	
	/**
	 * 过滤结果集<br>
	 * 1. 去空
	 * 2. 去重
	 * @param msgList
	 * @return
	 */
	private Set<LocalMsgBean> grep(Collection<LocalMsgBean> msgList){
		Set<LocalMsgBean> set 	= new HashSet<LocalMsgBean>(msgList.size());
		Set<LocalMsgBean> temp 	= new HashSet<LocalMsgBean>(msgList.size());
		for (LocalMsgBean msgBean : msgList) {
			if (isBlank(msgBean)) continue;
			if(msgBean.getId() > 0) set.add(msgBean);
			else temp.add(msgBean);
		}
		for (LocalMsgBean mb : temp)
			set.add(mb);
		temp.clear();
		return set;
	}
}