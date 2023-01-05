package com.virtualpairprogrammers.staffmanagement.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url="${driverMonitoring.url}", name="driver-monitoring-service")
public interface DriverManagementMicroserviceCalls {

	@RequestMapping(method=RequestMethod.POST, value="/driver/")
	void updateSpeedLogFor(@RequestParam("name") String name, @RequestBody String speed);
}
