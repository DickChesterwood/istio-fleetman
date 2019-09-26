package com.virtualpairprogrammers.telemetry.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;

import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GlobalPosition;
import org.springframework.stereotype.Service;

/**
 * This is a dirty implementation of speed. We wanted a smoothed value, to do so we store just the last 10 reports
 * for each vehicle. When calculating speed, we use the latest report and the oldest report that we have, and use those
 * two points. A Deque (Double Ended Queue) is used so we can get easy access to the latest and oldest.
 * 
 * Deliberately not production quality, and we may use inefficiencies in here for Istio demos.
 */
@Service
public class VehicleTelemetryService
{
	private GeodeticCalculator geoCalc = new GeodeticCalculator();
	private static final BigDecimal MPS_TO_MPH_FACTOR = new BigDecimal("2.236936");
	private static final int REPORTS_TO_SMOOTH = 10;
	private Map<String, Deque<VehiclePosition>> vehicleCache;

	public VehicleTelemetryService()
	{
		this.vehicleCache = new HashMap<>(); 
	}
	
	private BigDecimal calculateSpeedInMph(String vehicleName)
	{
		Deque<VehiclePosition> positions = vehicleCache.get(vehicleName);
		if (positions == null || positions.size() < 2) return null;
		
		VehiclePosition posA = positions.getFirst(); 
		VehiclePosition posB = positions.getLast();
		
		long timeAinMillis = posA.getTimestamp().getTime();
		long timeBinMillis = posB.getTimestamp().getTime();
		long timeInMillis = timeBinMillis - timeAinMillis;
		if (timeInMillis == 0) return new BigDecimal("0");
		
		BigDecimal timeInSeconds = new BigDecimal(timeInMillis / 1000.0);
				
		GlobalPosition pointA = new GlobalPosition(posA.getLat().doubleValue(), posA.getLng().doubleValue(), 0.0);
		GlobalPosition pointB = new GlobalPosition(posB.getLat().doubleValue(), posB.getLng().doubleValue(), 0.0);
	
		double distance = geoCalc.calculateGeodeticCurve(Ellipsoid.WGS84, pointA, pointB).getEllipsoidalDistance(); // Distance between Point A and Point B
		BigDecimal distanceInMetres = new BigDecimal (""+ distance);
		
		BigDecimal speedInMps = distanceInMetres.divide(timeInSeconds, RoundingMode.HALF_UP);
		BigDecimal milesPerHour = speedInMps.multiply(MPS_TO_MPH_FACTOR);
		
		// The data we're using has come from some odd sources and can have some odd values in it; so it gets fudged!
		if (milesPerHour.doubleValue() > 70)
		{
			milesPerHour = new BigDecimal(((Math.random() * 80) + 30));
		}
		
		return milesPerHour;
	}

	
	public void updateData(VehiclePosition update)
	{
		Deque<VehiclePosition> vehicleReports = this.vehicleCache.get(update.getName());
		if (vehicleReports == null)
		{
			vehicleReports = new LinkedBlockingDeque<>(REPORTS_TO_SMOOTH);
			this.vehicleCache.put(update.getName(), vehicleReports);
		}
		try
		{
			vehicleReports.add(update);
		}
		catch (IllegalStateException e)
		{
			vehicleReports.removeFirst();
			vehicleReports.add(update);
		}
	}
	
	public BigDecimal getSpeedFor(String vehicleName)
	{
		return this.calculateSpeedInMph(vehicleName);
	}
}
