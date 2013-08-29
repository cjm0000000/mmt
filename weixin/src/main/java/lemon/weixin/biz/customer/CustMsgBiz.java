package lemon.weixin.biz.customer;

import lemon.shared.common.Message;

/**
 * For all customer message business
 * @author lemon
 *
 */
public interface CustMsgBiz {
	/**
	 * Customer message process<BR>
	 * token is unique for every customer
	 * @param token
	 * @param msg
	 * @return
	 */
	String processBiz(String token, Message msg);
	
}
