package com.jdd.powermanager.ui.loginpage;

import com.jdd.powermanager.R;
import com.jdd.powermanager.basic.BaseActivity;
import com.jdd.powermanager.model.MeterSurvey.MeterSurveyDataManager;
import com.jdd.powermanager.model.MeterSurvey.SurveyDataManager.OnInitedListener;
import com.jdd.powermanager.ui.mainpage.MainPageActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LoginActivity extends BaseActivity 
{
	private EditText mDeviceNoEdit;
	
	private RadioButton mOnlineBtn;
	
	private RadioButton mOfflineBtn;
	
//	private EditText mUserNoEdit;
	
//	private EditText mUserPsdEdit;
	
	private RelativeLayout mWaiting;
	
	private boolean mIsIniting;
	
	private boolean mIsInitSuccess;
	
	/**
	 * ÊÇ·ñÔÚÏßµÇÂ½
	 */
	private boolean mIsOnlineLogin = true;
	
	private OnClickListener mOnClickListener = new OnClickListener() 
	{
		@Override
		public void onClick(View v) 
		{
			switch(v.getId())
			{
				case R.id.online_radio:
					
					mOfflineBtn.setChecked(false);
					
					mIsOnlineLogin = true;
					
					break;
					
				case R.id.offline_radio:
					
					mOnlineBtn.setChecked(false);
					
					mIsOnlineLogin = false;
					
					break;
					
				default:
					
					break;
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.login);
		
		initTopbar();
		
		initViews();
	}
	
	public void login(View v)
	{
		mWaiting.setVisibility(View.VISIBLE);
		
		if( mIsIniting )
		{
			return;
		}
		
		if( mIsInitSuccess )
		{
			goHome();
			
			return;
		}
		
		mIsIniting = true;
		
		MeterSurveyDataManager.getInstance().init(getApplication(),mInitedLis);
	}
	
	@Override
	public void onBackPressed() 
	{
		if(mWaiting.getVisibility() == View.VISIBLE)
		{
			mWaiting.setVisibility(View.INVISIBLE);
			
			return;
		}
		
		super.onBackPressed();
	}
	
	public void exit(View v)
	{
		android.os.Process.killProcess(android.os.Process.myPid());
	}
	
	private OnInitedListener mInitedLis = new OnInitedListener()
	{
		@Override
		public void onInitedSucess() 
		{
			mIsIniting = false;
			
			mIsInitSuccess = true;
			
			if( mWaiting.getVisibility() == View.VISIBLE )
			{
				goHome();
			}
		}
	};
	
	private void goHome()
	{
		Intent i = new Intent();
		
		i.setClass(getApplication(), MainPageActivity.class);
		
		startActivity(i);
		
		finish();
	}
	
	private void initViews()
	{
		mDeviceNoEdit = (EditText) findViewById(R.id.device_no_edit);
		
		mOnlineBtn = (RadioButton) findViewById(R.id.online_radio);
		
		mOfflineBtn = (RadioButton) findViewById(R.id.offline_radio);
		
		mOnlineBtn.setOnClickListener(mOnClickListener);
		
		mOfflineBtn.setOnClickListener(mOnClickListener);
		
		mOnlineBtn.setChecked(mIsOnlineLogin);
		
		mOfflineBtn.setChecked(!mIsOnlineLogin);
		
//		mUserNoEdit = (EditText) findViewById(R.id.user_no_edit);
		
//		mUserPsdEdit = (EditText) findViewById(R.id.user_passwd_edit);
		
		TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		
		mDeviceNoEdit.setText(tm.getDeviceId());
		
		mWaiting = (RelativeLayout) findViewById(R.id.waiting_layout);
	}
	
	private void initTopbar()
	{
		TextView title = (TextView) findViewById(R.id.title_text);
		
		title.setText(R.string.login_title);
	}
}
