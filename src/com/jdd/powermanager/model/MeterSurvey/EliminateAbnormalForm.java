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
		
		public static final String PHOTO_PATH = "PHOTO_PATH";
		
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
				 PHOTO_PATH,
				 COMMIT_STATUS,
				 SurveyForm.OPERATER,
				 SurveyForm.OPERATE_DATE,
				 SurveyForm.OPERATE_TIME
			};
		
		public static String[] DBToXLSColumnNameAll = 
			{
				 "用户编号（CONS_NO）",	
				 "计量点编号（MP_NO）",	
				 "电能表资产号（D_ASSET_NO）",	
				 "用户名称(USER_NAME)",	
				 "用电地址(USER_ADDRESS)",	
				 "台区编号(DISTRICT_ID)",	
				 "GPS经度坐标（LONGITUDE）",	
				 "GPS纬度坐标（LATITUDE）",	
				 "计量箱条形码（BAR_CODE）",	
				 "所在行（IN_ROW）",	
				 "所在列（IN_COLUMN）",	
				 "异常现象(ABNORMAL_PHENOMENON)",	
				 "消缺结果(ELIMINATE_RESULT)",	
				 "消缺方法(ELIMINATE_METHOD)",
				 "相片路径",
				 "提交状态",
				 "操作员(OPERATER)",
				 "操作日期(OPERATE_DATE)",
				 "操作时间(OPERATE_TIME)"
			};
	}
}
