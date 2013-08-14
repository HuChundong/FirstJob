package com.jdd.powermanager.ui.generalsurvey.assetspage;

import java.util.HashMap;
import java.util.List;
import com.jdd.common.utils.toast.ToastHelper;
import com.jdd.powermanager.R;
import com.jdd.powermanager.action.AbsCallback;
import com.jdd.powermanager.action.survey.SurveyActions;
import com.jdd.powermanager.ui.widgt.FullScreenWaitBar;
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
		
		FullScreenWaitBar.show(getActivity(), R.layout.full_screen_wait_bar);
		
		SurveyActions.getAllMetersInDistrict(new AbsCallback() 
		{
			@Override
			public void onResult(Object o) 
			{
				FullScreenWaitBar.hide();
				
				@SuppressWarnings("unchecked")
				List<HashMap<String, String>> list = null == o ? null : (List<HashMap<String, String>>)o;
				
				mAdapter.setData(list);
				
				SurveyStateSelectorAdapter sa = new SurveyStateSelectorAdapter(getActivity());
				
				String[] menus = 
				{
					getString(R.string.all) + "( " +  mAdapter.getAllCount() +" )" ,
					getString(R.string.state_survey) + "( " +  mAdapter.getSurveyCount() +" )" ,
					getString(R.string.state_un_survey) + "( " +  mAdapter.getUnSurveyCount() +" )" ,
					getString(R.string.state_new) + "( " +  mAdapter.getNewSurveyCount() +" )" 
				};
				
				sa.setItems(menus);
				
				mSpinner.setAdapter(sa);
			}
		}, mDistrictId);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		View v = inflater.inflate(R.layout.survey_assets_detail_page, null);
		
		mSpinner = (Spinner) v.findViewById(R.id.state_filter_selector);
		
		SurveyStateSelectorAdapter sa = new SurveyStateSelectorAdapter(getActivity());
		
		String[] menus = 
		{
			getString(R.string.all) + "( " +  mAdapter.getAllCount() +" )" ,
			getString(R.string.state_survey) + "( " +  mAdapter.getSurveyCount() +" )" ,
			getString(R.string.state_un_survey) + "( " +  mAdapter.getUnSurveyCount() +" )" ,
			getString(R.string.state_new) + "( " +  mAdapter.getNewSurveyCount() +" )" 
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
		
		FullScreenWaitBar.show(getActivity(), R.layout.full_screen_wait_bar);
		
		SurveyActions.commitAllUncommitedBoxMeterSurveyInDistrict(new AbsCallback() 
		{
			@Override
			public void onResult(Object o) 
			{
				FullScreenWaitBar.hide();
				
				ToastHelper.showToastShort(getActivity(), getActivity().getString(R.string.complete_sucess));
				
				back();
			}
		}, mDistrictId);
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
				mAdapter.switchData(AssetsDataAdapter.SURVEY);
			}
			else if( SurveyStateSelectorAdapter.isUnSurveyState(pos) )
			{
				mAdapter.switchData(AssetsDataAdapter.UN_SURVEY);
			}
			else if( SurveyStateSelectorAdapter.isNewState(pos) )
			{
				mAdapter.switchData(AssetsDataAdapter.NEW);
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
