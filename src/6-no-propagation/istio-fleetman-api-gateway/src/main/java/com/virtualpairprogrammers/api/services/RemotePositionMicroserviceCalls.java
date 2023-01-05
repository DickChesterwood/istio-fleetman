package com.virtualpairprogrammers.api.services;

import java.util.Collection;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.virtualpairprogrammers.api.domain.VehiclePosition;

@FeignClient(url="${position-tracker-url}", name="fleetman-position-tracker")
public interface RemotePositionMicroserviceCalls 
{
	@RequestMapping(method=RequestMethod.GET, value="/vehicles/")
	public Collection<VehiclePosition> getAllLatestPositions();
	
	@RequestMapping(method=RequestMethod.GET, value="/history/{vehicleName}")
	public Collection<VehiclePosition> getHistoryFor(@PathVariable("vehicleName") String vehicleName);

	@RequestMapping(method=RequestMethod.GET, value="/vehicles/{vehicleName}")
	public VehiclePosition getLastReportFor(@PathVariable("vehicleName") String vehicleName);
}
