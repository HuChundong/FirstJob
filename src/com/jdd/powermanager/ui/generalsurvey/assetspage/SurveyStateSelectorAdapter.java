package com.jdd.powermanager.ui.generalsurvey.assetspage;

import com.jdd.powermanager.R;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

public class SurveyStateSelectorAdapter implements SpinnerAdapter
{
	String[] menus;
	
	private Context mContext;
	
	public void setItems(String[] is)
	{
		menus = is;
	}
	
	public SurveyStateSelectorAdapter(Context context)
	{
		mContext = context;
	}
	
	public static boolean isSurveyState(int i)
	{
		return i == 1;
	}
	
	public static boolean isUnSurveyState(int i)
	{
		return i == 2;
	}
	
	@Override
	public int getCount() 
	{
		return menus.length;
	}

	@Override
	public Object getItem(int i) 
	{
		return menus[i];
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
		
		String all = mContext.getString(R.string.all);
		
		if( text.startsWith(all) )
		{
			textView.setText(text);
		}
		else
		{
			textView.setText(mContext.getString(R.string.state) + " : " + text);
		}
		
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
