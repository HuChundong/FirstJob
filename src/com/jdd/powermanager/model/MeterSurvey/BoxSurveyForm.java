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
		
		public static final String LONGITUDE = "LONGITUDE";
		
		public static final String LATITUDE = "LATITUDE";
		
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
									LONGITUDE,
									LATITUDE,									
									BOX_ROWS,
									BOX_COLS,
									METER_NUM,
									DISTRICT_LOGO,
									DISTRICT_ID,
									ASSET_NO,
									SurveyForm.ABNORMAL_COMMENT,
									SurveyForm.COMMIT_STATUS,
									SurveyForm.SURVEY_STATUS,
									SurveyForm.SURVEY_RELATION,
									SurveyForm.OPERATER,
									SurveyForm.OPERATE_DATE,
									SurveyForm.OPERATE_TIME									
								};
		
		public static String[] DBToXLSColumnNameAll = 
								{
									"序号(NO)",
									"业务系统ID(SYSTEM_ID)",
									"安装位置（INST_LOC）",
									"GPS经度坐标（LONGITUDE）",
									"GPS纬度坐标（LATITUDE）",									
									"行数（BOX_ROWS)",
									"计量箱列数和（BOX_COLS）",
									"表数",
									"台区标识(DISTRICT_LOGO)",
									"台区编号(DISTRICT_ID)",
									"计量箱资产编号(ASSET_NO)",
									"异常备注",
									"提交状态",
									"普查状态",
									"关系",
									"操作员(OPERATER)",
									"操作日期(OPERATE_DATE)",
									"操作时间(OPERATE_TIME)"
								};
		
		public static String[] DBToXLSColumnIndexDistrict = 
			{
				DISTRICT_LOGO,
				DISTRICT_ID,
				SurveyForm.OPERATER,
				SurveyForm.OPERATE_DATE,
				SurveyForm.OPERATE_TIME
			};
		
		public static String[] DBToXLSColumnIndexBox = 
			{
				NO,
				SYSTEM_ID,
				INST_LOC,
				LONGITUDE,
				LATITUDE,
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
