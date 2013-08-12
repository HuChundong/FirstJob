package com.jdd.powermanager.ui.boxcombing.allboxs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import com.jdd.powermanager.R;
import com.jdd.powermanager.model.MeterSurvey.SurveyForm;
import com.jdd.powermanager.model.MeterSurvey.BoxSurveyForm.BoxSurvey;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AssetsDataAdapter  extends BaseAdapter
{
	public static final int COMB = 1;
	
	public static final int UN_COMB = 2;
	
	public static final int ALL = 0;
	
	private Context mContext;
	
	private List<HashMap<String,String>> mCurList;
	
	private List<HashMap<String,String>> mFullList;
	
	private List<HashMap<String,String>> mCombList = new ArrayList<HashMap<String,String>>();
	
	private List<HashMap<String,String>> munCombList= new ArrayList<HashMap<String,String>>();
	
	/**
	 * 1 ÒÑÆÕ²é  2 Î´ÆÕ²é 0 all
	 */
	private int mState;
	
	private HashSet<String> mSelectedSet = new HashSet<String>();
	
	private String mDristritId;
	
	private String mDristritLogo;
	
	public AssetsDataAdapter(Context context,String dis,String logo)
	{
		mContext = context;
		
		mDristritId = dis;
		
		mDristritLogo = logo;
	}
	
	public int getCombCount()
	{
		return null == mCombList ? 0 : mCombList.size();
	}
	
	public int getUnCombCount()
	{
		return null == munCombList ? 0 : munCombList.size();
	}
	
	public int getAllCount()
	{
		return null == mFullList ? 0 : mFullList.size();
	}
	
	public int getState()
	{
		return mState;
	}
	
	public void switchData(int state)
	{
		mState = state;
		
		switch(state)
		{
			case COMB:
				
				mCurList = mCombList;
				
				break;
				
			case UN_COMB:
				
				mCurList = munCombList;
				
				break;
				
			case ALL:
				
				mCurList = mFullList;
				
				break;
		}
		
		notifyDataSetChanged();
	}
	
	public void setData(List<HashMap<String, String>> data)
	{
		mFullList = data;
		
		if( null != mSelectedSet )
		{
			mSelectedSet.clear();
		}
		
		mCombList .clear();
		
		munCombList.clear();
		
		if( null ==  mFullList)
		{
			return;
		}
		
		HashMap<String, String> m = null;
		
		String survey = mContext.getString(R.string.state_survey);
		
		for( int i = 0; i < mFullList.size() ; i++ )
		{
			m = mFullList.get(i);
			
			if( survey.equals(m.get(SurveyForm.SURVEY_STATUS)) )
			{
				mCombList.add(m);
			}
			else
			{
				munCombList.add(m);
			}
		}
		
		switchData(mState);
	}
	
	@Override
	public int getCount() 
	{
		return null == mCurList ? 0 : mCurList.size();
	}

	@Override
	public Object getItem(int arg0) 
	{
		return null == mCurList ? null : mCurList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) 
	{
		return arg0;
	}

	@Override
	public View getView(int pos, View view , ViewGroup parent) 
	{
		Holder h;
		
		if( null == view )
		{
			view = LayoutInflater.from(mContext).inflate(R.layout.combing_item, null);
			
			h = new Holder();
			
			h.order = (TextView) view.findViewById(R.id.order);
			
			h.systemId = (TextView) view.findViewById(R.id.system_id);
			
			h.address = (TextView) view.findViewById(R.id.address);
			
			h.rows = (TextView) view.findViewById(R.id.rows);
			
			h.columns = (TextView) view.findViewById(R.id.columns);
			
			h.meterCount = (TextView) view.findViewById(R.id.meters_count);
			
			h.disTag = (TextView) view.findViewById(R.id.district_tag);
			
			h.disId = (TextView) view.findViewById(R.id.district_id);
			
			h.barCode = (TextView) view.findViewById(R.id.box_barcode);
			
			view.setTag(h);
		}
		else
		{
			h = (Holder) view.getTag();
		}
		
		@SuppressWarnings("unchecked")
		HashMap<String,String> data = (HashMap<String, String>) getItem(pos);
		
		if( null != data )
		{
			h.order.setText(data.get(BoxSurvey.NO));
			
			h.systemId.setText(data.get(BoxSurvey.SYSTEM_ID));
			
			h.address.setText(data.get(BoxSurvey.INST_LOC));
			
			h.rows.setText(data.get(BoxSurvey.BOX_ROWS));
			
			h.columns.setText(data.get(BoxSurvey.BOX_COLS));
			
			h.meterCount.setText(data.get(BoxSurvey.METER_NUM));
			
			h.disTag.setText(mDristritLogo);
			
			h.disId.setText(mDristritId);
			
			h.barCode.setText(data.get(BoxSurvey.ASSET_NO));
		}
		
		final String code = (String) h.barCode.getText();
		
		view.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				if( mSelectedSet.contains(code) )
				{
					v.setBackgroundColor(mContext.getResources().getColor(R.color.white));
					
					mSelectedSet.remove(code);
				}
				else
				{
					v.setBackgroundColor(mContext.getResources().getColor(R.color.blue_7ACEEA));
					
					mSelectedSet.add(code);
				}
			}
		});
		
		if( mSelectedSet.contains(code) )
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
		TextView order;
		
		TextView systemId;
		
		TextView address;
		
		TextView rows;
		
		TextView columns;
		
		TextView meterCount;
		
		TextView disTag;
		
		TextView disId;
		
		TextView barCode;
	}
}
