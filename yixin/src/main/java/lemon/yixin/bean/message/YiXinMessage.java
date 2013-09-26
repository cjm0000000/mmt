package lemon.yixin.bean.message;

import lemon.shared.common.Message;
import lemon.shared.xstream.annotations.XStreamCDATA;
import lemon.shared.xstream.annotations.XStreamProcessCDATA;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * YiXin message
 * 
 * @author lemon
 * @version 1.0
 * 
 */
@XStreamProcessCDATA
public class YiXinMessage implements Message {
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
	protected int createTime;
	/** MsgType */
	@XStreamAlias("MsgType")
	@XStreamCDATA
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

	public YiXinMessage(String msgType) {
		this.msgType = msgType;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public int getCreateTime() {
		return createTime;
	}

	public void setCreateTime(int createTime) {
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
