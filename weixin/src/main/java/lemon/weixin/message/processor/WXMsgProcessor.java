package lemon.weixin.message.processor;

import lemon.shared.service.ServiceProperty;
import lemon.weixin.message.bean.WeiXinMessage;

/**
 * WeiXin customer message business interface
 * @author lemon
 * @version 1.0
 *
 */
public interface WXMsgProcessor extends ServiceProperty {
	/**
	 * customer message process<BR>
	 * @param mmt_token	is unique in MMT system
	 * @param msg
	 * @return
	 */
	String processBiz(String mmt_token, WeiXinMessage msg);
	
}
