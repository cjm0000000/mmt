package lemon.weixin.bean.message;

import static lemon.weixin.util.WXHelper.cDATA;

import lemon.shared.common.Message;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * WeiXin message
 * 
 * @author lemon
 * 
 */
public class WeiXinMessage implements Message,Cloneable {
	/** ToUserName */
	@XStreamAlias("ToUserName")
	protected String toUserName;
	/** FromUserName */
	@XStreamAlias("FromUserName")
	protected String fromUserName;
	/** CreateTime */
	@XStreamAlias("CreateTime")
	protected long createTime;
	/** MsgType */
	@XStreamAlias("MsgType")
	protected String msgType;
	/** MsgId */
	@XStreamAlias("MsgId")
	@XStreamOmitField
	protected long msgId;

	public WeiXinMessage(String msgType) {
		this.msgType = cDATA(msgType);
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = cDATA(fromUserName);
	}

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = cDATA(toUserName);
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public String getMsgType() {
		return msgType;
	}

	public long getMsgId() {
		return msgId;
	}

	public void setMsgId(long msgId) {
		this.msgId = msgId;
	}

	public WeiXinMessage cloneMsg() {
		WeiXinMessage bm = null;
		try {
			bm = (WeiXinMessage) this.clone();
			if(null == bm) return null;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		// exchange from user and to user
		String fromUserName = bm.getFromUserName();
		bm.setFromUserName(bm.getToUserName());
		bm.setToUserName(fromUserName);
		bm.setCreateTime(System.currentTimeMillis());
		return bm;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("toUserName=" + toUserName);
		sb.append(", fromUserName=" + fromUserName);
		sb.append(", createTime=" + createTime);
		return sb.toString();
	}
}
