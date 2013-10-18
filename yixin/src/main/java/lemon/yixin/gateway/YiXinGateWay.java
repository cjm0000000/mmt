package lemon.yixin.gateway;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

import lemon.shared.api.MmtAPI;
import lemon.shared.api.simple.MMTConfig;
import lemon.shared.api.simple.MMTGateWay;
import lemon.shared.entity.MMTCharset;
import lemon.yixin.config.YiXin;
import lemon.yixin.config.bean.YiXinConfig;
import lemon.yixin.config.mapper.YXConfigMapper;

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
public final class YiXinGateWay extends MMTGateWay {
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
	public void init(FilterConfig config) throws ServletException {
		YiXin.init();
		List<YiXinConfig> list = yiXinConfigMapper.availableList();
		for (YiXinConfig yxcfg : list) {
			YiXin.setConfig(yxcfg);
		}
		logger.info("易信网关初始化成功...");
	}

	@Override
	public MMTConfig getConfig(String mmt_token) {
		return YiXin.getConfig(mmt_token);
	}

	@Override
	public MmtAPI getMMTAPI() {
		return yxAPI;
	}

	@Override
	protected String getTargetCharset() {
		return MMTCharset.YIXIN_CHARSET;
	}
	
}
