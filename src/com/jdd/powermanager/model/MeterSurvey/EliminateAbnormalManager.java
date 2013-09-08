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
	 * 获取台区梳理任务文件路径
	 * @return 台区梳理任务文件路径
	 */
	private String getEliminateAbnormalTaskFilePath()
	{
		return SD + FOLDER + ELIMINATE_ABNORMAL_FILE;
	}
	
	/**
	 * 初始化计量普查
	 */
	protected void initSurveyTask()
	{
		mEliminateAbnormalDBHelper = new EliminateAbnormalDBHelper(mContext);
		
		//首先删除老表
		mEliminateAbnormalDBHelper.cleanEliminateAbnormalTable();
		
		//将excel内容导入数据库		
		parseEliminateAbnormalXLSToDB();
	}
	
	/**
	 * 将台区梳理excel中的数据加载到台区梳理数据库
	 */
	private void parseEliminateAbnormalXLSToDB()
	{
		XLS xls = new XLS(getEliminateAbnormalTaskFilePath());
		HSSFRow[] allRows = xls.getAllRows(0);
		ContentValues[] allContentValues = new ContentValues[allRows.length - 1];
		
		//i=1从第二行真正的数据开始
		for (int i = 1; i < allRows.length; i ++)
		{
			allContentValues[i - 1] = parseExcelRowToContentValues(allRows[i], 
											EliminateAbnormal.DBToXLSColumnIndexAll);
		}
		
		mEliminateAbnormalDBHelper.insertEliminateAbnormalTable(allContentValues);
	}
	
	/**
	 * 将数据库中的数据加载到excel
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
	 * 根据电表资产编号，更新某列的值
	 * @param meterAssetNO 电表资产编号
	 * @param columnName 列名
	 * @param columnValue 值
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
	 * 提交一个电表的异常消缺数据
	 * @param meterAssetNO 电表资产编号
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
	 * 保存异常消缺
	 */
	public void saveEliminateAbnormal()
	{
		synchronized(instance)
		{
			parseDBToXLS();	
		}
	}
	
	/**
	 * 获取异常消缺电表照片路径
	 * @param meterAssetNO 电表资产编号（条码）
	 * @return 照片路径
	 */
	public String getMeterAbnormalPhotoPath(String meterAssetNO)
	{
		synchronized(instance)
		{
			//将相对路径存数据库
			String relativePath = "./" + meterAssetNO;
			mEliminateAbnormalDBHelper.updateAColumnValueWithColumnNameAndMeterAssetNO(
					meterAssetNO, EliminateAbnormal.PHOTO_PATH, relativePath);
			
			//拼接绝对路径返回
			String absolutePath = SD + FOLDER + "/" + meterAssetNO;
			
			//生成文件夹
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
