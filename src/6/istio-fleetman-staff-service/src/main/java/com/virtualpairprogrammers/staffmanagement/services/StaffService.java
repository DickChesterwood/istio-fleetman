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
	
	private Map<String, String> photos = Stream.of(new String[][] {
		{"Pam Parry","https://rac-istio-course-images.s3.amazonaws.com/1.jpg"},
	    {"Duke T. Dog","https://rac-istio-course-images.s3.amazonaws.com/2.jpg"},
	    {"Denzil Tulser","https://rac-istio-course-images.s3.amazonaws.com/3.jpg"},
	    {"Herman Boyce","https://rac-istio-course-images.s3.amazonaws.com/4.jpg"},
	    {"June Snell","https://rac-istio-course-images.s3.amazonaws.com/5.jpg"}}).collect(Collectors.toMap(data -> data[0], data -> data[1]));
	
	
	public StaffRecord getDriverDetailsFor(String vehicleName) 
	{
		String driverName = drivers.get(vehicleName);
		String staffPhoto = photos.get(driverName);
		return new StaffRecord(driverName, staffPhoto);
	}

}
