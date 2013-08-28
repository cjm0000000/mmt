package lemon.weixin.biz.customer.yuanda;

import lemon.shared.common.Message;
import lemon.weixin.bean.message.TextMessage;
import lemon.weixin.biz.customer.CustomerReplyMsg;

/**
 * 0577 HR business
 * @author lemon
 *
 */
public class YD_0577HR implements CustomerReplyMsg {

	@Override
	public String reply(Message msg) {
		
		if(msg instanceof TextMessage){
			return "Welcome to 0577 HR! You send me a text message.";
		}
		// TODO 0577 HR business
		return "Welcome to 0577 HR!";
	}

}
