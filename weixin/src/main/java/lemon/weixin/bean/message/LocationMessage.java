package lemon.weixin.bean.message;

import lemon.weixin.xstream.annotations.XStreamCDATA;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Location message
 * @author lemon
 *
 */
@XStreamAlias("xml")
public class LocationMessage extends WeiXinMessage {
	/** Location_X */
	@XStreamAlias("Location_X")
	private double location_X;
	/** Location_Y */
	@XStreamAlias("Location_Y")
	private double location_Y;
	/** Scale */
	@XStreamAlias("Scale")
	private int scale;
	/** Label */
	@XStreamAlias("Label")
	@XStreamCDATA
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
