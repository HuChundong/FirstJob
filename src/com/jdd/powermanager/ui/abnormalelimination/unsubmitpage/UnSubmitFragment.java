package com.jdd.powermanager.ui.abnormalelimination.unsubmitpage;

import java.util.ArrayList;
import java.util.HashMap;
import com.jdd.powermanager.R;
import com.jdd.powermanager.action.AbsCallback;
import com.jdd.powermanager.action.elimination.EliminationActions;
import com.jdd.powermanager.ui.widgt.FullScreenWaitBar;
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
	
	private TextView mSubmit;
	
	private TextView mDel;
	
	private TextView mBack;
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		mDisId = getArguments().getString("DistrictId");
		
		mAdapter = new UnSubmitDataAdapter(getActivity(),mDisId);
		
		FullScreenWaitBar.show(getActivity(), R.layout.full_screen_wait_bar);
		
		EliminationActions.getEliminateTasksWithSpecifiedCommitStatus(2, new AbsCallback() 
		{
			@Override
			protected void onResult(Object o)
			{
				FullScreenWaitBar.hide();
				
				@SuppressWarnings("unchecked")
				ArrayList<HashMap<String, String>> list = null == o ? null : (ArrayList<HashMap<String, String>>)o;
				
				mAdapter.setData(list);
			}
		});
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		View v = inflater.inflate(R.layout.elimination_un_submit_page, null);
		
		mListView = (ListView) v.findViewById(R.id.list_view);
		
		mSubmit = (TextView) v.findViewById(R.id.submit);
		
		mDel = (TextView) v.findViewById(R.id.del);
		
		mBack = (TextView) v.findViewById(R.id.back_btn);
		
		mSubmit.setOnClickListener(mOnClickLis);
		
		mDel.setOnClickListener(mOnClickLis);
		
		mBack.setOnClickListener(mOnClickLis);
		
		mListView.setAdapter(mAdapter);
		
		return v;
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
