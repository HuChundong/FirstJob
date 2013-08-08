package com.jdd.powermanager.ui.boxcombing.allboxs;

import com.jdd.common.utils.toast.ToastHelper;
import com.jdd.powermanager.R;
import com.jdd.powermanager.model.MeterSurvey.MeterSurveyDataManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;

public class AssetsDetailFragment extends Fragment 
{
	private Spinner mSpinner;
	
	private String mDistrictId;
	
	private AssetsDataAdapter mAdapter;
	
	private ListView mListView;
	
	private TextView mComplete;
	
	private TextView mBack;
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		mDistrictId = getArguments().getString("DistrictId");
		
		mAdapter = new AssetsDataAdapter(getActivity() , mDistrictId);
		
		// TODO
//		List<HashMap<String, String>> list = MeterSurveyDataManager.getInstance().getAllMetersInDistrict(mDistrictId);
//		
//		mAdapter.setData(list);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		View v = inflater.inflate(R.layout.combing_assets_detail_page, null);
		
		mSpinner = (Spinner) v.findViewById(R.id.state_filter_selector);
		
		SurveyStateSelectorAdapter sa = new SurveyStateSelectorAdapter(getActivity());
		
		String[] menus = 
		{
			getString(R.string.all) + "( " +  mAdapter.getAllCount() +" )" ,
			getString(R.string.state_survey) + "( " +  mAdapter.getCombCount() +" )" ,
			getString(R.string.state_un_survey) + "( " +  mAdapter.getUnCombCount() +" )" ,
		};
		
		sa.setItems(menus);
		
		mSpinner.setAdapter(sa);
		
		mSpinner.setOnItemSelectedListener(mOnStateSelectorSelectLis);
		
		mListView = (ListView) v.findViewById(R.id.list_view);
		
		mListView.setAdapter(mAdapter);
		
		mComplete = (TextView) v.findViewById(R.id.complete);
		
		mBack = (TextView) v.findViewById(R.id.back);
		
		mComplete.setOnClickListener(mOnClickLis);
		
		mBack.setOnClickListener(mOnClickLis);
		
		return v;
	}
	
	private void complete()
	{
		ToastHelper.showToastShort(getActivity(), getActivity().getString(R.string.complete_wait_tip));
		
		MeterSurveyDataManager.getInstance().commitAllUncommitedBoxMeterSurveyInDistrict(mDistrictId);
		
		ToastHelper.showToastShort(getActivity(), getActivity().getString(R.string.complete_sucess));
		
		back();
	}
	
	private void back()
	{
		getActivity().finish();
	}
	
	private OnClickListener mOnClickLis = new OnClickListener() 
	{
		@Override
		public void onClick(View v) 
		{
			switch(v.getId())
			{
				case R.id.complete:
					
					complete();
					
					break;
					
				case R.id.back:
					
					back();
					
					break;
					
				default:
						
					break;
			}
		}
	};
	
	private OnItemSelectedListener mOnStateSelectorSelectLis = new OnItemSelectedListener()
	{
		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int pos,
				long arg3) 
		{
			if( SurveyStateSelectorAdapter.isSurveyState(pos) )
			{
				mAdapter.switchData(AssetsDataAdapter.COMB);
			}
			else if( SurveyStateSelectorAdapter.isUnSurveyState(pos) )
			{
				mAdapter.switchData(AssetsDataAdapter.UN_COMB);
			}
			else
			{
				mAdapter.switchData(AssetsDataAdapter.ALL);
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) 
		{
		}
	};
}
