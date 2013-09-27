package lemon.shared.gateway;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import lemon.shared.access.SiteAccess;
import lemon.shared.api.MmtAPI;
import lemon.shared.api.simple.MMTConfig;

/**
 * MMT 通用过滤器
 * @author lemon
 * @version 1.0
 *
 */
//FIXME 完成这个接口
public abstract class MMTGateWay implements Filter {
	private static Log logger = LogFactory.getLog(MMTGateWay.class);
	
	/**
	 * 获取App配置信息
	 * @param mmt_token
	 * @return
	 */
	public abstract MMTConfig getConfig(String mmt_token);
	
	/**
	 * 获取API实现
	 * @return
	 */
	public abstract MmtAPI getMMTAPI();
	
	/**
	 * 处理文字信息
	 * @param cfg
	 * @param resp
	 */
	public abstract void processMsg(MMTConfig cfg, HttpServletResponse resp);
	
	/**
	 * 网址接入
	 * @param cfg
	 * @param req
	 * @param resp
	 * @throws IOException 
	 */
	public void access(MMTConfig cfg, HttpServletRequest req,
			HttpServletResponse resp) throws IOException {
		// 微信加密签名
		String signature = req.getParameter("signature");
		// 时间戳
		String timestamp = req.getParameter("timestamp");
		// 随机数
		String nonce = req.getParameter("nonce");
		// 随机字符串
		String echostr = req.getParameter("echostr");

		// 参数装箱
		SiteAccess sa = new SiteAccess();
		sa.setEchostr(echostr);
		sa.setNonce(nonce);
		sa.setSignature(signature);
		sa.setTimestamp(timestamp);
		sa.setCust_id(cfg.getCust_id());
		sa.setToken(cfg.getToken());

		// 验证签名
		if (getMMTAPI().verifySignature(sa)) {
			resp.getWriter().print(echostr);
			return;
		}
		resp.getWriter().print("Failure.");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		//获取客户令牌
		String mmt_token = getMMTToken(req.getServletPath());
		//获取配置信息
		MMTConfig cfg = getConfig(mmt_token);
		if(null == cfg){
			resp.getWriter().print("No matchers.");
			logger.error("the URL["+mmt_token+"] have no matcher.");
			return;
		}
		boolean processMsg = req.getMethod().equals("POST");
		if(processMsg)
			processMsg(cfg, resp);
		else
			access(cfg, req, resp);
	}
	
	/**
	 * 获取客户令牌
	 * @param path
	 * @return
	 */
	private String getMMTToken(String path){
		if(path.lastIndexOf("/") == 0)
			path = path + "/";
		return path.substring(path.lastIndexOf("/")).substring(1);
	}

}
