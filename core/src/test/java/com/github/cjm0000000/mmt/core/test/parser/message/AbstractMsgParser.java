package com.github.cjm0000000.mmt.core.test.parser.message;

import static org.junit.Assert.assertEquals;

import com.github.cjm0000000.mmt.core.SimpleMessageService;
import com.github.cjm0000000.mmt.core.message.Message;
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
			Message original);
	
	/**
	 * Get message instance without message id
	 * @return
	 */
	protected abstract Message getMsgInstanceWithoutMsgId();
	
	/**
	 * Verify specific fields without message id
	 * @param after
	 * @param before
	 */
	protected abstract void verifySpecFieldsWithoutMsgId(Message after, Message before);

	@Override
	protected final void makeSpecNodes(StringBuilder sb,
			SimpleMessageService original) {
		Message msg = (Message) original;
		sb.append("<MsgId>" + msg.getMsgId() + "</MsgId>");
		makeSpecNodesWithoutMsgId(sb, msg);
	}

	@Override
	protected final void verifySpecFields(SimpleMessageService after, SimpleMessageService before) {
		Message m_before = (Message) before;
		Message m_after = (Message) after;
		assertEquals(m_after.getMsgId(), m_before.getMsgId());
		verifySpecFieldsWithoutMsgId(m_after, m_before);
	}

	@Override
	protected final SimpleMessageService getMsgInstance() {
		Message original = getMsgInstanceWithoutMsgId();
		original.setMsgId(5939685126051988740L);
		return original;
	}

}
