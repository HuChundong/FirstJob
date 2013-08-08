package com.jdd.powermanager.ui.boxcombing.submitboxs;

import com.jdd.powermanager.R;
import com.jdd.powermanager.ui.boxcombing.unsubmitboxs.UnSubmitDataAdapter;

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
		
		// TODO
//		List<HashMap<String, String>> list = MeterSurveyDataManager.getInstance().getAllSurveyedBoxesInDistrict(mDisId,1);
		
//		mAdapter.setData(list);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		View v = inflater.inflate(R.layout.combing_submit_page, null);
		
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