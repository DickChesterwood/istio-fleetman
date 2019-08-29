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
		System.out.println("sending...");
		remoteService.sendNewPositionReport(report);
		System.out.println("sent??");
	}
}

