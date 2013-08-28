package lemon.weixin.biz.customer;

import lemon.shared.common.Message;

/**
 * For all customer message business
 * @author lemon
 *
 */
public interface CustMsgBiz {
	/**
	 * Customer message process 
	 * @param msg
	 * @return a string message
	 */
	String processBiz(Message msg);
}
