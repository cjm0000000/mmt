package lemon.shared.message.metadata;

import lemon.shared.service.BaseService;
import lemon.shared.toolkit.xstream.annotations.XStreamCDATA;
import lemon.shared.toolkit.xstream.annotations.XStreamProcessCDATA;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Interface for message
 * 
 * @author lemon
 * @version 1.0
 * 
 */
@XStreamProcessCDATA
public class Message extends BaseService {
	/** ToUserName */
	@XStreamAlias("ToUserName")
	@XStreamCDATA
	protected String toUserName;
	/** FromUserName */
	@XStreamAlias("FromUserName")
	@XStreamCDATA
	protected String fromUserName;
	/** CreateTime */
	@XStreamAlias("CreateTime")
	protected long createTime;
	/** MsgType */
	@XStreamAlias("MsgType")
	@XStreamCDATA
	protected String msgType;
	/** MsgId */
	@XStreamAlias("MsgId")
	protected Long msgId;
	
	public Message(String msgType) {
		this.msgType = msgType;
	}
	public String getToUserName() {
		return toUserName;
	}
	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}
	public String getFromUserName() {
		return fromUserName;
	}
	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	protected String getMsgType() {
		return msgType;
	}
	protected void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public Long getMsgId() {
		return msgId;
	}
	public void setMsgId(Long msgId) {
		this.msgId = msgId;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("toUserName=" + toUserName);
		sb.append(", fromUserName=" + fromUserName);
		sb.append(", createTime=" + createTime);
		return sb.toString();
	}

}
