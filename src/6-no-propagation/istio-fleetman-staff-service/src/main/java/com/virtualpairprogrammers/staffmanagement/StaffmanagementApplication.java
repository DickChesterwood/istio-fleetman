package com.virtualpairprogrammers.staffmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class StaffmanagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(StaffmanagementApplication.class, args); 
	}

}
