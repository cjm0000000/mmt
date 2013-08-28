package lemon.weixin.biz.customer;

import lemon.shared.common.Message;

/**
 * For all customer reply message
 * @author lemon
 *
 */
public interface CustomerReplyMsg {
	/**
	 * Customer message process 
	 * @param msg
	 * @return a string message
	 */
	String reply(Message msg);
}
