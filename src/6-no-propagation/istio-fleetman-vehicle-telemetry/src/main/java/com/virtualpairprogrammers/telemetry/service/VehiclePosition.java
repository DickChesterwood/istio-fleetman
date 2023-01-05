package com.virtualpairprogrammers.telemetry.service;

import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonFormat;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class VehiclePosition implements Comparable<VehiclePosition>
{
	private String name;
	private BigDecimal lat;
	private BigDecimal lng;
	
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone="UTC")
	private Date timestamp;
	private BigDecimal speed;
	
	VehiclePosition() {}
	
	VehiclePosition(String name, BigDecimal lat, BigDecimal lng, Date timestamp, BigDecimal speed) {
		this.name = name;
		this.lat = lat;
		this.lng = lng;
		this.timestamp = timestamp;
		this.speed = speed;
	}

	@Override
	public int compareTo(VehiclePosition o) 
	{
		return o.timestamp.compareTo(this.timestamp);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((timestamp == null) ? 0 : timestamp.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VehiclePosition other = (VehiclePosition) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (timestamp == null) {
			if (other.timestamp != null)
				return false;
		} else if (!timestamp.equals(other.timestamp))
			return false;
		return true;
	}

	public String getName() {
		return this.name;
	}

	public BigDecimal getLat() {
		return this.lat;
	}

	public BigDecimal getLng() {
		return this.lng;
	}

	public Date getTimestamp() {
		return this.timestamp;
	}

	public BigDecimal getSpeed() {
		return this.speed;
	}

	@Override
	public String toString() {
		return "VehiclePosition [name=" + name + ", lat=" + lat + ", lng=" + lng + ", timestamp="
				+ timestamp + ", speed=" + speed + "]";
	}

}
