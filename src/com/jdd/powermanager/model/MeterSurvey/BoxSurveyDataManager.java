package com.jdd.powermanager.model.MeterSurvey;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.poi.hssf.usermodel.HSSFRow;

import android.content.ContentValues;

import com.jdd.common.utils.Excel.XLS;
import com.jdd.powermanager.model.MeterSurvey.BoxSurveyForm.BoxSurvey;

public class BoxSurveyDataManager extends SurveyDataManager 
{
	private BoxSurveyDBHelper mBoxSurveyDBHelper = null;
	
	private static final String BOX_SURVEY_FILE = "/boxSurvey.xls";
	
	private static BoxSurveyDataManager instance = new BoxSurveyDataManager();
	
	private BoxSurveyDataManager()
	{
		
	}
	
	public static BoxSurveyDataManager getInstance()
	{
		return instance;
	}
	
	/**
	 * ��ȡ̨�����������ļ�·��
	 * @return ̨�����������ļ�·��
	 */
	private String getBoxSurveyTaskFilePath()
	{
		return SD + FOLDER + BOX_SURVEY_FILE;
	}
	
	/**
	 * ��ʼ�������ղ�
	 */
	protected void initSurveyTask()
	{
		mBoxSurveyDBHelper = new BoxSurveyDBHelper(mContext);
		
		//����ɾ���ϱ�
		mBoxSurveyDBHelper.cleanBoxSurveyTable();
		
		//��excel���ݵ������ݿ�		
		parseBoxSurveyXLSToDB();
	}
	
	/**
	 * ��̨������excel�е����ݼ��ص�̨���������ݿ�
	 */
	private void parseBoxSurveyXLSToDB()
	{
		XLS xls = new XLS(getBoxSurveyTaskFilePath());
		HSSFRow[] allRows = xls.getAllRows(0);
		ContentValues[] allContentValues = new ContentValues[allRows.length - 1];
		
		//i=1�ӵڶ������������ݿ�ʼ
		for (int i = 1; i < allRows.length; i ++)
		{
			allContentValues[i - 1] = parseExcelRowToContentValues(allRows[i], 
												BoxSurvey.DBToXLSColumnIndexAll);
		}
		
		mBoxSurveyDBHelper.insertBoxSurveyTable(allContentValues);
	}
	
	/**
	 * ��ȡĳ̨�����������ղ�ļ������б�
	 * @param districtID ̨��id
	 * @param commitStatus �ύ״̬	0���������еı���	1���������ύ����	2������δ�ύ����
	 * @return ĳ̨�����������ղ�������б�,��hashmap��ʾ���������
	 */
	public ArrayList<HashMap<String, String>> getAllSurveyedBoxesInDistrict(String districtID, int commitStatus)
	{
		return mBoxSurveyDBHelper.getAllSurveyedBoxesInDistrict(districtID, commitStatus);
	}

}
