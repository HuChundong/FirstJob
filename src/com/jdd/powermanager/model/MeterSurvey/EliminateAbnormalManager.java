package com.jdd.powermanager.model.MeterSurvey;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import android.content.ContentValues;

import com.jdd.common.utils.Excel.XLS;
import com.jdd.powermanager.bean.District;
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
	 * 获取任务内所有台区对象
	 * @return 获取任务内所有台区对象
	 */
	public ArrayList<District> getAllDistrict()
	{
		synchronized(instance)
		{
			return mEliminateAbnormalDBHelper.getAllDistrict();
		}
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
	 * 提交一组电表的异常消缺数据
	 * @param meterAssetNOs 电表资产编号数组
	 */
	public void commitMeters(String[] meterAssetNOs)
	{
		synchronized(instance)
		{
			mEliminateAbnormalDBHelper.commitMeters(meterAssetNOs);
			
			parseDBToXLS();
		}
	}

	/**
	 * 取消提交一个电表的异常消缺数据
	 * @param meterAssetNO 电表资产编号
	 */
	public void uncommitOneMeter(String meterAssetNO)
	{
		synchronized(instance)
		{
			mEliminateAbnormalDBHelper.uncommitOneMeter(meterAssetNO);
			parseDBToXLS();	
		}	
	}
	
	/**
	 * 取消提交一组电表的异常消缺数据
	 * @param meterAssetNO 电表资产编号数组
	 */
	public void uncommitMeters(String[] meterAssetNOs)
	{
		synchronized(instance)
		{
			mEliminateAbnormalDBHelper.uncommitMeters(meterAssetNOs);
			parseDBToXLS();	
		}
	}
	
	/**
	 * 完成本计划
	 * @param districtID 台区id
	 */
	public void completeThePlan(String districtID)
	{
		synchronized(instance)
		{
			mEliminateAbnormalDBHelper.commitAllEliminatedTasks(districtID);
			
			parseDBToXLS();	
		}
	}
	
	/**
	 * 保存异常消缺
	 * @param meterAssetNO 电表资产编号
	 */
	public void saveOneMeter(String meterAssetNO)
	{
		synchronized(instance)
		{
			mEliminateAbnormalDBHelper.eliminateOneMeter(meterAssetNO);
			parseDBToXLS();	
		}
	}
	
	/**
	 * 取消消缺一个电表的异常消缺数据
	 * @param meterAssetNO 电表资产编号
	 */
	public void uneliminateOneMeter(String meterAssetNO)
	{
		synchronized(instance)
		{
			mEliminateAbnormalDBHelper.uneliminateOneMeter(meterAssetNO);
			parseDBToXLS();	
		}
	}
	
	/**
	 * 取消消缺一组电表的异常消缺数据
	 * @param meterAssetNO 电表资产编号数组
	 */
	public void uneliminateMeters(String[] meterAssetNOs)
	{
		synchronized(instance)
		{
			mEliminateAbnormalDBHelper.uneliminateMeters(meterAssetNOs);
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
	
	/**
	 * 获取所有消缺任务
	 * @return 所有消缺任务
	 */
	public ArrayList<HashMap<String, String>> getAllEliminateTasks()
	{
		synchronized(instance)
		{
			return mEliminateAbnormalDBHelper.getAllDatas();
		}
	}
	
	/**
	 * 根据提交状态返回消缺任务,仅返回已消缺的任务
	 * @param districtID 台区id
	 * @param commitStatus 提交状态 0：返回所有的消缺任务	1：返回已提交消缺任务	2：返回未提交消缺任务
	 * @return 相应提交状态的任务
	 */
	public ArrayList<HashMap<String, String>> getEliminateTasksWithSpecifiedCommitStatus(String districtID, int commitStatus)
	{
		synchronized(instance)
		{
			return mEliminateAbnormalDBHelper.getEliminateTasksWithSpecifiedCommitStatus(districtID, commitStatus);
		}
	}
	
	/**
	 * 根据消缺状态返回消缺任务
	 * @param districtID 台区id
	 * @param commitStatus 提交状态 0：返回所有的消缺任务	1：返回已消缺消缺任务	2：返回未消缺消缺任务
	 * @return 相应消缺状态的任务
	 */
	public ArrayList<HashMap<String, String>> getEliminateTasksWithSpecifiedEliminateStatus(String districtID, int eliminateStatus)
	{
		synchronized(instance)
		{
			return mEliminateAbnormalDBHelper.getEliminateTasksWithSpecifiedEliminateStatus(districtID, eliminateStatus);
		}
	}
	
	/**
	 * 获取某电表的异常消缺信息
	 * @return 某电表的异常消缺信息
	 */
	public HashMap<String, String> getEliminateTaskWithSpecifiedAssetNO(String meterAssetNO)
	{
		synchronized(instance)
		{
			return mEliminateAbnormalDBHelper.getEliminateTaskWithSpecifiedAssetNO(meterAssetNO);
		}
	}
	
	
}
