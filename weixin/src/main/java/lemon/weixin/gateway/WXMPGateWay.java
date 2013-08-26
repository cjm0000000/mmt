package lemon.weixin.gateway;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import lemon.shared.api.MmtAPI;
import lemon.shared.weixin.bean.SiteAccessLog;
import lemon.weixin.biz.WXGZAPI;
import static lemon.weixin.util.WXHelper.*;

/**
 * The gateway of Weixin
 * 
 * @author lemon
 * 
 */
public class WXMPGateWay extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Log logger = LogFactory.getLog(WXMPGateWay.class);
	@Autowired
	private MmtAPI wxAPI = new WXGZAPI();

	/**
	 * 验证网址接入
	 */
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
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
		
		paramMap.put("SiteAccess", log);
		
		//验证签名
		if(wxAPI.verifySignature(paramMap))
			resp.getWriter().print(echostr);
		resp.getWriter().print("I think you are not the Weixin Server.");
	}

	/**
	 * 消息通信接口
	 */
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// first, parse the message
		String msg = getMessage(req);
		logger.debug(msg);
		// third, process message by business logic
		processMessage(resp,msg);
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
	private void processMessage(HttpServletResponse resp, String msg)
			throws IOException {
		PrintWriter out = null;
		try {
			resp.setCharacterEncoding(LOCAL_CHARSET);
			out = resp.getWriter();
			logger.debug(msg);
			out.println(wxAPI.processMsg(msg));
			out.flush();
		} finally {
			if (null != out)
				out.close();
		}
	}

}
