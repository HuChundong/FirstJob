package com.jdd.powermanager.ui.generalsurvey.unsubmitpage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.jdd.common.utils.toast.ToastHelper;
import com.jdd.powermanager.R;
import com.jdd.powermanager.model.MeterSurvey.MeterSurveyDataManager;
import com.jdd.powermanager.model.MeterSurvey.MeterSurveyForm.MeterSurvey;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class UnSubmitDataAdapter extends BaseAdapter 
{
	private List<HashMap<String, String>> mData;
	
	private Context mContext;
	
	private List<HashMap<String, String>> mSelectedSet;
	
	public void submitSelected()
	{
		if( null == mSelectedSet || mSelectedSet.size() == 0 )
		{
			ToastHelper.showToastShort(mContext, mContext.getString(R.string.no_box_to_submit_tip));
			
			return;
		}
		
		int size = mSelectedSet.size();
		
		String waitTip = String.format(mContext.getString(R.string.submit_wait_tip), ""+size);
		
		ToastHelper.showToastShort(mContext, waitTip);
		
		HashMap<String, String> box ;
		
		while( mSelectedSet.size() > 0)
		{
			box = mSelectedSet.remove(0);
			
			mData.remove(box);
			
			MeterSurveyDataManager.getInstance().commitOneBoxMeterSurvey(box.get(MeterSurvey.BAR_CODE));
		}
		
		String successTip = String.format(mContext.getString(R.string.submit_success_tip), ""+size);
		
		ToastHelper.showToastShort(mContext, successTip);
		
		notifyDataSetChanged();
	}
	
	public void delSelected()
	{
		if( null == mSelectedSet || mSelectedSet.size() == 0 )
		{
			ToastHelper.showToastShort(mContext, mContext.getString(R.string.no_box_to_del_tip));
			
			return;
		}
		
		int size = mSelectedSet.size();
		
		String waitTip = String.format(mContext.getString(R.string.del_box_wait_tip), ""+size);
		
		ToastHelper.showToastShort(mContext, waitTip);
		
		List<String> idList = new ArrayList<String>();
		
		HashMap<String, String> temp;
		
		String code ;
		
		while(mSelectedSet.size() > 0)
		{
			temp = mSelectedSet.remove(0);
			
			mData.remove(temp);
			
			if( null != temp )
			{
				code = temp.get(MeterSurvey.BAR_CODE);
				
				if( null != code && !code.equals("") )
				{
					idList.add(code);
				}
			}
		}
		
		String[] del = new String[]{};
		
		idList.toArray(del);
		
		MeterSurveyDataManager.getInstance().deleteUncommitedBox(del);
		
		String sTip = String.format(mContext.getString(R.string.del_success_tip), ""+size);
		
		ToastHelper.showToastShort(mContext, sTip);
		
		notifyDataSetChanged();
	}
	
	public UnSubmitDataAdapter(Context context)
	{
		mContext = context;
		
		mSelectedSet = new ArrayList<HashMap<String, String>>();
	}
	
	public void setDate(List<HashMap<String, String>> data)
	{
		mData = data;
		
		if( null != mSelectedSet )
		{
			mSelectedSet.clear();
		}
		
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() 
	{
		return null == mData ? 0 : mData.size();
	}

	@Override
	public Object getItem(int arg0) 
	{
		return null == mData ? null : mData.get(arg0);
	}

	@Override
	public long getItemId(int arg0) 
	{
		return arg0;
	}

	@Override
	public View getView(int pos, View view, ViewGroup parent) 
	{
		Holder h = null;
		
		if( null == view )
		{
			view = LayoutInflater.from(mContext).inflate(R.layout.un_submit_item, null);
			
			h = new Holder();
			
			h.type = (TextView) view.findViewById(R.id.item_type);
			
			h.code = (TextView) view.findViewById(R.id.item_code);
			
			h.date = (TextView) view.findViewById(R.id.item_time);
			
			view.setTag(h);
		}
		else
		{
			h = (Holder) view.getTag();
		}
		
		@SuppressWarnings("unchecked")
		final HashMap<String, String> data = (HashMap<String, String>) getItem(pos);
		
		if( null != data )
		{
			h.type.setText(data.get(MeterSurvey.SORT_CODE));
			
			h.code.setText(data.get(MeterSurvey.BAR_CODE));
			
			h.date.setText(data.get(MeterSurvey.SURVEY_TIME));
		}
		
		view.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View arg0) 
			{
				if( mSelectedSet.contains(data) )
				{
					arg0.setBackgroundColor(mContext.getResources().getColor(R.color.white));
					
					mSelectedSet.remove(data);
				}
				else
				{
					arg0.setBackgroundColor(mContext.getResources().getColor(R.color.blue_7ACEEA));
					
					mSelectedSet.add(data);
				}
			}
		});
		
		if( mSelectedSet.contains(data) )
		{
			view.setBackgroundColor(mContext.getResources().getColor(R.color.blue_7ACEEA));
		}
		else
		{
			view.setBackgroundColor(mContext.getResources().getColor(R.color.white));
		}
		
		return view;
	}
	
	private class Holder
	{
		TextView type;
		
		TextView code;
		
		TextView date;
	}
}
