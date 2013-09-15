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
	 * ɾ���쳣��ȱ��
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
	 * ���ݲ��뵽�쳣��ȱ���ݿ��
	 * @param allContentValues ���ݶ���
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
	 * ��ȡ���ݿ���������Ϣ
	 * @return ��ȡ���ݿ���������Ϣ,��hashmap��ʾ��Ϣ�ж���
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
	 * ��ȡ����������̨������
	 * @return ��ȡ����������̨������
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
	 * ���ݵ���ʲ���ţ�����ĳ�е�ֵ
	 * @param meterAssetNO ����ʲ����
	 * @param columnName ����
	 * @param columnValue ֵ
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
	 * �ύһ�������쳣��ȱ����
	 * @param meterAssetNO ����ʲ����
	 */
	public void commitOneMeter(String meterAssetNO)
	{
		String SQL = "update " + EliminateAbnormal.TABLE_NAME + " set ";
		SQL += SurveyForm.COMMIT_STATUS + " = \"" + SurveyForm.COMMIT_STATUS_COMMITED + "\"";
		SQL += " where " + EliminateAbnormal.D_ASSET_NO + " = \"" + meterAssetNO +"\"";
		
		executeNoDataSetSQL(SQL);
	}
	
	/**
	 * �ύһ������쳣��ȱ����
	 * @param meterAssetNOs ����ʲ��������
	 */
	public void commitMeters(String[] meterAssetNOs)
	{
		String SQL = "update " + EliminateAbnormal.TABLE_NAME + " set ";
		SQL += SurveyForm.COMMIT_STATUS + " = \"" + SurveyForm.COMMIT_STATUS_COMMITED + "\"";
		SQL += " where " + EliminateAbnormal.D_ASSET_NO + " in " + SurveyForm.parseIdArray2SQLIds(meterAssetNOs);
		
		executeNoDataSetSQL(SQL);
	}
	
	/**
	 * ȡ���ύһ�������쳣��ȱ����
	 * @param meterAssetNO ����ʲ����
	 */
	public void uncommitOneMeter(String meterAssetNO)
	{
		String SQL = "update " + EliminateAbnormal.TABLE_NAME + " set ";
		SQL += SurveyForm.COMMIT_STATUS + " = \"" + SurveyForm.COMMIT_STATUS_UNCOMMITED + "\"";
		SQL += " where " + EliminateAbnormal.D_ASSET_NO + " = \"" + meterAssetNO +"\"";
		
		executeNoDataSetSQL(SQL);
	}
	
	/**
	 * ȡ���ύһ������쳣��ȱ����
	 * @param meterAssetNO ����ʲ��������
	 */
	public void uncommitMeters(String[] meterAssetNOs)
	{
		String SQL = "update " + EliminateAbnormal.TABLE_NAME + " set ";
		SQL += SurveyForm.COMMIT_STATUS + " = \"" + SurveyForm.COMMIT_STATUS_UNCOMMITED + "\"";
		SQL += " where " + EliminateAbnormal.D_ASSET_NO + " in " + SurveyForm.parseIdArray2SQLIds(meterAssetNOs);
		
		executeNoDataSetSQL(SQL);
	}
	
	/**
	 * �ύ��������ȱ������
	 * @param districtId ̨��id
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
	 * ��ȱһ�������쳣��ȱ����
	 * @param meterAssetNO ����ʲ����
	 */
	public void eliminateOneMeter(String meterAssetNO)
	{
		String SQL = "update " + EliminateAbnormal.TABLE_NAME + " set ";
		SQL += EliminateAbnormal.ELIMINATE_RESULT + " = \"" + SurveyForm.ELIMINATE_RESULT_ELIMINATED + "\"";
		SQL += " where " + EliminateAbnormal.D_ASSET_NO + " = \"" + meterAssetNO +"\"";
		
		executeNoDataSetSQL(SQL);
	}
	
	/**
	 * ȡ����ȱһ�������쳣��ȱ����
	 * @param meterAssetNO ����ʲ����
	 */
	public void uneliminateOneMeter(String meterAssetNO)
	{
		String SQL = "update " + EliminateAbnormal.TABLE_NAME + " set ";
		SQL += EliminateAbnormal.ELIMINATE_RESULT + " = \"" + SurveyForm.ELIMINATE_RESULT_UNELIMINATE + "\"";
		SQL += " where " + EliminateAbnormal.D_ASSET_NO + " = \"" + meterAssetNO +"\"";
		
		executeNoDataSetSQL(SQL);
	}
	
	/**
	 * ȡ����ȱһ������쳣��ȱ����
	 * @param meterAssetNO ����ʲ��������
	 */
	public void uneliminateMeters(String[] meterAssetNOs)
	{
		String SQL = "update " + EliminateAbnormal.TABLE_NAME + " set ";
		SQL += EliminateAbnormal.ELIMINATE_RESULT + " = \"" + SurveyForm.ELIMINATE_RESULT_UNELIMINATE + "\"";
		SQL += " where " + EliminateAbnormal.D_ASSET_NO + " in " + SurveyForm.parseIdArray2SQLIds(meterAssetNOs);
		
		executeNoDataSetSQL(SQL);
	}
	
	/**
	 * �����ύ״̬������ȱ����,����������ȱ������
	 * @param districtID ̨��id
	 * @param commitStatus �ύ״̬ 0���������е���ȱ����	1���������ύ��ȱ����	2������δ�ύ��ȱ����
	 * @return ��Ӧ�ύ״̬������
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
	 * ������ȱ״̬������ȱ����
	 * @param districtID ̨��id
	 * @param commitStatus �ύ״̬ 0���������е���ȱ����	1����������ȱ��ȱ����	2������δ��ȱ��ȱ����
	 * @return ��Ӧ��ȱ״̬������
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
		
		//�����Ƿ����ύ�������߼�
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
	 * ��ȡĳ�����쳣��ȱ��Ϣ
	 * @return ĳ�����쳣��ȱ��Ϣ
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
