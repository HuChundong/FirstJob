package com.jdd.powermanager.ui.generalsurvey.assetspage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import com.jdd.powermanager.R;
import com.jdd.powermanager.model.MeterSurvey.MeterSurveyForm.MeterSurvey;
import com.jdd.powermanager.model.MeterSurvey.SurveyForm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AssetsDataAdapter  extends BaseAdapter
{
	public static final int SURVEY = 1;
	
	public static final int UN_SURVEY = 2;
	
	public static final int NEW = 3;
	
	public static final int ALL = 0;
	
	private Context mContext;
	
	private List<HashMap<String,String>> mCurList;
	
	private List<HashMap<String,String>> mFullList;
	
	private List<HashMap<String,String>> mSurveyList = new ArrayList<HashMap<String,String>>();
	
	private List<HashMap<String,String>> munSurveyList= new ArrayList<HashMap<String,String>>();
	
	private List<HashMap<String,String>> mNewList= new ArrayList<HashMap<String,String>>();
	
	/**
	 * 1 已普查  2 未普查 0 all 3 新增
	 */
	private int mState;
	
	private HashSet<String> mSelectedSet = new HashSet<String>();
	
	private String mDristritId;
	
	public AssetsDataAdapter(Context context,String dis)
	{
		mContext = context;
		
		mDristritId = dis;
	}
	
	public int getSurveyCount()
	{
		return null == mSurveyList ? 0 : mSurveyList.size();
	}
	
	public int getUnSurveyCount()
	{
		return null == munSurveyList ? 0 : munSurveyList.size();
	}
	
	public int getNewSurveyCount()
	{
		return null == mNewList ? 0 : mNewList.size();
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
			case SURVEY:
				
				mCurList = mSurveyList;
				
				break;
				
			case UN_SURVEY:
				
				mCurList = munSurveyList;
				
				break;
				
			case ALL:
				
				mCurList = mFullList;
				
				break;
				
			case NEW:
				
				mCurList = mNewList;
				
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
		
		mSurveyList .clear();
		
		munSurveyList.clear();
		
		mNewList.clear();
		
		if( null ==  mFullList)
		{
			return;
		}
		
		HashMap<String, String> m = null;
		
		String survey = SurveyForm.SURVEY_STATUS_SURVEYED;
		
		String stateNew = SurveyForm.SURVEY_RELATION_NEW;
		
		for( int i = 0; i < mFullList.size() ; i++ )
		{
			m = mFullList.get(i);
			
			if( stateNew.equals(m.get(SurveyForm.SURVEY_RELATION))  )
			{
				mNewList.add(m);
			}
			else if( survey.equals(m.get(SurveyForm.SURVEY_STATUS)) )
			{
				mSurveyList.add(m);
			}
			else
			{
				munSurveyList.add(m);
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
			view = LayoutInflater.from(mContext).inflate(R.layout.survey_assets_detail_list_item, null);
			
			h = new Holder();
			
			h.order = (TextView) view.findViewById(R.id.order);
			h.boxNo = (TextView) view.findViewById(R.id.box_no);
			h.userNO = (TextView) view.findViewById(R.id.user_number);
			h.state = (TextView) view.findViewById(R.id.state);
			h.measureNO = (TextView) view.findViewById(R.id.measure_no);
			h.assetNO = (TextView) view.findViewById(R.id.asset_no);
			h.rowNO = (TextView) view.findViewById(R.id.row_no);
			h.columnNO = (TextView) view.findViewById(R.id.column_no);
			h.userName = (TextView) view.findViewById(R.id.user_name);
			h.address = (TextView) view.findViewById(R.id.address);
			h.setAddress = (TextView) view.findViewById(R.id.set_address);
			h.dristrictId = (TextView) view.findViewById(R.id.dristrict_id);
			h.userCategory = (TextView) view.findViewById(R.id.user_category);
			h.wiring = (TextView) view.findViewById(R.id.wiring);
			h.oper = (TextView) view.findViewById(R.id.operator);
			h.date = (TextView) view.findViewById(R.id.op_date);
			h.time = (TextView) view.findViewById(R.id.op_time);
			
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
			h.order.setText("          "); // 目前为空
			h.boxNo.setText(data.get(MeterSurvey.ASSET_NO));
			h.userNO.setText(data.get(MeterSurvey.CONS_NO));
			h.state.setText(data.get(SurveyForm.SURVEY_STATUS));
			
			if( mNewList.contains(data) )
			{
				h.state.setText(SurveyForm.SURVEY_RELATION_NEW);
			}
			
			h.measureNO.setText(data.get(MeterSurvey.MP_NO));
			h.assetNO.setText(data.get(MeterSurvey.D_ASSET_NO));
			h.rowNO.setText(data.get(MeterSurvey.IN_ROW));
			h.columnNO.setText(data.get(MeterSurvey.IN_COLUMN));
			h.userName.setText(data.get(MeterSurvey.USER_NAME));
			h.address.setText(data.get(MeterSurvey.USER_ADDRESS));
			h.setAddress.setText(data.get(MeterSurvey.INST_LOC));
			h.dristrictId.setText(mDristritId);
			h.userCategory.setText(data.get(MeterSurvey.USER_TYPE));
			h.wiring.setText(data.get(MeterSurvey.WIRING_METHOD));
			
			h.oper.setText(data.get(SurveyForm.OPERATER));
			h.date.setText(data.get(SurveyForm.OPERATE_DATE));
			h.time.setText(data.get(SurveyForm.OPERATE_TIME));
		}
		
		final String code = (String) h.assetNO.getText();
		
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
		
		TextView boxNo;
		
		TextView userNO;
		
		TextView state;
		
		TextView measureNO;
		
		TextView assetNO;
		
		TextView rowNO;
		
		TextView columnNO;
		
		TextView userName;
		
		TextView address;
		
		TextView setAddress;
		
		TextView dristrictId;
		
		TextView userCategory;
		
		TextView wiring;
		
		TextView date;
		
		TextView time;
		
		TextView oper;
	}
}
