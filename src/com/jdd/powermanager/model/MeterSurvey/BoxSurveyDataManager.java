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
		synchronized(instance)
		{
			return mBoxSurveyDBHelper.getAllSurveyedBoxesInDistrict(districtID, commitStatus);
		}
	}
	
	/**
	 * “保存”一个计量箱数据。
	 * 根据传入的新的计量箱列表，或更新数据库中已有的计量箱数据，或新增一条计量箱数据
	 * @param district 台区信息
	 * @param boxList 计量箱列表
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
	 * 获取任务内所有台区对象
	 * @return 获取任务内所有台区对象
	 */
	public ArrayList<HashMap<String, String>> getAllDistrict()
	{
		synchronized(instance)
		{
			return mBoxSurveyDBHelper.getAllDistrict();
		}
	}
	
	/**
	 * 提交一组计量箱的普查数据
	 * @param boxIds 计量箱id组
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
	 * 批量删除未提交的表箱
	 * 关系变为异常，普查状态改成未普查
	 * @param boxIds 待删除的计量箱id数组
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
	 * 批量删除已提交的表箱
	 * 仅修改提交状态为未提交
	 * @param boxIds 待删除的计量箱id数组
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
	 * 提交台区内所有未提交、已普查的表箱
	 * @param districtId 台区id
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
	 * 获取某台区内所有计量箱对象列表
	 * @return 某台区内所有计量箱对象列表,用hashmap表示电表对象
	 */
	public ArrayList<HashMap<String, String>> getAllBoxesInDistrict(String districtID)
	{
		synchronized(instance)
		{
			return mBoxSurveyDBHelper.getAllBoxesInDistrict(districtID);
		}
	}
	
	/**
	 * 将数据库中的数据加载到excel
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
	 * 获取某计量箱对象信息
	 * @return 获取某计量箱对象信息,用hashmap表示
	 */
	public HashMap<String, String> getBoxWithAssetNo(String assetNo)
	{
		synchronized(instance)
		{
			return mBoxSurveyDBHelper.getBoxWithAssetNo(assetNo);
		}
	}
}
