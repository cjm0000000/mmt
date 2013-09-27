package lemon.yixin.gateway;

import static lemon.yixin.YiXin.LOCAL_CHARSET;
import static lemon.yixin.YiXin.TARGET_CHARSET;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lemon.shared.access.SiteAccess;
import lemon.shared.api.MmtAPI;
import lemon.yixin.YiXin;
import lemon.yixin.bean.YiXinConfig;
import lemon.yixin.dao.YXConfigMapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * YiXin gateway
 * @author lemon
 * @version 1.0
 *
 */
@Service("yixinGW")
public class YiXinGateWay implements Filter {
	private static Log logger = LogFactory.getLog(YiXinGateWay.class);
	@Resource(name="yiXinAPI")
	private MmtAPI yxAPI;
	@Autowired
	private YXConfigMapper yiXinConfigMapper;
	@Override
	public void destroy() {
		YiXin.destory();
		logger.debug("YiXinGateWay destory...");
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain filter) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		//confirm which customer is it
		String shortPath = getShortPath(request.getServletPath());
		logger.debug("shortPath=" + shortPath);
		YiXinConfig config = YiXin.getConfig(shortPath);
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
		YiXin.init();
		List<YiXinConfig> list = yiXinConfigMapper.availableList();
		for (YiXinConfig yxcfg : list) {
			YiXin.setConfig(yxcfg);
		}
		logger.info("易信网关初始化成功...");
	}
	
	/**
	 * 验证网址接入
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void verifySignature(YiXinConfig config, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//易信加密签名
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
		SiteAccess sa = new SiteAccess();
		sa.setEchostr(echostr);
		sa.setNonce(nonce);
		sa.setSignature(signature);
		sa.setTimestamp(timestamp);
		sa.setCust_id(config.getCust_id());
		sa.setToken(config.getToken());
		
		//验证签名
		if(yxAPI.verifySignature(sa)){
			resp.getWriter().print(echostr);
			return;
		}
		resp.getWriter().print("I think you are not the YiXin Server.");
	}
	
	/**
	 * 消息通信接口
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void msgWorker(YiXinConfig config, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// first, parse the message
		String msg = getMessage(req);
		// third, process message by business logic
		processMessage(config.getApi_url(), resp,msg);
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
	 * @param mmt_token	MMT system's token
	 * @param resp
	 * @param msg
	 */
	private void processMessage(String mmt_token, HttpServletResponse resp, String msg){
		resp.setCharacterEncoding(LOCAL_CHARSET);
		try(PrintWriter out = resp.getWriter()) {
			out.println(yxAPI.processMsg(mmt_token, msg));
			out.flush();
		} catch(IOException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Get the unique path
	 * @param path
	 * @return
	 */
	private String getShortPath(String path){
		if(path.lastIndexOf("/") == 0)
			path = path + "/";
		return path.substring(path.lastIndexOf("/")).substring(1);
	}
	
}
