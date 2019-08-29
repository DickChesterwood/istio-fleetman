package com.virtualpairprogrammers.simulator.journey;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
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
 * Invoked periodcally by Quartz, a random position update is selected and transmitted.
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
	private Map<String, Queue<String>> setUpData() 
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
				InputStream is = PositionsimulatorApplication.class.getResourceAsStream("/tracks/" + f.getName());
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
			return Collections.unmodifiableMap(reports);
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}
	
    @Scheduled(fixedRate=1000)
	public void randomPositionUpdate()
	{
    	System.out.println("Now sending a message...");
		// Choose random vehicle
		int position = (int)(Math.random() * vehicleNames.size());
		String chosenVehicleName = vehicleNames.get(position);
		
		// Grab next report for this vehicle
		// TODO: handle end of journey with a refresh (refactor the setupdata method above...)
		String nextReport = reports.get(chosenVehicleName).poll();
		
		// TODO refactor this
		String[] data = nextReport.split("\"");
		String lat = data[1];
		String longitude = data[3];

		VehiclePosition report = new VehicleBuilder()
				                  .withName(chosenVehicleName)
				                  .withLat(lat)
				                  .withLng(longitude)
				                  .withTimestamp(new java.util.Date())
				                  .build();

		positionTracker.sendReportToPositionTracker(report);
	}
}
