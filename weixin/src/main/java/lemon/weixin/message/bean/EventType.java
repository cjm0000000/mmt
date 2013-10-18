package lemon.weixin.message.bean;

/**
 * Event types
 * 
 * @author lemon
 * @version 1.0
 * 
 */
public interface EventType {
	/** 订阅 */
	String SUBSCRIBE = "subscribe";
	/** 取消订阅 */
	String UNSUBSCRIBE = "unsubscribe";
	/** 自定义菜单点击事件 */
	String CLICK = "CLICK";
}
