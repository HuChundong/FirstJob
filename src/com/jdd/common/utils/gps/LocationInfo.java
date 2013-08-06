package com.jdd.common.utils.gps;

/**
 * 
 * ���� γ�� ����
 * 
 * @author Administrator
 *
 */
public class LocationInfo 
{
	/**
	 * ����
	 */
	private double mLongitude;
	
	/**
	 * γ��
	 */
	private double mLatitude;
	
	public LocationInfo()
	{
	}
	
	public LocationInfo(double lo , double la)
	{
		mLongitude = mathRound(lo);
		mLatitude = mathRound(la);
	}
	
	public void setLongitude(double lo)
	{
		mLongitude = mathRound(lo);
	}
	
	public void setLatitude(double la)
	{
		mLatitude = mathRound(la);
	}
	
	public double getLongitude()
	{
		return mLongitude;
	}
	
	public double getLatitude()
	{
		return mLatitude;
	}
	
	private double mathRound(double d)
	{
		return Math.round(d * 1000 ) / 1000d;
	}
	
	@Override
	public String toString() 
	{
		return "LocationInfo : \n Longitude = " + mLongitude + "\n Latitude = " + mLatitude ;
	}
}
