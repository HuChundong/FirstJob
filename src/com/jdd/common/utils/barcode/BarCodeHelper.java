package com.jdd.common.utils.barcode;

import android.content.Context;
import mexxen.mx5010.barcode.BarcodeConfig;
import mexxen.mx5010.barcode.BarcodeManager;

public class BarCodeHelper 
{
	private BarcodeConfig mBf;
	
	private BarcodeManager mBm;
	
	private OnBarCodeScanedListener mLis;
	
	private static BarCodeHelper mInstance = new BarCodeHelper();
	
	public static void init(Context context)
	{
		mInstance.mBf = new BarcodeConfig(context);
		
		if( !mInstance.mBf.isScannerOn() )
		{
			mInstance.mBf.setScanner(true);
		}
		
		// �����ս��ַ�Ϊ�س�
		mInstance.mBf.setTerminalChar(0);
		
		// ��UPC-A �롢EAN-13 �뼰39 �룬����ر�
//		mInstance.mBf.setBarcodeAll(new byte[] { 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0,0, 0 , 0 });
		
		// ��������֧�ֵ�����
		mInstance.mBf.setBarcodeOpenAll();
		
		mInstance.mBm = new BarcodeManager(context);
	}
	
	public static void addListener(OnBarCodeScanedListener bl)
	{
		mInstance.mLis = bl;
	}
	
	public static void clearListener()
	{
		mInstance.mLis = null;
	}
	
	public static void release()
	{
		clearListener();
		
		mInstance.mBm.dismiss();
	}
}
