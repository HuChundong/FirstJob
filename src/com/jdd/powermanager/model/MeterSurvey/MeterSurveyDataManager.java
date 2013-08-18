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
	 * ��ȡ�����ղ������ļ�·��
	 * @return �����ղ������ļ�·��
	 */
	private String getMeterSurveyTaskFilePath()
	{
		return SD + FOLDER + METER_SURVEY_FILE;
	}
	
	/**
	 * ��ʼ�������ղ�
	 */
	protected void initSurveyTask()
	{
		mMeterSurveyDBHelper = new MeterSurveyDBHelper(mContext);
		
		//����ɾ���ϱ�
		mMeterSurveyDBHelper.cleanMeterSurveyTable();
		
		//��excel���ݵ������ݿ�		
		parseMeterSurveyXLSToDB();
		
	}
	

	/**
	 * �������ղ�excel�е����ݼ��ص������ղ����ݿ�
	 */
	private void parseMeterSurveyXLSToDB()
	{
		XLS xls = new XLS(getMeterSurveyTaskFilePath());
		HSSFRow[] allRows = xls.getAllRows(0);
		ContentValues[] allContentValues = new ContentValues[allRows.length - 1];
		
		//i=1�ӵڶ������������ݿ�ʼ
		for (int i = 1; i < allRows.length; i ++)
		{
			allContentValues[i - 1] = parseExcelRowToContentValues(allRows[i], 
												MeterSurvey.DBToXLSColumnIndexAll);
		}
		
		mMeterSurveyDBHelper.insertMeterSurveyTable(allContentValues);
	}
	
	/**
	 * �����ݿ��е����ݼ��ص�excel
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
	 * ��ȡ����̨�������б�
	 * @return ���е�̨������
	 */
	public ArrayList<District> getAllDistrict()
	{
		synchronized(instance)
		{
			return mMeterSurveyDBHelper.getAllDistrict();
		}
	}
	
	/**
	 * ��ȡĳ̨�����������ղ�������б�
	 * @return ĳ̨�����������ղ�������б�,��hashmap��ʾ���������
	 */
	public ArrayList<HashMap<String, String>> getAllBoxesInDistrict(String districtID)
	{
		synchronized(instance)
		{
			return mMeterSurveyDBHelper.getAllBoxesInDistrict(districtID);
		}
	}
	
	/**
	 * �����桱һ�����������ݡ�
	 * @param box ��������Ϣ
	 * @param meterList ����б�
	 * @param districtId ̨��id
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
	 * �ύһ����������ղ�����
	 * @param boxId ������id
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
	 * ��ȡĳ̨�������е������б�
	 * @return ĳ̨�������е������б�,��hashmap��ʾ������
	 */
	public ArrayList<HashMap<String, String>> getAllMetersInDistrict(String districtID)
	{
		synchronized(instance)
		{
			return mMeterSurveyDBHelper.getAllMetersInDistrict(districtID);
		}
	}
	
	/**
	 * �ύ̨��������δ�ύ�����ղ�ı���
	 * @param districtId ̨��id
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
	 * ����ɾ��δ�ύ�ı���
	 * ��ϵ��Ϊ�쳣���ղ�״̬�ĳ�δ�ղ�
	 * @param boxIds ��ɾ���ļ�����id����
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
	 * ����ɾ�����ύ�ı���
	 * ���޸��ύ״̬Ϊδ�ύ
	 * @param boxIds ��ɾ���ļ�����id����
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
	 * ���Ӽ������쳣��ע��Ϣ
	 * @param boxId ������id
	 * @param comment ��ע��Ϣ
	 */
	public void addBoxAbnormalComment(String boxId, String comment)
	{
		synchronized(instance)
		{
			mMeterSurveyDBHelper.addBoxAbnormalComment(boxId, comment);
		}
	}
	
	/**
	 * ���Ӽ������ղ�ʱ��
	 * @param boxId ������id
	 * @param time �ղ�ʱ��
	 */
	public void addBoxSurveyTime(String boxId, String time)
	{
		synchronized(instance)
		{
			mMeterSurveyDBHelper.addBoxSurveyTime(boxId, time);
		}
	}
	
	/**
	 * ��ȡĳ̨�����������ղ�ļ������б�
	 * @param districtID ̨��id
	 * @param commitStatus �ύ״̬	0���������еı���	1���������ύ����	2������δ�ύ����
	 * @return ĳ̨�����������ղ�������б�,��hashmap��ʾ���������
	 */
	public ArrayList<HashMap<String, String>> getAllSurveyedBoxesInDistrict(String districtID, int commitStatus)
	{
		synchronized(instance)
		{
			return mMeterSurveyDBHelper.getAllSurveyedBoxesInDistrict(districtID, commitStatus);
		}
	}
	
	/**
	 * ��ȡĳ�����������е������б�
	 * @return �����������е������б�,��hashmap��ʾ������
	 */
	public ArrayList<HashMap<String, String>> getAllMetersInBox(String boxID)
	{
		synchronized(instance)
		{
			return mMeterSurveyDBHelper.getAllMetersInBox(boxID);
		}
	}
	
	/**
	 * ��ȡĳ������
	 * @param assetNo ����ʲ����
	 * @return ������
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
		addBoxAbnormalComment("370130005097597", "������");
		addBoxSurveyTime("370130005097597", "2013/08/03");
		parseDBToXLS();
	}
	
	private void test7()
	{
		ArrayList<HashMap<String, String>> list = getAllSurveyedBoxesInDistrict("0000853828", 2);
	}
	
	///////////////////////////////////////////UT test end/////////////////////////////////////
}
