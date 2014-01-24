package com.github.cjm0000000.mmt.shared.access;

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
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;

import static com.github.cjm0000000.mmt.core.config.MmtCharset.*;

import com.github.cjm0000000.mmt.core.MmtException;
import com.github.cjm0000000.mmt.core.access.Access;
import com.github.cjm0000000.mmt.core.access.MsgGateWay;
import com.github.cjm0000000.mmt.core.config.MmtConfig;
import com.github.cjm0000000.mmt.core.message.BaseMessage;
import com.github.cjm0000000.mmt.core.message.process.PassiveMsgProcessor;
import com.github.cjm0000000.mmt.core.parser.MmtXMLParser;
import com.github.cjm0000000.mmt.shared.MMTContext;
import com.github.cjm0000000.mmt.shared.message.log.MsgLog;
import com.github.cjm0000000.mmt.shared.message.log.MsgLogManager;

/**
 * basic message gateway
 * 
 * @author lemon
 * @version 2.0
 * 
 */
public abstract class AbstractMsgGateWay implements MsgGateWay, Filter {
  private static Log logger = LogFactory.getLog(AbstractMsgGateWay.class);
  @Autowired
  private MMTContext context;
  @Autowired
  private MsgLogManager msgLogManager;

  /**
   * 获取接口编码
   * 
   * @return
   */
  protected abstract String getGateWayCharset();

  /**
   * 获取配置信息
   * 
   * @param mmt_token
   * @return
   */
  protected abstract MmtConfig getConfig(String mmt_token);

  /**
   * 在处理消息之前需要做的事情<BR>
   * 微信新增身份验证，原理同doAuthentication
   * 
   * @param cfg
   * @param req
   */
  protected abstract void preProcessMsg(MmtConfig cfg, HttpServletRequest req);

  /**
   * 身份认证
   * 
   * @param cfg
   * @param req
   */
  protected void doAuthentication(MmtConfig cfg, HttpServletRequest req) {
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

    if (!getProcessor(cfg).verifySignature(sa))
      throw new MmtException("身份认证失败：CUST_ID=" + cfg.getCust_id());
  }

  @Override
  public final void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    // FIXME 植入代码开始跟踪请求 - begin
    if (logger.isDebugEnabled()) logger.debug("接受到消息请求。");
    HttpServletRequest req = (HttpServletRequest) request;
    HttpServletResponse resp = (HttpServletResponse) response;
    // 获取客户令牌
    String mmt_token = getMmtToken(req.getServletPath());
    if (mmt_token == null) throw new MmtException("找不到配置.");
    // 获取配置信息
    MmtConfig cfg = getConfig(mmt_token);
    if (null == cfg) {
      logger.error("the URL[" + mmt_token + "] have no matcher.");
      throw new MmtException("找不到配置.");
    }

    if ("POST".equals(req.getMethod()))// 处理消息
      process0(cfg, req, resp);
    else
      access(cfg, req, resp);
    // FIXME 植入代码开始跟踪请求 - end
  }

  @Override
  public final BaseMessage processMsg(MmtConfig cfg, InputStream is) {
    String xml = getStringFromStream(is);
    // save log
    saveRecvLog(cfg, xml);
    return getProcessor(cfg).process(cfg.getApi_url(), getMessage(xml));
  }

  /**
   * 网址接入
   * 
   * @param cfg
   * @param req
   * @param resp
   * @throws IOException
   */
  private void access(MmtConfig cfg, HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
    if (logger.isDebugEnabled())
      logger.debug("Verify signature[cust_id=" + cfg.getCust_id() + "].");
    // 身份认证
    doAuthentication(cfg, req);
    // 回应Server
    resp.getWriter().print(req.getParameter("echostr"));
    // FIXME 验证签名结束
  }

  /**
   * 获取客户令牌
   * 
   * @param path
   * @return
   */
  private String getMmtToken(String path) {
    if (path.lastIndexOf("/") == 0) path = path + "/";
    path = path.substring(path.lastIndexOf("/"));
    return path.length() > 0 ? path.substring(1) : null;
  }

  /**
   * get receive message
   * 
   * @param xml
   * @return
   */
  private BaseMessage getMessage(String xml) {
    return MmtXMLParser.fromXML(xml);
  }

  /**
   * 处理文字信息
   * 
   * @param cfg
   * @param req
   * @param resp
   */
  private void process0(MmtConfig cfg, HttpServletRequest request, HttpServletResponse response) {
    if (logger.isDebugEnabled())
      logger.debug("process message[cust_id=" + cfg.getCust_id() + "].");
    response.setCharacterEncoding(LOCAL_CHARSET);
    // 预处理
    preProcessMsg(cfg, request);
    try (PrintWriter out = response.getWriter()) {
      BaseMessage result = processMsg(cfg, request.getInputStream());
      String respXML = MmtXMLParser.toXML(result);
      // save log
      saveSendLog(cfg, respXML);
      // response to server
      out.println(respXML);
      out.flush();
      // FIXME 消息处理正常结束
    } catch (IOException e) {
      // FIXME 消息异常结束
      throw new MmtException("Process message failed. ", e.getCause());
    }
  }

  /**
   * 保存接收日志
   * 
   * @param cfg
   * @param xml
   */
  private void saveRecvLog(MmtConfig cfg, String xml) {
    MsgLog log = prepareLog(cfg, xml);
    if (msgLogManager.saveRecvLog(log) == 0) throw new MmtException("保存接收日志失败。");
  }

  /**
   * 保存发送日志
   * 
   * @param cfg
   * @param xml
   */
  private void saveSendLog(MmtConfig cfg, String xml) {
    MsgLog log = prepareLog(cfg, xml);
    if (msgLogManager.saveSendLog(log) == 0) throw new MmtException("保存发送日志失败。");
  }

  /**
   * 生成Log对象
   * 
   * @param cfg
   * @param xml
   * @return
   */
  private MsgLog prepareLog(MmtConfig cfg, String xml) {
    MsgLog log = new MsgLog();
    log.setCust_id(cfg.getCust_id());
    log.setMsg(xml);
    log.setService_type(getProcessor(cfg).getServiceType());
    return log;
  }

  /**
   * get string from input stream
   * 
   * @param is0
   * @return
   */
  private String getStringFromStream(InputStream is0) {
    try (InputStream is = is0) {
      BufferedReader br = new BufferedReader(new InputStreamReader(is));
      StringBuilder sb = new StringBuilder();
      String line;
      while ((line = br.readLine()) != null)
        sb.append(line);
      return new String(sb.toString().getBytes(LOCAL_CHARSET), getGateWayCharset());
    } catch (IOException e) {
      throw new MmtException("Cant't get string from InputStream.", e.getCause());
    }
  }

  /**
   * get passive message processor
   * 
   * @param cfg
   * @return
   */
  private PassiveMsgProcessor getProcessor(MmtConfig cfg) {
    if (cfg == null || cfg.getBiz_class() == null || "".equals(cfg.getBiz_class()))
      throw new MmtException("No implement bussiness class found!!!");
    try {
      return (PassiveMsgProcessor) context.getApplicationContext().getBean(
          Class.forName(cfg.getBiz_class()));
    } catch (BeansException | ClassNotFoundException e) {
      throw new MmtException("No implement bussiness class found!!!", e.getCause());
    }
  }
}
