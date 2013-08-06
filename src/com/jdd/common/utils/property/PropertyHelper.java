package com.jdd.common.utils.property;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class PropertyHelper 
{
	public static String getProperty(String name,InputStream in)
	{
		Properties p = getNetConfigProperties(in);
		
		if( null == p)
		{
			return null;
		}
		
		String s = p.getProperty(name);
		
		try 
		{
			return  new String(s.getBytes("ISO8859-1"), System.getProperty("file.encoding"));
			
		} 
		catch (UnsupportedEncodingException e) 
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static Properties getNetConfigProperties(InputStream in) 
	{     
        Properties props = new Properties();  
        
        try 
        {     
            props.load(in);  
            
        } catch (IOException e) 
        {     
            e.printStackTrace();     
        }     
        
        return props;     
    }     
}
