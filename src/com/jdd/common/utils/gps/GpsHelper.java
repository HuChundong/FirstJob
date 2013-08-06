package com.jdd.common.utils.gps;

import android.content.Context;

import com.jdd.common.utils.permission.PermissonHelper;

public class GpsHelper 
{
	/**
	 * ��ʼ��GPSģ��
	 * 
	 * 
	 * @param context    ������,��Ҫ�����������ڵ�������
	 * 
	 * @param perTime    ˢ�¼������
	 * 
	 * @param minDistance ���ȣ���
	 */
	public static void init(Context context, long perTime , float minDistance)
	{
		/**
		 * û��ʹ��gps��Ȩ�޽����׳��쳣
		 */
		if( !PermissonHelper.hasPermissions(context, GpsHub.GPS_PERMISSION) )
		{
			throw new RuntimeException("need permisson : " + GpsHub.GPS_PERMISSION);
		}
		
		/**
		 * ���app���������µ�Ȩ����˵��app������Ϊϵͳapp����
		 */
		if( PermissonHelper.hasPermissions(context, GpsHub.OPEN_CLOSE_GPS_PERMISSION_1)
				&& PermissonHelper.hasPermissions(context, GpsHub.OPEN_CLOSE_GPS_PERMISSION_2) )
		{
			GpsHub.setIsSystemApp(true);
		}
		
		GpsHub.init(context, perTime, minDistance);
	}
	
	/**
	 * ��ȡ��ǰλ����Ϣ
	 * 
	 * @return
	 */
	public static LocationInfo getCurLocation()
	{
		return GpsHub.getCurLocation();
	}
	
	/**
	 * ����һ��λ����Ϣ���Чʱ��
	 * 
	 * @param time  ����
	 */
	public static void setLocationEffectiveTime(long time)
	{
		GpsHub.setLocationEffectiveTime(time);
	}
}
