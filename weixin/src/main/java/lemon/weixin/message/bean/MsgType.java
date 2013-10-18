package lemon.weixin.message.bean;

/**
 * message types
 * 
 * @author lemon
 * @version 1.0
 * 
 */
public interface MsgType {
	/** 文本消息 */
	String TEXT = "text";
	/** 图片消息 */
	String IMAGE = "image";
	/** 地理位置消息 */
	String LOCATION = "location";
	/** 链接消息 */
	String LINK = "link";
	/** 事件推送 */
	String EVENT = "event";
	/** 音乐消息 */
	String MUSIC = "music";
	/** 图文消息 */
	String NEWS = "news";
	/** 语音消息 */
	String VOICE = "voice";
	/** 视频消息 */
	String VIDEO = "video";
}
