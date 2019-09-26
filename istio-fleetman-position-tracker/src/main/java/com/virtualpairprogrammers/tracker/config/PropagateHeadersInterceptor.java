package com.virtualpairprogrammers.tracker.config;

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
				System.out.println("incoming value of " + header + ":" + value);
				if (header.equalsIgnoreCase("x-dick")) 
				{
					System.out.println("BINGO! GOT THE VALUE " + value);
				}
				template.header(header, value);
				System.out.println("Added custom header");
			}
		}
	}
}