package com.jdd.powermanager.model.MeterSurvey;

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
								SurveyForm.SURVEY_RELATION,
								SurveyForm.OPERATER,
								SurveyForm.OPERATE_DATE,
								SurveyForm.OPERATE_TIME
								};
		
		public static String[] DBToXLSColumnNameAll = 
								{
								"计量箱资产号（ASSET_NO）",
								"计量箱条形码（BAR_CODE）",
								"计量箱出厂编号（MADE_NO）",
								"施封锁",
								"计量箱业务编码（BUSI_CODE）",
								"类别（SORT_CODE）",
								"计量箱列数和（BOX_COLS）",
								"计量箱行数和（BOX_ROWS）",
								"管理单位（DEPT_NO）",
								"产权（PR_CODE）",
								"产权单位（PR_ORG_NO）",
								"安装位置（INST_LOC）",
								"材料类型（DATA_TYPE_CODE）",
								"制造单位（MANUFACTURER）",
								"型号（MODEL_CODE）",
								"GPS经度坐标（LONGITUDE）",
								"GPS纬度坐标（LATITUDE）",
								"用户编号（CONS_NO）",
								"计量点编号（MP_NO）",
								"电能表资产号（D_ASSET_NO）",
								"所在行（IN_ROW）",
								"所在列（IN_COLUMN）",
								"用户名称(USER_NAME)",
								"用电地址(USER_ADDRESS)",
								"台区编号",
								"用户分类",
								"接线方式",
								"普查时间",
								"备注",
								"提交状态",
								"普查状态",
								"关系",
								"操作员(OPERATER)",
								"操作日期(OPERATE_DATE)",
								"操作时间(OPERATE_TIME)"
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
				INST_LOC,
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
				SurveyForm.COMMIT_STATUS,
				SurveyForm.OPERATER,
				SurveyForm.OPERATE_DATE,
				SurveyForm.OPERATE_TIME
			};
		
	}
}
