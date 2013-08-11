package com.jdd.powermanager.action.combing;

import java.util.ArrayList;
import java.util.HashMap;
import android.content.Context;
import com.jdd.powermanager.action.AbsAction;
import com.jdd.powermanager.action.AbsCallback;
import com.jdd.powermanager.model.MeterSurvey.BoxSurveyDataManager;

public class CombingActions 
{
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
	
	public static void getAllDistrict(AbsCallback cb)
	{
		new AbsAction(cb) 
		{
			@Override
			protected Object doJob() 
			{
				return BoxSurveyDataManager.getInstance().getAllDistrict();
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
