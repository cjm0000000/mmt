package com.github.cjm0000000.mmt.core.message.recv.yixin;

import com.github.cjm0000000.mmt.core.message.MsgType;
import com.github.cjm0000000.mmt.core.parser.annotations.MmtAlias;

/**
 * Music message
 * @author lemon
 * @version 1.0
 *
 */
@MmtAlias("xml")
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
