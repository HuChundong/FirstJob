package com.jdd.powermanager.model.MeterSurvey;

import android.provider.BaseColumns;

public class EliminateAbnormalForm extends SurveyForm 
{
	public EliminateAbnormalForm(){}
	
	public static abstract class EliminateAbnormal implements BaseColumns
	{
		public static final String TABLE_NAME = "EliminateAbnormal";
		
		public static final String CONS_NO = "CONS_NO";
		
		public static final String MP_NO = "MP_NO";
		
		public static final String D_ASSET_NO = "D_ASSET_NO";
		
		public static final String USER_NAME = "USER_NAME";
		
		public static final String USER_ADDRESS = "USER_ADDRESS";
		
		public static final String DISTRICT_ID = "DISTRICT_ID";
		
		public static final String LONGITUDE = "LONGITUDE";
		
		public static final String LATITUDE = "LATITUDE";
		
		public static final String BAR_CODE = "BAR_CODE";
		
		public static final String IN_ROW = "IN_ROW";
		
		public static final String IN_COLUMN = "IN_COLUMN";
		
		public static final String ABNORMAL_PHENOMENON = "ABNORMAL_PHENOMENON";
		
		public static final String ELIMINATE_RESULT = "ELIMINATE_RESULT";
		
		public static final String ELIMINATE_METHOD = "ELIMINATE_METHOD";
		
		public static String[] DBToXLSColumnIndexAll = 
			{
				 CONS_NO,	
				 MP_NO,	
				 D_ASSET_NO,	
				 USER_NAME,	
				 USER_ADDRESS,	
				 DISTRICT_ID,	
				 LONGITUDE,	
				 LATITUDE,	
				 BAR_CODE,	
				 IN_ROW,	
				 IN_COLUMN,	
				 ABNORMAL_PHENOMENON,	
				 ELIMINATE_RESULT,	
				 ELIMINATE_METHOD,
				 COMMIT_STATUS
			};
	}
}
