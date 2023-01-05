package com.virtualpairprogrammers.tracker.domain;

import java.math.BigDecimal;
import java.util.Date;

public class VehicleBuilder 
{
	private String name;
	private BigDecimal lat;
	private BigDecimal lng;
	private Date timestamp;
	private BigDecimal speed;
	
	public VehicleBuilder withTimestamp(Date date)
	{
		this.timestamp = date;
		return this;
	}
	
	public VehicleBuilder withName(String name)
	{
		this.name = name;
		return this;
	}
	
	public VehicleBuilder withLat(BigDecimal lat)
	{
		this.lat = lat;
		return this;
	}
	
	public VehicleBuilder withLng(BigDecimal lng)
	{
		this.lng = lng;
		return this;
	}
	
	public VehicleBuilder withSpeed(BigDecimal speed) {
		this.speed = speed;
		return this;
	}
	
	public VehiclePosition build()
	{
		return new VehiclePosition(name, lat, lng, timestamp, speed);
	}

	public VehicleBuilder withLat(String lat) {
		return this.withLat(new BigDecimal(lat));
	}

	public VehicleBuilder withLng(String lng) {
		return this.withLng(new BigDecimal(lng));
	}

	public VehicleBuilder withVehiclePostion(VehiclePosition data) {
		this.name = data.getName();
		this.lat = data.getLat();
		this.lng = data.getLng();
		this.timestamp = data.getTimestamp();
		this.speed = data.getSpeed();

		return this;
	}

}
