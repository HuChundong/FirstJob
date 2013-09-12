package com.jdd.powermanager.action.elimination;

import android.content.Context;
import com.jdd.powermanager.action.AbsAction;
import com.jdd.powermanager.action.AbsCallback;
import com.jdd.powermanager.model.MeterSurvey.EliminateAbnormalForm.EliminateAbnormal;
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
	
	public static void updateProblemAndMethodWithMeterAssetNO(final String meterAssetNO,final String problem,final String method,AbsCallback cb)
	{
		new AbsAction(cb) 
		{
			@Override
			protected Object doJob() 
			{
				EliminateAbnormalManager.getInstance().updateAColumnValueWithColumnNameAndMeterAssetNO(meterAssetNO,EliminateAbnormal.ABNORMAL_PHENOMENON,problem);
				
				EliminateAbnormalManager.getInstance().updateAColumnValueWithColumnNameAndMeterAssetNO(meterAssetNO,EliminateAbnormal.ELIMINATE_METHOD,method);
				
				EliminateAbnormalManager.getInstance().saveOneMeter(meterAssetNO);
				
				return null;
			}
		}.start();
	}
	
	public static void getEliminateTasksWithSpecifiedCommitStatus(final String disId,final int commitStatus,AbsCallback cb)
	{
		new AbsAction(cb) 
		{
			@Override
			protected Object doJob() 
			{
				return EliminateAbnormalManager.getInstance().getEliminateTasksWithSpecifiedCommitStatus(disId,commitStatus);
			}
		}.start();
	}
	
	public static void getEliminateTasksWithSpecifiedEliminateStatus(final String disId,final int commitStatus,AbsCallback cb)
	{
		new AbsAction(cb) 
		{
			@Override
			protected Object doJob() 
			{
				return EliminateAbnormalManager.getInstance().getEliminateTasksWithSpecifiedEliminateStatus(disId,commitStatus);
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
	
	public static void commitOneMeter(final String meterAssetNO,AbsCallback cb)
	{
		new AbsAction(cb) 
		{
			@Override
			protected Object doJob() 
			{
				EliminateAbnormalManager.getInstance().commitOneMeter(meterAssetNO);
				
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
	
	public static void completeThePlan(final String disId,AbsCallback cb)
	{
		new AbsAction(cb) 
		{
			@Override
			protected Object doJob() 
			{
				EliminateAbnormalManager.getInstance().completeThePlan(disId);
				
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
				return EliminateAbnormalManager.getInstance().getAllDistrict();
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
