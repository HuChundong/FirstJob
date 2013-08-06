package com.jdd.common.utils.permission;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

public class PermissionHub 
{
	/**
	 * 获取当前程序声明的所有权限
	 * 
	 * @param context
	 * @return
	 */
	public static String[] getAppPermission(Context context)
	{
		PackageManager pm = context.getPackageManager();  
		
		String[] sharedPkgList = null;
		
		try 
		{
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_PERMISSIONS);
			
			String pkgName = pi.packageName;  
			
			PackageInfo pkgInfo = pm.getPackageInfo(pkgName, PackageManager.GET_PERMISSIONS);
			
			sharedPkgList = pkgInfo.requestedPermissions;
		} 
		catch (NameNotFoundException e) 
		{
			e.printStackTrace();
		}  
		
		return sharedPkgList;
	}
}
