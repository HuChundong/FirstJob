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
	 * ģ���ʼ��
	 * @param context ������
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
}
