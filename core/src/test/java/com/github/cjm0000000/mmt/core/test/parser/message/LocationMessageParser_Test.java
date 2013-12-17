package com.github.cjm0000000.mmt.core.test.parser.message;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;

import com.github.cjm0000000.mmt.core.SimpleMessageService;
import com.github.cjm0000000.mmt.core.message.Message;
import com.github.cjm0000000.mmt.core.message.recv.LocationMessage;
import com.github.cjm0000000.mmt.core.parser.MmtXMLParser;

/**
 * Unit test cases for location message parse
 * @author lemon
 * @version 2.0
 *
 */
public class LocationMessageParser_Test extends AbstractMsgParser {

	@Override
	protected void makeSpecNodesWithoutMsgId(StringBuilder sb, Message original) {
		LocationMessage msg = (LocationMessage) original;
		sb.append("<Location_X>" + msg.getLocation_X() + "</Location_X>");
		sb.append("<Location_Y>" + msg.getLocation_Y() + "</Location_Y>");
		sb.append("<Scale>" + msg.getScale() + "</Scale>");
		sb.append("<Label><![CDATA[" + msg.getLabel() + "]]></Label>");
	}

	@Override
	protected void verifySpecFieldsWithoutMsgId(Message after, Message before) {
		LocationMessage l_before = (LocationMessage) before;
		LocationMessage l_after = (LocationMessage) after;
		final double delta = 0.000001D;
		assertEquals(l_after.getLocation_X(), l_before.getLocation_X(), delta);
		assertEquals(l_after.getLocation_X(), l_before.getLocation_X(), delta);
		assertEquals(l_after.getScale(), l_before.getScale());
		assertEquals(l_after.getLabel(), l_before.getLabel());
	}

	@Override
	protected Message getMsgInstanceWithoutMsgId() {
		LocationMessage original = new LocationMessage();
		original.setLocation_X(30.278740D);
		original.setLocation_Y(120.145300D);
		original.setScale(18);
		original.setLabel("中国浙江省杭州市西湖区文三路电子信息街区马塍路26号 邮政编码: 310015");
		return original;
	}

	@Override
	protected SimpleMessageService fromXML(InputStream is) {
		return MmtXMLParser.fromXML(is, LocationMessage.class);
	}

}
