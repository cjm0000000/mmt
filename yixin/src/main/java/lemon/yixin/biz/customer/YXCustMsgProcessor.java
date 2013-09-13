package lemon.yixin.biz.customer;

import lemon.yixin.bean.message.YiXinMessage;

/**
 * YiXin customer message business interface
 * @author lemon
 * @version 1.0
 *
 */
public interface YXCustMsgProcessor {
	/**
	 * customer message process<BR>
	 * @param mmt_token	is unique in MMT system
	 * @param msg
	 * @return
	 */
	String processBiz(String mmt_token, YiXinMessage msg);
	
}
