package com.github.cjm0000000.mmt.core.message.recv;

import com.github.cjm0000000.mmt.core.message.Message;
import com.github.cjm0000000.mmt.core.message.MsgType;
import com.github.cjm0000000.mmt.core.parser.annotations.MmtAlias;

/**
 * Location message
 * @author lemon
 * @version 1.0
 *
 */
@MmtAlias("xml")
public class LocationMessage extends Message {
	/** Location_X */
	@MmtAlias("Location_X")
	private double location_X;
	/** Location_Y */
	@MmtAlias("Location_Y")
	private double location_Y;
	/** Scale */
	@MmtAlias("Scale")
	private int scale;
	/** Label */
	@MmtAlias("Label")
	private String label;
	
	public LocationMessage(){
		super(MsgType.LOCATION);
	}
	public double getLocation_X() {
		return location_X;
	}
	public void setLocation_X(double location_X) {
		this.location_X = location_X;
	}
	public double getLocation_Y() {
		return location_Y;
	}
	public void setLocation_Y(double location_Y) {
		this.location_Y = location_Y;
	}
	public int getScale() {
		return scale;
	}
	public void setScale(int scale) {
		this.scale = scale;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
}
