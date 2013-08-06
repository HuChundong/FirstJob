package com.jdd.powermanager.model.MeterSurvey;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.ss.usermodel.Cell;

import com.jdd.powermanager.model.MeterSurvey.BoxSurveyForm.BoxSurvey;

import android.content.ContentValues;
import android.content.Context;
import android.os.Environment;

public class SurveyDataManager 
{
	public interface OnInitedListener
	{
		void onInitedSucess();
	}
	
	protected static final String SD = Environment.
			getExternalStorageDirectory().getAbsolutePath();
	
	protected static final String FOLDER = "/survey";
	
	protected Context mContext = null;
	
	protected OnInitedListener mOnInitedListener;
	
	protected void initSurveyTask()
	{
		
	}
	
	/**
	 * 模块初始化
	 * @param context 上下文
	 */
	public void init(Context context, OnInitedListener listener)
	{
		mContext = context;
		
		mOnInitedListener = listener;
		
		new Thread(mInitRunnable).start();
	}
	
	protected Runnable mInitRunnable = new Runnable() 
	{
		@Override
		public void run() 
		{
			initSurveyTask();
			
			if( null != mOnInitedListener )
			{
				mOnInitedListener.onInitedSucess();
			}
		}
	};
	
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
}
