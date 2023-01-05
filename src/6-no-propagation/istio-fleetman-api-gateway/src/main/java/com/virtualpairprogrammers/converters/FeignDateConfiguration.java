package com.virtualpairprogrammers.converters;

import org.springframework.cloud.openfeign.FeignFormatterRegistrar;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;

import feign.Contract;

@Configuration
// TODO does this do anything?
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