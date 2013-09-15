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
	 * 创建xls文件
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
     * 创建sheet
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
     * 获取xls文件对应的HSSFWorkbook对象
     * @return HSSFWorkbook
     */
    public HSSFWorkbook getWorkBook()
    {
    	return mWb;
    }
    
    /**
     * 保存到excel文件
     */
    public void saveToXlsFile()
    {
    	saveWorkBook(mWb);
    }
    
    /**
     * 解析xls文件对应的HSSFWorkbook对象
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
     * 根据sheet的名字获取sheet
     * @param sheetName sheet名称
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
     * 根据sheet的索引获取sheet
     * @param index sheet的索引
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
     * 根据sheet名字获取单元格
     * @param sheetName sheet名字
     * @param rowIndex 行索引
     * @param columnIndex 列索引
     * @return 单元格
     */
    public HSSFCell getCell(String sheetName, int rowIndex, int columnIndex)
    {
        return getCell(getSheet(sheetName), rowIndex, columnIndex);
    }
    
    /**
     * 根据sheet索引获取单元格
     * @param sheetIndex sheet索引
     * @param rowIndex 行索引
     * @param columnIndex 列索引
     * @return 单元格
     */
    public HSSFCell getCell(int sheetIndex, int rowIndex, int columnIndex)
    {
        return getCell(getSheet(sheetIndex), rowIndex, columnIndex);
    }
    
    /**
     * 根据sheet对象获取单元格
     * @param sheet sheet对象
     * @param rowIndex 行索引
     * @param columnIndex 列索引
     * @return 单元格
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
     * 保存HSSFWorkbook对象到文件
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
     * 将数据写入指定HSSFSheet对象位置
     * @param data 数据
     * @param sheet 需要写入的表页对象
     * @param rowIndex 行索引
     * @param columnIndex 列索引
     */
    private void writeSheetData(String data, HSSFSheet sheet, 
    										int rowIndex, int columnIndex)
    {
    	HSSFCell cell = getCell(sheet, rowIndex, columnIndex);
    	cell.setCellValue(data);
    }
    
    /**
     * 将数据写入指定位置，并保存到文件中
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
     * 某sheet下的所有行获取所有列
     * @param sheetIndex sheet索引
     * @return 所有列的数组
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
     * 获取xls文件中的某一行对象
     * @param sheet sheet对象
     * @param rowIndex 行索引
     * @return 行对象
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
     * 获取Excel超链接的样式
     * @return 样式
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
     * 保存一般的单元格样式
     * @param normalCellStyle 一般的单元格样式
     */
    public void saveNormalCellStyle(HSSFCellStyle normalCellStyle)
    {
    	mNormalCellStyle = normalCellStyle;
    }
    
    /**
     * 保存首行单元格样式
     * @param headLineCellStyle 首行单元格样式
     */
    public void saveHeadLineCellStyle(HSSFCellStyle headLineCellStyle)
    {
    	mHeadLineCellStyle = headLineCellStyle;
    }
    
    /**
     * 设置单元格的超链接路径
     * @param cell 单元格对象
     * @param path 路径
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
     * 设置首行的单元格值
     * @param cell 单元格对象
     * @param value 值
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
     * 设置普通的单元格值
     * @param cell 单元格对象
     * @param value 值
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
