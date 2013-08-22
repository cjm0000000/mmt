package lemon.weixin.bean.message;

import lemon.weixin.util.WXHelper;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Location message
 * @author lemon
 *
 */
@XStreamAlias("xml")
public class LocationMessage extends BasicMessage {
	/** Location_X */
	@XStreamAlias("Location_X")
	private double location_X;
	/** Location_Y */
	@XStreamAlias("Location_Y")
	private double location_Y;
	/** Scale */
	@XStreamAlias("Scale")
	private int Scale;
	/** Label */
	@XStreamAlias("Label")
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
		return Scale;
	}
	public void setScale(int scale) {
		Scale = scale;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = WXHelper.cDATA(label);
	}
}
