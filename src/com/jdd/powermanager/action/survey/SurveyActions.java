package com.jdd.powermanager.action.survey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.content.Context;
import com.jdd.powermanager.action.AbsAction;
import com.jdd.powermanager.action.AbsCallback;
import com.jdd.powermanager.bean.District;
import com.jdd.powermanager.model.MeterSurvey.MeterSurveyDataManager;

public class SurveyActions 
{
	public static class DistrictInfo
	{
		public District d;
		
		public int count;
		
		public int ok;
	}
	
	public static void init(final Context c,AbsCallback cb)
	{
		new AbsAction(cb) 
		{
			@Override
			protected Object doJob() 
			{
				MeterSurveyDataManager.getInstance().init(c.getApplicationContext());
					
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
				List<District> list = MeterSurveyDataManager.getInstance().getAllDistrict();
				
				List<DistrictInfo> l = new ArrayList<DistrictInfo>();
				
				if( null == list || list.size() == 0 )
				{
					return null;
				}
				
				for( int i = 0 ;  i < list.size() ; i++ )
				{
					DistrictInfo d = new DistrictInfo();
					
					l.add(d);
					
					d.d = list.get(i);
					
					if( null == d.d || null == d.d.getID() )
					{
						continue;
					}
					
					ArrayList<HashMap<String, String>>  meters = MeterSurveyDataManager.getInstance().getAllMetersInDistrict(d.d.getID());
					
					d.count = null == meters ? 0 : meters.size();
							
					ArrayList<HashMap<String, String>>  metersOK = MeterSurveyDataManager.getInstance().getAllSurveyedBoxesInDistrict(d.d.getID(),1);
						
					d.ok = null == metersOK ? 0 : metersOK.size();
				}
				
				return l;
			}
		}.start();
	}
	
	public static void commitOneBoxMeterSurvey(AbsCallback cb,final List<String> ids)
	{
		new AbsAction(cb) 
		{
			@Override
			protected Object doJob() 
			{
				if( null != ids && ids.size() > 0 )
				{
					for( int i = 0 ; i < ids.size(); i ++ )
					{
						MeterSurveyDataManager.getInstance().commitOneBoxMeterSurvey(ids.get(i));
					}
				}
				
				return null;
			}
		}.start();
	}
	
	public static void saveOneBoxMeterSurvey(AbsCallback cb,final HashMap<String, String> box,
			final ArrayList<HashMap<String, String>> meterList,
			final String districtId)
	{
		new AbsAction(cb) 
		{
			@Override
			protected Object doJob() 
			{
				MeterSurveyDataManager.getInstance().saveOneBoxMeterSurvey(box, meterList, districtId);
				
				return null;
			}
		}.start();
	}
	
	public static void deleteUncommitedBox(AbsCallback cb,final String[] ids)
	{
		new AbsAction(cb) 
		{
			@Override
			protected Object doJob() 
			{
				if( null != ids && ids.length > 0 )
				{
					MeterSurveyDataManager.getInstance().deleteUncommitedBox(ids);
				}
				
				return null;
			}
			
		}.start();
	}
	
	public static void getAllSurveyedBoxesInDistrict(AbsCallback cb,final String id,final int type)
	{
		new AbsAction(cb) 
		{
			@Override
			protected Object doJob() 
			{
				return MeterSurveyDataManager.getInstance().getAllSurveyedBoxesInDistrict(id,type);
			}
			
		}.start();
	}
	
	public static void deleteCommitedBox(AbsCallback cb,final String[] ids)
	{
		new AbsAction(cb) 
		{
			@Override
			protected Object doJob() 
			{
				if( null != ids && ids.length > 0 )
				{
					MeterSurveyDataManager.getInstance().deleteCommitedBox(ids);
				}
				
				return null;
			}
			
		}.start();
	}
	
	public static void getAllMetersInDistrict(AbsCallback cb,final String id)
	{
		new AbsAction(cb) 
		{
			@Override
			protected Object doJob() 
			{
				return MeterSurveyDataManager.getInstance().getAllMetersInDistrict(id);
			}
			
		}.start();
	}
	
	public static void commitAllUncommitedBoxMeterSurveyInDistrict(AbsCallback cb,final String id)
	{
		new AbsAction(cb) 
		{
			@Override
			protected Object doJob() 
			{
				MeterSurveyDataManager.getInstance().commitAllUncommitedBoxMeterSurveyInDistrict(id);
				
				return null;
			}
			
		}.start();
	}
}
