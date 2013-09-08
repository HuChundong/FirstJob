package com.jdd.powermanager.model.MeterSurvey;

import java.util.HashMap;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.ss.usermodel.Cell;

import com.jdd.common.utils.Excel.XLS;
import com.jdd.powermanager.model.MeterSurvey.BoxSurveyForm.BoxSurvey;
import com.jdd.powermanager.model.MeterSurvey.EliminateAbnormalForm.EliminateAbnormal;

import android.content.ContentValues;
import android.content.Context;
import android.os.Environment;

public class SurveyDataManager 
{
//	protected static final String SD = Environment.
//			getExternalStorageDirectory().getAbsolutePath();
	
	protected static final String SD = "/mnt/sdcard-ext";

	protected static final String FOLDER = "/survey";
	
	protected Context mContext = null;
	
	protected void initSurveyTask()
	{
		
	}
	
	/**
	 * ģ���ʼ��
	 * @param context ������
	 */
	public void init(Context context)
	{
		mContext = context;
		
		initSurveyTask();
	}
	
	/**
	 * ��excel���ж�����������ݿ�ʹ�õ�ContentValues
	 * @param row excel�ж���
	 * @param columns ������
	 * @return ContentValues ���ݿ�ʹ�õ�ContentValues
	 */
	public ContentValues parseExcelRowToContentValues(HSSFRow row, String[] columns)
	{
		ContentValues values = new ContentValues();
		HSSFCell cell = null;
		for (int i = 0; i < columns.length; i ++)
		{
			cell = row.getCell(i);
			
			if (cell != null)
			{
				//�����ַ�����ʽ�������ݣ���ֹ���ֿ�ѧ������
				cell.setCellType(Cell.CELL_TYPE_STRING);
				
				values.put(columns[i], cell.toString());
			}
			else
			{
				if (SurveyForm.COMMIT_STATUS.equals(columns[i]))
				{
					//Ĭ�϶���δ�ύ����Ϣ
					values.put(columns[i], SurveyForm.COMMIT_STATUS_UNCOMMITED);
				}
				else if(SurveyForm.SURVEY_STATUS.equals(columns[i]))
				{
					//Ĭ�϶���δ�ղ����Ϣ
					values.put(columns[i], SurveyForm.SURVEY_STATUS_UNSURVEYED);
				}
				else if(SurveyForm.SURVEY_RELATION.equals(columns[i]))
				{
					//Ĭ�϶����쳣��ϵ
					values.put(columns[i], SurveyForm.SURVEY_RELATION_ABNORMAL);
				}
				else if(SurveyForm.ABNORMAL_COMMENT.equals(columns[i]))
				{
					//Ĭ�϶������쳣��ע
					values.put(columns[i], SurveyForm.ABNORMAL_COMMENT_NULL);
				}
				else
				{
					values.put(columns[i], "");	
				}
			}
		}
			
		return values;
	}
	
	/**
	 * �����ݿ���һ����¼�ĵ�������д�뵽HSSFRow������
	 * @param row 
	 * @param meterRowMap ���ݿ���һ����¼
	 * @param columnDefine ���ݿ��ж�������
	 */
	protected void setRowHashMapToHSSFRow(HSSFRow row, HashMap<String, String> meterRowMap,
												String[] columnDefine, XLS xls)
	{
		HSSFCell cell = null;
		for (int i = 0; i < columnDefine.length; i ++)
		{
			cell = row.getCell(i);
	        if (cell == null) 
	        {
	            cell = row.createCell(i);
	        }
			
			String value = meterRowMap.get(columnDefine[i]);
			if ( value != null)
			{
				if (EliminateAbnormal.PHOTO_PATH.equals(columnDefine[i]))
				{
					xls.setCellHLinkPath(cell, value);
				}
				else
				{
					//�����ַ�����ʽд�����ݣ���ֹ���ֿ�ѧ������
					cell.setCellType(Cell.CELL_TYPE_STRING);
					
					cell.setCellValue(value);	
				}
			}
			else
			{
				cell.setCellValue("");
			}
		}
	}
}
