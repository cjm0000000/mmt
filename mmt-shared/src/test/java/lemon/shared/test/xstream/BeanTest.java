package lemon.shared.test.xstream;

import lemon.shared.toolkit.xstream.XStreamHelper;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.github.cjm0000000.shared.test.AbstractTester;
import com.thoughtworks.xstream.XStream;

public class BeanTest extends AbstractTester {
	private XStream xStream;
	@Before
	public void init() {
		xStream = XStreamHelper.createXMLXStream();
	}
	
	@Test
	public void test(){
		xStream.processAnnotations(Bean.class);
		Bean b = new Bean("lemon","Hangzhou");
		//System.out.println(xStream.toXML(b));
		b = (Bean) xStream.fromXML(xStream.toXML(b));
		assertEquals(b.getName(), "lemon");
	}
}
