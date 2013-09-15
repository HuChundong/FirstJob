package com.jdd.powermanager.model.MeterSurvey;

import java.util.ArrayList;
import java.util.HashMap;

import com.jdd.powermanager.bean.District;
import com.jdd.powermanager.model.MeterSurvey.EliminateAbnormalForm.EliminateAbnormal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EliminateAbnormalDBHelper extends SQLiteOpenHelper 
{
	public static final int DATABASE_VERSION = 1;
	
    public static final String DATABASE_NAME = "Survey.db";
    
	private static final String TEXT_TYPE = " TEXT";
	
	private static final String COMMA_SEP = ",";
	
	private static String SQL_CREATE_ENTRIES = null;

	private static final String SQL_DELETE_ENTRIES =
	    "DROP TABLE IF EXISTS " + EliminateAbnormal.TABLE_NAME;
	
	public EliminateAbnormalDBHelper(Context context) 
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
    
	@Override
	public void onCreate(SQLiteDatabase db) {
		SQL_CREATE_ENTRIES = "CREATE TABLE " + EliminateAbnormal.TABLE_NAME + " (";
		int DBToXLSColumnIndexAllLength = EliminateAbnormal.DBToXLSColumnIndexAll.length;
		
		for (int i = 0; i < DBToXLSColumnIndexAllLength - 1; i ++)
		{
			SQL_CREATE_ENTRIES += EliminateAbnormal.DBToXLSColumnIndexAll[i] + TEXT_TYPE + COMMA_SEP;
		}
		
		SQL_CREATE_ENTRIES += EliminateAbnormal.DBToXLSColumnIndexAll[DBToXLSColumnIndexAllLength - 1] 
																					+ TEXT_TYPE + " )";
		db.execSQL(SQL_CREATE_ENTRIES);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
	}
	
	/**
	 * 删除异常消缺表
	 */
	public void cleanEliminateAbnormalTable()
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
	 * 数据插入到异常消缺数据库表
	 * @param allContentValues 数据对象
	 */
	public void insertEliminateAbnormalTable(ContentValues[] allContentValues)
	{
		SQLiteDatabase db = getWritableDatabase();
		
		try
		{
			for (int i = 0; i < allContentValues.length; i ++)
			{
				db.insert(
						EliminateAbnormal.TABLE_NAME,
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
	 * 获取数据库内所有信息
	 * @return 获取数据库内所有信息,用hashmap表示信息行对象
	 */
	public ArrayList<HashMap<String, String>> getAllDatas()
	{
		String SQL = "SELECT ";
		int allColumnLength = EliminateAbnormal.DBToXLSColumnIndexAll.length;
		for (int i = 0 ; i < allColumnLength - 1 ; i ++)
		{
			SQL += EliminateAbnormal.DBToXLSColumnIndexAll[i] + COMMA_SEP;
		}
		SQL += EliminateAbnormal.DBToXLSColumnIndexAll[allColumnLength - 1] +
				" FROM " + EliminateAbnormal.TABLE_NAME;
		
		SQLiteDatabase db = getWritableDatabase();
		ArrayList<HashMap<String, String>> eliminateAbnormalList = new ArrayList<HashMap<String, String>>();
		Cursor c = null;
		try
		{
			c = db.rawQuery(SQL, null);  
	        while (c.moveToNext())
	        {
	        	HashMap<String, String> eliminateAbnormalColumnMap = new HashMap<String, String>();
	        	for (int i = 0; i < allColumnLength; i ++)
	        	{
	        		eliminateAbnormalColumnMap.put(EliminateAbnormal.DBToXLSColumnIndexAll[i], c.getString(i));	        		
	        	}
	        	eliminateAbnormalList.add(eliminateAbnormalColumnMap);
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
        
        return eliminateAbnormalList;  
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
	 * 获取任务内所有台区对象
	 * @return 获取任务内所有台区对象
	 */
	public ArrayList<District> getAllDistrict()
	{
		String SQL = "SELECT DISTINCT " + EliminateAbnormal.DISTRICT_ID;
		
		SQL += " FROM " + EliminateAbnormal.TABLE_NAME;
		
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
	 * 根据电表资产编号，更新某列的值
	 * @param meterAssetNO 电表资产编号
	 * @param columnName 列名
	 * @param columnValue 值
	 */
	public void updateAColumnValueWithColumnNameAndMeterAssetNO(String meterAssetNO,
													String columnName, String columnValue)
	{
		String SQL = "update " + EliminateAbnormal.TABLE_NAME + " set ";
		SQL += columnName + " = \"" + columnValue + "\"";
		SQL += " where " + EliminateAbnormal.D_ASSET_NO + " = \"" + meterAssetNO +"\"";
		
		executeNoDataSetSQL(SQL);
	}
	
	/**
	 * 提交一个电表的异常消缺数据
	 * @param meterAssetNO 电表资产编号
	 */
	public void commitOneMeter(String meterAssetNO)
	{
		String SQL = "update " + EliminateAbnormal.TABLE_NAME + " set ";
		SQL += SurveyForm.COMMIT_STATUS + " = \"" + SurveyForm.COMMIT_STATUS_COMMITED + "\"";
		SQL += " where " + EliminateAbnormal.D_ASSET_NO + " = \"" + meterAssetNO +"\"";
		
		executeNoDataSetSQL(SQL);
	}
	
	/**
	 * 提交一组电表的异常消缺数据
	 * @param meterAssetNOs 电表资产编号数组
	 */
	public void commitMeters(String[] meterAssetNOs)
	{
		String SQL = "update " + EliminateAbnormal.TABLE_NAME + " set ";
		SQL += SurveyForm.COMMIT_STATUS + " = \"" + SurveyForm.COMMIT_STATUS_COMMITED + "\"";
		SQL += " where " + EliminateAbnormal.D_ASSET_NO + " in " + SurveyForm.parseIdArray2SQLIds(meterAssetNOs);
		
		executeNoDataSetSQL(SQL);
	}
	
	/**
	 * 取消提交一个电表的异常消缺数据
	 * @param meterAssetNO 电表资产编号
	 */
	public void uncommitOneMeter(String meterAssetNO)
	{
		String SQL = "update " + EliminateAbnormal.TABLE_NAME + " set ";
		SQL += SurveyForm.COMMIT_STATUS + " = \"" + SurveyForm.COMMIT_STATUS_UNCOMMITED + "\"";
		SQL += " where " + EliminateAbnormal.D_ASSET_NO + " = \"" + meterAssetNO +"\"";
		
		executeNoDataSetSQL(SQL);
	}
	
	/**
	 * 取消提交一组电表的异常消缺数据
	 * @param meterAssetNO 电表资产编号数组
	 */
	public void uncommitMeters(String[] meterAssetNOs)
	{
		String SQL = "update " + EliminateAbnormal.TABLE_NAME + " set ";
		SQL += SurveyForm.COMMIT_STATUS + " = \"" + SurveyForm.COMMIT_STATUS_UNCOMMITED + "\"";
		SQL += " where " + EliminateAbnormal.D_ASSET_NO + " in " + SurveyForm.parseIdArray2SQLIds(meterAssetNOs);
		
		executeNoDataSetSQL(SQL);
	}
	
	/**
	 * 提交所有已消缺的任务
	 * @param districtId 台区id
	 */
	public void commitAllEliminatedTasks(String districtId)
	{
		String SQL = "update " + EliminateAbnormal.TABLE_NAME + " set ";
		SQL += SurveyForm.COMMIT_STATUS + " = \"" + SurveyForm.COMMIT_STATUS_COMMITED + "\"";
		SQL += " where " + EliminateAbnormal.ELIMINATE_RESULT + " = \"" + SurveyForm.ELIMINATE_RESULT_ELIMINATED +"\"";
		SQL += " and " + EliminateAbnormal.DISTRICT_ID + " = \"" + districtId +"\"";
		
		executeNoDataSetSQL(SQL);
	}
	
	/**
	 * 消缺一个电表的异常消缺数据
	 * @param meterAssetNO 电表资产编号
	 */
	public void eliminateOneMeter(String meterAssetNO)
	{
		String SQL = "update " + EliminateAbnormal.TABLE_NAME + " set ";
		SQL += EliminateAbnormal.ELIMINATE_RESULT + " = \"" + SurveyForm.ELIMINATE_RESULT_ELIMINATED + "\"";
		SQL += " where " + EliminateAbnormal.D_ASSET_NO + " = \"" + meterAssetNO +"\"";
		
		executeNoDataSetSQL(SQL);
	}
	
	/**
	 * 取消消缺一个电表的异常消缺数据
	 * @param meterAssetNO 电表资产编号
	 */
	public void uneliminateOneMeter(String meterAssetNO)
	{
		String SQL = "update " + EliminateAbnormal.TABLE_NAME + " set ";
		SQL += EliminateAbnormal.ELIMINATE_RESULT + " = \"" + SurveyForm.ELIMINATE_RESULT_UNELIMINATE + "\"";
		SQL += " where " + EliminateAbnormal.D_ASSET_NO + " = \"" + meterAssetNO +"\"";
		
		executeNoDataSetSQL(SQL);
	}
	
	/**
	 * 取消消缺一组电表的异常消缺数据
	 * @param meterAssetNO 电表资产编号数组
	 */
	public void uneliminateMeters(String[] meterAssetNOs)
	{
		String SQL = "update " + EliminateAbnormal.TABLE_NAME + " set ";
		SQL += EliminateAbnormal.ELIMINATE_RESULT + " = \"" + SurveyForm.ELIMINATE_RESULT_UNELIMINATE + "\"";
		SQL += " where " + EliminateAbnormal.D_ASSET_NO + " in " + SurveyForm.parseIdArray2SQLIds(meterAssetNOs);
		
		executeNoDataSetSQL(SQL);
	}
	
	/**
	 * 根据提交状态返回消缺任务,仅返回已消缺的任务
	 * @param districtID 台区id
	 * @param commitStatus 提交状态 0：返回所有的消缺任务	1：返回已提交消缺任务	2：返回未提交消缺任务
	 * @return 相应提交状态的任务
	 */
	public ArrayList<HashMap<String, String>> getEliminateTasksWithSpecifiedCommitStatus(String districtID, int commitStatus)
	{
		String SQL = "SELECT ";
		int allColumnLength = EliminateAbnormal.DBToXLSColumnIndexAll.length;
		for (int i = 0 ; i < allColumnLength - 1 ; i ++)
		{
			SQL += EliminateAbnormal.DBToXLSColumnIndexAll[i] + COMMA_SEP;
		}
		SQL += EliminateAbnormal.DBToXLSColumnIndexAll[allColumnLength - 1] +
				" FROM " + EliminateAbnormal.TABLE_NAME +
				" where " + EliminateAbnormal.DISTRICT_ID + " = \"" + districtID +"\""
				+ " and " + EliminateAbnormal.ELIMINATE_RESULT + " = \"" + SurveyForm.ELIMINATE_RESULT_ELIMINATED + "\"";
		
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
		ArrayList<HashMap<String, String>> eliminateAbnormalList = new ArrayList<HashMap<String, String>>();
		Cursor c = null;
		try
		{
			c = db.rawQuery(SQL, null);  
	        while (c.moveToNext())
	        {
	        	HashMap<String, String> eliminateAbnormalColumnMap = new HashMap<String, String>();
	        	for (int i = 0; i < allColumnLength; i ++)
	        	{
	        		eliminateAbnormalColumnMap.put(EliminateAbnormal.DBToXLSColumnIndexAll[i], c.getString(i));	        		
	        	}
	        	eliminateAbnormalList.add(eliminateAbnormalColumnMap);
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
        
        return eliminateAbnormalList;  
	}
	
	/**
	 * 根据消缺状态返回消缺任务
	 * @param districtID 台区id
	 * @param commitStatus 提交状态 0：返回所有的消缺任务	1：返回已消缺消缺任务	2：返回未消缺消缺任务
	 * @return 相应消缺状态的任务
	 */
	public ArrayList<HashMap<String, String>> getEliminateTasksWithSpecifiedEliminateStatus(String districtID, int eliminateStatus)
	{
		String SQL = "SELECT ";
		int allColumnLength = EliminateAbnormal.DBToXLSColumnIndexAll.length;
		for (int i = 0 ; i < allColumnLength - 1 ; i ++)
		{
			SQL += EliminateAbnormal.DBToXLSColumnIndexAll[i] + COMMA_SEP;
		}
		SQL += EliminateAbnormal.DBToXLSColumnIndexAll[allColumnLength - 1] +
				" FROM " + EliminateAbnormal.TABLE_NAME
				+ " where " + EliminateAbnormal.DISTRICT_ID + " = \"" + districtID +"\"";
		
		//增加是否已提交的条件逻辑
		String eliminateStatusCondition = "";
		
		switch (eliminateStatus)
		{
			case 0:
				eliminateStatusCondition = "";
				break;
			case 1:
				eliminateStatusCondition = " where " + EliminateAbnormal.ELIMINATE_RESULT + 
										" = \"" + SurveyForm.ELIMINATE_RESULT_ELIMINATED + "\"";
				
				break;
			case 2:
				eliminateStatusCondition = " where " + EliminateAbnormal.ELIMINATE_RESULT + 
										" = \"" + SurveyForm.ELIMINATE_RESULT_UNELIMINATE + "\"";
				
				break;
			default:
				eliminateStatusCondition = "";
				break;
		}
		
		SQL += eliminateStatusCondition;
		
		SQLiteDatabase db = getWritableDatabase();
		ArrayList<HashMap<String, String>> eliminateAbnormalList = new ArrayList<HashMap<String, String>>();
		Cursor c = null;
		try
		{
			c = db.rawQuery(SQL, null);  
	        while (c.moveToNext())
	        {
	        	HashMap<String, String> eliminateAbnormalColumnMap = new HashMap<String, String>();
	        	for (int i = 0; i < allColumnLength; i ++)
	        	{
	        		eliminateAbnormalColumnMap.put(EliminateAbnormal.DBToXLSColumnIndexAll[i], c.getString(i));	        		
	        	}
	        	eliminateAbnormalList.add(eliminateAbnormalColumnMap);
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
        
        return eliminateAbnormalList;  
	}
	
	/**
	 * 获取某电表的异常消缺信息
	 * @return 某电表的异常消缺信息
	 */
	public HashMap<String, String> getEliminateTaskWithSpecifiedAssetNO(String meterAssetNO)
	{
		String SQL = "SELECT ";
		int allColumnLength = EliminateAbnormal.DBToXLSColumnIndexAll.length;
		for (int i = 0 ; i < allColumnLength - 1 ; i ++)
		{
			SQL += EliminateAbnormal.DBToXLSColumnIndexAll[i] + COMMA_SEP;
		}
		SQL += EliminateAbnormal.DBToXLSColumnIndexAll[allColumnLength - 1] +
				" FROM " + EliminateAbnormal.TABLE_NAME;
		SQL += " where " + EliminateAbnormal.D_ASSET_NO + " = \"" + meterAssetNO +"\"";
		
		SQLiteDatabase db = getWritableDatabase();
		HashMap<String, String> eliminateAbnormalColumnMap = new HashMap<String, String>();
		Cursor c = null;
		try
		{
			c = db.rawQuery(SQL, null);  
	        while (c.moveToNext())
	        {
	        	for (int i = 0; i < allColumnLength; i ++)
	        	{
	        		eliminateAbnormalColumnMap.put(EliminateAbnormal.DBToXLSColumnIndexAll[i], c.getString(i));	        		
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
        
        return eliminateAbnormalColumnMap;  
	}

}
