package com.jdd.powermanager.bean;

/**
 * 计量普查表行对象
 * @author Administrator
 *
 */
public class MeterRow {

	/**
	 * 表计ID
	 */
	private String mMeterID = null;
	
	/**
	 * 表计在计量箱中的行索引
	 */
	private int mRowIndex = 0;
	
	/**
	 * 表计在计量箱中的列索引
	 */
	private int mColumnIndex = 0;
	
	/**
	 * 关系
	 */
	private int mRelation = Param.MeterRowParam.SurveyRelation.Relation_ABNORMAL;
	
	/**
	 * 台区ID
	 */
	private String mDistrictID = null;
	
	/**
	 * 安装位置
	 */
	private String mInstallationLocation = null;
	
	/**
	 * 计量箱ID
	 */
	private String mMeterBoxID = null;
	
	/**
	 * 计量箱经度
	 */
	private String mMeterBoxLongitude = null;
	
	/**
	 * 计量箱纬度
	 */
	private String mMeterBoxLatitude = null;
	
	@SuppressWarnings("unused")
	private MeterRow(){}
	
	/**
	 * 要求必须有ID才能实例化该对象
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
	 * 设置表计在计量箱中的位置
	 * @param rowIndex 行索引
	 * @param columnIndex 列索引
	 */
	public void setMeterRowAndColumnIndex(int rowIndex, int columnIndex)
	{
		mRowIndex = rowIndex;
		mColumnIndex = columnIndex;
	}
	
}
