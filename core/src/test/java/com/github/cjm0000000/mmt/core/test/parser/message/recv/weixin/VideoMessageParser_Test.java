package com.github.cjm0000000.mmt.core.test.parser.message.recv.weixin;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;

import org.junit.Test;

import com.github.cjm0000000.mmt.core.message.BaseMessage;
import com.github.cjm0000000.mmt.core.message.recv.SimpleRecvMessage;
import com.github.cjm0000000.mmt.core.message.recv.weixin.MediaMessage;
import com.github.cjm0000000.mmt.core.message.recv.weixin.VideoMessage;
import com.github.cjm0000000.mmt.core.parser.MmtXMLParser;
import com.github.cjm0000000.mmt.core.service.ServiceType;

/**
 * Unit test case for video message parse
 * @author lemon
 * @version 2.0
 *
 */
public final class VideoMessageParser_Test extends MediaMessageParser {
	
	@Test
	public void run(){
		parser(ServiceType.WEIXIN);
	}

	@Override
	protected void makeSpecNodesWithoutMediaId(StringBuilder sb,
			SimpleRecvMessage original) {
		VideoMessage msg = (VideoMessage) original;
		sb.append("<ThumbMediaId><![CDATA[" + msg.getThumbMediaId()+"]]></ThumbMediaId>");
	}

	@Override
	protected void verifySpecFieldsWithoutMediaId(SimpleRecvMessage after, SimpleRecvMessage before) {
		VideoMessage v_before = (VideoMessage) before;
		VideoMessage v_after = (VideoMessage) after;
		assertEquals(v_after.getThumbMediaId(), v_before.getThumbMediaId());
	}

	@Override
	protected MediaMessage getMsgInstanceWithoutMediaId() {
		VideoMessage original = new VideoMessage();
		original.setThumbMediaId("Okq_aCQbG0iFq1Q6b89SAB2pP3-1jqAHehh2QSiPihKB6-Uwp6VlB24KbKsmX1sqVl");
		return original;
	}

	@Override
	protected BaseMessage fromXML(InputStream is) {
		return MmtXMLParser.fromXML(is, VideoMessage.class);
	}

}
