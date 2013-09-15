package com.jdd.common.utils.Excel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFHyperlink;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import android.util.Log;

public class XLS {

	private static final String TAG = "XLS";
	
	private String mFilePath = null;
	
	HSSFWorkbook mWb = null;

	private HSSFCellStyle mHlinkStyle = null;

	private HSSFCellStyle mHeadLineCellStyle = null;
	
	private HSSFCellStyle mNormalCellStyle = null;
	
	private XLS()
	{
		
	}
	
	public XLS(String filePath)
	{
		mFilePath = filePath;
		mWb = parseWorkBook();
	}
	
	/**
	 * ����xls�ļ�
	 */
	public void createXLSFile() 
	{
	    HSSFWorkbook wb = new HSSFWorkbook();
	    try 
	    {
	        FileOutputStream fileOut = new FileOutputStream(mFilePath);
	        wb.write(fileOut);
	        fileOut.close();
	    } 
	    catch (FileNotFoundException ex) 
	    {
	        ex.printStackTrace();
	    } 
	    catch (IOException ex) 
	    {
	    	ex.printStackTrace();
	    }
	}
	
	/**
     * ����sheet
     */
    public void newSheet(String sheetName) 
    {
        HSSFWorkbook wb = new HSSFWorkbook();
        wb.createSheet(sheetName);
        try 
        {
            FileOutputStream fileOut = new FileOutputStream(mFilePath);
            wb.write(fileOut);
            fileOut.close();
        } 
        catch (FileNotFoundException ex) 
        {
        	ex.printStackTrace();
        } 
        catch (IOException ex) 
        {
        	ex.printStackTrace();
        }
    }
    
    /**
     * ��ȡxls�ļ���Ӧ��HSSFWorkbook����
     * @return HSSFWorkbook
     */
    public HSSFWorkbook getWorkBook()
    {
    	return mWb;
    }
    
    /**
     * ���浽excel�ļ�
     */
    public void saveToXlsFile()
    {
    	saveWorkBook(mWb);
    }
    
    /**
     * ����xls�ļ���Ӧ��HSSFWorkbook����
     * @return HSSFWorkbook
     */
    private HSSFWorkbook parseWorkBook()
    {
        try 
        {
            FileInputStream fileIn = new FileInputStream(mFilePath);
            HSSFWorkbook wb = new HSSFWorkbook(fileIn);
            fileIn.close();
            
            return wb;
        } 
        catch (IOException ex)
        {
        	ex.printStackTrace();
        	
            return null;
        }
    }
    
    /**
     * ����sheet�����ֻ�ȡsheet
     * @param sheetName sheet����
     * @return sheet
     */
    public HSSFSheet getSheet(String sheetName)
    {
    	if (mWb != null)
    	{
    		return mWb.getSheet(sheetName);
    	}
    	
    	return null;
    }
    
    /**
     * ����sheet��������ȡsheet
     * @param index sheet������
     * @return sheet
     */
    public HSSFSheet getSheet(int index)
    {
    	if (mWb != null)
    	{
    		return mWb.getSheetAt(index);
    	}
    	
    	return null;
    }
    
    /**
     * ����sheet���ֻ�ȡ��Ԫ��
     * @param sheetName sheet����
     * @param rowIndex ������
     * @param columnIndex ������
     * @return ��Ԫ��
     */
    public HSSFCell getCell(String sheetName, int rowIndex, int columnIndex)
    {
        return getCell(getSheet(sheetName), rowIndex, columnIndex);
    }
    
    /**
     * ����sheet������ȡ��Ԫ��
     * @param sheetIndex sheet����
     * @param rowIndex ������
     * @param columnIndex ������
     * @return ��Ԫ��
     */
    public HSSFCell getCell(int sheetIndex, int rowIndex, int columnIndex)
    {
        return getCell(getSheet(sheetIndex), rowIndex, columnIndex);
    }
    
    /**
     * ����sheet�����ȡ��Ԫ��
     * @param sheet sheet����
     * @param rowIndex ������
     * @param columnIndex ������
     * @return ��Ԫ��
     */
    public HSSFCell getCell(HSSFSheet sheet, int rowIndex, int columnIndex)
    {
        HSSFRow row = sheet.getRow(rowIndex);
        if (row == null) 
        {
            row = sheet.createRow(rowIndex);
        }
        
        HSSFCell cell = row.getCell(columnIndex);
        if (cell == null) 
        {
            cell = row.createCell( columnIndex);
        }
        
        return cell;
    }
    
    /**
     * ����HSSFWorkbook�����ļ�
     * @param wb
     */
    private void saveWorkBook(HSSFWorkbook wb) 
    {
        try 
        {
            FileOutputStream fileOut = new FileOutputStream(mFilePath);
            wb.write(fileOut);
            fileOut.close();
        }
        catch (FileNotFoundException ex)
        {
        	ex.printStackTrace();
        } 
        catch (IOException ex)
        {
        	ex.printStackTrace();
        }
    }
    
	/**
     * ������д��ָ��HSSFSheet����λ��
     * @param data ����
     * @param sheet ��Ҫд��ı�ҳ����
     * @param rowIndex ������
     * @param columnIndex ������
     */
    private void writeSheetData(String data, HSSFSheet sheet, 
    										int rowIndex, int columnIndex)
    {
    	HSSFCell cell = getCell(sheet, rowIndex, columnIndex);
    	cell.setCellValue(data);
    }
    
    /**
     * ������д��ָ��λ�ã������浽�ļ���
     * @param data
     * @param sheetIndex
     * @param rowIndex
     * @param columnIndex
     */
    public void writeData(String data, int sheetIndex, 
    								int rowIndex, int columnIndex)
    {
        if( mWb == null)
        {
        	Log.e(TAG, "JDD writeData can't get workBook");
        	
            return;
        }
        
        HSSFSheet sheet = mWb.getSheetAt(sheetIndex);
        
        if (sheet == null)
        {
            sheet = mWb.createSheet(sheetIndex + "");
            
            Log.d(TAG, "JDD writeData createSheet name is: " + sheetIndex);
        }
        
        writeSheetData(data, sheet, rowIndex, columnIndex);
    }
    
    /**
     * ĳsheet�µ������л�ȡ������
     * @param sheetIndex sheet����
     * @return �����е�����
     */
    public HSSFRow[] getAllRows(int sheetIndex)
    {
    	HSSFSheet sheet = getSheet(sheetIndex);
    	int firstRowNum = sheet.getFirstRowNum();
    	int lastRowNum = sheet.getLastRowNum();
    	int rowNum = lastRowNum - firstRowNum + 1;
    	
    	HSSFRow[] rows = new HSSFRow[rowNum];
    	
    	for (int i = 0; i < rowNum; i ++)
    	{
    		rows[i] = sheet.getRow(firstRowNum + i);
    	}
    	
    	return rows;
    }
    
    /**
     * ��ȡxls�ļ��е�ĳһ�ж���
     * @param sheet sheet����
     * @param rowIndex ������
     * @return �ж���
     */
    public HSSFRow getRow(HSSFSheet sheet, int rowIndex)
    {
        HSSFRow row = sheet.getRow(rowIndex);
        if (row == null) 
        {
            row = sheet.createRow(rowIndex);
        }
        
        return row;
    }
    
    /**
     * ��ȡExcel�����ӵ���ʽ
     * @return ��ʽ
     */
    public HSSFCellStyle getHLinkStyle()
    {
    	if (null == mHlinkStyle)
    	{
    		mHlinkStyle  = mWb.createCellStyle();
            HSSFFont hlink_font = mWb.createFont();
            hlink_font.setUnderline(HSSFFont.U_SINGLE);
            hlink_font.setColor(HSSFColor.BLUE.index);
            mHlinkStyle.setFont(hlink_font);	
    	}
        
        return mHlinkStyle;
    }
    
    /**
     * ����һ��ĵ�Ԫ����ʽ
     * @param normalCellStyle һ��ĵ�Ԫ����ʽ
     */
    public void saveNormalCellStyle(HSSFCellStyle normalCellStyle)
    {
    	mNormalCellStyle = normalCellStyle;
    }
    
    /**
     * �������е�Ԫ����ʽ
     * @param headLineCellStyle ���е�Ԫ����ʽ
     */
    public void saveHeadLineCellStyle(HSSFCellStyle headLineCellStyle)
    {
    	mHeadLineCellStyle = headLineCellStyle;
    }
    
    /**
     * ���õ�Ԫ��ĳ�����·��
     * @param cell ��Ԫ�����
     * @param path ·��
     */
    public void setCellHLinkPath(HSSFCell cell, String path)
    {
    	if (null == cell || null == path)
    	{
    		return;
    	}
    	
    	HSSFCellStyle hlinkStyle = getHLinkStyle();    	
    	cell.setCellStyle(hlinkStyle);
    	cell.setCellValue(path);
    	
    	HSSFHyperlink hyperlink = new HSSFHyperlink(HSSFHyperlink.LINK_FILE);
    	hyperlink.setAddress(path);
		cell.setHyperlink(hyperlink);
		
    }
    
    /**
     * �������еĵ�Ԫ��ֵ
     * @param cell ��Ԫ�����
     * @param value ֵ
     */
    public void setHeadLineCellValue(HSSFCell cell, String value)
    {
    	if (null == cell || null == value)
    	{
    		return;
    	}
    	
    	cell.setCellStyle(mHeadLineCellStyle);
    	cell.setCellValue(value);    	
    }
    
    /**
     * ������ͨ�ĵ�Ԫ��ֵ
     * @param cell ��Ԫ�����
     * @param value ֵ
     */
    public void setNormalCellValue(HSSFCell cell, String value)
    {
    	if (null == cell || null == value)
    	{
    		return;
    	}
    	
    	cell.setCellStyle(mNormalCellStyle);
    	cell.setCellValue(value);    	
    }
}
