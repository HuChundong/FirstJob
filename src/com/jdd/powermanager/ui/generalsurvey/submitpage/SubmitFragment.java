package com.jdd.powermanager.ui.generalsurvey.submitpage;

import java.util.HashMap;
import java.util.List;
import com.jdd.powermanager.R;
import com.jdd.powermanager.action.AbsCallback;
import com.jdd.powermanager.action.survey.SurveyActions;
import com.jdd.powermanager.ui.generalsurvey.unsubmitpage.UnSubmitDataAdapter;
import com.jdd.powermanager.ui.widgt.FullScreenWaitBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;


public class SubmitFragment extends Fragment
{
	private ListView mListView;
	
	private UnSubmitDataAdapter mAdapter;
	
	private String mDisId;
	
	private TextView mDel;
	
	private TextView mBack;
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		mDisId = getArguments().getString("DistrictId");
		
		mAdapter = new UnSubmitDataAdapter(getActivity());
		
		FullScreenWaitBar.show(getActivity(),R.layout.full_screen_wait_bar);
		
		SurveyActions.getAllSurveyedBoxesInDistrict(new AbsCallback() 
		{
			@Override
			public void onResult(Object o) 
			{
				FullScreenWaitBar.hide();
				
				@SuppressWarnings("unchecked")
				List<HashMap<String, String>> list = null == o ? null : (List<HashMap<String, String>>)o;
				
				mAdapter.setData(list);
			}
		}, mDisId, 1);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		View v = inflater.inflate(R.layout.survey_submit_page, null);
		
		mListView = (ListView) v.findViewById(R.id.list_view);
		
		mDel = (TextView) v.findViewById(R.id.del);
		
		mBack = (TextView) v.findViewById(R.id.back_btn);
		
		mDel.setOnClickListener(mOnClickLis);
		
		mBack.setOnClickListener(mOnClickLis);
		
		mListView.setAdapter(mAdapter);
		
		return v;
	}
	
	private void del()
	{
		mAdapter.delSelected();
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
				case R.id.del:
					
					del();
					
					break;
					
				case R.id.back_btn:
					
					back();
					
					break;
					
				default:
						
					break;
			}
		}
	};
}
