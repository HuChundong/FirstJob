package com.jdd.common.utils.time;

import java.util.Calendar;

public class TimeHelper
{
	public static int getHour()
	{
		Calendar c = Calendar.getInstance();
		
		return c.get(Calendar.HOUR_OF_DAY);
	}
	
	public static int getMinute()
	{
		Calendar c = Calendar.getInstance();
		
		return c.get(Calendar.MINUTE);
	}
	
	public static int getSecond()
	{
		Calendar c = Calendar.getInstance();
		
		return c.get(Calendar.SECOND);
	}
	
	public static String getTime(String splite )
	{
		Calendar c = Calendar.getInstance();
		
		String s = c.get(Calendar.HOUR_OF_DAY)
				+ splite + c.get(Calendar.MINUTE) 
				+ splite + c.get(Calendar.SECOND);
		return s;
	}
}
