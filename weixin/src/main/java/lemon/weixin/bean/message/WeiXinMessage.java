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
	protected Long msgId;
	/** ID */
	@XStreamOmitField
	protected int id;
	/** customer ID */
	@XStreamOmitField
	protected int cust_id;

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

	public Long getMsgId() {
		return msgId;
	}

	public void setMsgId(Long msgId) {
		this.msgId = msgId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCust_id() {
		return cust_id;
	}

	public void setCust_id(int cust_id) {
		this.cust_id = cust_id;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("toUserName=" + toUserName);
		sb.append(", fromUserName=" + fromUserName);
		sb.append(", createTime=" + createTime);
		return sb.toString();
	}
}
