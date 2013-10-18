package lemon.weixin.gateway;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

import lemon.shared.api.MmtAPI;
import lemon.shared.api.simple.MMTConfig;
import lemon.shared.api.simple.MMTGateWay;
import lemon.shared.entity.MMTCharset;
import lemon.weixin.WeiXin;
import lemon.weixin.config.bean.WeiXinConfig;
import lemon.weixin.config.mapper.WXConfigMapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * MicroChat gateway
 * @author lemon
 * @version 1.0
 *
 */
@Service("weixinGW")
public final class WeiXinGateWay extends MMTGateWay {
	private static Log logger = LogFactory.getLog(WeiXinGateWay.class);
	@Resource(name="weiXinAPI")
	private MmtAPI wxAPI;
	@Autowired
	private WXConfigMapper weiXinConfigMapper;
	@Override
	public void destroy() {
		WeiXin.destory();
		logger.debug("MicroChatGateWay destory...");
	}


	@Override
	public void init(FilterConfig config) throws ServletException {
		WeiXin.init();
		List<WeiXinConfig> list = weiXinConfigMapper.availableList();
		for (WeiXinConfig wxcfg : list) {
			WeiXin.setConfig(wxcfg);
		}
		logger.info("微信网关初始化成功...");
	}
	

	@Override
	public MMTConfig getConfig(String mmt_token) {
		return WeiXin.getConfig(mmt_token);
	}

	@Override
	public MmtAPI getMMTAPI() {
		return wxAPI;
	}

	@Override
	protected String getTargetCharset() {
		return MMTCharset.WEIXIN_CHARSET;
	}
	
}
