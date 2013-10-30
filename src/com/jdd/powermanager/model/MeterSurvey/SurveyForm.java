package com.jdd.powermanager.model.MeterSurvey;

public class SurveyForm 
{

	public static final String SURVEY_RELATION_NEW = "新增";
	public static final String SURVEY_RELATION_YES = "是";
	public static final String SURVEY_RELATION_ABNORMAL = "异常";
	public static final String SURVEY_STATUS_SURVEYED = "已普查";
	public static final String SURVEY_STATUS_UNSURVEYED = "未普查";
	public static final String COMMIT_STATUS_UNCOMMITED = "未提交";
	public static final String COMMIT_STATUS_COMMITED = "已提交";
	public static final String ELIMINATE_RESULT_ELIMINATED = "已消缺";
	public static final String ELIMINATE_RESULT_UNELIMINATE = "未消缺";
	
	public static final String ABNORMAL_COMMENT_NULL = "无备注";
	
	/**
	 * 异常备注
	 */
	public static final String ABNORMAL_COMMENT = "ABNORMAL_COMMENT";
	
	/**
	 * 是否提交
	 */
	public static final String COMMIT_STATUS = "COMMIT_STATUS";
	/**
	 * 普查状态：已普查、未普查
	 */
	public static final String SURVEY_STATUS = "SURVEY_STATUS";
	/**
	 * 普查关系
	 */
	public static final String SURVEY_RELATION = "SURVEY_RELATION";
	
	/**
	 * 操作员
	 */
	public static final String OPERATER = "OPERATER";
	
	/**
	 * 操作日期
	 */
	public static final String OPERATE_DATE = "OPERATE_DATE";
	
	/**
	 * 操作时间
	 */
	public static final String OPERATE_TIME = "OPERATE_TIME";
	
	public static final String COMMA_SEP = ",";
	
	/**
	 * 将id数组转成(id,id,id...)格式
	 * @param idArray 将 id数组
	 * @return (id,id,id...)格式字符串
	 */
	public static String parseIdArray2SQLIds(String[] idArray)
	{
		String ids = "(";
		
		for (int i = 0; i < idArray.length; i ++)
		{
			ids += idArray[i] + COMMA_SEP;
		}
		//去掉最后的逗号
		ids = ids.substring(0, ids.length() - 1);
		ids += ")";
		
		return ids;
	}

}
