package lemon.weixin.bean.message;

import static lemon.weixin.util.WXHelper.cDATA;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * Basic message
 * @author lemon
 *
 */
@XStreamAlias("xml")
public class BasicMessage {
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
	
	public BasicMessage(String msgType){
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
}
