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

	// In this "TODO" version, just a placeholder image is returned
	private Map<String, String> photos = Stream.of(new String[][] {
		{"Pam Parry","https://rac-istio-course-images.s3.amazonaws.com/placeholder.png"},
	    {"Duke T. Dog","https://rac-istio-course-images.s3.amazonaws.com/placeholder.png"},
	    {"Denzil Tulser","https://rac-istio-course-images.s3.amazonaws.com/placeholder.png"},
	    {"Herman Boyce","https://rac-istio-course-images.s3.amazonaws.com/placeholder.png"},
	    {"June Snell","https://rac-istio-course-images.s3.amazonaws.com/placeholder.png"}}).collect(Collectors.toMap(data -> data[0], data -> data[1]));
	
	
	public StaffRecord getDriverDetailsFor(String vehicleName) 
	{
		String driverName = drivers.get(vehicleName);
		String staffPhoto = photos.get(driverName);
		return new StaffRecord(driverName, staffPhoto);
	}

}
