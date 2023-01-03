package com.virtualpairprogrammers.photos.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.virtualpairprogrammers.photos.services.PhotoService;

@RestController
public class PhotosController {

	@Autowired
	private PhotoService photoService;

	@RequestMapping(method=RequestMethod.GET, value="/photo/{driverName}", produces="application/json")
	public String getPhotoFor(@PathVariable String driverName)
	{
		return photoService.getPhotoFor(driverName);
	}
	
}
