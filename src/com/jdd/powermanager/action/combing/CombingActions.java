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
}
