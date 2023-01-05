package com.virtualpairprogrammers.staffmanagement.services;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.virtualpairprogrammers.staffmanagement.domain.StaffRecord;

@Service
public class StaffService
{
	@Autowired
	private ExternalPhotoService externalPhotoService;
	private Map<String, String> drivers = Stream.of(new String[][] {
		{"City Truck", "Pam Parry"},
	    {"Huddersfield Truck A", "Duke T. Dog"},
	    {"Huddersfield Truck B", "Denzil Tulser"},
	    {"London Riverside", "Herman Boyce"},
	    {"Village Truck", "June Snell"}}).collect(Collectors.toMap(data -> data[0], data -> data[1]));
	
	public StaffRecord getDriverDetailsFor(String vehicleName) 
	{
		String driverName = drivers.get(vehicleName);
		String staffPhoto = externalPhotoService.getStaffPhotoFor(driverName);
		return new StaffRecord(driverName, staffPhoto);
	}

}
