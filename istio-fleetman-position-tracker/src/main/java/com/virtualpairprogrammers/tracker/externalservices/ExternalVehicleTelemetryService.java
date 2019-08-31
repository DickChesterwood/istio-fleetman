package com.virtualpairprogrammers.tracker.externalservices;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.virtualpairprogrammers.tracker.domain.VehiclePosition;

/**
 * This external service is capable of providing telemetry for Vehicles.
 */
@Service
public class ExternalVehicleTelemetryService 
{
	@Autowired
	private TelemetryRestService telemetryService;

	public void updateData(VehiclePosition vehicleName)
	{
		this.telemetryService.updateData(vehicleName);
	}
	
	public BigDecimal getSpeedFor(String vehicleName)
	{
		return this.telemetryService.getSpeedFor(vehicleName);
	}
}
