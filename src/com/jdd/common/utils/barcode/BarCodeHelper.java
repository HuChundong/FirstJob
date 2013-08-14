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
		
		// �����ս��ַ�Ϊ�س�
		mInstance.mBf.setTerminalChar(0);
		
		// ��UPC-A �롢EAN-13 �뼰39 �룬����ر�
//		mInstance.mBf.setBarcodeAll(new byte[] { 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0,0, 0 , 0 });
		
		// ��������֧�ֵ�����
		mInstance.mBf.setBarcodeOpenAll();
		
		mInstance.mBm = new BarcodeManager(context);
		
		mInstance.mBm.addListener(mInstance.mBarcodeLis);
	}
	
	private BarcodeListener mBarcodeLis = new BarcodeListener()
	{
		@Override
		public void barcodeEvent(BarcodeEvent event) 
		{
			// �������¼�������Ϊ��SCANNER_READ��ʱ�����в���
			if (event.getOrder().equals("SCANNER_READ"))
			{
				// ���� getBarcode()������ȡ������Ϣ
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
