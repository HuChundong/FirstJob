package com.jdd.powermanager.model.MeterSurvey;

import java.util.ArrayList;
import java.util.HashMap;

import com.jdd.powermanager.model.MeterSurvey.BoxSurveyForm.BoxSurvey;
import com.jdd.powermanager.model.MeterSurvey.MeterSurveyForm.MeterSurvey;
import com.jdd.powermanager.bean.District;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MeterSurveyDBHelper extends SQLiteOpenHelper {
	
	public static final int DATABASE_VERSION = 1;
	
    public static final String DATABASE_NAME = "Survey.db";
	
	private static final String TEXT_TYPE = " TEXT";
	
	private static final String INTEGER_TYPE = " INTEGER";
	
	private static final String COMMA_SEP = ",";
	
	private static String SQL_CREATE_ENTRIES = null;

	private static final String SQL_DELETE_ENTRIES =
	    "DROP TABLE IF EXISTS " + MeterSurvey.TABLE_NAME;

	private static final String TAG = "MeterSurveyDBHelper";
	
	public MeterSurveyDBHelper(Context context) 
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}


	@Override
	public void onCreate(SQLiteDatabase db)
	{
		SQL_CREATE_ENTRIES = "CREATE TABLE " + MeterSurvey.TABLE_NAME + " (";
		int DBToXLSColumnIndexAllLength = MeterSurvey.DBToXLSColumnIndexAll.length;
		
		for (int i = 0; i < DBToXLSColumnIndexAllLength - 1; i ++)
		{
			SQL_CREATE_ENTRIES += MeterSurvey.DBToXLSColumnIndexAll[i] + TEXT_TYPE + COMMA_SEP;
		}
		
		SQL_CREATE_ENTRIES += MeterSurvey.DBToXLSColumnIndexAll[DBToXLSColumnIndexAllLength - 1] 
																					+ TEXT_TYPE + " )";
		db.execSQL(SQL_CREATE_ENTRIES);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
	}
	
	public void insertMeterSurveyTable(ContentValues contentValues)
	{
		SQLiteDatabase db = getWritableDatabase();
		try
		{
			db.insert(
					MeterSurvey.TABLE_NAME,
					null,
					contentValues);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			db.close();
		}
	}
	
	public void insertMeterSurveyTable(ContentValues[] allContentValues)
	{
		SQLiteDatabase db = getWritableDatabase();
		
		try
		{
			for (int i = 0; i < allContentValues.length; i ++)
			{
				db.insert(
						MeterSurvey.TABLE_NAME,
						null,
						allContentValues[i]);	
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			db.close();
		}
	}
	
	/**
	 * 删除计量普查表
	 */
	public void cleanMeterSurveyTable()
	{
		SQLiteDatabase db = getWritableDatabase();
		
		try
		{
			db.execSQL(SQL_DELETE_ENTRIES);
			onCreate(db);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			db.close();
		}
	}
	
	/**
	 * 获取所有台区对象列表
	 * @return 所有的台区对象
	 */
	public ArrayList<District> getAllDistrict()
	{
		String SQL = "SELECT DISTINCT " + MeterSurvey.DISTRICT_ID + 
					" FROM " + MeterSurvey.TABLE_NAME;
		SQLiteDatabase db = getWritableDatabase();
		ArrayList<District> districtList = new ArrayList<District>();
		Cursor c = null;
		try
		{
			c = db.rawQuery(SQL, null);  
	        while (c.moveToNext())
	        {
	        	districtList.add(new District(c.getString(0)));
	        }	
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (c != null)
			{
				c.close();	
			}
			
			db.close();
				
		}
        
        return districtList;  
	}
	
	
	/**
	 * 获取某台区内所有电表对象列表
	 * @return 某台区内所有电表对象列表,用hashmap表示电表对象(并且包含表箱资产编号信息)
	 */
	public ArrayList<HashMap<String, String>> getAllMetersInDistrict(String districtID)
	{
		String SQL = "SELECT ";
		int meterColumnLength = MeterSurvey.DBToXLSColumnIndexMeter.length;
		for (int i = 0 ; i < meterColumnLength - 1 ; i ++)
		{
			SQL += MeterSurvey.DBToXLSColumnIndexMeter[i] + COMMA_SEP;
		}
		SQL += MeterSurvey.DBToXLSColumnIndexMeter[meterColumnLength - 1] + COMMA_SEP +
				MeterSurvey.ASSET_NO +
				" FROM " + MeterSurvey.TABLE_NAME +
				" where " + MeterSurvey.DISTRICT_ID + " = \"" + districtID +"\"";
		
		SQLiteDatabase db = getWritableDatabase();
		ArrayList<HashMap<String, String>> meterList = new ArrayList<HashMap<String, String>>();
		Cursor c = null;
		try
		{
			c = db.rawQuery(SQL, null);  
	        while (c.moveToNext())
	        {
	        	HashMap<String, String> meterColumnMap = new HashMap<String, String>();
	        	int i = 0;
	        	for (i = 0; i < meterColumnLength; i ++)
	        	{
	        		meterColumnMap.put(MeterSurvey.DBToXLSColumnIndexMeter[i], c.getString(i));	        		
	        	}
	        	meterColumnMap.put(MeterSurvey.ASSET_NO, c.getString(i));
	        	
	        	meterList.add(meterColumnMap);
	        }	
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (c != null)
			{
				c.close();	
			}
			
			db.close();
				
		}
        
        return meterList;  
	}
	
	/**
	 * 获取某台区内所有已普查计量箱列表
	 * @return 某台区内所有已普查计量箱列表,用hashmap表示计量箱对象
	 */
	public ArrayList<HashMap<String, String>> getAllBoxesInDistrict(String districtID)
	{
		String SQL = "SELECT DISTINCT ";
		int boxColumnLength = MeterSurvey.DBToXLSColumnIndexBox.length;
		for (int i = 0 ; i < boxColumnLength - 1 ; i ++)
		{
			SQL += MeterSurvey.DBToXLSColumnIndexBox[i] + COMMA_SEP;
		}
		SQL += MeterSurvey.DBToXLSColumnIndexBox[boxColumnLength - 1] +
				" FROM " + MeterSurvey.TABLE_NAME +
				" where DISTRICT_ID = \"" + districtID +"\"";
		
		SQLiteDatabase db = getWritableDatabase();
		ArrayList<HashMap<String, String>> meterList = new ArrayList<HashMap<String, String>>();
		Cursor c = null;
		try
		{
			c = db.rawQuery(SQL, null);  
	        while (c.moveToNext())
	        {
	        	HashMap<String, String> meterColumnMap = new HashMap<String, String>();
	        	for (int i = 0; i < boxColumnLength; i ++)
	        	{
	        		meterColumnMap.put(MeterSurvey.DBToXLSColumnIndexBox[i], c.getString(i));	        		
	        	}
	        	meterList.add(meterColumnMap);
	        }	
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (c != null)
			{
				c.close();	
			}
			
			db.close();
				
		}
        
        return meterList;  
	}
	
	/**
	 * 获取某台区内所有已普查的计量箱列表
	 * @param districtID 台区id
	 * @param commitStatus 提交状态	0：返回所有的表箱	1：返回已提交表箱	2：返回未提交表箱
	 * @return 某台区内所有已普查计量箱列表,用hashmap表示计量箱对象
	 */
	public ArrayList<HashMap<String, String>> getAllSurveyedBoxesInDistrict(String districtID, int commitStatus)
	{
		String SQL = "SELECT DISTINCT ";
		int boxColumnLength = MeterSurvey.DBToXLSColumnIndexBox.length;
		for (int i = 0 ; i < boxColumnLength - 1 ; i ++)
		{
			SQL += MeterSurvey.DBToXLSColumnIndexBox[i] + COMMA_SEP;
		}
		SQL += MeterSurvey.DBToXLSColumnIndexBox[boxColumnLength - 1] +
				" FROM " + MeterSurvey.TABLE_NAME +
				" where " + BoxSurvey.DISTRICT_ID + " = \"" + districtID +"\""
				+ " and " + SurveyForm.SURVEY_STATUS + " = \"" + SurveyForm.SURVEY_STATUS_SURVEYED + "\"";
		
		//增加是否已提交的条件逻辑
		String commitStatusCondition = "";
		
		switch (commitStatus)
		{
			case 0:
				commitStatusCondition = "";
				break;
			case 1:
				commitStatusCondition = " and " + SurveyForm.COMMIT_STATUS + 
										" = \"" + SurveyForm.COMMIT_STATUS_COMMITED + "\"";
				
				break;
			case 2:
				commitStatusCondition = " and " + SurveyForm.COMMIT_STATUS + 
										" = \"" + SurveyForm.COMMIT_STATUS_UNCOMMITED + "\"";
				
				break;
			default:
				commitStatusCondition = "";
				break;
		}
		
		SQL += commitStatusCondition;
		
		SQLiteDatabase db = getWritableDatabase();
		ArrayList<HashMap<String, String>> boxList = new ArrayList<HashMap<String, String>>();
		Cursor c = null;
		try
		{
			c = db.rawQuery(SQL, null);  
	        while (c.moveToNext())
	        {
	        	HashMap<String, String> boxColumnMap = new HashMap<String, String>();
	        	for (int i = 0; i < boxColumnLength; i ++)
	        	{
	        		boxColumnMap.put(MeterSurvey.DBToXLSColumnIndexBox[i], c.getString(i));	        		
	        	}
	        	boxList.add(boxColumnMap);
	        }	
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (c != null)
			{
				c.close();	
			}
			
			db.close();
				
		}
        
        return boxList;  
	}
	
	
	/**
	 * 获取某计量箱内所有电表对象列表
	 * @return 计量箱内所有电表对象列表,用hashmap表示电表对象
	 */
	public ArrayList<HashMap<String, String>> getAllMetersInBox(String boxID)
	{
		String SQL = "SELECT ";
		int meterColumnLength = MeterSurvey.DBToXLSColumnIndexMeter.length;
		for (int i = 0 ; i < meterColumnLength - 1 ; i ++)
		{
			SQL += MeterSurvey.DBToXLSColumnIndexMeter[i] + COMMA_SEP;
		}
		SQL += MeterSurvey.DBToXLSColumnIndexMeter[meterColumnLength - 1] +
				" FROM " + MeterSurvey.TABLE_NAME +
				" where " + MeterSurvey.BAR_CODE + " = \"" + boxID +"\"";
		
		SQLiteDatabase db = getWritableDatabase();
		ArrayList<HashMap<String, String>> meterList = new ArrayList<HashMap<String, String>>();
		Cursor c = null;
		try
		{
			c = db.rawQuery(SQL, null);  
	        while (c.moveToNext())
	        {
	        	HashMap<String, String> meterColumnMap = new HashMap<String, String>();
	        	for (int i = 0; i < meterColumnLength; i ++)
	        	{
	        		meterColumnMap.put(MeterSurvey.DBToXLSColumnIndexMeter[i], c.getString(i));	        		
	        	}
	        	meterList.add(meterColumnMap);
	        }	
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (c != null)
			{
				c.close();	
			}
			
			db.close();
				
		}
        
        return meterList;  
	}
	
	/**
	 * 获取某电表对象
	 * @param assetNo 电表资产编号
	 * @return 电表对象
	 */
	public HashMap<String, String> getMeterWithAssetNo(String assetNo)
	{
		String SQL = "SELECT ";
		int meterColumnLength = MeterSurvey.DBToXLSColumnIndexMeter.length;
		for (int i = 0 ; i < meterColumnLength - 1 ; i ++)
		{
			SQL += MeterSurvey.DBToXLSColumnIndexMeter[i] + COMMA_SEP;
		}
		SQL += MeterSurvey.DBToXLSColumnIndexMeter[meterColumnLength - 1] +
				" FROM " + MeterSurvey.TABLE_NAME +
				" where " + MeterSurvey.D_ASSET_NO + " = \"" + assetNo +"\"";
		
		SQLiteDatabase db = getWritableDatabase();
		Cursor c = null;
		HashMap<String, String> meterColumnMap = new HashMap<String, String>();
		try
		{
			c = db.rawQuery(SQL, null);  
	        while (c.moveToNext())
	        {
	        	for (int i = 0; i < meterColumnLength; i ++)
	        	{
	        		meterColumnMap.put(MeterSurvey.DBToXLSColumnIndexMeter[i], c.getString(i));	        		
	        	}
	        }	
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (c != null)
			{
				c.close();	
			}
			
			db.close();
				
		}
        
        return meterColumnMap;  
	}
	
	
	/**
	 * 获取数据库内所有信息
	 * @return 获取数据库内所有信息,用hashmap表示信息行对象
	 */
	public ArrayList<HashMap<String, String>> getAllDatas()
	{
		String SQL = "SELECT ";
		int allColumnLength = MeterSurvey.DBToXLSColumnIndexAll.length;
		for (int i = 0 ; i < allColumnLength - 1 ; i ++)
		{
			SQL += MeterSurvey.DBToXLSColumnIndexAll[i] + COMMA_SEP;
		}
		SQL += MeterSurvey.DBToXLSColumnIndexAll[allColumnLength - 1] +
				" FROM " + MeterSurvey.TABLE_NAME;
		
		SQLiteDatabase db = getWritableDatabase();
		ArrayList<HashMap<String, String>> meterList = new ArrayList<HashMap<String, String>>();
		Cursor c = null;
		try
		{
			c = db.rawQuery(SQL, null);  
	        while (c.moveToNext())
	        {
	        	HashMap<String, String> meterColumnMap = new HashMap<String, String>();
	        	for (int i = 0; i < allColumnLength; i ++)
	        	{
	        		meterColumnMap.put(MeterSurvey.DBToXLSColumnIndexAll[i], c.getString(i));	        		
	        	}
	        	meterList.add(meterColumnMap);
	        }	
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (c != null)
			{
				c.close();	
			}
			
			db.close();
				
		}
        
        return meterList;  
	}
	
	/**
	 * 执行无数据集返回的sql语句
	 */
	private void executeNoDataSetSQL(String SQL)
	{
		SQLiteDatabase db = getWritableDatabase();
		
		try
		{
			db.execSQL(SQL);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			db.close();
		}
	}
	
	/**
	 * 提交一个计量箱的普查数据
	 * @param boxId 计量箱id
	 */
	public void commitOneBoxMeterSurvey(String boxId)
	{
		String SQL = "update " + MeterSurvey.TABLE_NAME + " set ";
		SQL += SurveyForm.COMMIT_STATUS + " = \"" + SurveyForm.COMMIT_STATUS_COMMITED + "\"";
		SQL += " where " + MeterSurvey.BAR_CODE + " = \"" + boxId +"\"";
		
		executeNoDataSetSQL(SQL);
	}
	
	/**
	 * 提交台区内所有未提交、已普查的表箱
	 * @param districtId 台区id
	 */
	public void commitAllUncommitedBoxMeterSurveyInDistrict(String districtId)
	{
		String SQL = "update " + MeterSurvey.TABLE_NAME + " set ";
		SQL += SurveyForm.COMMIT_STATUS + " = \"" + SurveyForm.COMMIT_STATUS_COMMITED + "\"";
		SQL += " where " + MeterSurvey.DISTRICT_ID + " = \"" + districtId +"\"";
		SQL += " and " + SurveyForm.SURVEY_STATUS + " = \"" + SurveyForm.SURVEY_STATUS_SURVEYED +"\"";
		SQL += " and " + SurveyForm.COMMIT_STATUS + " != \"" + SurveyForm.COMMIT_STATUS_COMMITED +"\"";
		
		executeNoDataSetSQL(SQL);
	}
	
	/**
	 * 批量删除未提交的表箱
	 * 关系变为异常，普查状态改成未普查
	 * @param boxIds 待删除的计量箱id数组
	 */
	public void deleteUncommitedBox(String[] boxIds)
	{
		String SQL = "update " + MeterSurvey.TABLE_NAME + " set ";
		SQL += SurveyForm.SURVEY_STATUS + " = \"" + SurveyForm.SURVEY_STATUS_UNSURVEYED + "\"";
		SQL += COMMA_SEP + SurveyForm.SURVEY_RELATION + " = \"" + SurveyForm.SURVEY_RELATION_ABNORMAL + "\"";
		SQL += " where " + MeterSurvey.BAR_CODE + " in " + SurveyForm.parseIdArray2SQLIds(boxIds);
		SQL += " and " + SurveyForm.COMMIT_STATUS + " = \"" + SurveyForm.COMMIT_STATUS_UNCOMMITED +"\"";
		
		executeNoDataSetSQL(SQL);
	}
	
	/**
	 * 批量删除已提交的表箱
	 * 仅修改提交状态为未提交
	 * @param boxIds 待删除的计量箱id数组
	 */
	public void deleteCommitedBox(String[] boxIds)
	{
		String SQL = "update " + MeterSurvey.TABLE_NAME + " set ";
		SQL += SurveyForm.COMMIT_STATUS + " = \"" + SurveyForm.COMMIT_STATUS_UNCOMMITED + "\"";
		SQL += " where " + MeterSurvey.BAR_CODE + " in " + SurveyForm.parseIdArray2SQLIds(boxIds);
		SQL += " and " + SurveyForm.COMMIT_STATUS + " = \"" + SurveyForm.COMMIT_STATUS_COMMITED +"\"";
		
		executeNoDataSetSQL(SQL);
	}
	
	/**
	 * 增加计量箱异常备注信息
	 * @param boxId 计量箱id
	 * @param comment 备注信息
	 */
	public void addBoxAbnormalComment(String boxId, String comment)
	{
		String SQL = "update " + MeterSurvey.TABLE_NAME + " set ";
		SQL += SurveyForm.ABNORMAL_COMMENT + " = \"" + comment + "\"";
		SQL += " where " + MeterSurvey.BAR_CODE + " = \"" + boxId +"\"";
		
		executeNoDataSetSQL(SQL);
	}
	
	/**
	 * 增加计量箱普查时间
	 * @param boxId 计量箱id
	 * @param time 普查时间
	 */
	public void addBoxSurveyTime(String boxId, String time)
	{
		String SQL = "update " + MeterSurvey.TABLE_NAME + " set ";
		SQL += MeterSurvey.SURVEY_TIME + " = \"" + time + "\"";
		SQL += " where " + MeterSurvey.BAR_CODE + " = \"" + boxId +"\"";
		
		executeNoDataSetSQL(SQL);
	}
	
	
	/////////////////////////////////////////“保存”相关的方法begin//////////////////////////////////////
	/**
	 * “保存”一个计量箱数据。
	 * 先将原始的计量箱数据清空
	 * 再根据传入的新的电表列表，或更新数据库中已有的电表数据，或新增一条电表数据
	 * @param box 计量箱信息
	 * @param meterList 电表列表
	 * @param districtId 台区id
	 */
	public void saveOneBoxMeterSurvey(HashMap<String, String> box,
							ArrayList<HashMap<String, String>> meterList,
							String districtId)
	{
		cleanMetersInBox(box.get(MeterSurvey.BAR_CODE));
		
		if (meterList.size() > 0)
		{
			updateAndInsertMetersToDB(box, meterList, districtId);	
		}
	}

	/**
	 * 更新电表列表中电表对应的表箱信息并将电表列表中原数据库中不存在的电表插入到数据库中
	 * 原数据库中已有的电表：已普查、是、未提交
	 * 原数据库中不存在的电表：已普查、新增、未提交
	 * @param box
	 * @param meterList
	 * @param districtId 台区id
	 */
	private void updateAndInsertMetersToDB(HashMap<String, String> box,
			ArrayList<HashMap<String, String>> meterList,
			String districtId) 
	{
		String meterIdsString = createMeterIdsListString(meterList);
		
		//必须先执行update，否则会造成把后insert的记录的普查关系也变更成“是”
		String updateSql = createUpdateMeterInIdSetSQL(box, meterIdsString);
		executeNoDataSetSQL(updateSql);
		
		//将新的电表数据插入数据库
		ArrayList<HashMap<String, String>> metersNotInDB = getMetersNotInDB(meterList, meterIdsString);
		String insertSQLStrings[] = createInsertMeterInIdSetSQL(box, metersNotInDB, districtId);
		
		for (int i = 0; i < insertSQLStrings.length; i ++)
		{
			executeNoDataSetSQL(insertSQLStrings[i]);
		}
	}
	
	/**
	 * 删除某计量箱里的所有电表信息，即把所有在某箱中的电表对应的表箱信息列全部清空
	 * @param boxId 计量箱ID
	 */
	public void cleanMetersInBox(String boxId)
	{
		String SQL = "update " + MeterSurvey.TABLE_NAME + " set ";
		int boxColumnLength = MeterSurvey.DBToXLSColumnIndexBox.length;
		
		for (int i = 0 ; i < boxColumnLength ; i ++)
		{
			if (!SurveyForm.COMMIT_STATUS.equals(MeterSurvey.DBToXLSColumnIndexBox[i]))
			{
				SQL += MeterSurvey.DBToXLSColumnIndexBox[i] + " = \"\"" + COMMA_SEP;	
			}
		}
		
		//更新相应的普查状态
		SQL += SurveyForm.COMMIT_STATUS + " = \"" + SurveyForm.COMMIT_STATUS_UNCOMMITED + "\"" + COMMA_SEP;
		SQL += SurveyForm.SURVEY_STATUS + " = \"" + SurveyForm.SURVEY_STATUS_UNSURVEYED + "\"" + COMMA_SEP;
		SQL += SurveyForm.SURVEY_RELATION + " = \"" + SurveyForm.SURVEY_RELATION_ABNORMAL + "\" ";
		SQL += "where " + MeterSurvey.BAR_CODE + " = \"" + boxId + "\"";
		
		executeNoDataSetSQL(SQL);
	}
	
	/**
	 * 获取不在数据库中的电表列表
	 * @param meterList 电表列表
	 * @param meterIdsString 电表("id,id,id...")字符串
	 * @return 获取不在数据库中的电表列表
	 */
	private ArrayList<HashMap<String, String>> getMetersNotInDB(ArrayList<HashMap<String, String>> meterList
																							,String meterIdsString)
	{
		ArrayList<HashMap<String, String>> metersNotInDBList = new ArrayList<HashMap<String, String>>();
		
		//现将所有数据加入的数组中
		for (int i = 0; i < meterList.size(); i ++)
		{
			metersNotInDBList.add(meterList.get(i));
		}
		
		String selectSql = createSelectMeterInIdSet(meterIdsString);
		
		SQLiteDatabase db = getWritableDatabase();
		Cursor c = null;
		try
		{
			c = db.rawQuery(selectSql, null);  
	        while (c.moveToNext())
	        {
	        	//从列表中移除数据库中已存在的项
	        	for (int i = 0; i < metersNotInDBList.size(); i ++)
	        	{
	        		if (c.getString(0).equals(metersNotInDBList.get(i).get(MeterSurvey.D_ASSET_NO)))
	        		{
	        			metersNotInDBList.remove(i);
	        			
	        			break;
	        		}
	        	}
	        }	
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (c != null)
			{
				c.close();	
			}
			
			db.close();
				
		}
		
		return metersNotInDBList;
	}

	/**
	 * 根据传入的id集合字符串，构建insert语句。
	 * 原数据库中已有的电表：已普查、新增、未提交
	 * 所有列的已DBToXLSColumnIndexAll数组为准，数据从box和电表中取
	 * DBToXLSColumnIndexBox、DBToXLSColumnIndexMeter
	 * @param box 计量箱信息
	 * @param meterList 待插入的电表列表
	 * @param districtId 台区id
	 * @return insert语句
	 */
	private String[] createInsertMeterInIdSetSQL(HashMap<String, String> box,
			ArrayList<HashMap<String, String>> meterList,
			String districtId) 
	{
		int meterListSize = meterList.size();
		String[] insertStrings = new String[meterListSize];
		
		int allColumnLength = MeterSurvey.DBToXLSColumnIndexAll.length;
		String columnName = null;
		String columnValue = null;
		HashMap<String, String> meter = null;
		
		for (int j = 0 ; j < meterListSize; j ++)
		{
			meter = meterList.get(j);
			insertStrings[j] = "INSERT INTO " + MeterSurvey.TABLE_NAME + " values(";
			
			for (int i = 0 ; i < allColumnLength ; i ++)
			{
				columnName = MeterSurvey.DBToXLSColumnIndexAll[i];
				
				//已普查、新增、未提交
				if (SurveyForm.COMMIT_STATUS.equals(columnName))
				{
					columnValue = SurveyForm.COMMIT_STATUS_UNCOMMITED;
				}
				else if (SurveyForm.SURVEY_STATUS.equals(columnName))
				{
					columnValue = SurveyForm.SURVEY_STATUS_SURVEYED;
				}
				else if (SurveyForm.SURVEY_RELATION.equals(columnName))
				{
					columnValue = SurveyForm.SURVEY_RELATION_NEW;
				}
				else if (MeterSurvey.DISTRICT_ID.equals(columnName))
				{
					columnValue = districtId;
				}
				else
				{
					//box的列名不可能跟meter的列名重复，故先再box里面找，找的了则认为就是正确的值。
					columnValue = box.get(columnName);
					if (null == columnValue)
					{
						columnValue = meter.get(columnName);
						
						if (null == columnValue)
						{
							columnValue = "";
						}
					}
				}
				
				insertStrings[j] += "\"" + columnValue + "\"" + COMMA_SEP;
			}
			
			//去掉最后一个逗号
			insertStrings[j] = insertStrings[j].substring(0, insertStrings[j].length() - 1) + ")";
		}
		
		return insertStrings;
	}
	
	/**
	 * 根据传入的id集合字符串，构建update语句。
	 * 原数据库中已有的电表：已普查、是、未提交
	 * @param box 计量箱信息
	 * @param meterIdsString 电表("id,id,id...")字符串
	 * @return update语句
	 */
	private String createUpdateMeterInIdSetSQL(HashMap<String, String> box,
			String meterIdsString) 
	{
		String updateSql = "UPDATE " + MeterSurvey.TABLE_NAME +	" SET ";
		
		int boxColumnLength = MeterSurvey.DBToXLSColumnIndexBox.length;
		String boxColumnValue = null;
		for (int i = 0 ; i < boxColumnLength ; i ++)
		{
			boxColumnValue = box.get(MeterSurvey.DBToXLSColumnIndexBox[i]);
			if (null == boxColumnValue)
			{
				boxColumnValue = "";
			}
			
			updateSql += MeterSurvey.DBToXLSColumnIndexBox[i] + " = \"" +
						boxColumnValue + "\"" + COMMA_SEP;
		}
		
		updateSql += SurveyForm.COMMIT_STATUS + " = \"" + SurveyForm.COMMIT_STATUS_UNCOMMITED + "\"" + COMMA_SEP;
		updateSql += SurveyForm.SURVEY_STATUS + " = \"" + SurveyForm.SURVEY_STATUS_SURVEYED + "\"" + COMMA_SEP;
		updateSql += SurveyForm.SURVEY_RELATION + " = \"" + SurveyForm.SURVEY_RELATION_YES + "\"";
		
		updateSql += " WHERE " + MeterSurvey.D_ASSET_NO + " IN " + meterIdsString;
		
		return updateSql;
	}

	/**
	 * 根据传入的id集合字符串，构建select语句
	 * @param meterIdsString 电表("id,id,id...")字符串
	 * @return select语句
	 */
	private String createSelectMeterInIdSet(String meterIdsString) 
	{
		String selectSql = "SELECT " + MeterSurvey.D_ASSET_NO + 
							" FROM " + MeterSurvey.TABLE_NAME + 
							" WHERE " + MeterSurvey.D_ASSET_NO + 
							" IN " + meterIdsString;
		return selectSql;
	}

	/**
	 * 根据传入的电表列表，构建电表("id,id,id...")字符串
	 * @param meterList 电表列表
	 * @return 电表("id,id,id...")字符串
	 */
	private String createMeterIdsListString(ArrayList<HashMap<String, String>> meterList)
	{
		int meterListSize = meterList.size();
		String meterIdsString = "(";
		for (int i = 0; i < meterListSize - 1; i ++)
		{
			meterIdsString += "\"" + meterList.get(i).get(MeterSurvey.D_ASSET_NO) + "\"" + COMMA_SEP;
		}
		
		meterIdsString += "\"" + meterList.get(meterListSize - 1).get(MeterSurvey.D_ASSET_NO) + "\")";
		
		Log.d(TAG, meterIdsString);
		return meterIdsString;
	}
	
	/////////////////////////////////////////“保存”相关的方法end//////////////////////////////////////	
	
	
	
}
