package com.virtualpairprogrammers.staffmanagement.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class StaffRecord 
{
	private String name;
	private String photo;
	
	public StaffRecord()
	{
	}

	public StaffRecord(String driverName, String staffPhoto) {
		this.name = driverName;
		this.photo = staffPhoto;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
}
