package com.virtualpairprogrammers.telemetry.rest;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.virtualpairprogrammers.telemetry.service.VehiclePosition;
import com.virtualpairprogrammers.telemetry.service.VehicleTelemetryService;

@RestController
public class VehicleTelemetryRestController 
{
	@Autowired
	private VehicleTelemetryService service;
	
	@RequestMapping(method=RequestMethod.POST, value="/vehicles/")
	public void updateData(@RequestBody VehiclePosition data)
	{
		this.service.updateData(data);
	}

	@RequestMapping(method=RequestMethod.GET, value="/vehicles/{vehicleName}")
	public BigDecimal getSpeedFor(@PathVariable("vehicleName") String vehicleName)
	{
		return this.service.getSpeedFor(vehicleName);
	}
}
