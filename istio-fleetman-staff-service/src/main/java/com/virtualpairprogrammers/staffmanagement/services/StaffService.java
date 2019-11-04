package com.virtualpairprogrammers.staffmanagement.services;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.virtualpairprogrammers.staffmanagement.domain.StaffRecord;

@Service
public class StaffService
{
	private Map<String, String> drivers = Stream.of(new String[][] {
		{"City Truck", "Pam Parry"},
	    {"Huddersfield Truck A", "Duke T. Dog"},
	    {"Huddersfield Truck B", "Denzil Tulser"},
	    {"London Riverside", "Herman Boyce"},
	    {"Village Truck", "June Snell"}}).collect(Collectors.toMap(data -> data[0], data -> data[1]));
	
	private static final String PLACEHOLDER="https://avatars0.githubusercontent.com/u/16841947?s=460&v=4";
	
	public StaffRecord getDriverDetailsFor(String vehicleName) 
	{
		throw new RuntimeException("Low on resources");
//		String driverName = drivers.get(vehicleName);
//		String staffPhoto = PLACEHOLDER;
//		return new StaffRecord(driverName, staffPhoto);
	}

}
