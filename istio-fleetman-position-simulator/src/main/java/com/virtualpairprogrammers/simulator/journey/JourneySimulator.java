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
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.virtualpairprogrammers.simulator.PositionsimulatorApplication;
import com.virtualpairprogrammers.simulator.utils.VehicleNameUtils;

@Component
public class JourneySimulator implements Runnable {

	@Autowired
	private JmsTemplate template;
	
	@Value("${fleetman.position.queue}")
	private String queueName;

	private ExecutorService threadPool;

	public void run() 
	{
		try 
		{
			this.runVehicleSimulation();
		} 
		catch (InterruptedException e) 
		{
			Thread.currentThread().interrupt();
		}
	}

	/**
	 * For each vehicle, a thread is started which simulates a journey for that vehicle. 
	 * When all vehicles have completed, we start all over again. 
	 * @throws InterruptedException 
	 */
	public void runVehicleSimulation() throws InterruptedException 
	{
		Map<String, List<String>> reports = setUpData();
		threadPool = Executors.newCachedThreadPool();		
		boolean stillRunning = true;
		while (stillRunning)
		{
			List<Callable<Object>> calls = new ArrayList<>();

			for (String vehicleName : reports.keySet())
			{
				// kick off a message sending thread for this vehicle.
				calls.add(new Journey(vehicleName, reports.get(vehicleName), template, queueName));
			}
			
			threadPool.invokeAll(calls);
			if (threadPool.isShutdown())
			{
				stillRunning = false;
			}
		}
	}

	/**
	 * Read the data from the resources directory - should work for an executable Jar as
	 * well as through direct execution
	 */
	private Map<String, List<String>> setUpData() 
	{
		Map<String, List<String>> reports = new HashMap<>();
		PathMatchingResourcePatternResolver path = new PathMatchingResourcePatternResolver();

		try
		{
			for (Resource nextFile : path.getResources("tracks/*"))
			{
				URL resource = nextFile.getURL();
				File f = new File(resource.getFile()); 
				String vehicleName = VehicleNameUtils.prettifyName(f.getName());
				InputStream is = PositionsimulatorApplication.class.getResourceAsStream("/tracks/" + f.getName());
				try (Scanner sc = new Scanner(is))
				{
					List<String> thisVehicleReports = new ArrayList<>();
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

	public void finish() 
	{
		threadPool.shutdownNow();
	}


}
