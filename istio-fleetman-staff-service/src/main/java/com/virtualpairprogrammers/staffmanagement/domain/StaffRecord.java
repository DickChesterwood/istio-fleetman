package com.virtualpairprogrammers.staffmanagement.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class StaffRecord 
{
	private String name;
	// TODO image here
	
	public StaffRecord()
	{
		this.name = "Eric Watson";
	}
}
