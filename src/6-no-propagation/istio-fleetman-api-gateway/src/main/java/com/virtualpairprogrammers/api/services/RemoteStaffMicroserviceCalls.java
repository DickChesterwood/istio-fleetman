package com.virtualpairprogrammers.api.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(url="${staff-service-url}", name="fleetman-staff-service")
public interface RemoteStaffMicroserviceCalls 
{
	@RequestMapping(method=RequestMethod.GET, value="/driver/{vehicleName}", produces = "application/json")
	public String getDriverFor(@PathVariable("vehicleName") String vehicleName);
}
