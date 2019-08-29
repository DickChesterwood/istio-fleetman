package com.virtualpairprogrammers.simulator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class ThreadingConfig {
	@Bean
	public TaskExecutor taskExecutor()
	{
		return new ThreadPoolTaskExecutor();
	}
}
