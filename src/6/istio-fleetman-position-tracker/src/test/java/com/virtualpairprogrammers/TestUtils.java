package com.virtualpairprogrammers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

class TestUtils {
	public static Date getDateFrom(String timestamp)
	{
		DateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
		try {
			return format.parse(timestamp);
		} 
		catch (ParseException e) 
		{
			// code error
			throw new RuntimeException(e);
		} 
	}
}
