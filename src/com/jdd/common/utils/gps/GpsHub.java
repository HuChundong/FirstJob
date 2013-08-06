package com.jdd.common.utils.gps;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

public class GpsHub 
{
	private static final String TAG = GpsHub.class.getSimpleName();
	
	/**
	 * GPS 所必须的权限
	 */
	public static final String GPS_PERMISSION = "android.permission.ACCESS_FINE_LOCATION";
	
	/**
	 * 直接开启关闭GPS时所需要的权限
	 */
	public static final String OPEN_CLOSE_GPS_PERMISSION_1 = "android.permission.WRITE_SECURE_SETTINGS";
	
	/**
	 * 直接开启关闭GPS时所需要的权限
	 */
	public static final String OPEN_CLOSE_GPS_PERMISSION_2 = "android.permission.WRITE_SETTINGS";
	
	/**
	 * 最后一次获取位置的时间
	 */
	private static long mLastKnownTime;
	
	/**
	 * 最后一次获取到的位置
	 */
	private static LocationInfo mLastLocation;
	
	/**
	 * 长声明周期的Context
	 */
	private static Context mContext;
	
	/**
	 * 是否为系统app
	 */
	private static boolean mIsSystemApp;
	
	/**
	 * 一次位置信息的有效时长
	 */
	private static long mEffectiveTime = 10 * 60 * 1000;
	
	/**
	 * 获取当前位置信息
	 * 
	 * @return
	 */
	public static LocationInfo getCurLocation()
	{
		long curTime = System.currentTimeMillis();
		
		long d = curTime - mLastKnownTime;
		
		// 10 分钟前的数据就作废了,判定当前没有获取到新地点信息
		if( d  >  mEffectiveTime )
		{ 
			return null;
		}
		
		return mLastLocation;
	}
	
	/**
	 * 初始化
	 * 
	 * @param context
	 * @param perTime  毫秒
	 * @param minDistance  米
	 */
	public static void init(Context context, long perTime , float minDistance)
	{
		Log.d(TAG, " -- init -- " );
		
		mContext = context;
		
		LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, perTime, minDistance, mLocationLis);
	}
	
	/**
	 * 设置一次位置信息的有效时长 
	 * 
	 * @param t   毫秒
	 */
	public static void setLocationEffectiveTime(long t)
	{
		mEffectiveTime = t;
	}
	
	/**
	 * 设置是否为系统app
	 * 
	 * @param isSystemApp
	 */
	public static void setIsSystemApp(boolean isSystemApp)
	{
		mIsSystemApp = isSystemApp;
	}
	
	/**
	 * gps 状态监听器
	 */
	public static LocationListener mLocationLis = new LocationListener ()
	{
		@Override
		public void onLocationChanged(Location arg0) 
		{
			Log.d(TAG, " -- onLocationChanged -- Location " + arg0);
			
			mLastKnownTime = System.currentTimeMillis();
			
			mLastLocation = new LocationInfo(arg0.getLongitude(), arg0.getLatitude());
		}

		@Override
		public void onProviderDisabled(String arg0) 
		{
			Log.d(TAG, " -- onProviderDisabled --  " + arg0);
			
			openOrCloseGps(mContext,true);
		}

		@Override
		public void onProviderEnabled(String arg0) 
		{
			Log.d(TAG, " -- onProviderEnabled --  " + arg0);
		}

		@Override
		public void onStatusChanged(String arg0, int arg1, Bundle arg2) 
		{
			// Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
			
			Log.d(TAG, " -- onStatusChanged --  " + arg0);
		}
	};
	
	/**
	 * 开启gps服务
	 * 
	 * @param context
	 * @param open
	 */
	private static void openOrCloseGps(Context context,boolean open)
	{
		if(mIsSystemApp)
		{
		    boolean gpsEnabled = Settings.Secure.isLocationProviderEnabled( context.getContentResolver(), LocationManager.GPS_PROVIDER );
		 
		    if( !gpsEnabled)
		    {
		    	Settings.Secure.setLocationProviderEnabled( context.getContentResolver(), LocationManager.GPS_PROVIDER, true);
		    }
		}
		else
		{
			Toast.makeText(mContext, "请开启GPS!", Toast.LENGTH_LONG).show();
			
			Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			
			context.startActivity(intent);
		}
	}
}
