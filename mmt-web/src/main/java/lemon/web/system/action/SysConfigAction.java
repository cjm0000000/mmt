package lemon.web.system.action;

import java.util.Map;

import lemon.web.base.AdminNavAction;
import lemon.web.base.MMTAction;
import lemon.web.system.bean.SystemConfig;
import lemon.web.system.bean.User;
import lemon.web.system.mapper.SystemConfigMapper;
import lemon.web.ui.BS3UI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

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
	private SystemConfigMapper systemConfigMapper;
	
	/**
	 * 首页
	 * @return
	 */
	@RequestMapping("list")
	public String list() {
		return "redirect:show";
	}

	/**
	 * 显示配置信息
	 * @param user
	 * @return
	 */
	@RequestMapping("show")
	public ModelAndView show(@ModelAttribute(TOKEN) User user) {
		//获取Main视图名称
		String mainViewName = getMainViewName(Thread.currentThread().getStackTrace()[1].getMethodName());
		if(null == mainViewName)
			sendNotFoundError();
		//获取导航条数据
		Map<String, Object> resultMap = buildNav(user.getRole_id());
		//获取Main数据
		SystemConfig domainCfg = systemConfigMapper.getItem(DOMAIN_KEY, DOMAIN_KEY);
		resultMap.put("mainViewName", mainViewName);
		resultMap.put("domain", domainCfg);
		return new ModelAndView(VIEW_MANAGE_HOME_PAGE, "page", resultMap);
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
