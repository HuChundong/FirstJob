package com.jdd.powermanager.model.MeterSurvey;

public class SurveyForm 
{

	public static final String SURVEY_RELATION_NEW = "����";
	public static final String SURVEY_RELATION_YES = "��";
	public static final String SURVEY_RELATION_ABNORMAL = "�쳣";
	public static final String SURVEY_STATUS_SURVEYED = "���ղ�";
	public static final String SURVEY_STATUS_UNSURVEYED = "δ�ղ�";
	public static final String COMMIT_STATUS_UNCOMMITED = "δ�ύ";
	public static final String COMMIT_STATUS_COMMITED = "���ύ";
	public static final String ELIMINATE_RESULT_ELIMINATED = "����ȱ";
	public static final String ELIMINATE_RESULT_UNELIMINATE = "δ��ȱ";
	
	public static final String ABNORMAL_COMMENT_NULL = "�ޱ�ע";
	
	/**
	 * �쳣��ע
	 */
	public static final String ABNORMAL_COMMENT = "ABNORMAL_COMMENT";
	
	/**
	 * �Ƿ��ύ
	 */
	public static final String COMMIT_STATUS = "COMMIT_STATUS";
	/**
	 * �ղ�״̬�����ղ顢δ�ղ�
	 */
	public static final String SURVEY_STATUS = "SURVEY_STATUS";
	/**
	 * �ղ��ϵ
	 */
	public static final String SURVEY_RELATION = "SURVEY_RELATION";
	
	/**
	 * ����Ա
	 */
	public static final String OPERATER = "OPERATER";
	
	/**
	 * ��������
	 */
	public static final String OPERATE_DATE = "OPERATE_DATE";
	
	/**
	 * ����ʱ��
	 */
	public static final String OPERATE_TIME = "OPERATE_TIME";
	
	public static final String COMMA_SEP = ",";
	
	/**
	 * ��id����ת��(id,id,id...)��ʽ
	 * @param idArray �� id����
	 * @return (id,id,id...)��ʽ�ַ���
	 */
	public static String parseIdArray2SQLIds(String[] idArray)
	{
		String ids = "(";
		
		for (int i = 0; i < idArray.length; i ++)
		{
			ids += idArray[i] + COMMA_SEP;
		}
		//ȥ�����Ķ���
		ids = ids.substring(0, ids.length() - 1);
		ids += ")";
		
		return ids;
	}

}
