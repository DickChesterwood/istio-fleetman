package com.virtualpairprogrammers.simulator.journey;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.virtualpairprogrammers.simulator.PositionsimulatorApplication;
import com.virtualpairprogrammers.simulator.services.PositionTrackerExternalService;
import com.virtualpairprogrammers.simulator.services.VehicleBuilder;
import com.virtualpairprogrammers.simulator.services.VehiclePosition;
import com.virtualpairprogrammers.simulator.utils.VehicleNameUtils;

/**
 * Invoked periodically by Quartz, a random position update is selected and transmitted.
 */
@Component

public class JourneySimulator {

	@Autowired
	private PositionTrackerExternalService positionTracker;

	private Map<String, Queue<String>> reports = new HashMap<>();
	private List<String> vehicleNames = new ArrayList<>();
	
	/**
	 * Read the data from the resources directory - should work for an executable Jar as
	 * well as through direct execution
	 */
	@PostConstruct
	private void setUpData() 
	{
		PathMatchingResourcePatternResolver path = new PathMatchingResourcePatternResolver();
		try
		{
			for (Resource nextFile : path.getResources("tracks/*"))
			{
				URL resource = nextFile.getURL();
				File f = new File(resource.getFile()); 
				String vehicleName = VehicleNameUtils.prettifyName(f.getName());				
				vehicleNames.add(vehicleName);
				populateReportQueueForVehicle(vehicleName);
			}
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
		positionTracker.clearHistories();
	}
	
	private void populateReportQueueForVehicle(String vehicleName) {
		InputStream is = PositionsimulatorApplication.class.getResourceAsStream("/tracks/" + VehicleNameUtils.uglifyName(vehicleName));
		try (Scanner sc = new Scanner(is))
		{
			Queue<String> thisVehicleReports = new LinkedBlockingQueue<>();
			while (sc.hasNextLine())
			{
				String nextReport = sc.nextLine();
				thisVehicleReports.add(nextReport);
			}
			reports.put(vehicleName,thisVehicleReports);
		}
	}	
	

	@Scheduled(fixedDelay=100)
	public void randomPositionUpdate()
	{
		// Random jitter. Sometimes we'll do nothing
		if (Math.random() < 0.9) return;
				
		// Choose random vehicle
		int position = (int)(Math.random() * vehicleNames.size());
		String chosenVehicleName = vehicleNames.get(position);
		
		// Grab next report for this vehicle
		// To smooth out fluctuations, we'll miss out lots of reports
		int randomReportDrop = (int)(Math.random() * 10);
		String nextReport = null;
		for (int i=0; i<=randomReportDrop; i++)
		{
			nextReport = reports.get(chosenVehicleName).poll();
			if (nextReport == null)
			{
				System.out.println("Journey over for " + chosenVehicleName + ". Restarting route");
				populateReportQueueForVehicle(chosenVehicleName);
				nextReport = reports.get(chosenVehicleName).poll();
			}
		}
		
		VehiclePosition report = getVehicleDataFromRawString(chosenVehicleName, nextReport);

		positionTracker.sendReportToPositionTracker(report);
	}

	private VehiclePosition getVehicleDataFromRawString(String chosenVehicleName, String nextReport) {
		String[] data = nextReport.split("\"");
		String lat = data[1];
		String lng = data[3];

		VehiclePosition report = new VehicleBuilder()
				                  .withName(chosenVehicleName)
				                  .withLat(lat)
				                  .withLng(lng)
				                  .withTimestamp(new java.util.Date())
				                  .build();
		return report;
	}
}
