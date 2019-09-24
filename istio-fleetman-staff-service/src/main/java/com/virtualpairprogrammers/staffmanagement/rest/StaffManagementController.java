package com.virtualpairprogrammers.staffmanagement.rest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.virtualpairprogrammers.staffmanagement.domain.StaffRecord;

@RestController
public class StaffManagementController {

	@RequestMapping(method=RequestMethod.GET, value="/driver/{vehicleName}")
	public StaffRecord getDriverAssignedTo(@PathVariable String vehicleName)
	{
		// TODO: Call onward service (photo service) to get photo info
		return new StaffRecord();
	}
	
}
