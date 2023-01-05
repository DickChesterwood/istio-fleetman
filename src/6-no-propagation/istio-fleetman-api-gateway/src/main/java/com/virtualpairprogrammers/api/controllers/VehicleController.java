package com.virtualpairprogrammers.api.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.virtualpairprogrammers.api.domain.LatLong;
import com.virtualpairprogrammers.api.domain.VehiclePosition;
import com.virtualpairprogrammers.api.services.PositionTrackingExternalService;

@Controller
@RequestMapping("/")
public class VehicleController 
{	
	@Autowired
	private PositionTrackingExternalService externalService;

	@GetMapping("/")
	@ResponseBody
	/**
	 * This is just a test mapping so we can easily check the API gateway is standing.
	 * When running through the Angular Front end, can visit this URL at /api/
	 */
	public String apiTestUrl()
	{
		return "<p>Fleetman API Gateway at " + new Date() + "</p>";
	}

	@GetMapping("/history/{vehicleName}")
	@ResponseBody
	@CrossOrigin(origins = "*")
	public Collection<LatLong> getHistoryFor(@PathVariable("vehicleName") String vehicleName)
	{
		Collection<LatLong> results = new ArrayList<>();
		Collection<VehiclePosition> vehicles = externalService.getHistoryFor(vehicleName);
		for (VehiclePosition next: vehicles)
		{
			LatLong position = new LatLong(next.getLat(), next.getLng()); 
			results.add(position);
		}
		Collections.reverse((List<?>) results);
		return results;
	}
	
	@GetMapping("/vehicles/{vehicleName}")
	@ResponseBody
	@CrossOrigin(origins = "*")
	public VehiclePosition getLastReportFor(@PathVariable("vehicleName") String vehicleName)
	{
		return externalService.getLastReportFor(vehicleName);
	}	
	
	@GetMapping("/vehicles/driver/{vehicleName}")
	@ResponseBody
	@CrossOrigin(origins = "*")
	public String getDriverFor(@PathVariable("vehicleName") String vehicleName)
	{
		return externalService.getDriverFor(vehicleName);
	}		

	@GetMapping("/vehicles/")
	@ResponseBody
    @CrossOrigin(origins = "*")
    public Collection<VehiclePosition> getAllVehiclePositions()
    {
    	Collection<VehiclePosition> results = externalService.getAllPositions();
    	return results;
    }
}
