package com.jdd.powermanager.action.elimination;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.content.Context;
import com.jdd.powermanager.action.AbsAction;
import com.jdd.powermanager.action.AbsCallback;
import com.jdd.powermanager.action.survey.SurveyActions;
import com.jdd.powermanager.bean.District;
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
	
	public static void updateProblemAndMethodWithMeterAssetNO(final String meterAssetNO,final String problem,final String method,final String lo,final String la,final String LoginNo,AbsCallback cb)
	{
		new AbsAction(cb) 
		{
			@Override
			protected Object doJob() 
			{
				EliminateAbnormalManager.getInstance().updateAColumnValueWithColumnNameAndMeterAssetNO(meterAssetNO,EliminateAbnormal.ABNORMAL_PHENOMENON,problem);
				
				EliminateAbnormalManager.getInstance().updateAColumnValueWithColumnNameAndMeterAssetNO(meterAssetNO,EliminateAbnormal.ELIMINATE_METHOD,method);
				
				EliminateAbnormalManager.getInstance().updateAColumnValueWithColumnNameAndMeterAssetNO(meterAssetNO,EliminateAbnormal.LONGITUDE,lo);
				
				EliminateAbnormalManager.getInstance().updateAColumnValueWithColumnNameAndMeterAssetNO(meterAssetNO,EliminateAbnormal.LATITUDE,la);
				
				// TODO
				
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
				List<District> list = EliminateAbnormalManager.getInstance().getAllDistrict();
				
				List<SurveyActions.DistrictInfo> l = new ArrayList<SurveyActions.DistrictInfo>();
				
				if( null == list || list.size() == 0 )
				{
					return null;
				}
				
				for( int i = 0 ;  i < list.size() ; i++ )
				{
					SurveyActions.DistrictInfo d = new SurveyActions.DistrictInfo();
					
					l.add(d);
					
					d.d = list.get(i);
					
					if( null == d.d || null == d.d.getID() )
					{
						continue;
					}
					
					ArrayList<HashMap<String, String>>  meters = EliminateAbnormalManager.getInstance().getEliminateTasksWithSpecifiedCommitStatus(d.d.getID(),0);
					
					d.count = null == meters ? 0 : meters.size();
							
					ArrayList<HashMap<String, String>>  metersOK = EliminateAbnormalManager.getInstance().getEliminateTasksWithSpecifiedCommitStatus(d.d.getID(),1);
						
					d.ok = null == metersOK ? 0 : metersOK.size();
				}
				
				return l;
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
