package com.jdd.powermanager.ui.generalsurvey.submitpage.newsurveypage;

import com.jdd.powermanager.R;
import com.jdd.powermanager.basic.Pager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

public class LockView implements Pager
{
	private Context mContext;
	
	private View mRoot;
	
	private TextView mBackBtn;
	
	private TextView mSavebtn;
	
	private EditText mBarCodeEdit;
	
	public LockView(Context c)
	{
		mContext = c;
	}
	
	@Override
	public View getView() 
	{
		mRoot = LayoutInflater.from(mContext).inflate(R.layout.survey_lock_page, null);
		
		initViews();
		
		return mRoot;
	}
	
	public String getLockCode()
	{
		return mBarCodeEdit.getText().toString();
	}
	
	private void initViews()
	{
		mBackBtn = (TextView) mRoot.findViewById(R.id.back_btn);
		
		mSavebtn = (TextView) mRoot.findViewById(R.id.save_btn);
		
		mBarCodeEdit = (EditText) mRoot.findViewById(R.id.bar_code_edit);
		
		mBackBtn.setOnClickListener(mOnClickLis);
		
		mSavebtn.setOnClickListener(mOnClickLis);
	}
	
	private void save()
	{
		NewSurveyActivity a = (NewSurveyActivity) mContext;
		
		a.save(false);
	}
	
	private void back()
	{
		NewSurveyActivity a = (NewSurveyActivity) mContext;
		
		a.finish();
	}
	
	private OnClickListener mOnClickLis = new OnClickListener() 
	{
		@Override
		public void onClick(View v) 
		{
			switch(v.getId())
			{
				case R.id.back_btn:
					
					back();
					
					break;
					
				case R.id.save_btn:
					
					save();
					
					break;
			}
		}
	};
	
	@Override
	public void onSelected() 
	{
	}

	@Override
	public void onCancelSelected() 
	{
	}

	@Override
	public void onDestroy()
	{
		mContext = null;
		
		mRoot = null;
	}
}
