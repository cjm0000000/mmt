package lemon.weixin.gateway;

import static lemon.weixin.util.WXHelper.LOCAL_CHARSET;
import static lemon.weixin.util.WXHelper.TARGET_CHARSET;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lemon.shared.api.MmtAPI;
import lemon.weixin.WeiXin;
import lemon.weixin.bean.WeiXinConfig;
import lemon.weixin.bean.log.SiteAccessLog;
import lemon.weixin.biz.WXGZAPI;
import lemon.weixin.dao.WeiXinConfigMapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * MicroChat gateway
 * @author lemon
 *
 */
public class MicroChatGateWay implements Filter {
	private static Log logger = LogFactory.getLog(MicroChatGateWay.class);
	private MmtAPI wxAPI = new WXGZAPI();
	@Autowired
	private WeiXinConfigMapper weiXinConfigMapper;
	@Override
	public void destroy() {
		WeiXin.destory();
		logger.debug("MicroChatGateWay destory...");
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain filter) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		//confirm which customer is it
		logger.debug(request.getServletPath());
		String shortPath = getShortPath(request.getServletPath());
		logger.debug("shortPath=" + shortPath);
		WeiXinConfig config = WeiXin.getConfig(shortPath);
		if(null == config){
			response.getWriter().print("No matchers.");
			logger.error("the URL["+shortPath+"] have no matcher.");
			return;
		}
		//check if this is channel for verify signature
		boolean isVerifySignatureChannel = request.getMethod().equals("GET");
		if(isVerifySignatureChannel)
			verifySignature(config,request,response);
		else
			msgWorker(config,request,response);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		WeiXin.init();
		List<WeiXinConfig> list = weiXinConfigMapper.activeList();
		for (WeiXinConfig wxcfg : list) {
			WeiXin.setConfig(wxcfg.getToken(), wxcfg);
		}
		logger.info("微信网关初始化成功...");
	}
	
	/**
	 * 验证网址接入
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void verifySignature(WeiXinConfig config, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//微信加密签名
		String signature = req.getParameter("signature");
		//时间戳
		String timestamp = req.getParameter("timestamp");
		//随机数
		String nonce = req.getParameter("nonce");
		//随机字符串
		String echostr = req.getParameter("echostr");
		
		logger.debug("signature="+signature);
		logger.debug("timestamp="+timestamp);
		logger.debug("nonce="+nonce);
		logger.debug("echostr="+echostr);
		
		//参数装箱
		Map<String, Object> paramMap = new HashMap<>();
		
		SiteAccessLog log = new SiteAccessLog();
		log.setEchostr(echostr);
		log.setNonce(nonce);
		log.setSignature(signature);
		log.setTimestamp(timestamp);
		log.setCust_id(config.getCust_id());
		log.setToken(config.getToken());
		
		paramMap.put("SiteAccess", log);
		
		//验证签名
		if(wxAPI.verifySignature(paramMap))
			resp.getWriter().print(echostr);
		resp.getWriter().print("I think you are not the Weixin Server.");
	}
	
	/**
	 * 消息通信接口
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void msgWorker(WeiXinConfig config, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// first, parse the message
		String msg = getMessage(req);
		// third, process message by business logic
		processMessage(config.getToken(), resp,msg);
	}
	
	/**
	 * get receive message via input stream
	 * 
	 * @param req
	 * @return
	 * @throws IOException
	 */
	private String getMessage(HttpServletRequest req) throws IOException {
		InputStream is = null;
		try {
			is = req.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = br.readLine()) != null)
				sb.append(line);
			return new String(sb.toString().getBytes(LOCAL_CHARSET),
					TARGET_CHARSET);
		} finally {
			if (null != is)
				is.close();
		}
	}

	/**
	 * Process message by business logic
	 * @param resp
	 * @param msg
	 * @throws IOException
	 */
	private void processMessage(String token, HttpServletResponse resp, String msg)
			throws IOException {
		PrintWriter out = null;
		try {
			resp.setCharacterEncoding(LOCAL_CHARSET);
			out = resp.getWriter();
			logger.debug(msg);
			out.println(wxAPI.processMsg(token, msg));
			out.flush();
		} finally {
			if (null != out)
				out.close();
		}
	}
	
	/**
	 * Get the unique path
	 * @param path
	 * @return
	 */
	private String getShortPath(String path){
		return path.substring(path.lastIndexOf("/")).substring(1);
	}

}
