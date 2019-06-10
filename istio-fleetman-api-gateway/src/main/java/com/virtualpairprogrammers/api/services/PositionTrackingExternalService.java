package com.virtualpairprogrammers.api.services;

import java.text.SimpleDateFormat;
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
	
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
	
	public Collection<VehiclePosition> getAllUpdatedPositionsSince(Date since)
	{
		String date = formatter.format(since);
		Collection<VehiclePosition> results = remoteService.getAllLatestPositionsSince(date);
		return results;
	}
	
	public Collection<VehiclePosition> getHistoryFor(String vehicleName) {
		return remoteService.getHistoryFor(vehicleName);
	}
		
}
