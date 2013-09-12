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
	
	public static void getEliminateTasksWithSpecifiedCommitStatus(final int commitStatus,AbsCallback cb)
	{
		new AbsAction(cb) 
		{
			@Override
			protected Object doJob() 
			{
				return EliminateAbnormalManager.getInstance().getEliminateTasksWithSpecifiedCommitStatus(commitStatus);
			}
		}.start();
	}
	
	public static void commitMeters(final String[] meterAssetNOs,AbsCallback cb)
	{
		new AbsAction(cb) 
		{
			@Override
			protected Object doJob() 
			{
				EliminateAbnormalManager.getInstance().commitMeters(meterAssetNOs);
				
				return null;
			}
		}.start();
	}
	
	public static void uneliminateMeters(final String[] meterAssetNOs,AbsCallback cb)
	{
		new AbsAction(cb) 
		{
			@Override
			protected Object doJob() 
			{
				EliminateAbnormalManager.getInstance().uneliminateMeters(meterAssetNOs);
				
				return null;
			}
		}.start();
	}
	
	public static void uncommitMeters(final String[] meterAssetNOs,AbsCallback cb)
	{
		new AbsAction(cb) 
		{
			@Override
			protected Object doJob() 
			{
				EliminateAbnormalManager.getInstance().uncommitMeters(meterAssetNOs);
				
				return null;
			}
		}.start();
	}
	
	public static void getAllEliminateTasks(AbsCallback cb)
	{
		new AbsAction(cb) 
		{
			@Override
			protected Object doJob() 
			{
				return EliminateAbnormalManager.getInstance().getAllEliminateTasks();
			}
		}.start();
	}
	
	public static void completeThePlan(AbsCallback cb)
	{
		new AbsAction(cb) 
		{
			@Override
			protected Object doJob() 
			{
				EliminateAbnormalManager.getInstance().completeThePlan();
				
				return null;
			}
		}.start();
	}
	
	public static void getAllDistrict(AbsCallback cb)
	{
		new AbsAction(cb) 
		{
			@Override
			protected Object doJob() 
			{
				// TODO
				
				return null;
			}
		}.start();
	}
	
	public static void getEliminateTaskWithSpecifiedAssetNO(final String no,AbsCallback cb)
	{
		new AbsAction(cb) 
		{
			@Override
			protected Object doJob() 
			{
				return EliminateAbnormalManager.getInstance().getEliminateTaskWithSpecifiedAssetNO(no);
			}
		}.start();
	}
}
