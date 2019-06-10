package com.virtualpairprogrammers.tracker.messaging;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.virtualpairprogrammers.tracker.domain.VehicleBuilder;
import com.virtualpairprogrammers.tracker.domain.VehiclePosition;

/**
 * Whilst running standalone, we send some random chaotic vehicle positions
 * to the embedded queue.
 * @author Richard Chesterwood
 *
 */
@Profile({"standalone"})
@Component
public class LocalDevelopmentMessageSender 
{
	private static final String[] testVehicleNames = { "Test Vehicle 1", "Test Vehicle 2", "Test Vehicle 3", "Test Vehicle 4", "Test Vehicle 5"};
	private static final BigDecimal startLat = new BigDecimal("53.383882");
	private static final BigDecimal startLng = new BigDecimal("-1.483979");
	private DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
	private VehiclePosition[] lastPositions;
	
	@Autowired
	private JmsMessagingTemplate template;
	
	@Value("${fleetman.position.queue}")
	private String destination;
	
	@PostConstruct
	public void init() {
		lastPositions = new VehiclePosition[testVehicleNames.length];
		for (int i=0; i<testVehicleNames.length; i++)
		{
			String testVehicleName = testVehicleNames[i];
			VehiclePosition testVehicle =new VehicleBuilder().withName(testVehicleName)
                    										.withLat(startLat)
                    										.withLng(startLng)
                    										.withTimestamp(new java.util.Date()).build(); 
			lastPositions[i]=testVehicle;
			sendMessageToEmbeddedQueue(testVehicle);
		}
	}
	
	@Scheduled(fixedRate=100)
	public void sendPeriodicVehcileUpdates()
	{
		double randomChangeX = (Math.random() - 0.5) / 10000;
		double randomChangeY = (Math.random() - 0.5) / 10000;

		int randomVehicleIndex = (int)(testVehicleNames.length * Math.random());
		VehiclePosition lastPosition = lastPositions[randomVehicleIndex]; 
		
		BigDecimal newLat = lastPosition.getLat().add(new BigDecimal("" + randomChangeX));
		BigDecimal newLng = lastPosition.getLongitude().add(new BigDecimal("" + randomChangeY));
		
		VehiclePosition newPosition = new VehicleBuilder().withName(lastPosition.getName())
				                                         .withLat(newLat)
				                                         .withLng(newLng)
				                                         .withTimestamp(new java.util.Date()).build();
		lastPositions[randomVehicleIndex] = newPosition;
		sendMessageToEmbeddedQueue(lastPosition);
	}

	private void sendMessageToEmbeddedQueue(VehiclePosition position) {
		Map<String, String> mapMessage = new HashMap<>();
		mapMessage.put("vehicle", position.getName());
		mapMessage.put("lat", position.getLat().toString());
		mapMessage.put("long", position.getLongitude().toString());
		mapMessage.put("time", format.format(position.getTimestamp()));
		template.convertAndSend(destination, mapMessage);
	}
}
