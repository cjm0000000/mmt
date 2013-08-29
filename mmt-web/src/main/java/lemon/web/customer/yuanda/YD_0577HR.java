package lemon.web.customer.yuanda;

import lemon.weixin.bean.message.EventMessage;
import lemon.weixin.bean.message.ImageMessage;
import lemon.weixin.bean.message.LinkMessage;
import lemon.weixin.bean.message.LocationMessage;
import lemon.weixin.bean.message.MusicMessage;
import lemon.weixin.bean.message.NewsMessage;
import lemon.weixin.bean.message.TextMessage;
import lemon.weixin.biz.customer.CustBasicMsgBiz;

/**
 * A sample example for message processor: 0577 HR business
 * @author lemon
 *
 */
public final class YD_0577HR extends CustBasicMsgBiz{

	@Override
	public String processImageMsg(String token, ImageMessage msg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String processLinkMsg(String token, LinkMessage msg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String processLocationMsg(String token, LocationMessage msg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String processMusicMsg(String token, MusicMessage msg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String processNewsMsg(String token, NewsMessage msg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String processTextMsg(String token, TextMessage msg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String processClickEvent(String token, EventMessage msg) {
		// TODO Auto-generated method stub
		return null;
	}

}
