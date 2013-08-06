package com.jdd.powermanager.model.MeterSurvey;

import java.util.HashMap;

import android.provider.BaseColumns;

/**
 * 计量普查表
 * @author Administrator
 *
 */
public final class MeterSurveyForm extends SurveyForm
{
	public MeterSurveyForm(){}
	
	public static abstract class MeterSurvey implements BaseColumns
	{
		public static final String TABLE_NAME = "MeterSurvey";
		
		public static final String ASSET_NO = "ASSET_NO";
		
		public static final String BAR_CODE = "BAR_CODE";
		
		public static final String MADE_NO = "MADE_NO";
		
		public static final String LOCKER_NO = "LOCKER_NO";
		
		public static final String BUSI_CODE = "BUSI_CODE";
		
		public static final String SORT_CODE = "SORT_CODE";
		
		public static final String BOX_COLS = "BOX_COLS";
		
		public static final String BOX_ROWS = "BOX_ROWS";
		
		public static final String DEPT_NO = "DEPT_NO";
		
		public static final String PR_CODE = "PR_CODE";
		
		public static final String PR_ORG_NO = "PR_ORG_NO";
		
		public static final String INST_LOC = "INST_LOC";
		
		public static final String DATA_TYPE_CODE = "DATA_TYPE_CODE";
		
		public static final String MANUFACTURER = "MANUFACTURER";
		
		public static final String MODEL_CODE = "MODEL_CODE";
		
		public static final String LONGITUDE = "LONGITUDE";
		
		public static final String LATITUDE = "LATITUDE";
		
		public static final String CONS_NO = "CONS_NO";
		
		public static final String MP_NO = "MP_NO";
		
		public static final String D_ASSET_NO = "D_ASSET_NO";
		
		public static final String IN_ROW = "IN_ROW";
		
		public static final String IN_COLUMN = "IN_COLUMN";
		
		public static final String USER_NAME = "USER_NAME";
		
		public static final String USER_ADDRESS = "USER_ADDRESS";
		
		public static final String DISTRICT_ID = "DISTRICT_ID";
		
		public static final String USER_TYPE = "USER_TYPE";
		
		public static final String WIRING_METHOD = "WIRING_METHOD";
		
		public static final String SURVEY_TIME = "SURVEY_TIME";
		
		public static String[] DBToXLSColumnIndexAll = 
								{
								ASSET_NO,
								BAR_CODE,
								MADE_NO,
								LOCKER_NO,
								BUSI_CODE,
								SORT_CODE,
								BOX_COLS,
								BOX_ROWS,
								DEPT_NO,
								PR_CODE,
								PR_ORG_NO,
								INST_LOC,
								DATA_TYPE_CODE,
								MANUFACTURER,
								MODEL_CODE,
								LONGITUDE,
								LATITUDE,
								CONS_NO,
								MP_NO,
								D_ASSET_NO,
								IN_ROW,
								IN_COLUMN,
								USER_NAME,
								USER_ADDRESS,
								DISTRICT_ID,
								USER_TYPE,
								WIRING_METHOD,
								SURVEY_TIME,
								SurveyForm.ABNORMAL_COMMENT,
								SurveyForm.COMMIT_STATUS,
								SurveyForm.SURVEY_STATUS,
								SurveyForm.SURVEY_RELATION
								};
		
		public static String[] DBToXLSColumnIndexMeter = 
			{
				CONS_NO,
				MP_NO,
				D_ASSET_NO,
				IN_ROW,
				IN_COLUMN,
				USER_NAME,
				USER_ADDRESS,
				USER_TYPE,
				WIRING_METHOD,
				SurveyForm.SURVEY_STATUS,
				SurveyForm.SURVEY_RELATION
			};
		
		public static String[] DBToXLSColumnIndexBox = 
			{
				ASSET_NO,
				BAR_CODE,
				MADE_NO,
				LOCKER_NO,
				BUSI_CODE,
				SORT_CODE,
				BOX_COLS,
				BOX_ROWS,
				DEPT_NO,
				PR_CODE,
				PR_ORG_NO,
				INST_LOC,
				DATA_TYPE_CODE,
				MANUFACTURER,
				MODEL_CODE,
				LONGITUDE,
				LATITUDE,
				SURVEY_TIME,
				SurveyForm.ABNORMAL_COMMENT,
				SurveyForm.COMMIT_STATUS
			};
		
	}
}
