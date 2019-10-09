package com.virtualpairprogrammers.tracker.externalservices;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
		
	private Logger log = LoggerFactory.getLogger(ExternalVehicleTelemetryService.class);

	public void updateData(VehiclePosition vehicleName)
	{
		try
		{
			this.telemetryService.updateData(vehicleName);
		}
		catch (Exception e)
		{
			log.info("Telemetry service unavailable. Unable to update data for " + vehicleName);
		}
	}
	
	public BigDecimal getSpeedFor(String vehicleName) throws TelemetryServiceUnavailableException
	{
		try {
			return this.telemetryService.getSpeedFor(vehicleName);	
		}
		catch (Exception e)
		{
			log.info("Telemetry service unavailable. Cannot get speed for vehicle " + vehicleName);
			throw new TelemetryServiceUnavailableException();
		}
	}
}
