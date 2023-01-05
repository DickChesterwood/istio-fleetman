package com.virtualpairprogrammers.tracker.externalservices;

import java.math.BigDecimal;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.virtualpairprogrammers.tracker.domain.VehiclePosition;

@FeignClient(url="${telemetry-url}", name="fleetman-vehicle-telemetry")
public interface TelemetryRestService 
{
	@RequestMapping(method=RequestMethod.POST, value="/vehicles/")
	public void updateData(VehiclePosition data);

	@RequestMapping(method=RequestMethod.GET, value="/vehicles/{vehicleName}")
	public BigDecimal getSpeedFor(@PathVariable("vehicleName") String vehicleName);
}
