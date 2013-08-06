package com.jdd.common.utils.date;

import java.util.Calendar;

public class DateHelper 
{
	public static int getYear()
	{
		Calendar c = Calendar.getInstance();
		
		return c.get(Calendar.YEAR);
	}
	
	public static int getMonth()
	{
		Calendar c = Calendar.getInstance();
		
		return c.get(Calendar.MONTH);
	}
	
	public static int getDay()
	{
		Calendar c = Calendar.getInstance();
		
		return c.get(Calendar.DAY_OF_MONTH);
	}
	
	public static String getDate(String dateSplite )
	{
		Calendar c = Calendar.getInstance();
		
		String s = c.get(Calendar.YEAR)
				+ dateSplite + c.get(Calendar.MONTH) 
				+ dateSplite + c.get(Calendar.DAY_OF_MONTH);
		return s;
	}
}
