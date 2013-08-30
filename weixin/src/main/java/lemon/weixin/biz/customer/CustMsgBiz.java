package lemon.weixin.biz.customer;

import lemon.shared.common.Message;

/**
 * Customer message business interface
 * @author lemon
 *
 */
public interface CustMsgBiz {
	/**
	 * Customer message process<BR>
	 * @param mmt_token	is unique in MMT system
	 * @param msg
	 * @return
	 */
	String processBiz(String mmt_token, Message msg);
	
}
