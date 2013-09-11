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
	 * 模块初始化
	 * @param context 上下文
	 */
	public void init(Context context)
	{
		mContext = context;
		
		initSurveyTask();
	}
	
	/**
	 * 将excel的行对象解析成数据库使用的ContentValues
	 * @param row excel行对象
	 * @param columns 列数组
	 * @return ContentValues 数据库使用的ContentValues
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
				//均以字符串方式读出数据，防止出现科学计数法
				cell.setCellType(Cell.CELL_TYPE_STRING);
				
				values.put(columns[i], cell.toString());
			}
			else
			{
				if (SurveyForm.COMMIT_STATUS.equals(columns[i]))
				{
					//默认都是未提交的信息
					values.put(columns[i], SurveyForm.COMMIT_STATUS_UNCOMMITED);
				}
				else if(SurveyForm.SURVEY_STATUS.equals(columns[i]))
				{
					//默认都是未普查的信息
					values.put(columns[i], SurveyForm.SURVEY_STATUS_UNSURVEYED);
				}
				else if(SurveyForm.SURVEY_RELATION.equals(columns[i]))
				{
					//默认都是异常关系
					values.put(columns[i], SurveyForm.SURVEY_RELATION_ABNORMAL);
				}
				else if(SurveyForm.ABNORMAL_COMMENT.equals(columns[i]))
				{
					//默认都是无异常备注
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
	 * 将数据库中一条记录的的所有列写入到HSSFRow对象中
	 * @param row 
	 * @param meterRowMap 数据库中一条记录
	 * @param columnDefine 数据库列定义数组
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
					//均以字符串方式写入数据，防止出现科学计数法
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
