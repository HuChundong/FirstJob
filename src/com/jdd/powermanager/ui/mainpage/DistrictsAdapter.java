package com.jdd.powermanager.ui.mainpage;

import java.util.List;
import com.jdd.powermanager.R;
import com.jdd.powermanager.action.survey.SurveyActions;
import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

public class DistrictsAdapter implements ListAdapter
{
	public interface onItemClickListener
	{
		void onClick(String id);
	}
	
	private List<SurveyActions.DistrictInfo> mList;
	
	private Context mContext;
	
	private onItemClickListener mLis;
	
	public DistrictsAdapter(Context context,onItemClickListener lis)
	{
		mContext = context;
		
		mLis = lis;
	}
	
	public void setList(List<SurveyActions.DistrictInfo> l)
	{
		mList = l;
	}
	
	@Override
	public int getCount() 
	{
		return null == mList ? 0 : mList.size();
	}

	@Override
	public Object getItem(int arg0) 
	{
		return null == mList ? null : mList.get(arg0);
	}
	
	public String getDistrictId(int pos)
	{
		SurveyActions.DistrictInfo d = (SurveyActions.DistrictInfo) getItem(pos);
		
		return null == d || null == d.d ? null : d.d.getID();
	}
	
	public int getAllMeterCount(int pos)
	{
		SurveyActions.DistrictInfo d = (SurveyActions.DistrictInfo) getItem(pos);
		
		return null == d || null == d.d ? 0 : d.count;
	}
	
	public int getOkMeterCount(int pos)
	{
		SurveyActions.DistrictInfo d = (SurveyActions.DistrictInfo) getItem(pos);
		
		return null == d || null == d.d ? 0 : d.ok;
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
	public View getView(final int pos, View view, ViewGroup parent) 
	{
		Holder h = null;
		
		if( null == view )
		{
			view = LayoutInflater.from(mContext).inflate(R.layout.district_list_item, null);
			
			h = new Holder();
			
			h.context = (TextView) view.findViewById(R.id.district_id);
			
			view.setTag(h);
		}
		else
		{
			h = (Holder) view.getTag();
		}
		
		h.context.setText(getDistrictId(pos) + " ( " + getOkMeterCount(pos) + " / " + getAllMeterCount(pos) + " ) ");
		
		view.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) 
			{
				if( null != mLis )
				{
					mLis.onClick(getDistrictId(pos));
				}
			}
		});
		
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
		return null == mList ? true : mList.isEmpty();
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
	public boolean areAllItemsEnabled() 
	{
		return false;
	}

	@Override
	public boolean isEnabled(int position) 
	{
		return false;
	}
	
	class Holder
	{
		TextView context;
	}
}
