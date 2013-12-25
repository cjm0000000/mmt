package com.github.cjm0000000.mmt.core.test.parser.message.recv;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;

import com.github.cjm0000000.mmt.core.message.BaseMessage;
import com.github.cjm0000000.mmt.core.message.recv.ImageMessage;
import com.github.cjm0000000.mmt.core.message.recv.SimpleRecvMessage;
import com.github.cjm0000000.mmt.core.parser.MmtXMLParser;

/**
 * Unit test case for image message parse
 * @author lemon
 * @version 2.0
 *
 */
public final class ImageMessageParser_Test extends AbstractMsgParser {

	@Override
	protected void makeSpecNodesWithoutMsgId(StringBuilder sb, SimpleRecvMessage original) {
		ImageMessage msg = (ImageMessage) original;
		sb.append("<PicUrl><![CDATA["+msg.getPicUrl()+"]]></PicUrl>");
		sb.append("<MediaId><![CDATA["+msg.getMediaId()+"]]></MediaId>");
	}

	@Override
	protected void verifySpecFieldsWithoutMsgId(SimpleRecvMessage after, SimpleRecvMessage before) {
		ImageMessage i_before = (ImageMessage) before;
		ImageMessage i_after = (ImageMessage) after;
		assertEquals(i_after.getPicUrl(), i_before.getPicUrl());
		assertEquals(i_after.getMediaId(), i_before.getMediaId());
	}

	@Override
	protected SimpleRecvMessage getMsgInstanceWithoutMsgId() {
		ImageMessage msg = new ImageMessage();
		msg.setPicUrl("http://mmbiz.qpic.cn/mmbiz/QXd6JDcZQ1kNscXWUKkI4ZuLcZQQZtPIicAOB2ic5iaXKzxWytwobOXQKjiaGYFO9aO2wCGJWLyuuyhicaUqictyOibNQ/0");
		msg.setMediaId("Okq_aCQbG0iFQ6b89SAB2pP3-1jqAHehh2QSiPihKB6-Uwp6VlB24KbKsmX1sqVl");
		return msg;
	}

	@Override
	protected BaseMessage fromXML(InputStream is) {
		return MmtXMLParser.fromXML(is);
	}

}
