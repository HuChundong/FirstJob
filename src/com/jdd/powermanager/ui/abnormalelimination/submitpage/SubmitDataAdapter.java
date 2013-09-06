package com.jdd.powermanager.ui.abnormalelimination.submitpage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.jdd.common.utils.toast.ToastHelper;
import com.jdd.powermanager.R;
import com.jdd.powermanager.ui.widgt.FullScreenWaitBar;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SubmitDataAdapter extends BaseAdapter 
{
	private List<HashMap<String, String>> mData;
	
	private Context mContext;
	
	private List<HashMap<String, String>> mSelectedSet;
	
	private String mDistrictId;
	
	public void delSelected()
	{
		if( null == mSelectedSet || mSelectedSet.size() == 0 )
		{
			ToastHelper.showToastShort(mContext, mContext.getString(R.string.no_meter_to_del_tip));
			
			return;
		}

		FullScreenWaitBar.show(mContext, R.layout.full_screen_wait_bar);
		
		final int size = mSelectedSet.size();
		
		String waitTip = String.format(mContext.getString(R.string.del_meter_wait_tip), ""+size);
		
		ToastHelper.showToastShort(mContext, waitTip);
		
		// TODO
		
//		List<String> idList = new ArrayList<String>();
//		
//		HashMap<String, String> temp;
//		
//		String code ;
//		
//		while(mSelectedSet.size() > 0)
//		{
//			temp = mSelectedSet.remove(0);
//			
//			mData.remove(temp);
//			
//			if( null != temp )
//			{
//				code = temp.get(BoxSurvey.ASSET_NO);
//				
//				if( null != code && !code.equals("") )
//				{
//					idList.add(code);
//				}
//			}
//		}
//		
//		String[] del = new String[idList.size()];
//		
//		idList.toArray(del);
//		
//		CombingActions.deleteCommitedBox(new AbsCallback() 
//		{
//			@Override
//			public void onResult(Object o) 
//			{
//				FullScreenWaitBar.hide();
//				
//				String sTip = String.format(mContext.getString(R.string.del_success_tip_meter), ""+size);
//				
//				ToastHelper.showToastShort(mContext, sTip);
//				
//				notifyDataSetChanged();
//			}
//		}, del);
	}
	
	public SubmitDataAdapter(Context context,String id)
	{
		mContext = context;
		
		mDistrictId = id;
		
		mSelectedSet = new ArrayList<HashMap<String, String>>();
	}
	
	public void setData(List<HashMap<String, String>> data)
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
			view = LayoutInflater.from(mContext).inflate(R.layout.elimination_item, null);
			
			h = new Holder();
			
			h.userNo = (TextView) view.findViewById(R.id.user_no);
			
			h.mesureNo = (TextView) view.findViewById(R.id.measure_no);
			
			h.meterBarcode = (TextView) view.findViewById(R.id.meter_asset_no);
			
			h.userName = (TextView) view.findViewById(R.id.user_name);
			
			h.address = (TextView) view.findViewById(R.id.address);
			
			h.lo =  (TextView) view.findViewById(R.id.lo);
			
			h.la =  (TextView) view.findViewById(R.id.la);
			
			h.rows = (TextView) view.findViewById(R.id.rows);
			
			h.columns = (TextView) view.findViewById(R.id.columns);
			
			h.disId = (TextView) view.findViewById(R.id.district_id);
			
			h.barCode = (TextView) view.findViewById(R.id.box_barcode);
			
			h.phenomenon = (TextView) view.findViewById(R.id.phenomenon);
			
			h.result = (TextView) view.findViewById(R.id.result);
			
			h.method = (TextView) view.findViewById(R.id.method);
			
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
			// TODO
//			h.order.setText(data.get(BoxSurvey.NO));
//			
//			h.systemId.setText(data.get(BoxSurvey.SYSTEM_ID));
//			
//			h.address.setText(data.get(BoxSurvey.INST_LOC));
//			
//			h.lo.setText(data.get(BoxSurvey.LONGITUDE));
//			
//			h.la.setText(data.get(BoxSurvey.LATITUDE));
//			
//			h.rows.setText(data.get(BoxSurvey.BOX_ROWS));
//			
//			h.columns.setText(data.get(BoxSurvey.BOX_COLS));
//			
//			h.meterCount.setText(data.get(BoxSurvey.METER_NUM));
//			
//			h.disTag.setText(mDristritLogo);
//			
//			h.disId.setText(mDistrictId);
//			
//			h.barCode.setText(data.get(BoxSurvey.ASSET_NO));
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
		TextView userNo;
		
		TextView mesureNo;
		
		TextView meterBarcode;
		
		TextView userName;
		
		TextView address;
		
		TextView lo;
		
		TextView la;
		
		TextView rows;
		
		TextView columns;
		
		TextView disId;
		
		TextView barCode;
		
		TextView phenomenon;
		
		TextView result;
		
		TextView method;
	}
}
