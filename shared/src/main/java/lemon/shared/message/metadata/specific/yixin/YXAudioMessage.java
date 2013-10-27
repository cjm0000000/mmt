package lemon.shared.message.metadata.specific.yixin;

import lemon.shared.message.metadata.AudioMessage;
import lemon.shared.message.metadata.MsgType;
import lemon.shared.toolkit.xstream.annotations.XStreamProcessCDATA;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Voice message
 * 
 * @author lemon
 * @version 1.0
 * 
 */
@XStreamAlias("xml")
@XStreamProcessCDATA
public class YXAudioMessage extends MediaMessage implements AudioMessage {
	public YXAudioMessage() {
		super(MsgType.AUDIO, "audio/aac");
	}

}
