package com.jdd.powermanager.ui.abnormalelimination.assets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import com.jdd.powermanager.R;
import com.jdd.powermanager.model.MeterSurvey.EliminateAbnormalForm.EliminateAbnormal;
import com.jdd.powermanager.model.MeterSurvey.EliminateAbnormalManager;
import com.jdd.powermanager.model.MeterSurvey.SurveyForm;
import com.jdd.powermanager.ui.abnormalelimination.viewimage.ViewImageActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AssetsDataAdapter  extends BaseAdapter
{
	public static final int TREATED = 1;
	
	public static final int UN_TREATED = 2;
	
	public static final int ALL = 0;
	
	private Context mContext;
	
	private ArrayList<HashMap<String,String>> mCurList;
	
	private ArrayList<HashMap<String,String>> mFullList;
	
	private ArrayList<HashMap<String,String>> mTreatedList = new ArrayList<HashMap<String,String>>();
	
	private ArrayList<HashMap<String,String>> munTreatedList= new ArrayList<HashMap<String,String>>();
	
	/**
	 * 1 已处理  2 未处理 0 all  
	 */
	private int mState;
	
	private HashSet<String> mSelectedSet = new HashSet<String>();
	
	private String mDristritId;
	
	public AssetsDataAdapter(Context context,String dis)
	{
		mContext = context;
		
		mDristritId = dis;
	}
	
	public int getTreatedCount()
	{
		return null == mTreatedList ? 0 : mTreatedList.size();
	}
	
	public int getUnTreatedCount()
	{
		return null == munTreatedList ? 0 : munTreatedList.size();
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
			case TREATED:
				
				mCurList = mTreatedList;
				
				break;
				
			case UN_TREATED:
				
				mCurList = munTreatedList;
				
				break;
				
			case ALL:
				
				mCurList = mFullList;
				
				break;
		}
		
		notifyDataSetChanged();
	}
	
	public void setData(ArrayList<HashMap<String, String>> data)
	{
		mFullList = data;
		
		if( null != mSelectedSet )
		{
			mSelectedSet.clear();
		}
		
		mTreatedList .clear();
		
		munTreatedList.clear();
		
		if( null ==  mFullList)
		{
			return;
		}
		
		HashMap<String, String> m = null;
		
		String survey = SurveyForm.ELIMINATE_RESULT_ELIMINATED;
		
		for( int i = 0; i < mFullList.size() ; i++ )
		{
			m = mFullList.get(i);
			
			if( survey.equals(m.get(EliminateAbnormal.ELIMINATE_RESULT)) )
			{
				mTreatedList.add(m);
			}
			else
			{
				munTreatedList.add(m);
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
			
			h.photo = (TextView) view.findViewById(R.id.photo);
			
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
			h.userNo.setText(data.get(EliminateAbnormal.CONS_NO));
			
			h.mesureNo.setText(data.get(EliminateAbnormal.MP_NO));
			
			h.meterBarcode.setText(data.get(EliminateAbnormal.D_ASSET_NO));
			
			h.userName.setText(data.get(EliminateAbnormal.USER_NAME));
			
			h.address.setText(data.get(EliminateAbnormal.USER_ADDRESS));
			
			h.lo.setText(data.get(EliminateAbnormal.LONGITUDE));
			
			h.la.setText(data.get(EliminateAbnormal.LATITUDE));
			
			h.rows.setText(data.get(EliminateAbnormal.IN_ROW));
			
			h.columns.setText(data.get(EliminateAbnormal.IN_COLUMN));
			
			h.disId.setText(mDristritId);
			
			h.barCode.setText(data.get(EliminateAbnormal.BAR_CODE));
			
			h.phenomenon.setText(data.get(EliminateAbnormal.ABNORMAL_PHENOMENON));
			
			h.result.setText(data.get(EliminateAbnormal.ELIMINATE_RESULT));
			
			h.method.setText(data.get(EliminateAbnormal.ELIMINATE_METHOD));
			
			h.photo.setText(mContext.getString(R.string.click_to_view));
		}
		
		final String code = (String) h.meterBarcode.getText();
		
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
		
		h.photo.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				String path = EliminateAbnormalManager.getInstance().getMeterAbnormalPhotoPath(code);
				
				Intent i = new Intent();
				
				i.setClass(mContext, ViewImageActivity.class);
				
				i.putExtra("path", path);
				
				i.putExtra("index", 0);
				
				mContext.startActivity(i);
			}
		});
		
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
		
		TextView photo;
	}
}
