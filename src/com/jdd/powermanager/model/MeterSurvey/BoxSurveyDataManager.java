package com.jdd.powermanager.model.MeterSurvey;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

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
		synchronized(instance)
		{
			return mBoxSurveyDBHelper.getAllSurveyedBoxesInDistrict(districtID, commitStatus);
		}
	}
	
	/**
	 * �����桱һ�����������ݡ�
	 * ���ݴ�����µļ������б���������ݿ������еļ��������ݣ�������һ������������
	 * @param district ̨����Ϣ
	 * @param boxList �������б�
	 */
	public void saveBoxSurvey(HashMap<String, String> district,
							ArrayList<HashMap<String, String>> boxList)
	{
		synchronized(instance)
		{
			mBoxSurveyDBHelper.saveBoxSurvey(district, boxList);
			
			parseDBToXLS();
		}
	}
	
	/**
	 * ��ȡ����������̨������
	 * @return ��ȡ����������̨������
	 */
	public ArrayList<HashMap<String, String>> getAllDistrict()
	{
		synchronized(instance)
		{
			return mBoxSurveyDBHelper.getAllDistrict();
		}
	}
	
	/**
	 * �ύһ���������ղ�����
	 * @param boxIds ������id��
	 */
	public void commitBoxesSurvey(String[] boxIds)
	{
		synchronized(instance)
		{
			mBoxSurveyDBHelper.commitBoxesSurvey(boxIds);
			
			parseDBToXLS();
		}
	}
	
	/**
	 * ����ɾ��δ�ύ�ı���
	 * ��ϵ��Ϊ�쳣���ղ�״̬�ĳ�δ�ղ�
	 * @param boxIds ��ɾ���ļ�����id����
	 */
	public void deleteUncommitedBox(String[] boxIds)
	{
		synchronized(instance)
		{
			mBoxSurveyDBHelper.deleteUncommitedBox(boxIds);
			
			parseDBToXLS();
		}
	}
	
	/**
	 * ����ɾ�����ύ�ı���
	 * ���޸��ύ״̬Ϊδ�ύ
	 * @param boxIds ��ɾ���ļ�����id����
	 */
	public void deleteCommitedBox(String[] boxIds)
	{
		synchronized(instance)
		{
			mBoxSurveyDBHelper.deleteCommitedBox(boxIds);
			
			parseDBToXLS();
		}
	}
	
	/**
	 * �ύ̨��������δ�ύ�����ղ�ı���
	 * @param districtId ̨��id
	 */
	public void commitAllUncommitedBoxSurveyInDistrict(String districtId)
	{
		synchronized(instance)
		{
			mBoxSurveyDBHelper.commitAllUncommitedBoxSurveyInDistrict(districtId);
			
			parseDBToXLS();
		}
	}
	
	/**
	 * ��ȡĳ̨�������м���������б�
	 * @return ĳ̨�������м���������б�,��hashmap��ʾ������
	 */
	public ArrayList<HashMap<String, String>> getAllBoxesInDistrict(String districtID)
	{
		synchronized(instance)
		{
			return mBoxSurveyDBHelper.getAllBoxesInDistrict(districtID);
		}
	}
	
	/**
	 * �����ݿ��е����ݼ��ص�excel
	 */
	private void parseDBToXLS()
	{
		XLS xls = new XLS(getBoxSurveyTaskFilePath());
		HSSFSheet sheet = xls.getSheet(0);
		HSSFRow row = null;
		
		ArrayList<HashMap<String, String>> allDBRows = mBoxSurveyDBHelper.getAllDatas();
		
		for (int i = 0; i < allDBRows.size(); i ++)
		{
			row = xls.getRow(sheet,i + 1);
			setRowHashMapToHSSFRow(row, allDBRows.get(i), BoxSurvey.DBToXLSColumnIndexAll, xls);
		}
		
		xls.saveToXlsFile();
	}
	
	/**
	 * ��ȡĳ�����������Ϣ
	 * @return ��ȡĳ�����������Ϣ,��hashmap��ʾ
	 */
	public HashMap<String, String> getBoxWithAssetNo(String assetNo)
	{
		synchronized(instance)
		{
			return mBoxSurveyDBHelper.getBoxWithAssetNo(assetNo);
		}
	}
}
