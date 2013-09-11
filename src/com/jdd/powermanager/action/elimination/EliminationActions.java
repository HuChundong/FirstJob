package com.jdd.powermanager.action.elimination;

import android.content.Context;
import com.jdd.powermanager.action.AbsAction;
import com.jdd.powermanager.action.AbsCallback;
import com.jdd.powermanager.model.MeterSurvey.EliminateAbnormalManager;

public class EliminationActions 
{
	public static void init(final Context c,AbsCallback cb)
	{
		new AbsAction(cb) 
		{
			@Override
			protected Object doJob() 
			{
				EliminateAbnormalManager.getInstance().init(c.getApplicationContext());
					
				return null;
			}
		}.start();
	}
}
