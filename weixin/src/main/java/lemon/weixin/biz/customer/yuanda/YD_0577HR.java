package lemon.weixin.biz.customer.yuanda;

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
public class YD_0577HR extends CustBasicMsgBiz{

	@Override
	public String processImageMsg(ImageMessage msg) {
		// TODO implement 0577 HR image message processor
		return null;
	}

	@Override
	public String processLinkMsg(LinkMessage msg) {
		// TODO implement 0577 HR link message processor
		return null;
	}

	@Override
	public String processLocationMsg(LocationMessage msg) {
		// TODO implement 0577 HR location message processor
		return null;
	}

	@Override
	public String processMusicMsg(MusicMessage msg) {
		// TODO implement 0577 HR music message processor
		return null;
	}

	@Override
	public String processNewsMsg(NewsMessage msg) {
		// TODO implement 0577 HR news message processor
		return null;
	}

	@Override
	public String processTextMsg(TextMessage msg) {
		// TODO implement 0577 HR text message processor
		return "0577 HR text message.";
	}

	@Override
	public String processClickEvent(EventMessage msg) {
		// TODO implement 0577 HR click event message processor
		return null;
	}

}
