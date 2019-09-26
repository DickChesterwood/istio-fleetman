package com.virtualpairprogrammers.tracker.rest;

import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

	private @Autowired HttpServletRequest request;

	@RequestMapping(method=RequestMethod.GET, value="/history/{vehicleName}")
	public Collection<VehiclePosition> getEntireHistoryForVehicle(@PathVariable String vehicleName) throws VehicleNotFoundException
	{
		// Peek at request headers...
		Enumeration<String> names = request.getHeaderNames();
		while (names.hasMoreElements())
		{
			String name = names.nextElement();
			String value = request.getHeader(name);
			System.out.println("Got the header: " + name + "," + value);
		}

		return this.data.getHistoryFor(vehicleName);
	}

	@RequestMapping(method=RequestMethod.GET, value="/vehicles/")
	public Collection<VehiclePosition> getUpdatedPositions(@RequestParam(value="since", required=false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date since)
	{
		Collection<VehiclePosition> results = data.getLatestPositionsOfAllVehiclesUpdatedSince(since);
		return results;
	}
}
