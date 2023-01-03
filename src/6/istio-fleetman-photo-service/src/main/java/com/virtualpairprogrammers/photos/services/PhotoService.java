package com.virtualpairprogrammers.photos.services;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

@Service
public class PhotoService
{
	private Map<String, String> drivers = Stream.of(new String[][] {
		{"Pam Parry","https://rac-istio-course-images.s3.amazonaws.com/1.jpg"},
	    {"Duke T. Dog","https://rac-istio-course-images.s3.amazonaws.com/2.jpg"},
	    {"Denzil Tulser","https://rac-istio-course-images.s3.amazonaws.com/3.jpg"},
	    {"Herman Boyce","https://rac-istio-course-images.s3.amazonaws.com/4.jpg"},
	    {"June Snell","https://rac-istio-course-images.s3.amazonaws.com/5.jpg"}}).collect(Collectors.toMap(data -> data[0], data -> data[1]));
	

	public String getPhotoFor(String driverName) {
		return drivers.get(driverName);
	}

}
