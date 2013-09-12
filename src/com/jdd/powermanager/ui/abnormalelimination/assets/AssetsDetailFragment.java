package com.jdd.powermanager.ui.abnormalelimination.assets;

import java.util.ArrayList;
import java.util.HashMap;
import com.jdd.common.utils.toast.ToastHelper;
import com.jdd.powermanager.R;
import com.jdd.powermanager.action.AbsCallback;
import com.jdd.powermanager.action.elimination.EliminationActions;
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
		
		EliminationActions.getEliminateTasksWithSpecifiedEliminateStatus(mDistrictId,0,new AbsCallback() 
		{
			@Override
			public void onResult(Object o) 
			{
				FullScreenWaitBar.hide();
				
				@SuppressWarnings("unchecked")
				ArrayList<HashMap<String, String>> list = null == o ? null : (ArrayList<HashMap<String, String>>)o;
				
				mAdapter.setData(list);
				
				StateSelectorAdapter sa = new StateSelectorAdapter(getActivity());
				
				String[] menus = 
				{
					getString(R.string.all) + "( " +  mAdapter.getAllCount() +" )" ,
					getString(R.string.state_treated) + "( " +  mAdapter.getTreatedCount() +" )" ,
					getString(R.string.state_untreated) + "( " +  mAdapter.getUnTreatedCount() +" )" ,
				};
				
				sa.setItems(menus);
				
				mSpinner.setAdapter(sa);
			}
		});
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		View v = inflater.inflate(R.layout.elimination_assets_detail_page, null);
		
		mSpinner = (Spinner) v.findViewById(R.id.state_filter_selector);
		
		StateSelectorAdapter sa = new StateSelectorAdapter(getActivity());
		
		String[] menus = 
		{
			getString(R.string.all) + "( " +  mAdapter.getAllCount() +" )" ,
			getString(R.string.state_treated) + "( " +  mAdapter.getTreatedCount() +" )" ,
			getString(R.string.state_untreated) + "( " +  mAdapter.getUnTreatedCount() +" )" ,
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
		
		EliminationActions.completeThePlan(mDistrictId,new AbsCallback() 
		{
			@Override
			public void onResult(Object o) 
			{
				FullScreenWaitBar.hide();
				
				ToastHelper.showToastShort(getActivity(), getActivity().getString(R.string.complete_sucess));
				
				back();
			}
		});
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
			if( StateSelectorAdapter.isTreatedState(pos) )
			{
				mAdapter.switchData(AssetsDataAdapter.TREATED);
			}
			else if( StateSelectorAdapter.isUnTreatedState(pos) )
			{
				mAdapter.switchData(AssetsDataAdapter.UN_TREATED);
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
