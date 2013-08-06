package com.jdd.powermanager.bean;

/**
 * 
 * @author Administrator
 * 台区类
 */
public class District 
{

	/**
	 * 台区ID
	 */
	private String mID = null;
	
	private District(){}
	
	public District(String ID)
	{
		mID = ID;
	}
	
	public String getID()
	{
		return mID;
	}
	
	public void setID(String ID)
	{
		mID = ID;
	}
}
