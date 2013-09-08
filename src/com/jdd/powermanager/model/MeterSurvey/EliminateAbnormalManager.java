package com.jdd.powermanager.model.MeterSurvey;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import android.content.ContentValues;

import com.jdd.common.utils.Excel.XLS;
import com.jdd.powermanager.model.MeterSurvey.EliminateAbnormalForm.EliminateAbnormal;

public class EliminateAbnormalManager extends SurveyDataManager 
{
	private EliminateAbnormalDBHelper mEliminateAbnormalDBHelper = null;
	
	private static final String ELIMINATE_ABNORMAL_FILE = "/eliminateAbnormal.xls";
	
	private static EliminateAbnormalManager instance = new EliminateAbnormalManager();
	
	private EliminateAbnormalManager()
	{
		
	}
	
	public static EliminateAbnormalManager getInstance()
	{
		return instance;
	}
	
	/**
	 * ��ȡ̨�����������ļ�·��
	 * @return ̨�����������ļ�·��
	 */
	private String getEliminateAbnormalTaskFilePath()
	{
		return SD + FOLDER + ELIMINATE_ABNORMAL_FILE;
	}
	
	/**
	 * ��ʼ�������ղ�
	 */
	protected void initSurveyTask()
	{
		mEliminateAbnormalDBHelper = new EliminateAbnormalDBHelper(mContext);
		
		//����ɾ���ϱ�
		mEliminateAbnormalDBHelper.cleanEliminateAbnormalTable();
		
		//��excel���ݵ������ݿ�		
		parseEliminateAbnormalXLSToDB();
	}
	
	/**
	 * ��̨������excel�е����ݼ��ص�̨���������ݿ�
	 */
	private void parseEliminateAbnormalXLSToDB()
	{
		XLS xls = new XLS(getEliminateAbnormalTaskFilePath());
		HSSFRow[] allRows = xls.getAllRows(0);
		ContentValues[] allContentValues = new ContentValues[allRows.length - 1];
		
		//i=1�ӵڶ������������ݿ�ʼ
		for (int i = 1; i < allRows.length; i ++)
		{
			allContentValues[i - 1] = parseExcelRowToContentValues(allRows[i], 
											EliminateAbnormal.DBToXLSColumnIndexAll);
		}
		
		mEliminateAbnormalDBHelper.insertEliminateAbnormalTable(allContentValues);
	}
	
	/**
	 * �����ݿ��е����ݼ��ص�excel
	 */
	private void parseDBToXLS()
	{
		XLS xls = new XLS(getEliminateAbnormalTaskFilePath());
		HSSFSheet sheet = xls.getSheet(0);
		HSSFRow row = null;
		
		ArrayList<HashMap<String, String>> allDBRows = mEliminateAbnormalDBHelper.getAllDatas();
		
		for (int i = 0; i < allDBRows.size(); i ++)
		{
			row = xls.getRow(sheet,i + 1);
			setRowHashMapToHSSFRow(row, allDBRows.get(i), EliminateAbnormal.DBToXLSColumnIndexAll, xls);
		}
		
		xls.saveToXlsFile();
	}
	
	/**
	 * ���ݵ���ʲ���ţ�����ĳ�е�ֵ
	 * @param meterAssetNO ����ʲ����
	 * @param columnName ����
	 * @param columnValue ֵ
	 */
	public void updateAColumnValueWithColumnNameAndMeterAssetNO(String meterAssetNO,
													String columnName, String columnValue)
	{
		synchronized(instance)
		{
			mEliminateAbnormalDBHelper.updateAColumnValueWithColumnNameAndMeterAssetNO(
													meterAssetNO, columnName, columnValue);
		}
	}
	
	/**
	 * �ύһ�������쳣��ȱ����
	 * @param meterAssetNO ����ʲ����
	 */
	public void commitOneMeter(String meterAssetNO)
	{
		synchronized(instance)
		{
			mEliminateAbnormalDBHelper.commitOneMeter(meterAssetNO);
			parseDBToXLS();	
		}		
	}

	/**
	 * �����쳣��ȱ
	 */
	public void saveEliminateAbnormal()
	{
		synchronized(instance)
		{
			parseDBToXLS();	
		}
	}
	
	/**
	 * ��ȡ�쳣��ȱ�����Ƭ·��
	 * @param meterAssetNO ����ʲ���ţ����룩
	 * @return ��Ƭ·��
	 */
	public String getMeterAbnormalPhotoPath(String meterAssetNO)
	{
		synchronized(instance)
		{
			//�����·�������ݿ�
			String relativePath = "./" + meterAssetNO;
			mEliminateAbnormalDBHelper.updateAColumnValueWithColumnNameAndMeterAssetNO(
					meterAssetNO, EliminateAbnormal.PHOTO_PATH, relativePath);
			
			//ƴ�Ӿ���·������
			String absolutePath = SD + FOLDER + "/" + meterAssetNO;
			
			//�����ļ���
			try
			{
				File dir = new File(absolutePath);
	            dir.mkdir();
				
				return absolutePath;	
			}
			catch(Exception e)
			{
				e.printStackTrace();
				
				return null;
			}
		}
	}
}
