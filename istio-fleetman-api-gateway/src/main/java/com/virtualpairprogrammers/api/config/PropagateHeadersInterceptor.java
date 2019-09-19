package com.virtualpairprogrammers.api.config;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import feign.RequestInterceptor;
import feign.RequestTemplate;

@Component
public class PropagateHeadersInterceptor implements RequestInterceptor {
	
	private @Autowired HttpServletRequest request;

	@Override
	public void apply(RequestTemplate template) {
		Enumeration<String> e = request.getHeaderNames();
		while (e.hasMoreElements())
		{
			String header = e.nextElement();
			if (header.startsWith("x-"))
			{
				String value = request.getHeader(header);
				template.header(header, value);
				System.out.println("Added custom header " + value);
			}
		}
		System.out.println("final header state: " + template.headers());
		System.out.println("Done. Now check the onward service!");
	}
}