package com.jdd.powermanager.model.MeterSurvey;

import java.util.ArrayList;
import java.util.HashMap;

import com.jdd.powermanager.model.MeterSurvey.BoxSurveyForm.BoxSurvey;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BoxSurveyDBHelper extends SQLiteOpenHelper
{
	public static final int DATABASE_VERSION = 1;
	
    public static final String DATABASE_NAME = "Survey.db";
	
	private static final String TEXT_TYPE = " TEXT";
	
	private static final String INTEGER_TYPE = " INTEGER";
	
	private static final String COMMA_SEP = ",";
	
	private static String SQL_CREATE_ENTRIES = null;

	private static final String SQL_DELETE_ENTRIES =
	    "DROP TABLE IF EXISTS " + BoxSurvey.TABLE_NAME;
	
	
	public BoxSurveyDBHelper(Context context) 
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	@Override
	public void onCreate(SQLiteDatabase db) 
	{
		SQL_CREATE_ENTRIES = "CREATE TABLE " + BoxSurvey.TABLE_NAME + " (";
		int DBToXLSColumnIndexAllLength = BoxSurvey.DBToXLSColumnIndexAll.length;
		
		for (int i = 0; i < DBToXLSColumnIndexAllLength - 1; i ++)
		{
			SQL_CREATE_ENTRIES += BoxSurvey.DBToXLSColumnIndexAll[i] + TEXT_TYPE + COMMA_SEP;
		}
		
		SQL_CREATE_ENTRIES += BoxSurvey.DBToXLSColumnIndexAll[DBToXLSColumnIndexAllLength - 1] 
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
	 * 删除计量普查表
	 */
	public void cleanBoxSurveyTable()
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
	 * 数据插入到台区普查数据库
	 * @param allContentValues 数据对象
	 */
	public void insertBoxSurveyTable(ContentValues[] allContentValues)
	{
		SQLiteDatabase db = getWritableDatabase();
		
		try
		{
			for (int i = 0; i < allContentValues.length; i ++)
			{
				db.insert(
						BoxSurvey.TABLE_NAME,
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
		int allColumnLength = BoxSurvey.DBToXLSColumnIndexAll.length;
		for (int i = 0 ; i < allColumnLength - 1 ; i ++)
		{
			SQL += BoxSurvey.DBToXLSColumnIndexAll[i] + COMMA_SEP;
		}
		SQL += BoxSurvey.DBToXLSColumnIndexAll[allColumnLength - 1] +
				" FROM " + BoxSurvey.TABLE_NAME;
		
		SQLiteDatabase db = getWritableDatabase();
		ArrayList<HashMap<String, String>> boxList = new ArrayList<HashMap<String, String>>();
		Cursor c = null;
		try
		{
			c = db.rawQuery(SQL, null);  
	        while (c.moveToNext())
	        {
	        	HashMap<String, String> boxColumnMap = new HashMap<String, String>();
	        	for (int i = 0; i < allColumnLength; i ++)
	        	{
	        		boxColumnMap.put(BoxSurvey.DBToXLSColumnIndexAll[i], c.getString(i));	        		
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
	 * 获取任务内所有已普查的台区ID列表
	 * @return 已普查的台区ID列表
	 */
	public ArrayList<String> getAllSurveyedDistrict()
	{
		String SQL = "SELECT DISTINCT " + BoxSurvey.DISTRICT_ID;
		SQL += " FROM " + BoxSurvey.TABLE_NAME +
				" where" + SurveyForm.SURVEY_STATUS + " = \"" + 
				SurveyForm.SURVEY_STATUS_SURVEYED + "\"";
		
		SQLiteDatabase db = getWritableDatabase();
		ArrayList<String> districtList = new ArrayList<String>();
		Cursor c = null;
		try
		{
			c = db.rawQuery(SQL, null);  
	        while (c.moveToNext())
	        {
	        	districtList.add(c.getString(0));
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

}
