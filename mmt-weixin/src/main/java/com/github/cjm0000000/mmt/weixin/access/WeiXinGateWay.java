package com.github.cjm0000000.mmt.weixin.access;

import java.util.List;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.cjm0000000.mmt.core.config.MmtCharset;
import com.github.cjm0000000.mmt.core.config.MmtConfig;
import com.github.cjm0000000.mmt.shared.access.AbstractMsgGateWay;
import com.github.cjm0000000.mmt.weixin.WeiXin;
import com.github.cjm0000000.mmt.weixin.config.WeiXinConfig;
import com.github.cjm0000000.mmt.weixin.config.persistence.WeiXinConfigRepository;

/**
 * WeiXin gateway
 * 
 * @author lemon
 * @version 1.0
 * 
 */
@Service("weixinGW")
public final class WeiXinGateWay extends AbstractMsgGateWay {
  private static Log logger = LogFactory.getLog(WeiXinGateWay.class);
  @Autowired
  private WeiXinConfigRepository weiXinConfigRepository;

  @Override
  public void destroy() {
    WeiXin.destory();
    logger.info("微信网销毁成功...");
  }
  
  @Override
  public MmtConfig getConfig(String mmt_token) {
    return WeiXin.getConfig(mmt_token);
  }

  @Override
  public void init(FilterConfig config) throws ServletException {
    WeiXin.init();
    List<WeiXinConfig> list = weiXinConfigRepository.availableList();
    for (WeiXinConfig wxcfg : list)
      WeiXin.setConfig(wxcfg);
    list.clear();
    logger.info("微信网关初始化成功...");
  }

  @Override
  protected void preProcessMsg(MmtConfig cfg, HttpServletRequest req) {
    super.doAuthentication(cfg, req);
  }

  @Override
  protected String getGateWayCharset() {
    return MmtCharset.WEIXIN_CHARSET;
  }

}
