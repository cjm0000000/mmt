package com.github.cjm0000000.mmt.yixin.access;

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
import com.github.cjm0000000.mmt.yixin.YiXin;
import com.github.cjm0000000.mmt.yixin.config.YiXinConfig;
import com.github.cjm0000000.mmt.yixin.config.persistence.YiXinConfigRepository;

/**
 * YiXin gateway
 * 
 * @author lemon
 * @version 1.0
 * 
 */
@Service("yixinGW")
public final class YiXinGateWay extends AbstractMsgGateWay {
  private static Log logger = LogFactory.getLog(YiXinGateWay.class);
  @Autowired
  private YiXinConfigRepository yiXinConfigMapper;

  @Override
  public void destroy() {
    YiXin.destory();
    logger.info("易信网关销毁成功...");
  }

  @Override
  public void init(FilterConfig config) throws ServletException {
    YiXin.init();
    List<YiXinConfig> list = yiXinConfigMapper.availableList();
    for (YiXinConfig yxcfg : list)
      YiXin.setConfig(yxcfg);
    list.clear();
    logger.info("易信网关初始化成功...");
  }

  @Override
  protected void preProcessMsg(MmtConfig cfg, HttpServletRequest req) {
    // 易信暂时不需要实现，API没有相关说明，如果加了验证，很可能消息处理会失败
  }

  @Override
  protected String getGateWayCharset() {
    return MmtCharset.YIXIN_CHARSET;
  }

  @Override
  protected MmtConfig getConfig(String mmt_token) {
    return YiXin.getConfig(mmt_token);
  }

}
