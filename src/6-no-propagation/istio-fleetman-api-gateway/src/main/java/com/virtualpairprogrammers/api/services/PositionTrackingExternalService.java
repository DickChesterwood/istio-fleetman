package com.virtualpairprogrammers.api.services;

import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.virtualpairprogrammers.api.domain.VehiclePosition;

@Service 
public class PositionTrackingExternalService 
{
	@Autowired
	private RemotePositionMicroserviceCalls remoteService;
	
	@Autowired
	private RemoteStaffMicroserviceCalls remoteStaffService;
	
	Logger logger = LoggerFactory.getLogger(PositionTrackingExternalService.class);
	
	public Collection<VehiclePosition> getHistoryFor(String vehicleName) {
		return remoteService.getHistoryFor(vehicleName);
	}

	public VehiclePosition getLastReportFor(String vehicleName) {
		return remoteService.getLastReportFor(vehicleName);
	}

	public String getDriverFor(String vehicleName) {
		String response = remoteStaffService.getDriverFor(vehicleName);
		return response;
	}

	public Collection<VehiclePosition> getAllPositions()
	{
		try {
			return remoteService.getAllLatestPositions();
		}
		catch (feign.RetryableException e)
		{ 
			logger.info("Unable to contact the position tracker. Most likely this is because that microservice hasn't yet started. This is only an error if the position tracker is running and healthy. Wait a few minutes before panicking!");
			return new ArrayList<VehiclePosition>();
		}
	}
		
}
