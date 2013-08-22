package lemon.weixin.bean.message;

/**
 * Event types
 * 
 * @author lemon
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
