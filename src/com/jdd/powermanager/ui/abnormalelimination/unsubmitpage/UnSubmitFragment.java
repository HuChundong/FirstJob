package com.jdd.powermanager.ui.abnormalelimination.unsubmitpage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.jdd.common.utils.barcode.BarCodeHelper;
import com.jdd.common.utils.barcode.OnBarCodeScanedListener;
import com.jdd.powermanager.R;
import com.jdd.powermanager.action.AbsCallback;
import com.jdd.powermanager.action.elimination.EliminationActions;
import com.jdd.powermanager.bean.Meters;
import com.jdd.powermanager.ui.abnormalelimination.eliminate.EliminateActivity;
import com.jdd.powermanager.ui.widgt.FullScreenWaitBar;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
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
	
	private TextView mAdd;
	
	private AutoCompleteTextView mCodeEdit;
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		mDisId = getArguments().getString("DistrictId");
		
		mAdapter = new UnSubmitDataAdapter(getActivity(),mDisId);
		
		FullScreenWaitBar.show(getActivity(), R.layout.full_screen_wait_bar);
		
		EliminationActions.getEliminateTasksWithSpecifiedCommitStatus(mDisId,2, new AbsCallback() 
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
		
		mAdd = (TextView) v.findViewById(R.id.add_new);
		
		mSubmit.setOnClickListener(mOnClickLis);
		
		mDel.setOnClickListener(mOnClickLis);
		
		mBack.setOnClickListener(mOnClickLis);
		
		mAdd.setOnClickListener(mOnClickLis);
		
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
	
	private void add()
	{
		FullScreenWaitBar.show(getActivity(), R.layout.full_screen_wait_bar);
		
		EliminationActions.getAllMeterAssetNO( new AbsCallback() 
		{
			@Override
			protected void onResult(Object o)
			{
				FullScreenWaitBar.hide();
				
				Meters m = (Meters)o;
				
				showDiyDialog(m.getAllNos());
			}
		});
	}
	
	private OnClickListener mOnClickLis = new OnClickListener() 
	{
		@Override
		public void onClick(View v) 
		{
			switch(v.getId())
			{
				case R.id.add_new:
				
					add();
				
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
	
	private OnBarCodeScanedListener mBarCodeLis = new OnBarCodeScanedListener() 
	{
		@Override
		public void onScaned(String code) 
		{
			if( null != mCodeEdit )
			{
				mCodeEdit.setText(code);
			}
		}
	};
	
	private void showDiyDialog(final List<String> nos)
	{
		final OnBarCodeScanedListener backLis = BarCodeHelper.getListener();
		
		BarCodeHelper.addListener(mBarCodeLis);
		
		View v = LayoutInflater.from(getActivity()).inflate(R.layout.edittext_dialog_view, null);
		
		mCodeEdit = (AutoCompleteTextView) v.findViewById(R.id.edit_text);
		
		if( null != nos )
		{
			FilterAdapter adapter = new FilterAdapter( getActivity(),  R.layout.drop_down_list_item,  nos); 
			
			mCodeEdit.setAdapter(adapter);
		}
		
		AlertDialog.Builder builder = new Builder(getActivity());
		
		builder.setView(v);
		
		builder.setTitle(this.getString(R.string.eliminate_title));
		
		builder.setPositiveButton(this.getString(R.string.ok), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{
				dialog.dismiss();
				
				String code = mCodeEdit.getText().toString();
				
				Intent i = new Intent();
				
				i.setClass(getActivity(), EliminateActivity.class);
					
				i.putExtra("code", code);
				
				startActivity(i);
			}
		});
		
		builder.setNegativeButton(this.getString(R.string.cancel), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{
				dialog.dismiss();
				
				mCodeEdit = null;
				
				BarCodeHelper.clearListener();
				
				BarCodeHelper.addListener(backLis);
			}
		});

		builder.create().show();
	}
}
