package com.jdd.powermanager.model.MeterSurvey;

import android.provider.BaseColumns;

/**
 * 台区梳理表
 * @author Administrator
 *
 */
public final class BoxSurveyForm extends SurveyForm
{
	public BoxSurveyForm(){}
	
	public static abstract class BoxSurvey implements BaseColumns
	{
		public static final String TABLE_NAME = "BoxSurvey";
		
		public static final String NO = "NO";
		
		public static final String SYSTEM_ID = "SYSTEM_ID";
		
		public static final String INST_LOC = "INST_LOC";
		
		public static final String BOX_ROWS = "BOX_ROWS";
		
		public static final String BOX_COLS = "BOX_COLS";
		
		public static final String METER_NUM = "METER_NUM";
		
		public static final String DISTRICT_LOGO = "DISTRICT_LOGO";
		
		public static final String DISTRICT_ID = "DISTRICT_ID";
		
		public static final String ASSET_NO = "ASSET_NO";
		
		
		public static String[] DBToXLSColumnIndexAll = 
								{
									NO,
									SYSTEM_ID,
									INST_LOC,
									BOX_ROWS,
									BOX_COLS,
									METER_NUM,
									DISTRICT_LOGO,
									DISTRICT_ID,
									ASSET_NO,
									SurveyForm.ABNORMAL_COMMENT,
									SurveyForm.COMMIT_STATUS,
									SurveyForm.SURVEY_STATUS,
									SurveyForm.SURVEY_RELATION
								};
		
		public static String[] DBToXLSColumnIndexDistrict = 
			{
				DISTRICT_LOGO,
				DISTRICT_ID
			};
		
		public static String[] DBToXLSColumnIndexBox = 
			{
				NO,
				SYSTEM_ID,
				INST_LOC,
				BOX_ROWS,
				BOX_COLS,
				METER_NUM,
				ASSET_NO,
				SurveyForm.ABNORMAL_COMMENT,
				SurveyForm.COMMIT_STATUS,
				SurveyForm.SURVEY_STATUS,
				SurveyForm.SURVEY_RELATION
			};
		
	}
}
