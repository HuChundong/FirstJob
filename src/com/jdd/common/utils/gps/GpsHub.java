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
	 * GPS �������Ȩ��
	 */
	public static final String GPS_PERMISSION = "android.permission.ACCESS_FINE_LOCATION";
	
	/**
	 * ֱ�ӿ����ر�GPSʱ����Ҫ��Ȩ��
	 */
	public static final String OPEN_CLOSE_GPS_PERMISSION_1 = "android.permission.WRITE_SECURE_SETTINGS";
	
	/**
	 * ֱ�ӿ����ر�GPSʱ����Ҫ��Ȩ��
	 */
	public static final String OPEN_CLOSE_GPS_PERMISSION_2 = "android.permission.WRITE_SETTINGS";
	
	/**
	 * ���һ�λ�ȡλ�õ�ʱ��
	 */
	private static long mLastKnownTime;
	
	/**
	 * ���һ�λ�ȡ����λ��
	 */
	private static LocationInfo mLastLocation;
	
	/**
	 * ���������ڵ�Context
	 */
	private static Context mContext;
	
	/**
	 * �Ƿ�Ϊϵͳapp
	 */
	private static boolean mIsSystemApp;
	
	/**
	 * һ��λ����Ϣ����Чʱ��
	 */
	private static long mEffectiveTime = 10 * 60 * 1000;
	
	/**
	 * ��ȡ��ǰλ����Ϣ
	 * 
	 * @return
	 */
	public static LocationInfo getCurLocation()
	{
		long curTime = System.currentTimeMillis();
		
		long d = curTime - mLastKnownTime;
		
		// 10 ����ǰ�����ݾ�������,�ж���ǰû�л�ȡ���µص���Ϣ
		if( d  >  mEffectiveTime )
		{ 
			return null;
		}
		
		return mLastLocation;
	}
	
	/**
	 * ��ʼ��
	 * 
	 * @param context
	 * @param perTime  ����
	 * @param minDistance  ��
	 */
	public static void init(Context context, long perTime , float minDistance)
	{
		Log.d(TAG, " -- init -- " );
		
		mContext = context;
		
		LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, perTime, minDistance, mLocationLis);
	}
	
	/**
	 * ����һ��λ����Ϣ����Чʱ�� 
	 * 
	 * @param t   ����
	 */
	public static void setLocationEffectiveTime(long t)
	{
		mEffectiveTime = t;
	}
	
	/**
	 * �����Ƿ�Ϊϵͳapp
	 * 
	 * @param isSystemApp
	 */
	public static void setIsSystemApp(boolean isSystemApp)
	{
		mIsSystemApp = isSystemApp;
	}
	
	/**
	 * gps ״̬������
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
			// Provider��״̬�ڿ��á���ʱ�����ú��޷�������״ֱ̬���л�ʱ�����˺���
			
			Log.d(TAG, " -- onStatusChanged --  " + arg0);
		}
	};
	
	/**
	 * ����gps����
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
			Toast.makeText(mContext, "�뿪��GPS!", Toast.LENGTH_LONG).show();
			
			Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			
			context.startActivity(intent);
		}
	}
}
