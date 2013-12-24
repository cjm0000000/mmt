package com.github.cjm0000000.mmt.core.message.event;

/**
 * Event types
 * 
 * @author lemon
 * @version 1.0
 * 
 */
public enum EventType {
	/** 订阅 */
	subscribe,
	/** 取消订阅 */
	unsubscribe,
	/** 扫二维码 */
	scan,
	/** 上报地理位置 */
	LOCATION,
	/** 自定义菜单点击事件 */
	CLICK
}
