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
		
		// TODO this is work in progress, will be removing the httpservletreq I hope shortly
		try
		{
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
		catch (IllegalStateException e)
		{
			// no problem, this isn't an incoming web request - scheduled timed update
		}
	}
}