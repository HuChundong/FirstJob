package com.jdd.powermanager.ui.abnormalelimination.eliminate;

import java.util.ArrayList;
import java.util.List;

import com.jdd.powermanager.R;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

public class LocalSpinnerAdapter implements SpinnerAdapter
{
	List<String> mMenus;
	
	private Context mContext;
	
	private int mSelectedIndex;
	
	public void setItems(List<String> is)
	{
		if( null == is )
		{
			return;
		}
		
		mMenus = is;
		
		mMenus.add(0, mContext.getString(R.string.diy));
	}
	
	public void setItems(String[] is)
	{
		if( null == is || is.length == 0 )
		{
			return;
		}
		
		if( null == mMenus )
		{
			mMenus = new ArrayList<String>();
		}
		
		mMenus.clear();
		
		mMenus.add(0, mContext.getString(R.string.diy));
		
		for(int i = 0 ; i < is.length ; i++)
		{
			mMenus.add(is[i]);
		}
	}
	
	public void setSelectedIndex(int i)
	{
		mSelectedIndex = i;
	}
	
	public int getSelectedIndex()
	{
		return mSelectedIndex;
	}
	
	public LocalSpinnerAdapter(Context context)
	{
		mContext = context;
	}
	
	@Override
	public int getCount() 
	{
		return null == mMenus ? 0 : mMenus.size();
	}

	@Override
	public Object getItem(int i) 
	{
		if( null == mMenus || i < 0 || i > mMenus.size() - 1 )
		{
			return null;
		}
		
		return mMenus.get(i);
	}
	
	public int addItem(String item)
	{
		if( null == mMenus )
		{
			mMenus = new ArrayList<String>();
		}
		
		if( mMenus.contains(item) )
		{
			return mMenus.indexOf(item);
		}
		
		mMenus.add(item);
		
		return mMenus.indexOf(item);
	}
	
	@Override
	public long getItemId(int arg0) 
	{
		return arg0;
	}

	@Override
	public int getItemViewType(int arg0) 
	{
		return arg0;
	}

	@Override
	public View getView(int i, View view, ViewGroup parent) 
	{
		if( null == view)
		{
			view = LayoutInflater.from(mContext).inflate(R.layout.survey_state_selector_item, null);
		}
		
		String text = (String) getItem(i);
		
		TextView  textView = (TextView) view.findViewById(R.id.content);
		
		textView.setText(text);
		
		return view;
	}

	@Override
	public int getViewTypeCount() 
	{
		return 1;
	}

	@Override
	public boolean hasStableIds() 
	{
		return false;
	}

	@Override
	public boolean isEmpty() 
	{
		return false;
	}

	@Override
	public void registerDataSetObserver(DataSetObserver arg0) 
	{
	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver arg0) 
	{
	}

	@Override
	public View getDropDownView(int i, View view, ViewGroup arg2) 
	{
		if( null == view)
		{
			view = LayoutInflater.from(mContext).inflate(R.layout.survey_state_selector_item, null);
		}
		
		String text = (String) getItem(i);
		
		TextView  textView = (TextView) view.findViewById(R.id.content);
		
		textView.setText(text);
		
		return view;
	}
}
