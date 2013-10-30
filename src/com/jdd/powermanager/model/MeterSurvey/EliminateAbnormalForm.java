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
				 "�û���ţ�CONS_NO��",	
				 "�������ţ�MP_NO��",	
				 "���ܱ��ʲ��ţ�D_ASSET_NO��",	
				 "�û�����(USER_NAME)",	
				 "�õ��ַ(USER_ADDRESS)",	
				 "̨�����(DISTRICT_ID)",	
				 "GPS�������꣨LONGITUDE��",	
				 "GPSγ�����꣨LATITUDE��",	
				 "�����������루BAR_CODE��",	
				 "�����У�IN_ROW��",	
				 "�����У�IN_COLUMN��",	
				 "�쳣����(ABNORMAL_PHENOMENON)",	
				 "��ȱ���(ELIMINATE_RESULT)",	
				 "��ȱ����(ELIMINATE_METHOD)",
				 "��Ƭ·��",
				 "�ύ״̬",
				 "����Ա(OPERATER)",
				 "��������(OPERATE_DATE)",
				 "����ʱ��(OPERATE_TIME)"
			};
	}
}
