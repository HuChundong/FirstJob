package com.jdd.powermanager.model.MeterSurvey;

import java.util.ArrayList;
import java.util.HashMap;

import com.jdd.powermanager.model.MeterSurvey.BoxSurveyForm.BoxSurvey;
import com.jdd.powermanager.model.MeterSurvey.MeterSurveyForm.MeterSurvey;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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

	private static final String TAG = "BoxSurveyDBHelper";
	
	
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
	 * 获取任务内所有台区对象
	 * @return 获取任务内所有台区对象
	 */
	public ArrayList<HashMap<String, String>> getAllDistrict()
	{
		String SQL = "SELECT DISTINCT ";
		
		int districtColumnLength = BoxSurvey.DBToXLSColumnIndexDistrict.length;		
		for (int i = 0; i < districtColumnLength - 1; i ++)
		{
			SQL += BoxSurvey.DBToXLSColumnIndexDistrict[i] + COMMA_SEP;
		}		
		SQL += BoxSurvey.DBToXLSColumnIndexDistrict[districtColumnLength - 1];
			
		SQL += " FROM " + BoxSurvey.TABLE_NAME +
				" where" + SurveyForm.SURVEY_STATUS + " = \"" + 
				SurveyForm.SURVEY_STATUS_SURVEYED + "\"";
		
		SQLiteDatabase db = getWritableDatabase();
		
    	ArrayList<HashMap<String, String>> districtList = new ArrayList<HashMap<String, String>>();
		Cursor c = null;
		try
		{
			c = db.rawQuery(SQL, null);
	        while (c.moveToNext())
	        {
	        	HashMap<String, String> districtColumnMap = new HashMap<String, String>();
	        	for (int i = 0; i < districtColumnLength; i ++)
	        	{
	        		districtColumnMap.put(BoxSurvey.DBToXLSColumnIndexDistrict[i], c.getString(i));	        		
	        	}
	        	
	        	districtList.add(districtColumnMap);
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
	 * 获取某台区内所有已普查的计量箱列表
	 * @param districtID 台区id
	 * @param commitStatus 提交状态	0：返回所有的表箱	1：返回已提交表箱	2：返回未提交表箱
	 * @return 某台区内所有已普查计量箱列表,用hashmap表示计量箱对象
	 */
	public ArrayList<HashMap<String, String>> getAllSurveyedBoxesInDistrict(String districtID, int commitStatus)
	{
		String SQL = "SELECT DISTINCT ";
		int boxColumnLength = BoxSurvey.DBToXLSColumnIndexBox.length;
		for (int i = 0 ; i < boxColumnLength - 1 ; i ++)
		{
			SQL += BoxSurvey.DBToXLSColumnIndexBox[i] + COMMA_SEP;
		}
		SQL += BoxSurvey.DBToXLSColumnIndexBox[boxColumnLength - 1] +
				" FROM " + BoxSurvey.TABLE_NAME +
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
	        		boxColumnMap.put(BoxSurvey.DBToXLSColumnIndexBox[i], c.getString(i));	        		
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
	 * 根据传入的计量箱列表，构建计量箱("id,id,id...")字符串
	 * @param boxList 计量箱列表
	 * @return 计量箱("id,id,id...")字符串
	 */
	private String createBoxIdsListString(ArrayList<HashMap<String, String>> boxList)
	{
		int boxListSize = boxList.size();
		String boxIdsString = "(";
		for (int i = 0; i < boxListSize - 1; i ++)
		{
			boxIdsString += "\"" + boxList.get(i).get(BoxSurvey.ASSET_NO) + "\"" + COMMA_SEP;
		}
		
		boxIdsString += "\"" + boxList.get(boxListSize - 1).get(BoxSurvey.ASSET_NO) + "\")";
		
		return boxIdsString;
	}
	
	/**
	 * 根据传入的id集合字符串，构建update语句。
	 * 原数据库中已有的计量箱：已普查、是、未提交
	 * @param district 台区信息
	 * @param boxIdsString 计量箱("id,id,id...")字符串
	 * @return update语句
	 */
	private String createUpdateBoxInIdSetSQL(HashMap<String, String> district,
			String boxIdsString) 
	{
		String updateSql = "UPDATE " + BoxSurvey.TABLE_NAME +	" SET ";
		
		int districtColumnLength = BoxSurvey.DBToXLSColumnIndexDistrict.length;
		String districtColumnValue = null;
		for (int i = 0 ; i < districtColumnLength ; i ++)
		{
			districtColumnValue = district.get(BoxSurvey.DBToXLSColumnIndexDistrict[i]);
			if (null == districtColumnValue)
			{
				districtColumnValue = "";
			}
			
			updateSql += BoxSurvey.DBToXLSColumnIndexDistrict[i] + " = \"" +
						districtColumnValue + "\"" + COMMA_SEP;
		}
		
		updateSql += SurveyForm.COMMIT_STATUS + " = \"" + SurveyForm.COMMIT_STATUS_UNCOMMITED + "\"" + COMMA_SEP;
		updateSql += SurveyForm.SURVEY_STATUS + " = \"" + SurveyForm.SURVEY_STATUS_SURVEYED + "\"" + COMMA_SEP;
		updateSql += SurveyForm.SURVEY_RELATION + " = \"" + SurveyForm.SURVEY_RELATION_YES + "\"";
		
		updateSql += " WHERE " + BoxSurvey.ASSET_NO + " IN " + boxIdsString;
		
		Log.d(TAG, "updateSql is: " + updateSql);
		
		return updateSql;
	}
	
	/**
	 * 根据传入的id集合字符串，构建select语句
	 * @param boxIdsString 计量箱("id,id,id...")字符串
	 * @return select语句
	 */
	private String createSelectBoxInIdSet(String boxIdsString) 
	{
		String selectSql = "SELECT " + BoxSurvey.ASSET_NO + 
							" FROM " + BoxSurvey.TABLE_NAME + 
							" WHERE " + BoxSurvey.ASSET_NO + 
							" IN " + boxIdsString;
		return selectSql;
	}
	
	/**
	 * 获取不在数据库中的计量箱列表
	 * @param boxList 计量箱列表
	 * @param boxIdsString 计量箱("id,id,id...")字符串
	 * @return 获取不在数据库中的计量箱列表
	 */
	private ArrayList<HashMap<String, String>> getBoxesNotInDB(ArrayList<HashMap<String, String>> boxList
																							,String boxIdsString)
	{
		ArrayList<HashMap<String, String>> boxesNotInDBList = new ArrayList<HashMap<String, String>>();
		
		//先将所有数据加入的数组中
		for (int i = 0; i < boxList.size(); i ++)
		{
			boxesNotInDBList.add(boxList.get(i));
		}
		
		String selectSql = createSelectBoxInIdSet(boxIdsString);
		
		SQLiteDatabase db = getWritableDatabase();
		Cursor c = null;
		try
		{
			c = db.rawQuery(selectSql, null);  
	        while (c.moveToNext())
	        {
	        	//从列表中移除数据库中已存在的项
	        	for (int i = 0; i < boxesNotInDBList.size(); i ++)
	        	{
	        		if (c.getString(0).equals(boxesNotInDBList.get(i).get(BoxSurvey.ASSET_NO)))
	        		{
	        			boxesNotInDBList.remove(i);
	        			
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
		
		return boxesNotInDBList;
	}
	
	
	/**
	 * 根据传入的id集合字符串，构建insert语句。
	 * 原数据库中已有的电表：已普查、新增、未提交
	 * 所有列的已DBToXLSColumnIndexAll数组为准，数据从box和台区中取
	 * DBToXLSColumnIndexBox、DBToXLSColumnIndexDistrict
	 * @param district 台区信息
	 * @param boxList 待插入的计量箱列表
	 * @return insert语句
	 */
	private String[] createInsertBoxInIdSetSQL(HashMap<String, String> district,
			ArrayList<HashMap<String, String>> boxList) 
	{
		int boxListSize = boxList.size();
		String[] insertStrings = new String[boxListSize];
		
		int allColumnLength = BoxSurvey.DBToXLSColumnIndexAll.length;
		String columnName = null;
		String columnValue = null;
		HashMap<String, String> box = null;
		
		for (int j = 0 ; j < boxListSize; j ++)
		{
			box = boxList.get(j);
			insertStrings[j] = "INSERT INTO " + BoxSurvey.TABLE_NAME + " values(";
			
			for (int i = 0 ; i < allColumnLength ; i ++)
			{
				columnName = BoxSurvey.DBToXLSColumnIndexAll[i];
				
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
				else
				{
					//台区的列名不可能跟box的列名重复，故先再台区里面找，找的了则认为就是正确的值。
					columnValue = district.get(columnName);
					if (null == columnValue)
					{
						columnValue = box.get(columnName);
						
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
	 * 更新台区梳理任务中表箱对应的台区信息，并将电表列表中原数据库中不存在的电表插入到数据库中
	 * 原数据库中已有的电表：已普查、是、未提交
	 * 原数据库中不存在的电表：已普查、新增、未提交
	 * @param box
	 * @param meterList
	 */
	private void updateAndInsertBoxesToDB(HashMap<String, String> district,
			ArrayList<HashMap<String, String>> boxList) 
	{
		String boxIdsString = createBoxIdsListString(boxList);
		
		//必须先执行update，否则会造成把后insert的记录的普查关系也变更成“是”
		String updateSql = createUpdateBoxInIdSetSQL(district, boxIdsString);
		executeNoDataSetSQL(updateSql);
		
		//将新的电表数据插入数据库
		ArrayList<HashMap<String, String>> boxesNotInDB = getBoxesNotInDB(boxList, boxIdsString);
		String insertSQLStrings[] = createInsertBoxInIdSetSQL(district, boxesNotInDB);
		
		for (int i = 0; i < insertSQLStrings.length; i ++)
		{
			executeNoDataSetSQL(insertSQLStrings[i]);
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
		if (boxList.size() > 0)
		{
			updateAndInsertBoxesToDB(district, boxList);	
		}
	}

	/**
	 * 提交一组计量箱的普查数据
	 * @param boxIds 计量箱id组
	 */
	public void commitBoxesSurvey(String[] boxIds)
	{
		String SQL = "update " + BoxSurvey.TABLE_NAME + " set ";
		SQL += SurveyForm.COMMIT_STATUS + " = \"" + SurveyForm.COMMIT_STATUS_COMMITED + "\"";
		SQL += " where " + BoxSurvey.ASSET_NO + " in " + SurveyForm.parseIdArray2SQLIds(boxIds);
		
		executeNoDataSetSQL(SQL);
	}
	
	
	/**
	 * 批量删除未提交的表箱
	 * 关系变为异常，普查状态改成未普查
	 * @param boxIds 待删除的计量箱id数组
	 */
	public void deleteUncommitedBox(String[] boxIds)
	{
		String SQL = "update " + BoxSurvey.TABLE_NAME + " set ";
		SQL += SurveyForm.SURVEY_STATUS + " = \"" + SurveyForm.SURVEY_STATUS_UNSURVEYED + "\"";
		SQL += COMMA_SEP + SurveyForm.SURVEY_RELATION + " = \"" + SurveyForm.SURVEY_RELATION_ABNORMAL + "\"";
		SQL += " where " + BoxSurvey.ASSET_NO + " in " + SurveyForm.parseIdArray2SQLIds(boxIds);
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
		String SQL = "update " + BoxSurvey.TABLE_NAME + " set ";
		SQL += SurveyForm.COMMIT_STATUS + " = \"" + SurveyForm.COMMIT_STATUS_UNCOMMITED + "\"";
		SQL += " where " + BoxSurvey.ASSET_NO + " in " + SurveyForm.parseIdArray2SQLIds(boxIds);
		SQL += " and " + SurveyForm.COMMIT_STATUS + " = \"" + SurveyForm.COMMIT_STATUS_COMMITED +"\"";
		
		executeNoDataSetSQL(SQL);
	}
	
	/**
	 * 提交台区内所有未提交、已普查的表箱
	 * @param districtId 台区id
	 */
	public void commitAllUncommitedBoxSurveyInDistrict(String districtId)
	{
		String SQL = "update " + BoxSurvey.TABLE_NAME + " set ";
		SQL += SurveyForm.COMMIT_STATUS + " = \"" + SurveyForm.COMMIT_STATUS_COMMITED + "\"";
		SQL += " where " + BoxSurvey.DISTRICT_ID + " = \"" + districtId +"\"";
		SQL += " and " + SurveyForm.SURVEY_STATUS + " = \"" + SurveyForm.SURVEY_STATUS_SURVEYED +"\"";
		SQL += " and " + SurveyForm.COMMIT_STATUS + " != \"" + SurveyForm.COMMIT_STATUS_COMMITED +"\"";
		
		executeNoDataSetSQL(SQL);
	}
}
