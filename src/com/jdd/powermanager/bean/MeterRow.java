package com.jdd.powermanager.bean;

/**
 * �����ղ���ж���
 * @author Administrator
 *
 */
public class MeterRow {

	/**
	 * ���ID
	 */
	private String mMeterID = null;
	
	/**
	 * ����ڼ������е�������
	 */
	private int mRowIndex = 0;
	
	/**
	 * ����ڼ������е�������
	 */
	private int mColumnIndex = 0;
	
	/**
	 * ��ϵ
	 */
	private int mRelation = Param.MeterRowParam.SurveyRelation.Relation_ABNORMAL;
	
	/**
	 * ̨��ID
	 */
	private String mDistrictID = null;
	
	/**
	 * ��װλ��
	 */
	private String mInstallationLocation = null;
	
	/**
	 * ������ID
	 */
	private String mMeterBoxID = null;
	
	/**
	 * �����侭��
	 */
	private String mMeterBoxLongitude = null;
	
	/**
	 * ������γ��
	 */
	private String mMeterBoxLatitude = null;
	
	@SuppressWarnings("unused")
	private MeterRow(){}
	
	/**
	 * Ҫ�������ID����ʵ�����ö���
	 * @param meterID
	 */
	public MeterRow(String meterID)
	{
		mMeterID = meterID;
	}

	public String getMeterID() 
	{
		return mMeterID;
	}

	public void setMeterID(String meterID) 
	{
		this.mMeterID = meterID;
	}

	public int getRowIndex() 
	{
		return mRowIndex;
	}

	public void setRowIndex(int rowIndex) 
	{
		this.mRowIndex = rowIndex;
	}

	public int getColumnIndex() 
	{
		return mColumnIndex;
	}

	public void setColumnIndex(int columnIndex) 
	{
		this.mColumnIndex = columnIndex;
	}

	public int getRelation() 
	{
		return mRelation;
	}

	public void setRelation(int relation) 
	{
		this.mRelation = relation;
	}

	public String getDistrictID() 
	{
		return mDistrictID;
	}

	public void setDistrictID(String districtID) 
	{
		this.mDistrictID = districtID;
	}

	public String getInstallationLocation() 
	{
		return mInstallationLocation;
	}

	public void setInstallationLocation(String installationLocation) 
	{
		this.mInstallationLocation = installationLocation;
	}

	public String getMeterBoxID() 
	{
		return mMeterBoxID;
	}

	public void setMeterBoxID(String meterBoxID) 
	{
		this.mMeterBoxID = meterBoxID;
	}

	public String getMeterBoxLongitude() 
	{
		return mMeterBoxLongitude;
	}

	public void setMeterBoxLongitude(String meterBoxLongitude) 
	{
		this.mMeterBoxLongitude = meterBoxLongitude;
	}

	public String getMeterBoxLatitude() 
	{
		return mMeterBoxLatitude;
	}

	public void setMeterBoxLatitude(String meterBoxLatitude) 
	{
		this.mMeterBoxLatitude = meterBoxLatitude;
	}
	
	/**
	 * ���ñ���ڼ������е�λ��
	 * @param rowIndex ������
	 * @param columnIndex ������
	 */
	public void setMeterRowAndColumnIndex(int rowIndex, int columnIndex)
	{
		mRowIndex = rowIndex;
		mColumnIndex = columnIndex;
	}
	
}
