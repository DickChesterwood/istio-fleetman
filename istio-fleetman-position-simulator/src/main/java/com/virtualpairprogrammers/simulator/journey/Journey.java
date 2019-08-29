package com.virtualpairprogrammers.simulator.journey;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.virtualpairprogrammers.simulator.services.PositionTrackerExternalService;
import com.virtualpairprogrammers.simulator.services.VehicleBuilder;
import com.virtualpairprogrammers.simulator.services.VehiclePosition;

/**
 * A callable (so we can invoke in on a executor and join on it) that sends messages
 * to a queue periodically - representing the journey of a delivery vehicle.
 */
public class Journey implements Callable<Object> 
{
	@Autowired
	private PositionTrackerExternalService positionTracker;
	
	private List<String> positions;
	private String vehicleName;
	
	private static Logger log  = Logger.getLogger(Journey.class);

	public Journey(String vehicleName, List<String> positions) 
	{
		this.positions = Collections.unmodifiableList(positions);
		this.vehicleName = vehicleName;
	}

	@Override
	public Object call() throws InterruptedException  
	{
		while(true)
		{
			for (String nextReport: this.positions)
			{
				// To speed the vehicles up, we're going to drop some reports
				if (Math.random() < 0.5) continue;
				
				String[] data = nextReport.split("\"");
				String lat = data[1];
				String longitude = data[3];
	
				VehiclePosition report = new VehicleBuilder()
						                  .withName(vehicleName)
						                  .withLat(lat)
						                  .withLng(longitude)
						                  .withTimestamp(new java.util.Date())
						                  .build();
	
				positionTracker.sendReportToPositionTracker(report);
	
				// We have an element of randomness to help the queue be nicely 
				// distributed
				delay(Math.random() * 10000 + 2000);
			}
		}
	}

	private static void delay(double d) throws InterruptedException {
		log.debug("Sleeping for " + d + " millsecs");
		Thread.sleep((long) d);
	}


}
