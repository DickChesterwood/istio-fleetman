package com.virtualpairprogrammers.simulator.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(url="${position-tracker-url}", name="fleetman-position-tracker")
public interface RemotePositionMicroserviceCalls 
{
	@RequestMapping(method=RequestMethod.POST, value="/vehicles/")
	public void sendNewPositionReport(@RequestBody VehiclePosition report);

	@RequestMapping(method=RequestMethod.DELETE, value="/vehicles/")
	public void clearHistories();
}
