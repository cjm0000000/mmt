package com.github.cjm0000000.mmt.core.test.parser.weixin;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.github.cjm0000000.mmt.core.ServiceType;
import com.github.cjm0000000.mmt.core.message.Message;
import com.github.cjm0000000.mmt.core.message.recv.weixin.VideoMessage;
import com.github.cjm0000000.mmt.core.test.parser.AbstractMmtXMLParser;

/**
 * Unit test case for video message parse
 * @author lemon
 * @version 2.0
 *
 */
public final class VideoMessageParser_Test extends AbstractMmtXMLParser {
	
	@Test
	public void run(){
		parser(ServiceType.WEIXIN);
	}

	@Override
	protected void generateSpecXMLNodes(StringBuilder sb, Message original) {
		VideoMessage msg = (VideoMessage) original;
		sb.append("<MediaId><![CDATA[" + msg.getMediaId()+"]]></MediaId>");
		sb.append("<ThumbMediaId><![CDATA[" + msg.getThumbMediaId()+"]]></ThumbMediaId>");
	}

	@Override
	protected void verifySpecFields(Message after, Message before) {
		VideoMessage v_before = (VideoMessage) before;
		VideoMessage v_after = (VideoMessage) after;
		assertEquals(v_after.getMediaId(), v_before.getMediaId());
		assertEquals(v_after.getThumbMediaId(), v_before.getThumbMediaId());
	}

	@Override
	protected Message getMsgInstance() {
		VideoMessage original = new VideoMessage();
		original.setMediaId("Okq_aCQbG0iFQ6b89SAB2pP3-1jqAHehh2QSiPihKB6-Uwp6VlB24KbKsmX1sqVl");
		original.setThumbMediaId("Okq_aCQbG0iFq1Q6b89SAB2pP3-1jqAHehh2QSiPihKB6-Uwp6VlB24KbKsmX1sqVl");
		return original;
	}

}
