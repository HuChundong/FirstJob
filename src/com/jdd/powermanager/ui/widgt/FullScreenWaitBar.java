package com.jdd.powermanager.ui.widgt;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

public class FullScreenWaitBar 
{
	private static View mView;
	
	public static void  show(Context c,int layoutId)
	{
		mView = LayoutInflater.from(c).inflate(layoutId, null);
		
		Activity a = (Activity) c;
		
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		
		a.addContentView(mView, lp);
	}
	
	public static void hide()
	{
		if( null != mView )
		{
			mView.setVisibility(View.INVISIBLE);
			
			ViewGroup vg = (ViewGroup) mView.getParent();
			
			vg.removeViewInLayout(mView);
			
			mView = null;
		}
	}
	
	public static boolean isShow()
	{
		return null == mView ? false : mView.getVisibility() == View.VISIBLE;
	}
}
