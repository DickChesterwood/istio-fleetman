package com.virtualpairprogrammers.staffmanagement.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(url="${photoservice.url}", name="fleetman-photo-service")
public interface PhotoServiceMicroserviceCalls 
{
	@RequestMapping(method=RequestMethod.GET, value="/photo/{driverName}", produces = "application/json")
	public String getStaffPhotoFor(@PathVariable("driverName") String driverName);
}
