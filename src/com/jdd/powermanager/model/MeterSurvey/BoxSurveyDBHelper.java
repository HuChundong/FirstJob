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
	 * ִ�������ݼ����ص�sql���
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
	 * ɾ�������ղ��
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
	 * ���ݲ��뵽̨���ղ����ݿ�
	 * @param allContentValues ���ݶ���
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
	 * ��ȡ���ݿ���������Ϣ
	 * @return ��ȡ���ݿ���������Ϣ,��hashmap��ʾ��Ϣ�ж���
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
	 * ��ȡ����������̨������
	 * @return ��ȡ����������̨������
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
	 * ��ȡĳ̨�����������ղ�ļ������б�
	 * @param districtID ̨��id
	 * @param commitStatus �ύ״̬	0���������еı���	1���������ύ����	2������δ�ύ����
	 * @return ĳ̨�����������ղ�������б�,��hashmap��ʾ���������
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
		
		//�����Ƿ����ύ�������߼�
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
	 * ���ݴ���ļ������б�����������("id,id,id...")�ַ���
	 * @param boxList �������б�
	 * @return ������("id,id,id...")�ַ���
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
	 * ���ݴ����id�����ַ���������update��䡣
	 * ԭ���ݿ������еļ����䣺���ղ顢�ǡ�δ�ύ
	 * @param district ̨����Ϣ
	 * @param boxIdsString ������("id,id,id...")�ַ���
	 * @return update���
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
	 * ���ݴ����id�����ַ���������select���
	 * @param boxIdsString ������("id,id,id...")�ַ���
	 * @return select���
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
	 * ��ȡ�������ݿ��еļ������б�
	 * @param boxList �������б�
	 * @param boxIdsString ������("id,id,id...")�ַ���
	 * @return ��ȡ�������ݿ��еļ������б�
	 */
	private ArrayList<HashMap<String, String>> getBoxesNotInDB(ArrayList<HashMap<String, String>> boxList
																							,String boxIdsString)
	{
		ArrayList<HashMap<String, String>> boxesNotInDBList = new ArrayList<HashMap<String, String>>();
		
		//�Ƚ��������ݼ����������
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
	        	//���б����Ƴ����ݿ����Ѵ��ڵ���
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
	 * ���ݴ����id�����ַ���������insert��䡣
	 * ԭ���ݿ������еĵ�����ղ顢������δ�ύ
	 * �����е���DBToXLSColumnIndexAll����Ϊ׼�����ݴ�box��̨����ȡ
	 * DBToXLSColumnIndexBox��DBToXLSColumnIndexDistrict
	 * @param district ̨����Ϣ
	 * @param boxList ������ļ������б�
	 * @return insert���
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
				
				//���ղ顢������δ�ύ
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
					//̨�������������ܸ�box�������ظ���������̨�������ң��ҵ�������Ϊ������ȷ��ֵ��
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
			
			//ȥ�����һ������
			insertStrings[j] = insertStrings[j].substring(0, insertStrings[j].length() - 1) + ")";
		}
		
		return insertStrings;
	}
	
	
	/**
	 * ����̨�����������б����Ӧ��̨����Ϣ����������б���ԭ���ݿ��в����ڵĵ����뵽���ݿ���
	 * ԭ���ݿ������еĵ�����ղ顢�ǡ�δ�ύ
	 * ԭ���ݿ��в����ڵĵ�����ղ顢������δ�ύ
	 * @param box
	 * @param meterList
	 */
	private void updateAndInsertBoxesToDB(HashMap<String, String> district,
			ArrayList<HashMap<String, String>> boxList) 
	{
		String boxIdsString = createBoxIdsListString(boxList);
		
		//������ִ��update���������ɰѺ�insert�ļ�¼���ղ��ϵҲ����ɡ��ǡ�
		String updateSql = createUpdateBoxInIdSetSQL(district, boxIdsString);
		executeNoDataSetSQL(updateSql);
		
		//���µĵ�����ݲ������ݿ�
		ArrayList<HashMap<String, String>> boxesNotInDB = getBoxesNotInDB(boxList, boxIdsString);
		String insertSQLStrings[] = createInsertBoxInIdSetSQL(district, boxesNotInDB);
		
		for (int i = 0; i < insertSQLStrings.length; i ++)
		{
			executeNoDataSetSQL(insertSQLStrings[i]);
		}
	}
	
	/**
	 * �����桱һ�����������ݡ�
	 * ���ݴ�����µļ������б���������ݿ������еļ��������ݣ�������һ������������
	 * @param district ̨����Ϣ
	 * @param boxList �������б�
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
	 * �ύһ���������ղ�����
	 * @param boxIds ������id��
	 */
	public void commitBoxesSurvey(String[] boxIds)
	{
		String SQL = "update " + BoxSurvey.TABLE_NAME + " set ";
		SQL += SurveyForm.COMMIT_STATUS + " = \"" + SurveyForm.COMMIT_STATUS_COMMITED + "\"";
		SQL += " where " + BoxSurvey.ASSET_NO + " in " + SurveyForm.parseIdArray2SQLIds(boxIds);
		
		executeNoDataSetSQL(SQL);
	}
	
	
	/**
	 * ����ɾ��δ�ύ�ı���
	 * ��ϵ��Ϊ�쳣���ղ�״̬�ĳ�δ�ղ�
	 * @param boxIds ��ɾ���ļ�����id����
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
	 * ����ɾ�����ύ�ı���
	 * ���޸��ύ״̬Ϊδ�ύ
	 * @param boxIds ��ɾ���ļ�����id����
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
	 * �ύ̨��������δ�ύ�����ղ�ı���
	 * @param districtId ̨��id
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
