package lemon.shared.gateway;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

import lemon.shared.access.Access;
import lemon.shared.api.MmtAPI;
import lemon.shared.config.MMTConfig;

/**
 * MMT通用消息网关
 * 
 * @author lemon
 * @version 1.0
 * 
 */
public abstract class AbstractGateWay implements Filter {
	private static Log logger = LogFactory.getLog(AbstractGateWay.class);
	
	/**
	 * 获取客户接口配置信息
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
	 * 获取目标App的字符集
	 * @return
	 */
	protected abstract String getTargetCharset();
	
	/**
	 * 在处理消息之前需要做的事情<BR>
	 *  微信新增身份验证，原理同doAuthentication
	 * @param cfg
	 * @param req
	 */
	protected abstract void preProcessMsg(MMTConfig cfg, HttpServletRequest req);
	
	/**
	 * 身份认证
	 * @param cfg
	 * @param req
	 */
	protected void doAuthentication(MMTConfig cfg, HttpServletRequest req){
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
		
		if (!getMMTAPI().verifySignature(sa)) 
			throw new MmtException("身份认证失败：CUST_ID=" + cfg.getCust_id());
	}

	@Override
	public final void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req 		= (HttpServletRequest) request;
		HttpServletResponse resp 	= (HttpServletResponse) response;
		//获取客户令牌
		String mmt_token = getMMTToken(req.getServletPath());
		//获取配置信息
		MMTConfig cfg = getConfig(mmt_token);
		if(null == cfg){
			resp.getWriter().print("No matchers.");
			logger.error("the URL["+mmt_token+"] have no matcher.");
			return;
		}
		//是否处理消息
		boolean processMsg = req.getMethod().equals("POST");
		if(processMsg)
			processMsg(cfg, req, resp);
		else
			access(cfg, req, resp);
	}
	
	/**
	 * 网址接入
	 * @param cfg
	 * @param req
	 * @param resp
	 * @throws IOException 
	 */
	private final void access(MMTConfig cfg, HttpServletRequest req,
			HttpServletResponse resp) throws IOException {
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
	private String getMMTToken(String path) {
		if (path.lastIndexOf("/") == 0)
			path = path + "/";
		return path.substring(path.lastIndexOf("/")).substring(1);
	}
	
	/**
	 * get receive message via input stream
	 * 
	 * @param req
	 * @return
	 * @throws IOException
	 */
	private String getMessage(HttpServletRequest req) {
		try (InputStream is = req.getInputStream()) {
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = br.readLine()) != null)
				sb.append(line);
			return new String(sb.toString().getBytes(LOCAL_CHARSET),
					getTargetCharset());
		} catch (IOException e) {
			throw new MmtException("Cant't get message from InputStream. ",
					e.getCause());
		}
	}
	
	/**
	 * 处理文字信息
	 * @param cfg
	 * @param req
	 * @param resp
	 */
	private void processMsg(MMTConfig cfg, HttpServletRequest request,
			HttpServletResponse response) {
		response.setCharacterEncoding(LOCAL_CHARSET);
		preProcessMsg(cfg, request);
		try (PrintWriter out = response.getWriter()) {
			String msg = getMessage(request);
			out.println(getMMTAPI().processMsg(cfg.getApi_url(), msg));
			out.flush();
		} catch (IOException e) {
			throw new MmtException("Process message failed. ", e.getCause());
		}
	}

}
