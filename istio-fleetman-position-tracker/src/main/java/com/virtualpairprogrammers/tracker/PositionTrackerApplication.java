package com.virtualpairprogrammers.tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PositionTrackerApplication {
	public static void main(String[] args) {
	    SpringApplication.run(PositionTrackerApplication.class, args);
	}
}
