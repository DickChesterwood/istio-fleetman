package com.virtualpairprogrammers.staffmanagement.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExternalDriverMonitoringSystem {

	private Logger log = LoggerFactory.getLogger(ExternalDriverMonitoringSystem.class);

	@Autowired
	private DriverManagementMicroserviceCalls microservice;
	
	public void updateSpeedLogFor(String name, String speed) {
		try 
		{
			microservice.updateSpeedLogFor(name, speed);
		}
		catch (Exception e)
		{
			log.warn("Legacy driver monitoring system unavailable");
		}
	}
	
}