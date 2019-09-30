package com.virtualpairprogrammers.staffmanagement.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExternalPhotoService {

	@Autowired
	private  PhotoServiceMicroserviceCalls photoServiceMicroserviceCalls;

	public String getStaffPhotoFor(String driverName) 
	{
		return photoServiceMicroserviceCalls.getStaffPhotoFor(driverName);
	}

}
