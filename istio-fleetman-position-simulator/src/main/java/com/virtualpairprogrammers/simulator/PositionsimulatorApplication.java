package com.virtualpairprogrammers.simulator;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

import com.virtualpairprogrammers.simulator.journey.JourneySimulator;
import com.virtualpairprogrammers.simulator.services.PositionTrackerExternalService;
import com.virtualpairprogrammers.simulator.services.RemotePositionMicroserviceCalls;
import com.virtualpairprogrammers.simulator.services.VehicleBuilder;
import com.virtualpairprogrammers.simulator.services.VehiclePosition;

/**
 * Written for the Microservices course, this is a toy application which simulates the progress
 * of vehicles on a delivery route. The program reads from one or more text files containing
 * a list of lat/long positions (these can be created from .gpx files or similar).
 * 
 * Messages are then sent on to a queue (currently hardcoded as positionQueue - to be fixed on 
 * the course!)
 * 
 * Intended for use on the training videos, questions to contact@virtualpairprogrammers.com
 * 
 * @author Richard Chesterwood
 */
@SpringBootApplication
@EnableFeignClients
public class PositionsimulatorApplication {

	public static void main(String[] args) throws IOException, InterruptedException 
	{
		ConfigurableApplicationContext ctx = SpringApplication.run(PositionsimulatorApplication.class);
		
		final JourneySimulator simulator = ctx.getBean(JourneySimulator.class);

		Thread mainThread = new Thread(simulator);
		mainThread.start();
	}

}

