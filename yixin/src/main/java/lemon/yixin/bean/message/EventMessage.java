package lemon.yixin.bean.message;

import lemon.shared.toolkit.xstream.annotations.XStreamCDATA;
import lemon.shared.toolkit.xstream.annotations.XStreamProcessCDATA;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Event message
 * @author lemon
 * @version 1.0
 *
 */
@XStreamAlias("xml")
@XStreamProcessCDATA
public class EventMessage extends YiXinMessage {
	/** Event */
	@XStreamAlias("Event")
	@XStreamCDATA
	private String eventType; 
	/** EventKey(事件KEY值，与自定义菜单接口中KEY值对应) */
	@XStreamCDATA
	@XStreamAlias("EventKey")
	private String eventKey;
	
	public EventMessage(){
		super(MsgType.EVENT);
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public String getEventKey() {
		return eventKey;
	}
	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}
}
