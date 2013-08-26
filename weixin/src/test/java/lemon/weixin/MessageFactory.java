package lemon.weixin;

import java.util.Date;

import lemon.weixin.bean.message.TextMessage;

public class MessageFactory {
	
	public static TextMessage makeTextMsg(){
		TextMessage txt = new TextMessage();
		txt.setToUserName("weixin");
		txt.setFromUserName("lemon");
		txt.setCreateTime(new Date().getTime());
		txt.setContent("hello,weixin, I am \"lemon\".");
		txt.setMsgId(1024102410241024L);
		return txt;
	}
}
