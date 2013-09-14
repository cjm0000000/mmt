package lemon.yixin.bean.message;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Music message
 * @author lemon
 * @version 1.0
 *
 */
@XStreamAlias("xml")
public class MusicMessage extends MediaMessage {

	private String desc;
	
	public MusicMessage(){
		super(MsgType.MUSIC, "audio/mpeg");
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
