package com.jdd.powermanager.action.combing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.content.Context;
import com.jdd.powermanager.action.AbsAction;
import com.jdd.powermanager.action.AbsCallback;
import com.jdd.powermanager.model.MeterSurvey.BoxSurveyDataManager;
import com.jdd.powermanager.model.MeterSurvey.BoxSurveyForm.BoxSurvey;

public class CombingActions 
{
	public static class DistrictInfo
	{
		public String dId;
		
		public String dLogo;
		
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
				BoxSurveyDataManager.getInstance().init(c.getApplicationContext());
					
				return null;
			}
		}.start();
	}
	
	public static void saveBoxSurvey(AbsCallback cb,final HashMap<String, String> district,
			final ArrayList<HashMap<String, String>> boxList)
	{
		new AbsAction(cb) 
		{
			@Override
			protected Object doJob() 
			{
				BoxSurveyDataManager.getInstance().saveBoxSurvey(district, boxList);
				
				return null;
			}
		}.start();
	}
	
	public static void getAllSurveyedBoxesInDistrict(AbsCallback cb,final String districtID, final int commitStatus)
	{
		new AbsAction(cb) 
		{
			@Override
			protected Object doJob() 
			{
				return BoxSurveyDataManager.getInstance().getAllSurveyedBoxesInDistrict(districtID, commitStatus);
			}
		}.start();
	}
	
	public static void getAllBoxesInDistrict(AbsCallback cb,final String districtID)
	{
		new AbsAction(cb) 
		{
			@Override
			protected Object doJob() 
			{
				return BoxSurveyDataManager.getInstance().getAllBoxesInDistrict(districtID);
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
				ArrayList<HashMap<String, String>>  list = BoxSurveyDataManager.getInstance().getAllDistrict();
				
				List<DistrictInfo> l = new ArrayList<DistrictInfo>();
				
				if( null == list || list.size() == 0 )
				{
					return null;
				}
				
				HashMap<String, String> m = null;
				
				for( int i = 0 ;  i < list.size() ; i++ )
				{
					m = list.get(i);
					
					if( null == m || null == m.get(BoxSurvey.DISTRICT_ID) )
					{
						continue;
					}
					
					DistrictInfo d = new DistrictInfo();
					
					l.add(d);
					
					d.dId = m.get(BoxSurvey.DISTRICT_ID);
					
					d.dLogo = m.get(BoxSurvey.DISTRICT_LOGO);
					
					ArrayList<HashMap<String, String>>  meters = BoxSurveyDataManager.getInstance().getAllBoxesInDistrict(d.dId);
					
					d.count = null == meters ? 0 : meters.size();
							
					ArrayList<HashMap<String, String>>  metersOK = BoxSurveyDataManager.getInstance().getAllSurveyedBoxesInDistrict(d.dId, 1);
						
					d.ok = null == metersOK ? 0 : metersOK.size();
				}
				
				return l;
			}
		}.start();
	}
	
	public static void commitBoxesSurvey(AbsCallback cb, final String[] ids)
	{
		new AbsAction(cb) 
		{
			@Override
			protected Object doJob() 
			{
				BoxSurveyDataManager.getInstance().commitBoxesSurvey(ids);
				
				return null;
			}
		}.start();
	}
	
	public static void deleteUncommitedBox(AbsCallback cb, final String[] ids)
	{
		new AbsAction(cb) 
		{
			@Override
			protected Object doJob() 
			{
				BoxSurveyDataManager.getInstance().deleteUncommitedBox(ids);
				
				return null;
			}
		}.start();
	}
	
	public static void deleteCommitedBox(AbsCallback cb, final String[] ids)
	{
		new AbsAction(cb) 
		{
			@Override
			protected Object doJob() 
			{
				BoxSurveyDataManager.getInstance().deleteCommitedBox(ids);
				
				return null;
			}
		}.start();
	}
	
	public static void commitAllUncommitedBoxSurveyInDistrict(AbsCallback cb,final String id)
	{
		new AbsAction(cb) 
		{
			@Override
			protected Object doJob() 
			{
				BoxSurveyDataManager.getInstance().commitAllUncommitedBoxSurveyInDistrict(id);
				
				return null;
			}
		}.start();	
	}
}
