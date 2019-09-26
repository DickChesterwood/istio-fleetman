package com.virtualpairprogrammers.api.services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.virtualpairprogrammers.api.domain.VehiclePosition;

@Service 
public class PositionTrackingExternalService 
{
	@Autowired
	private RemotePositionMicroserviceCalls remoteService;
	
	public Collection<VehiclePosition> getAllUpdatedPositionsSince(Date since)
	{
		Collection<VehiclePosition> results = remoteService.getAllLatestPositionsSince(since);
		return results;
	}
	
	public Collection<VehiclePosition> getHistoryFor(String vehicleName) {
		return remoteService.getHistoryFor(vehicleName);
	}

	public VehiclePosition getLastReportFor(String vehicleName) {
		return remoteService.getLastReportFor(vehicleName);
	}
		
}
