package com.jdd.common.utils.permission;

import android.content.Context;


public class PermissonHelper 
{
	/**
	 * 检查指定的权限是否被申明
	 * 
	 * @param c
	 * @param ps
	 * @return
	 */
	public static boolean hasPermissions(Context c , String ps)
	{
		String[] pes = PermissionHub.getAppPermission(c);
		
		if( null == pes || null == ps )
		{
			return false;
		}
		
		for(int i = 0 ; i < pes.length ; i++ )
		{
			if( ps.equals(pes[i]) )
			{
				return true;
			}
		}
		
		return false;
	}
}
