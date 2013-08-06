package com.jdd.common.utils.toast;

import android.content.Context;
import android.widget.Toast;

public class ToastHelper 
{
	public static void showToastShort(Context c ,String msg)
	{
		Toast.makeText(c, msg, Toast.LENGTH_SHORT).show();
	}
}
