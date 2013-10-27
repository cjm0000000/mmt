package lemon.shared.message.metadata.specific.yixin;

import lemon.shared.message.metadata.MsgType;
import lemon.shared.message.metadata.VideoMessage;
import lemon.shared.toolkit.xstream.annotations.XStreamProcessCDATA;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * video message
 * 
 * @author lemon
 * @version 1.0
 * 
 */
@XStreamAlias("xml")
@XStreamProcessCDATA
public class YXVideoMessage extends MediaMessage implements VideoMessage {
	public YXVideoMessage() {
		super(MsgType.VIDEO, "video/mp4");
	}

}
