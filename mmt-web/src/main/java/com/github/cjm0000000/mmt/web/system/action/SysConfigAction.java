package com.github.cjm0000000.mmt.web.system.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.github.cjm0000000.mmt.web.common.AdminNavAction;
import com.github.cjm0000000.mmt.web.common.MMTAction;
import com.github.cjm0000000.mmt.web.system.bean.SystemConfig;
import com.github.cjm0000000.mmt.web.system.bean.User;
import com.github.cjm0000000.mmt.web.system.persistence.SysConfigRepository;
import com.github.cjm0000000.mmt.web.ui.BS3UI;

/**
 * 系统配置管理
 * 
 * @author lemon
 * @version 1.1
 * 
 */
@Controller
@RequestMapping("/system/sysconfig")
@SessionAttributes(MMTAction.TOKEN)
public final class SysConfigAction extends AdminNavAction {
	@Autowired
	private SysConfigRepository systemConfigMapper;
	
	/**
	 * 首页
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list() {
		return "redirect:show";
	}

	/**
	 * 显示配置信息
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "show", method = RequestMethod.GET)
	public ModelAndView show(ModelMap model) {
		User user = (User) model.get(TOKEN);
		//获取operation
		String operation = Thread.currentThread().getStackTrace()[1].getMethodName();
		//获取Main数据
		SystemConfig domainCfg = systemConfigMapper.getItem(DOMAIN_KEY, DOMAIN_KEY);
		ModelAndView mv = getListResult(user.getRole_id(), operation, null);
		mv.addObject("domain", domainCfg);
		return mv;
	}
	
	/**
	 * 保存数据
	 * @param domain
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="save", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String save(String domain) {
		if(domain == null)
			return BS3UI.danger("域名不能为空。");
		SystemConfig domainCfg = systemConfigMapper.getItem(DOMAIN_KEY, DOMAIN_KEY);
		int result = 0;
		if(domainCfg == null){
			domainCfg = new SystemConfig();
			domainCfg.setGroup(DOMAIN_KEY);
			domainCfg.setKey(DOMAIN_KEY);
			domainCfg.setValue(domain);
			result = systemConfigMapper.addItem(domainCfg);
		}else{
			domainCfg.setValue(domain);
			result = systemConfigMapper.updateItem(domainCfg);
		}
		if(result != 0)
			return BS3UI.success("系统参数保存成功。");
		else
			return BS3UI.danger("系统参数保存失败。");
	}

	@Override
	protected String getMenuURL() {
		return "system/sysconfig";
	}

}
