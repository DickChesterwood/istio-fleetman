package com.virtualpairprogrammers.telemetry.service;

import java.math.BigDecimal;

import org.gavaghan.geodesy.GeodeticCalculator;
import org.springframework.stereotype.Service;

@Service
public class VehicleTelemetryService
{
	private GeodeticCalculator geoCalc = new GeodeticCalculator();
	private static final BigDecimal MPS_TO_MPH_FACTOR = new BigDecimal("2.236936");

	// TODO: Design; use last 10 positions, produce an average speed across them (maybe just use distance from latest positon to 10 earlier?
	private BigDecimal calculateSpeedInMph(String vehicleName)
	{	
//		TreeSet<VehiclePosition> positions = positionDatabase.get(vehicleName);
//		if (positions.isEmpty()) return null;
//		
//		VehiclePosition posB = newPosition;
//		VehiclePosition posA = positions.first(); // confusing - this is actually the last report recorded
//		
//		long timeAinMillis = posA.getTimestamp().getTime();
//		long timeBinMillis = posB.getTimestamp().getTime();
//		long timeInMillis = timeBinMillis - timeAinMillis;
//		if (timeInMillis == 0) return new BigDecimal("0");
//		
//		BigDecimal timeInSeconds = new BigDecimal(timeInMillis / 1000.0);
//				
//		GlobalPosition pointA = new GlobalPosition(posA.getLat().doubleValue(), posA.getLongitude().doubleValue(), 0.0);
//		GlobalPosition pointB = new GlobalPosition(posB.getLat().doubleValue(), posB.getLongitude().doubleValue(), 0.0);
//	
//		double distance = geoCalc.calculateGeodeticCurve(Ellipsoid.WGS84, pointA, pointB).getEllipsoidalDistance(); // Distance between Point A and Point B
//		BigDecimal distanceInMetres = new BigDecimal (""+ distance);
//		
//		BigDecimal speedInMps = distanceInMetres.divide(timeInSeconds, RoundingMode.HALF_UP);
//		BigDecimal milesPerHour = speedInMps.multiply(MPS_TO_MPH_FACTOR);
//		return milesPerHour;
		return new BigDecimal("47.0");
	}

	
	public void updateData(VehiclePosition update)
	{
		// TODO
	}
	
	public BigDecimal getSpeedFor(String vehicleName)
	{
		return this.calculateSpeedInMph(vehicleName);
	}
}
