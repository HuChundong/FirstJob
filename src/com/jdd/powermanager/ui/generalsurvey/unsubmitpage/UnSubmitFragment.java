package com.jdd.powermanager.ui.generalsurvey.unsubmitpage;

import java.util.HashMap;
import java.util.List;
import com.jdd.powermanager.R;
import com.jdd.powermanager.model.MeterSurvey.MeterSurveyDataManager;
import com.jdd.powermanager.ui.generalsurvey.submitpage.newsurveypage.NewSurveyActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;


public class UnSubmitFragment extends Fragment
{
	private ListView mListView;
	
	private UnSubmitDataAdapter mAdapter;
	
	private String mDisId;
	
	private TextView mAddNew;
	
	private TextView mSubmit;
	
	private TextView mDel;
	
	private TextView mBack;
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		mDisId = getArguments().getString("DistrictId");
		
		mAdapter = new UnSubmitDataAdapter(getActivity());
		
		List<HashMap<String, String>> list = MeterSurveyDataManager.getInstance().getAllSurveyedBoxesInDistrict(mDisId,2);
		
		mAdapter.setData(list);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		View v = inflater.inflate(R.layout.survey_un_submit_page, null);
		
		mListView = (ListView) v.findViewById(R.id.list_view);
		
		mAddNew = (TextView) v.findViewById(R.id.add_new);
		
		mSubmit = (TextView) v.findViewById(R.id.submit);
		
		mDel = (TextView) v.findViewById(R.id.del);
		
		mBack = (TextView) v.findViewById(R.id.back_btn);
		
		mAddNew.setOnClickListener(mOnClickLis);
		
		mSubmit.setOnClickListener(mOnClickLis);
		
		mDel.setOnClickListener(mOnClickLis);
		
		mBack.setOnClickListener(mOnClickLis);
		
		mListView.setAdapter(mAdapter);
		
		return v;
	}
	
	private void addNew()
	{
		Intent i = new Intent();
		
		i.setClass(getActivity(), NewSurveyActivity.class);
		
		i.putExtra("DistrictId", mDisId);
		
		startActivity(i);
	}
	
	private void submit()
	{
		mAdapter.submitSelected();
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
				case R.id.add_new:
					
					addNew();
					
					break;
					
				case R.id.submit:
					
					submit();
					
					break;
					
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
