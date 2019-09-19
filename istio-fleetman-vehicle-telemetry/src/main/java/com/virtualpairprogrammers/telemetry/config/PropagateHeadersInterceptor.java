package com.virtualpairprogrammers.telemetry.config;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import feign.RequestInterceptor;
import feign.RequestTemplate;

@Component
public class PropagateHeadersInterceptor implements RequestInterceptor {
	@Override
	public void apply(RequestTemplate template) {
		System.out.println("Now getting incoming headers...all of them: " + template.request().headers());
		Collection<String> value = template.request().headers().get("x-dick");
		System.out.println(value);
		System.out.println("Value for x-dick was " + value);
		if (value != null)
		{
			Map<String, Collection<String>> propagated = new HashMap<>();
			propagated.put("x-dick", value);
			template.headers(propagated);
			System.out.println("Done. Now check the onward service!");
		}
	}
}
