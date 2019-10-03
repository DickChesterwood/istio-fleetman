package com.virtualpairprogrammers.telemetry.config;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * This interceptor grabs all incoming REST requests, takes all headers beginning "x-", and adds them
 * to any outgoing REST requests (assuming they are made using Feign).
 * 
 * Providing this is done consistently through the architecture, this will have the end result of propagating
 * all custom headers through every single service. This is particularly useful for Tracing, but can also 
 * be used for eg Dark Releases based on a custom header.
 * 
 * Note that we're using the rather old HttpServletRequest API, hence the use of a very clunky Enumeration.
 * There's probably a neater way.
 * 
 * Really, in a service mesh such as Istio, this shouldn't be necessary and I hope future versions of Istio will address this.
 * Info: https://istio.io/docs/tasks/telemetry/distributed-tracing/overview/
 *       https://istio.io/faq/distributed-tracing/#istio-copy-headers (Why can't Istio Propagate headers instead of the application?)
 */
@Component
public class PropagateHeadersInterceptor implements RequestInterceptor {
	
	private @Autowired HttpServletRequest request;

	public void apply(RequestTemplate template) {
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
				}
			}
		}
		catch (IllegalStateException e) {}
	}
}