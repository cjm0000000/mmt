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

import lemon.shared.common.MMTConfig;

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
	 */
	public abstract void access(MMTConfig cfg, HttpServletRequest req,
			HttpServletResponse resp);

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
