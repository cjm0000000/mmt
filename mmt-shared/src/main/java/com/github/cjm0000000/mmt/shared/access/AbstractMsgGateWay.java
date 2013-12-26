package com.github.cjm0000000.mmt.shared.access;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import static com.github.cjm0000000.mmt.core.config.MmtCharset.*;

import com.github.cjm0000000.mmt.core.MmtException;
import com.github.cjm0000000.mmt.core.access.Access;
import com.github.cjm0000000.mmt.core.access.MsgGateWay;
import com.github.cjm0000000.mmt.core.config.MmtConfig;
import com.github.cjm0000000.mmt.core.message.BaseMessage;
import com.github.cjm0000000.mmt.core.message.process.PassiveProcessor;
import com.github.cjm0000000.mmt.core.parser.MmtXMLParser;

/**
 * basic message gateway
 * @author lemon
 * @version 2.0
 *
 */
public abstract class AbstractMsgGateWay implements MsgGateWay, Filter {
	private static Log logger = LogFactory.getLog(AbstractMsgGateWay.class);

	protected PassiveProcessor msgProcessor;
	
	public AbstractMsgGateWay(PassiveProcessor msgProcessor){
		this.msgProcessor = msgProcessor;
	}
	
	/**
	 * 在处理消息之前需要做的事情<BR>
	 *  微信新增身份验证，原理同doAuthentication
	 * @param cfg
	 * @param req
	 */
	protected abstract void preProcessMsg(MmtConfig cfg, HttpServletRequest req);
	
	/**
	 * 身份认证
	 * @param cfg
	 * @param req
	 */
	protected void doAuthentication(MmtConfig cfg, HttpServletRequest req){
		// 加密签名
		String signature = req.getParameter("signature");
		// 时间戳
		String timestamp = req.getParameter("timestamp");
		// 随机数
		String nonce = req.getParameter("nonce");
		// 随机字符串
		String echostr = req.getParameter("echostr");

		// 参数装箱
		Access sa = new Access();
		sa.setEchostr(echostr);
		sa.setNonce(nonce);
		sa.setSignature(signature);
		sa.setTimestamp_api(timestamp);
		sa.setCust_id(cfg.getCust_id());
		sa.setToken(cfg.getToken());
		
		if (!msgProcessor.verifySignature(sa)) 
			throw new MmtException("身份认证失败：CUST_ID=" + cfg.getCust_id());
	}
	
	@Override
	public final void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		if(logger.isDebugEnabled())
			logger.debug("接受到消息请求。");
		//FIXME 植入代码开始跟踪消息
		HttpServletRequest req 		= (HttpServletRequest) request;
		HttpServletResponse resp 	= (HttpServletResponse) response;
		//获取客户令牌
		String mmt_token = getMmtToken(req.getServletPath());
		if(mmt_token == null)
			throw new MmtException("找不到配置.");
		//获取配置信息
		MmtConfig cfg = msgProcessor.getConfig(mmt_token);
		if(null == cfg){
			logger.error("the URL[" + mmt_token + "] have no matcher.");
			throw new MmtException("找不到配置.");
		}
		
		if("POST".equals(req.getMethod()))//处理消息
			process0(cfg, req, resp);
		else
			access(cfg, req, resp);
	}
	
	@Override
	public final BaseMessage processMsg(MmtConfig cfg, InputStream is) {
		return msgProcessor.process(cfg.getApi_url(), getMessage(is));
	}
	
	/**
	 * 网址接入
	 * @param cfg
	 * @param req
	 * @param resp
	 * @throws IOException 
	 */
	private final void access(MmtConfig cfg, HttpServletRequest req,
			HttpServletResponse resp) throws IOException {
		if(logger.isDebugEnabled())
			logger.debug("Verify signature[cust_id=" + cfg.getCust_id() + "].");
		//身份认证
		doAuthentication(cfg, req);
		//回应Server
		resp.getWriter().print(req.getParameter("echostr"));
	}
	
	/**
	 * 获取客户令牌
	 * @param path
	 * @return
	 */
	private String getMmtToken(String path) {
		if (path.lastIndexOf("/") == 0)
			path = path + "/";
		path = path.substring(path.lastIndexOf("/"));
		return path.length() > 0 ? path.substring(1) : null;
	}
	
	/**
	 * get receive message via input stream
	 * @param is
	 * @return
	 */
	private BaseMessage getMessage(InputStream is) {
		return MmtXMLParser.fromXML(is);
	}
	
	/**
	 * 处理文字信息
	 * @param cfg
	 * @param req
	 * @param resp
	 */
	private void process0(MmtConfig cfg, HttpServletRequest request,
			HttpServletResponse response) {
		if(logger.isDebugEnabled())
			logger.debug("process message[cust_id=" + cfg.getCust_id() + "].");
		response.setCharacterEncoding(LOCAL_CHARSET);
		//预处理
		preProcessMsg(cfg, request);
		try (PrintWriter out = response.getWriter()) {
			BaseMessage result = processMsg(cfg, request.getInputStream());
			out.println(MmtXMLParser.toXML(result));
			out.flush();
		} catch (IOException e) {
			throw new MmtException("Process message failed. ", e.getCause());
		}
	}

}
