package com.virtualpairprogrammers.simulator.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service 
public class PositionTrackerExternalService 
{
	@Autowired
	private RemotePositionMicroserviceCalls remoteService;
	
	public void sendReportToPositionTracker(VehiclePosition report)
	{
		remoteService.sendNewPositionReport(report);
	}

	public void clearHistories() 
	{
		remoteService.clearHistories();
	}
}

