package lemon.weixin.biz.customer;

import lemon.weixin.bean.message.WeiXinMessage;

/**
 * WeiXin customer message business interface
 * @author lemon
 * @version 1.0
 *
 */
public interface WXCustMsgProcessor {
	/**
	 * customer message process<BR>
	 * @param mmt_token	is unique in MMT system
	 * @param msg
	 * @return
	 */
	String processBiz(String mmt_token, WeiXinMessage msg);
	
}
