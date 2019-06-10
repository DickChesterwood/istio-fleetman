package com.virtualpairprogrammers.converters;

import org.springframework.cloud.netflix.feign.FeignFormatterRegistrar;
import org.springframework.cloud.netflix.feign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;

import feign.Contract;

@Configuration
public class FeignDateConfiguration {
	@Bean
	public FeignFormatterRegistrar localDateFeignFormatterRegistrar() {
	    return new FeignFormatterRegistrar() {
	        @Override
	        public void registerFormatters(FormatterRegistry formatterRegistry) {
	            DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
	            registrar.setUseIsoFormat(true);
	            registrar.registerFormatters(formatterRegistry);
	        }
	    };
	}
	
    @Bean
    public Contract feignContract() {
        return new SpringMvcContract();
    }
}