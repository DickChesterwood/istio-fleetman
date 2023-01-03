package com.virtualpairprogrammers.tracker.externalservices;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name="staffService", url="${staff-service-url}")
public interface DriverMonitoringService {
	@RequestMapping(method=RequestMethod.POST, value="/driver/{vehicleName}/{speed}")
	public void updateSpeedDataFor(@PathVariable("vehicleName") String vehicleName, @PathVariable("speed") String speed); 
}
