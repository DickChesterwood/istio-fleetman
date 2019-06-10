package com.virtualpairprogrammers.simulator.journey;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;
import org.springframework.jms.UncategorizedJmsException;
import org.springframework.jms.core.JmsTemplate;

/**
 * A callable (so we can invoke in on a executor and join on it) that sends messages
 * to a queue periodically - representing the journey of a delivery vehicle.
 */
public class Journey implements Callable<Object> 
{
	private List<String> positions;
	private String vehicleName;
	private JmsTemplate jmsTemplate;
	private String queueName;
    private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
	
	private static Logger log  = Logger.getLogger(Journey.class);

	public Journey(String vehicleName, List<String> positions, JmsTemplate jmsTemplate, String queueName) 
	{
		this.positions = Collections.unmodifiableList(positions);
		this.vehicleName = vehicleName;
		this.jmsTemplate = jmsTemplate;
		this.queueName = queueName;
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
	
				// Spring will convert a HashMap into a MapMessage using the default MessageConverter.
				HashMap<String,String> positionMessage = new HashMap<>();
				positionMessage.put("vehicle", vehicleName);
				positionMessage.put("lat", lat);
				positionMessage.put("long", longitude);
				positionMessage.put("time", formatter.format(new java.util.Date()));
	
				sendToQueue(positionMessage);
	
				// We have an element of randomness to help the queue be nicely 
				// distributed
				delay(Math.random() * 10000 + 2000);
			}
		}
	}

	/**
	 * Sends a message to the position queue - we've hardcoded this in at present - of course
	 * this needs to be fixed on the course!
	 * @param positionMessage
	 * @throws InterruptedException 
	 */
	private void sendToQueue(Map<String, String> positionMessage) throws InterruptedException {
		boolean messageNotSent = true;
		while (messageNotSent)
		{
			// broadcast this report
			try
			{
				jmsTemplate.convertAndSend(queueName,positionMessage);
				messageNotSent = false;
			}
			catch (UncategorizedJmsException e)
			{
				// we are going to assume that this is due to downtime - back off and go again
				log.warn("Queue unavailable - backing off 5000ms before retry");
				delay(5000);
			}
		}
	}

	private static void delay(double d) throws InterruptedException {
		log.debug("Sleeping for " + d + " millsecs");
		Thread.sleep((long) d);
	}


}
