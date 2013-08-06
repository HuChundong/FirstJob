package com.jdd.common.utils.barcode;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;


import mexxen.mx5010.barcode.BarcodeEvent;
import mexxen.mx5010.barcode.BarcodeListener;
import mexxen.mx5010.barcode.BarcodeManager;

public class GetBarCodeListener implements BarcodeListener
{
	List<WeakReference<OnBarCodeScanedListener>> mLis;
	
	BarcodeManager mBm;
	
	public GetBarCodeListener(BarcodeManager bm)
	{
		mBm = bm;
	}
	
	public void addLis(OnBarCodeScanedListener lis)
	{
		if( null == mLis )
		{
			mLis = new ArrayList<WeakReference<OnBarCodeScanedListener>>();
		}
		
		WeakReference<OnBarCodeScanedListener> w;
		
		for(int i = 0; i < mLis.size() ; i++ )
		{
			w = mLis.get(i);
			
			if( null != w && null != w.get() && w.get() == lis )
			{
				return;
			}
		}
		
		mLis.add(new WeakReference<OnBarCodeScanedListener>(lis));
	}
	
	public void removeLis(OnBarCodeScanedListener lis)
	{
		if( null == mLis )
		{
			mLis = new ArrayList<WeakReference<OnBarCodeScanedListener>>();
		}
		
		WeakReference<OnBarCodeScanedListener> w;
		
		for(int i = 0; i < mLis.size() ; i++ )
		{
			w = mLis.get(i);
			
			if( null != w && null != w.get() && w.get() == lis )
			{
				mLis.remove(i);
				
				return;
			}
		}
	}
	
	public void clear()
	{
		if( null == mLis )
		{
			return;
		}
		
		mLis.clear();
	}
	
	@Override
	public void barcodeEvent(BarcodeEvent arg0) 
	{
		Log.d("","zhou barcode  event " + arg0);
		
		if (arg0.getOrder().equals("SCANNER_READ")) 
		{
			String barcode = mBm.getBarcode();
			
			if( null == mLis )
			{
				return;
			}
			
			WeakReference<OnBarCodeScanedListener> w;
			
			OnBarCodeScanedListener ol ;
			
			for(int i = 0 ; i < mLis.size(); i ++ )
			{
				w = mLis.get(i);
				
				if(  null != w )
				{
					ol = w.get();
					
					if( null != ol )
					{
						ol.onScaned(barcode);
					}
				}
			}
		}
	}
}
