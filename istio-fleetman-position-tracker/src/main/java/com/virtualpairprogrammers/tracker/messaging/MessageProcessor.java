package com.virtualpairprogrammers.tracker.messaging;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.virtualpairprogrammers.tracker.data.Data;
import com.virtualpairprogrammers.tracker.domain.VehicleBuilder;
import com.virtualpairprogrammers.tracker.domain.VehiclePosition;

@Component
public class MessageProcessor {
	
	@Autowired
	private Data data;
	
	@Value("${fleetman.position.queue}")
	private String queueName;
	
	@JmsListener(destination="${fleetman.position.queue}")
	public void processPositionMessageFromQueue(Map<String, String> incomingMessage ) throws ParseException 
	{
		Date convertedDatestamp = new java.util.Date();
		
		VehiclePosition newReport = new VehicleBuilder()
				                          .withName(incomingMessage.get("vehicle"))
				                          .withLat(new BigDecimal(incomingMessage.get("lat")))
				                          .withLng(new BigDecimal(incomingMessage.get("long")))
				                          .withTimestamp(convertedDatestamp)
				                          .build();
				                          
		data.updatePosition(newReport);
	}

}
