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
	 * ɾ�������ղ��
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
	 * ��ȡ����̨�������б�
	 * @return ���е�̨������
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
	 * ��ȡĳ̨�������е������б�
	 * @return ĳ̨�������е������б�,��hashmap��ʾ������(���Ұ��������ʲ������Ϣ)
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
	 * ��ȡĳ̨�����������ղ�������б�
	 * @return ĳ̨�����������ղ�������б�,��hashmap��ʾ���������
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
	 * ��ȡĳ̨�����������ղ�ļ������б�
	 * @param districtID ̨��id
	 * @param commitStatus �ύ״̬	0���������еı���	1���������ύ����	2������δ�ύ����
	 * @return ĳ̨�����������ղ�������б�,��hashmap��ʾ���������
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
	 * ��ȡĳ�����������е������б�
	 * @return �����������е������б�,��hashmap��ʾ������
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
	 * ��ȡĳ������
	 * @param assetNo ����ʲ����
	 * @return ������
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
	 * ��ȡ���ݿ���������Ϣ
	 * @return ��ȡ���ݿ���������Ϣ,��hashmap��ʾ��Ϣ�ж���
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
	 * �ύһ����������ղ�����
	 * @param boxId ������id
	 */
	public void commitOneBoxMeterSurvey(String boxId)
	{
		String SQL = "update " + MeterSurvey.TABLE_NAME + " set ";
		SQL += SurveyForm.COMMIT_STATUS + " = \"" + SurveyForm.COMMIT_STATUS_COMMITED + "\"";
		SQL += " where " + MeterSurvey.BAR_CODE + " = \"" + boxId +"\"";
		
		executeNoDataSetSQL(SQL);
	}
	
	/**
	 * �ύ̨��������δ�ύ�����ղ�ı���
	 * @param districtId ̨��id
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
	 * ����ɾ��δ�ύ�ı���
	 * ��ϵ��Ϊ�쳣���ղ�״̬�ĳ�δ�ղ�
	 * @param boxIds ��ɾ���ļ�����id����
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
	 * ����ɾ�����ύ�ı���
	 * ���޸��ύ״̬Ϊδ�ύ
	 * @param boxIds ��ɾ���ļ�����id����
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
	 * ���Ӽ������쳣��ע��Ϣ
	 * @param boxId ������id
	 * @param comment ��ע��Ϣ
	 */
	public void addBoxAbnormalComment(String boxId, String comment)
	{
		String SQL = "update " + MeterSurvey.TABLE_NAME + " set ";
		SQL += SurveyForm.ABNORMAL_COMMENT + " = \"" + comment + "\"";
		SQL += " where " + MeterSurvey.BAR_CODE + " = \"" + boxId +"\"";
		
		executeNoDataSetSQL(SQL);
	}
	
	/**
	 * ���Ӽ������ղ�ʱ��
	 * @param boxId ������id
	 * @param time �ղ�ʱ��
	 */
	public void addBoxSurveyTime(String boxId, String time)
	{
		String SQL = "update " + MeterSurvey.TABLE_NAME + " set ";
		SQL += MeterSurvey.SURVEY_TIME + " = \"" + time + "\"";
		SQL += " where " + MeterSurvey.BAR_CODE + " = \"" + boxId +"\"";
		
		executeNoDataSetSQL(SQL);
	}
	
	
	/////////////////////////////////////////�����桱��صķ���begin//////////////////////////////////////
	/**
	 * �����桱һ�����������ݡ�
	 * �Ƚ�ԭʼ�ļ������������
	 * �ٸ��ݴ�����µĵ���б���������ݿ������еĵ�����ݣ�������һ���������
	 * @param box ��������Ϣ
	 * @param meterList ����б�
	 * @param districtId ̨��id
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
	 * ���µ���б��е���Ӧ�ı�����Ϣ��������б���ԭ���ݿ��в����ڵĵ����뵽���ݿ���
	 * ԭ���ݿ������еĵ�����ղ顢�ǡ�δ�ύ
	 * ԭ���ݿ��в����ڵĵ�����ղ顢������δ�ύ
	 * @param box
	 * @param meterList
	 * @param districtId ̨��id
	 */
	private void updateAndInsertMetersToDB(HashMap<String, String> box,
			ArrayList<HashMap<String, String>> meterList,
			String districtId) 
	{
		String meterIdsString = createMeterIdsListString(meterList);
		
		//������ִ��update���������ɰѺ�insert�ļ�¼���ղ��ϵҲ����ɡ��ǡ�
		String updateSql = createUpdateMeterInIdSetSQL(box, meterIdsString);
		executeNoDataSetSQL(updateSql);
		
		//���µĵ�����ݲ������ݿ�
		ArrayList<HashMap<String, String>> metersNotInDB = getMetersNotInDB(meterList, meterIdsString);
		String insertSQLStrings[] = createInsertMeterInIdSetSQL(box, metersNotInDB, districtId);
		
		for (int i = 0; i < insertSQLStrings.length; i ++)
		{
			executeNoDataSetSQL(insertSQLStrings[i]);
		}
	}
	
	/**
	 * ɾ��ĳ������������е����Ϣ������������ĳ���еĵ���Ӧ�ı�����Ϣ��ȫ�����
	 * @param boxId ������ID
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
		
		//������Ӧ���ղ�״̬
		SQL += SurveyForm.COMMIT_STATUS + " = \"" + SurveyForm.COMMIT_STATUS_UNCOMMITED + "\"" + COMMA_SEP;
		SQL += SurveyForm.SURVEY_STATUS + " = \"" + SurveyForm.SURVEY_STATUS_UNSURVEYED + "\"" + COMMA_SEP;
		SQL += SurveyForm.SURVEY_RELATION + " = \"" + SurveyForm.SURVEY_RELATION_ABNORMAL + "\" ";
		SQL += "where " + MeterSurvey.BAR_CODE + " = \"" + boxId + "\"";
		
		executeNoDataSetSQL(SQL);
	}
	
	/**
	 * ��ȡ�������ݿ��еĵ���б�
	 * @param meterList ����б�
	 * @param meterIdsString ���("id,id,id...")�ַ���
	 * @return ��ȡ�������ݿ��еĵ���б�
	 */
	private ArrayList<HashMap<String, String>> getMetersNotInDB(ArrayList<HashMap<String, String>> meterList
																							,String meterIdsString)
	{
		ArrayList<HashMap<String, String>> metersNotInDBList = new ArrayList<HashMap<String, String>>();
		
		//�ֽ��������ݼ����������
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
	        	//���б����Ƴ����ݿ����Ѵ��ڵ���
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
	 * ���ݴ����id�����ַ���������insert��䡣
	 * ԭ���ݿ������еĵ�����ղ顢������δ�ύ
	 * �����е���DBToXLSColumnIndexAll����Ϊ׼�����ݴ�box�͵����ȡ
	 * DBToXLSColumnIndexBox��DBToXLSColumnIndexMeter
	 * @param box ��������Ϣ
	 * @param meterList ������ĵ���б�
	 * @param districtId ̨��id
	 * @return insert���
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
				else if (MeterSurvey.DISTRICT_ID.equals(columnName))
				{
					columnValue = districtId;
				}
				else
				{
					//box�����������ܸ�meter�������ظ���������box�����ң��ҵ�������Ϊ������ȷ��ֵ��
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
			
			//ȥ�����һ������
			insertStrings[j] = insertStrings[j].substring(0, insertStrings[j].length() - 1) + ")";
		}
		
		return insertStrings;
	}
	
	/**
	 * ���ݴ����id�����ַ���������update��䡣
	 * ԭ���ݿ������еĵ�����ղ顢�ǡ�δ�ύ
	 * @param box ��������Ϣ
	 * @param meterIdsString ���("id,id,id...")�ַ���
	 * @return update���
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
	 * ���ݴ����id�����ַ���������select���
	 * @param meterIdsString ���("id,id,id...")�ַ���
	 * @return select���
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
	 * ���ݴ���ĵ���б��������("id,id,id...")�ַ���
	 * @param meterList ����б�
	 * @return ���("id,id,id...")�ַ���
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
	
	/////////////////////////////////////////�����桱��صķ���end//////////////////////////////////////	
	
	
	
}
