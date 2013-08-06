package com.jdd.common.utils.gps;

import android.content.Context;

import com.jdd.common.utils.permission.PermissonHelper;

public class GpsHelper 
{
	/**
	 * 初始化GPS模块
	 * 
	 * 
	 * @param context    上下文,需要永久生命周期的上下文
	 * 
	 * @param perTime    刷新间隔，秒
	 * 
	 * @param minDistance 精度，米
	 */
	public static void init(Context context, long perTime , float minDistance)
	{
		/**
		 * 没有使用gps的权限将会抛出异常
		 */
		if( !PermissonHelper.hasPermissions(context, GpsHub.GPS_PERMISSION) )
		{
			throw new RuntimeException("need permisson : " + GpsHub.GPS_PERMISSION);
		}
		
		/**
		 * 如果app声明了以下的权限则说明app可以作为系统app运行
		 */
		if( PermissonHelper.hasPermissions(context, GpsHub.OPEN_CLOSE_GPS_PERMISSION_1)
				&& PermissonHelper.hasPermissions(context, GpsHub.OPEN_CLOSE_GPS_PERMISSION_2) )
		{
			GpsHub.setIsSystemApp(true);
		}
		
		GpsHub.init(context, perTime, minDistance);
	}
	
	/**
	 * 获取当前位置信息
	 * 
	 * @return
	 */
	public static LocationInfo getCurLocation()
	{
		return GpsHub.getCurLocation();
	}
	
	/**
	 * 设置一个位置信息最长有效时间
	 * 
	 * @param time  毫秒
	 */
	public static void setLocationEffectiveTime(long time)
	{
		GpsHub.setLocationEffectiveTime(time);
	}
}
