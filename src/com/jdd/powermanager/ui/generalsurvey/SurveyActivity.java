package com.jdd.powermanager.ui.generalsurvey;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.jdd.powermanager.R;
import com.jdd.powermanager.basic.BaseFragmentActivity;
import com.jdd.powermanager.ui.generalsurvey.assetspage.AssetsDetailFragment;
import com.jdd.powermanager.ui.generalsurvey.submitpage.SubmitFragment;
import com.jdd.powermanager.ui.generalsurvey.unsubmitpage.UnSubmitFragment;

public class SurveyActivity extends BaseFragmentActivity
{
	private TextView mBtn_tab_un_submit;
	
	private TextView mBbtn_tab_submit;
	
	private TextView mBtn_tab_assets;
	
	private TextView mTitle;
	
	private String mDistrictId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.survey_page);
		
		mDistrictId = (String) getIntent().getCharSequenceExtra("DistrictId");
		
		initViews();
	}
	
	private void initViews()
	{
		mTitle = (TextView) findViewById(R.id.title_text);
		
		mTitle.setText(R.string.general_survey);
		
		mBtn_tab_un_submit = (TextView) findViewById(R.id.tab_btn_un_submit);
		
		mBbtn_tab_submit = (TextView) findViewById(R.id.tab_btn_submit);
		
		mBtn_tab_assets = (TextView) findViewById(R.id.tab_btn_assets);
		
		mBtn_tab_un_submit.setOnClickListener(mOnClickLis);
		
		mBbtn_tab_submit.setOnClickListener(mOnClickLis);
		
		mBtn_tab_assets.setOnClickListener(mOnClickLis);
		
		mOnClickLis.onClick(mBtn_tab_un_submit);
	}
	
	private OnClickListener mOnClickLis = new OnClickListener()
	{
		public void onClick(View v) 
		{
			switch(v.getId())
			{
				case R.id.tab_btn_un_submit:
					
					mBtn_tab_un_submit.setSelected(true);
					mBbtn_tab_submit.setSelected(false);
					mBtn_tab_assets.setSelected(false);
					
					showUnSubmit();
					
					break;
					
				case R.id.tab_btn_submit:
					
					mBtn_tab_un_submit.setSelected(false);
					mBbtn_tab_submit.setSelected(true);
					mBtn_tab_assets.setSelected(false);
					
					showSubmit();
					
					break;
					
				case R.id.tab_btn_assets:
					
					mBtn_tab_un_submit.setSelected(false);
					mBbtn_tab_submit.setSelected(false);
					mBtn_tab_assets.setSelected(true);
					
					showAssetsDetail();
					
					break;
					
				default:
					
					break;
			}
		}
	};
	
	private void showAssetsDetail()
	{
		AssetsDetailFragment f = new AssetsDetailFragment();
		
		Bundle b = new Bundle();
		
		b.putCharSequence("DistrictId", mDistrictId);
		
		f.setArguments(b);
		
		showFragment(f);
	}
	
	private void showUnSubmit()
	{
		UnSubmitFragment f = new UnSubmitFragment();
		
		Bundle b = new Bundle();
		
		b.putCharSequence("DistrictId", mDistrictId);
		
		f.setArguments(b);
		
		showFragment(f);
	}
	
	private void showSubmit()
	{
		SubmitFragment f = new SubmitFragment();
		
		Bundle b = new Bundle();
		
		b.putCharSequence("DistrictId", mDistrictId);
		
		f.setArguments(b);
		
		showFragment(f);
	}
	
	private void showFragment(Fragment f)
	{
		FragmentManager fm = getSupportFragmentManager();
		
		fm.beginTransaction().replace(R.id.content_fragment, f).
			setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
	}
	
}
