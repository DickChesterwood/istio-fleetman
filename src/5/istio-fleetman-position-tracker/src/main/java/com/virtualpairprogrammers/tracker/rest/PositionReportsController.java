package com.virtualpairprogrammers.tracker.rest;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.virtualpairprogrammers.tracker.data.Data;
import com.virtualpairprogrammers.tracker.domain.VehicleNotFoundException;
import com.virtualpairprogrammers.tracker.domain.VehiclePosition;

@RestController
public class PositionReportsController 
{
	@Autowired
	private Data data;

	@RequestMapping(method=RequestMethod.GET,value="/vehicles/{vehicleName}")
	public VehiclePosition getLatestReportForVehicle(@PathVariable String vehicleName) throws VehicleNotFoundException
	{
		VehiclePosition position = data.getLatestPositionFor(vehicleName);
		return position;
	}

	@RequestMapping(method=RequestMethod.GET, value="/history/{vehicleName}")
	public Collection<VehiclePosition> getEntireHistoryForVehicle(@PathVariable String vehicleName) throws VehicleNotFoundException
	{
		return this.data.getHistoryFor(vehicleName);
	}

	@RequestMapping(method=RequestMethod.GET, value="/vehicles/")
	public Collection<VehiclePosition> getUpdatedPositions(@RequestHeader Map<String, String> headers)
	{
		System.out.println("Position tracker called - here's all the headers....");
		
	    headers.forEach((key, value) -> {
	        System.out.println(String.format("Header '%s' = %s", key, value));
	    });

		Collection<VehiclePosition> results = data.getLatestPositionsOfAllVehicles();
		return results;
	}
}
