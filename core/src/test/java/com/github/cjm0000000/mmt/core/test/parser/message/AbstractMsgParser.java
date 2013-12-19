package com.github.cjm0000000.mmt.core.test.parser.message;

import static org.junit.Assert.assertEquals;

import com.github.cjm0000000.mmt.core.SimpleMessageService;
import com.github.cjm0000000.mmt.core.message.recv.SimpleRecvMessage;
import com.github.cjm0000000.mmt.core.test.parser.AbstractXMLParser;

/**
 * Message test template
 * @author lemon
 * @version 2.0
 *
 */
public abstract class AbstractMsgParser extends AbstractXMLParser {
	
	/**
	 * make specific nodes without message id
	 * @param sb
	 * @param original
	 */
	protected abstract void makeSpecNodesWithoutMsgId(StringBuilder sb,
			SimpleRecvMessage original);
	
	/**
	 * Get message instance without message id
	 * @return
	 */
	protected abstract SimpleRecvMessage getMsgInstanceWithoutMsgId();
	
	/**
	 * Verify specific fields without message id
	 * @param after
	 * @param before
	 */
	protected abstract void verifySpecFieldsWithoutMsgId(SimpleRecvMessage after, SimpleRecvMessage before);

	@Override
	protected final void makeSpecNodes(StringBuilder sb,
			SimpleMessageService original) {
		SimpleRecvMessage msg = (SimpleRecvMessage) original;
		sb.append("<MsgId>" + msg.getMsgId() + "</MsgId>");
		makeSpecNodesWithoutMsgId(sb, msg);
	}

	@Override
	protected final void verifySpecFields(SimpleMessageService after, SimpleMessageService before) {
		SimpleRecvMessage m_before = (SimpleRecvMessage) before;
		SimpleRecvMessage m_after = (SimpleRecvMessage) after;
		assertEquals(m_after.getMsgId(), m_before.getMsgId());
		verifySpecFieldsWithoutMsgId(m_after, m_before);
	}

	@Override
	protected final SimpleMessageService getMsgInstance() {
		SimpleRecvMessage original = getMsgInstanceWithoutMsgId();
		original.setMsgId(5939685126051988740L);
		return original;
	}

}
