package lemon.weixin.bean.message;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import static lemon.weixin.util.WXHelper.cDATA;

/**
 * Event message
 * @author lemon
 *
 */
@XStreamAlias("xml")
public class EventMessage extends BasicMessage {
	/** Event */
	@XStreamAlias("Event")
	private String eventType; 
	/** EventKey(事件KEY值，与自定义菜单接口中KEY值对应) */
	@XStreamAlias("EventKey")
	private String eventKey;
	
	public EventMessage(){
		super(MsgType.EVENT);
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = cDATA(eventType);
	}
	public String getEventKey() {
		return eventKey;
	}
	public void setEventKey(String eventKey) {
		this.eventKey = cDATA(eventKey);
	}
}
