package com.jdd.powermanager.model.MeterSurvey;


import java.util.ArrayList;
import java.util.HashMap;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;

import com.jdd.common.utils.Excel.XLS;
import com.jdd.powermanager.bean.District;
import com.jdd.powermanager.model.MeterSurvey.BoxSurveyForm.BoxSurvey;
import com.jdd.powermanager.model.MeterSurvey.MeterSurveyForm.MeterSurvey;

import android.content.ContentValues;
import android.content.Context;
import android.os.Environment;

public class MeterSurveyDataManager extends SurveyDataManager 
{
	private static final String METER_SURVEY_FILE = "/meterSurvey.xls";
	
	private static final String TAG = "MeterSurveyDataManager";
	
	private MeterSurveyDBHelper mMeterSurveyDBHelper = null;
	
	private static MeterSurveyDataManager instance = new MeterSurveyDataManager();
	
	private MeterSurveyDataManager()
	{
		
	}
	
	public static MeterSurveyDataManager getInstance()
	{
		return instance;
	}
	
	/**
	 * 获取计量普查任务文件路径
	 * @return 计量普查任务文件路径
	 */
	private String getMeterSurveyTaskFilePath()
	{
		return SD + FOLDER + METER_SURVEY_FILE;
	}
	
	/**
	 * 初始化计量普查
	 */
	protected void initSurveyTask()
	{
		mMeterSurveyDBHelper = new MeterSurveyDBHelper(mContext);
		
		//首先删除老表
		mMeterSurveyDBHelper.cleanMeterSurveyTable();
		
		//将excel内容导入数据库		
		parseMeterSurveyXLSToDB();
		
	}
	

	/**
	 * 将计量普查excel中的数据加载到计量普查数据库
	 */
	private void parseMeterSurveyXLSToDB()
	{
		XLS xls = new XLS(getMeterSurveyTaskFilePath());
		HSSFRow[] allRows = xls.getAllRows(0);
		ContentValues[] allContentValues = new ContentValues[allRows.length - 1];
		
		//i=1从第二行真正的数据开始
		for (int i = 1; i < allRows.length; i ++)
		{
			allContentValues[i - 1] = parseExcelRowToContentValues(allRows[i], 
												MeterSurvey.DBToXLSColumnIndexAll);
		}
		
		mMeterSurveyDBHelper.insertMeterSurveyTable(allContentValues);
	}
	
	/**
	 * 将数据库中的数据加载到excel
	 */
	private void parseDBToXLS()
	{
		XLS xls = new XLS(getMeterSurveyTaskFilePath());
		HSSFSheet sheet = xls.getSheet(0);
		HSSFRow row = null;
		
		ArrayList<HashMap<String, String>> allDBRows = mMeterSurveyDBHelper.getAllDatas();
		
		for (int i = 0; i < allDBRows.size(); i ++)
		{
			row = xls.getRow(sheet,i + 1);
			setRowHashMapToHSSFRow(row, allDBRows.get(i), MeterSurvey.DBToXLSColumnIndexAll);
		}
		
		xls.saveToXlsFile();
	}

	/**
	 * 获取所有台区对象列表
	 * @return 所有的台区对象
	 */
	public ArrayList<District> getAllDistrict()
	{
		synchronized(instance)
		{
			return mMeterSurveyDBHelper.getAllDistrict();
		}
	}
	
	/**
	 * 获取某台区内所有已普查计量箱列表
	 * @return 某台区内所有已普查计量箱列表,用hashmap表示计量箱对象
	 */
	public ArrayList<HashMap<String, String>> getAllBoxesInDistrict(String districtID)
	{
		synchronized(instance)
		{
			return mMeterSurveyDBHelper.getAllBoxesInDistrict(districtID);
		}
	}
	
	/**
	 * “保存”一个计量箱数据。
	 * @param box 计量箱信息
	 * @param meterList 电表列表
	 * @param districtId 台区id
	 */
	public void saveOneBoxMeterSurvey(HashMap<String, String> box,
							ArrayList<HashMap<String, String>> meterList,
							String districtId)
	{
		synchronized(instance)
		{
			mMeterSurveyDBHelper.saveOneBoxMeterSurvey(box, meterList, districtId);
		
			parseDBToXLS();
		}
	}
	
	/**
	 * 提交一个计量箱的普查数据
	 * @param boxId 计量箱id
	 */
	public void commitOneBoxMeterSurvey(String boxId)
	{
		synchronized(instance)
		{
			mMeterSurveyDBHelper.commitOneBoxMeterSurvey(boxId);
		
			parseDBToXLS();
		}
	}
	
	/**
	 * 获取某台区内所有电表对象列表
	 * @return 某台区内所有电表对象列表,用hashmap表示电表对象
	 */
	public ArrayList<HashMap<String, String>> getAllMetersInDistrict(String districtID)
	{
		synchronized(instance)
		{
			return mMeterSurveyDBHelper.getAllMetersInDistrict(districtID);
		}
	}
	
	/**
	 * 提交台区内所有未提交、已普查的表箱
	 * @param districtId 台区id
	 */
	public void commitAllUncommitedBoxMeterSurveyInDistrict(String districtId)
	{
		synchronized(instance)
		{
			mMeterSurveyDBHelper.commitAllUncommitedBoxMeterSurveyInDistrict(districtId);
		
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
			mMeterSurveyDBHelper.deleteUncommitedBox(boxIds);
		
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
			mMeterSurveyDBHelper.deleteCommitedBox(boxIds);
		
			parseDBToXLS();
		}
	}
	
	
	/**
	 * 增加计量箱异常备注信息
	 * @param boxId 计量箱id
	 * @param comment 备注信息
	 */
	public void addBoxAbnormalComment(String boxId, String comment)
	{
		synchronized(instance)
		{
			mMeterSurveyDBHelper.addBoxAbnormalComment(boxId, comment);
		}
	}
	
	/**
	 * 增加计量箱普查时间
	 * @param boxId 计量箱id
	 * @param time 普查时间
	 */
	public void addBoxSurveyTime(String boxId, String time)
	{
		synchronized(instance)
		{
			mMeterSurveyDBHelper.addBoxSurveyTime(boxId, time);
		}
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
			return mMeterSurveyDBHelper.getAllSurveyedBoxesInDistrict(districtID, commitStatus);
		}
	}
	
	/**
	 * 获取某计量箱内所有电表对象列表
	 * @return 计量箱内所有电表对象列表,用hashmap表示电表对象
	 */
	public ArrayList<HashMap<String, String>> getAllMetersInBox(String boxID)
	{
		synchronized(instance)
		{
			return mMeterSurveyDBHelper.getAllMetersInBox(boxID);
		}
	}
	
	/**
	 * 获取某电表对象
	 * @param assetNo 电表资产编号
	 * @return 电表对象
	 */
	public HashMap<String, String> getMeterWithAssetNo(String assetNo)
	{
		synchronized(instance)
		{
			return mMeterSurveyDBHelper.getMeterWithAssetNo(assetNo);
		}
	}
	
	///////////////////////////////////////////UT test begin/////////////////////////////////////
	private void test() 
	{
		HashMap<String, String> box = new HashMap<String, String>();
		ArrayList<HashMap<String, String>> meterList = new ArrayList<HashMap<String, String>>();
		
		int boxColumnLength = MeterSurvey.DBToXLSColumnIndexBox.length;
		for (int i = 0 ; i < boxColumnLength - 1 ; i ++)
		{
			box.put(MeterSurvey.DBToXLSColumnIndexBox[i], "box" + i);
		}
		
		box.put(MeterSurvey.BAR_CODE, "370130005097597");
		
		for (int i = 0; i < 6; i ++)
		{
			HashMap<String, String> meter = new HashMap<String, String>();
			
			int meterColumnLength = MeterSurvey.DBToXLSColumnIndexMeter.length;
			for (int j = 0 ; j < meterColumnLength - 1 ; j ++)
			{
				meter.put(MeterSurvey.DBToXLSColumnIndexMeter[j], "meter" + j);
			}
			
			meterList.add(meter);
		}
		meterList.get(0).put(MeterSurvey.D_ASSET_NO, "370110031352058");
		meterList.get(1).put(MeterSurvey.D_ASSET_NO, "370110031352057");
		meterList.get(2).put(MeterSurvey.D_ASSET_NO, "370110031351922");
		meterList.get(3).put(MeterSurvey.D_ASSET_NO, "100000000000004");
		meterList.get(4).put(MeterSurvey.D_ASSET_NO, "100000000000005");
		meterList.get(5).put(MeterSurvey.D_ASSET_NO, "100000000000006");
		
		saveOneBoxMeterSurvey(box, meterList, "1000001");
	}
	
	private void test2()
	{
		commitOneBoxMeterSurvey("370130005097597");
	}
	
	private void test3()
	{
		commitAllUncommitedBoxMeterSurveyInDistrict("0000853828");
	}
	
	private void test4()
	{
		String[] boxIds = {"370130005097597"};
		deleteUncommitedBox(boxIds);
	}
	
	private void test5()
	{
		String[] boxIds = {"370130005097597"};
		deleteCommitedBox(boxIds);
	}
	
	private void test6()
	{
		addBoxAbnormalComment("370130005097597", "哈哈哈");
		addBoxSurveyTime("370130005097597", "2013/08/03");
		parseDBToXLS();
	}
	
	private void test7()
	{
		ArrayList<HashMap<String, String>> list = getAllSurveyedBoxesInDistrict("0000853828", 2);
	}
	
	///////////////////////////////////////////UT test end/////////////////////////////////////
}
