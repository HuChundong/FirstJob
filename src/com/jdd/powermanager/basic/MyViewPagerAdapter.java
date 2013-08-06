package com.jdd.powermanager.basic;

import java.util.ArrayList;
import java.util.List;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

public class MyViewPagerAdapter extends PagerAdapter
{
	private List<View> mViews;
	
	private ViewPager mPager;
	
	public MyViewPagerAdapter(ViewPager v)
	{
		mPager = v;
	}
	
	public void addView(View v)
	{
		if( null == mViews )
		{
			mViews = new ArrayList<View>();
		}
		
		mViews.add(v);
	}
	
	public void removeView(int i)
	{
		// TODO
	}
	
	@Override
	public int getCount() 
	{
		return null == mViews ? 0 : mViews.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) 
	{
		return arg0 == arg1;
	}
	
	@Override
	public Object instantiateItem(ViewGroup container, int position) 
	{
		View v = null == mViews ? null : mViews.get(position);
		
		if( null != v )
		{
			mPager.addView(v);
		}
		
		return v;
	}
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) 
	{
//		super.destroyItem(container, position, object);
	}
}
