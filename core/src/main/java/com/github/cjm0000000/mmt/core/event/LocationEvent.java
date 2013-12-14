package com.github.cjm0000000.mmt.core.event;

import com.github.cjm0000000.mmt.core.parser.annotations.MmtAlias;

/**
 * location event
 * @author lemon
 * @version 2.0
 *
 */
@MmtAlias("xml")
public class LocationEvent extends SimpleEvent {
	@MmtAlias("Latitude")
	private double latitude;
	@MmtAlias("Longitude")
	private double longitude;
	@MmtAlias("Precision")
	private double precision;
	
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getPrecision() {
		return precision;
	}
	public void setPrecision(double precision) {
		this.precision = precision;
	}
	
}
