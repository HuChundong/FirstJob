package com.jdd.common.utils.barcode;

import android.content.Context;
import android.util.Log;
import mexxen.mx5010.barcode.BarcodeConfig;
import mexxen.mx5010.barcode.BarcodeEvent;
import mexxen.mx5010.barcode.BarcodeListener;
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
		
		// 设置终结字符为回车
		mInstance.mBf.setTerminalChar(0);
		
		// 打开UPC-A 码、EAN-13 码及39 码，其余关闭
//		mInstance.mBf.setBarcodeAll(new byte[] { 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0,0, 0 , 0 });
		
		// 开启所有支持的条码
		mInstance.mBf.setBarcodeOpenAll();
		
		mInstance.mBm = new BarcodeManager(context);
		
		mInstance.mBm.addListener(mInstance.mBarcodeLis);
	}
	
	private BarcodeListener mBarcodeLis = new BarcodeListener()
	{
		@Override
		public void barcodeEvent(BarcodeEvent event) 
		{
			// 当条码事件的命令为“SCANNER_READ”时，进行操作
			if (event.getOrder().equals("SCANNER_READ"))
			{
				// 调用 getBarcode()方法读取条码信息
				String barcode = mInstance.mBm.getBarcode();
				
				Log.d("", "zhou -- barcodeEvent -- barcode = " + barcode);
				
				if( null  !=  mInstance.mLis )
				{
					Log.d("", "zhou -- barcodeEvent -- mLis = " + mLis);
					
					mInstance.mLis.onScaned(barcode);
				}
			}
		}
	};
	
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
		
		mInstance.mBm.removeListener(mInstance.mBarcodeLis);
		
		mInstance.mBm.dismiss();
	}
}
