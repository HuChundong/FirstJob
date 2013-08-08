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
	 * 获取台区梳理任务文件路径
	 * @return 台区梳理任务文件路径
	 */
	private String getBoxSurveyTaskFilePath()
	{
		return SD + FOLDER + BOX_SURVEY_FILE;
	}
	
	/**
	 * 初始化计量普查
	 */
	protected void initSurveyTask()
	{
		mBoxSurveyDBHelper = new BoxSurveyDBHelper(mContext);
		
		//首先删除老表
		mBoxSurveyDBHelper.cleanBoxSurveyTable();
		
		//将excel内容导入数据库		
		parseBoxSurveyXLSToDB();
	}
	
	/**
	 * 将台区梳理excel中的数据加载到台区梳理数据库
	 */
	private void parseBoxSurveyXLSToDB()
	{
		XLS xls = new XLS(getBoxSurveyTaskFilePath());
		HSSFRow[] allRows = xls.getAllRows(0);
		ContentValues[] allContentValues = new ContentValues[allRows.length - 1];
		
		//i=1从第二行真正的数据开始
		for (int i = 1; i < allRows.length; i ++)
		{
			allContentValues[i - 1] = parseExcelRowToContentValues(allRows[i], 
												BoxSurvey.DBToXLSColumnIndexAll);
		}
		
		mBoxSurveyDBHelper.insertBoxSurveyTable(allContentValues);
	}
	
	/**
	 * 获取某台区内所有已普查的计量箱列表
	 * @param districtID 台区id
	 * @param commitStatus 提交状态	0：返回所有的表箱	1：返回已提交表箱	2：返回未提交表箱
	 * @return 某台区内所有已普查计量箱列表,用hashmap表示计量箱对象
	 */
	public ArrayList<HashMap<String, String>> getAllSurveyedBoxesInDistrict(String districtID, int commitStatus)
	{
		return mBoxSurveyDBHelper.getAllSurveyedBoxesInDistrict(districtID, commitStatus);
	}

}
