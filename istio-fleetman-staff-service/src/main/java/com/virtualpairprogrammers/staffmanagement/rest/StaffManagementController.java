package com.virtualpairprogrammers.staffmanagement.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.virtualpairprogrammers.staffmanagement.domain.StaffRecord;
import com.virtualpairprogrammers.staffmanagement.services.ExternalDriverMonitoringSystem;
import com.virtualpairprogrammers.staffmanagement.services.StaffService;

@RestController
public class StaffManagementController {

	@Autowired
	private StaffService staffService;
	
	@Autowired
	private ExternalDriverMonitoringSystem externalDriverMonitoringSystem;

	@RequestMapping(method=RequestMethod.GET, value="/driver/{vehicleName}", produces="application/json")
	public ResponseEntity<StaffRecord> getDriverAssignedTo(@PathVariable String vehicleName)
	{
		try {
			return new ResponseEntity<StaffRecord>(staffService.getDriverDetailsFor(vehicleName), HttpStatus.OK);
		}
		catch (Exception e)
		{
			return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
		}
	}
	
	// See case #22 for details of this odd design...
	@RequestMapping(method=RequestMethod.POST, value="/driver/{vehicleName}/{speed}")
	public void updateSpeedLogFor(@PathVariable String vehicleName, @PathVariable String speed)
	{
		StaffRecord driverDetails = this.getDriverAssignedTo(vehicleName).getBody();
		externalDriverMonitoringSystem.updateSpeedLogFor(driverDetails.getName(), speed);
	}
	
}
