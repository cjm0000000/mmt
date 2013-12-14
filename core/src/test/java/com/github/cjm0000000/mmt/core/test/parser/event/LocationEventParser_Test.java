package com.github.cjm0000000.mmt.core.test.parser.event;

import static org.junit.Assert.assertEquals;

import com.github.cjm0000000.mmt.core.EventType;
import com.github.cjm0000000.mmt.core.event.LocationEvent;
import com.github.cjm0000000.mmt.core.event.SimpleEvent;

/**
 * Unit test case for location event
 * @author lemon
 * @version 2.0
 *
 */
public class LocationEventParser_Test extends AbstractEventParser {

	@Override
	protected void makeSpecNodesWithoutEventType(StringBuilder sb,
			SimpleEvent original) {
		LocationEvent event = (LocationEvent) original;
		sb.append("<Latitude>" + event.getLatitude() + "</Latitude>");
		sb.append("<Longitude>" + event.getLongitude() + "</Longitude>");
		sb.append("<Precision>" + event.getPrecision() + "</Precision>");
	}

	@Override
	protected SimpleEvent getEventInstance() {
		LocationEvent event = new LocationEvent();
		event.setEventType(EventType.LOCATION);
		event.setLatitude(23.137466D);
		event.setLongitude(113.352425D);
		event.setPrecision(119.385040D);
		return event;
	}

	@Override
	protected void verifySpecFieldsWithoutEventType(SimpleEvent after,
			SimpleEvent before) {
		LocationEvent l_before = (LocationEvent) before;
		LocationEvent l_after = (LocationEvent) after;
		final double delta = 0.000001D;
		assertEquals(l_after.getLatitude(), l_before.getLatitude(), delta);
		assertEquals(l_after.getLongitude(), l_before.getLongitude(), delta);
		assertEquals(l_after.getPrecision(), l_before.getPrecision(), delta);
	}

}
