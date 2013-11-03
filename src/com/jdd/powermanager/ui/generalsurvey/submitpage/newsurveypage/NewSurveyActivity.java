package com.jdd.powermanager.ui.generalsurvey.submitpage.newsurveypage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.TextView;
import com.jdd.common.utils.barcode.BarCodeHelper;
import com.jdd.common.utils.date.DateHelper;
import com.jdd.common.utils.time.TimeHelper;
import com.jdd.common.utils.toast.ToastHelper;
import com.jdd.powermanager.R;
import com.jdd.powermanager.action.AbsCallback;
import com.jdd.powermanager.action.survey.SurveyActions;
import com.jdd.powermanager.basic.BaseActivity;
import com.jdd.powermanager.basic.MyViewPagerAdapter;
import com.jdd.powermanager.model.MeterSurvey.MeterSurveyForm.MeterSurvey;
import com.jdd.powermanager.model.MeterSurvey.SurveyForm;
import com.jdd.powermanager.ui.widgt.FullScreenWaitBar;

public class NewSurveyActivity extends BaseActivity 
{
	private ViewPager mViewPaper;
	
	private MyViewPagerAdapter mAdapter;
	
	private BoxView bv;
	
	private BoxAssetsView bav;
	
	private TextView mTabBoxBtn;
	
	private TextView mTabAssetsBtn;
	
	private LockView lv;
	
	private TextView mTabLockBtn;
	
	private String mDistrictId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.survey_add_new_survey_page);
		
		mDistrictId = getIntent().getStringExtra("DistrictId");
		
		initViews();
	}
	
	private void initViews()
	{
		TextView title = (TextView) findViewById(R.id.title_text);
		
		title.setText(R.string.survey_info);
		
		mViewPaper = (ViewPager) findViewById(R.id.view_pager);
		
		mAdapter = new MyViewPagerAdapter(mViewPaper);
		
		bv = new BoxView(this);
		
		bav = new BoxAssetsView(this);
		
		lv = new LockView(this);
		
		mAdapter.addView(bv.getView());
		
		mAdapter.addView(bav.getView());
		
		mAdapter.addView(lv.getView());
		
		mViewPaper.setAdapter(mAdapter);
		
		mViewPaper.setOffscreenPageLimit(3);
		
		mViewPaper.setOnPageChangeListener(mOnPageChangeLis);
		
		mTabBoxBtn = (TextView) findViewById(R.id.tab_btn_box);
		
		mTabBoxBtn.setOnClickListener(mOnClickLis);
		
		mTabAssetsBtn = (TextView) findViewById(R.id.tab_btn_box_assets);
		
		mTabAssetsBtn.setOnClickListener(mOnClickLis);
		
		mTabLockBtn = (TextView) findViewById(R.id.tab_btn_lock);
		
		mTabLockBtn.setOnClickListener(mOnClickLis);
		
		mOnPageChangeLis.onPageSelected(0);
	}
	
	private void submit()
	{
		HashMap<String, String> box = bv.getBoxInfo();
		
		String boxid = box.get(MeterSurvey.BAR_CODE);
		
		List<String> ids = new ArrayList<String>();
		
		ids.add(boxid);
		
		SurveyActions.commitOneBoxMeterSurvey(new AbsCallback() 
		{
			@Override
			public void onResult(Object o) 
			{
				ToastHelper.showToastShort(NewSurveyActivity.this, getString(R.string.commit_sucess));
				
				FullScreenWaitBar.hide();
				
				finish();
			}
		}, ids);
	}
	
	public void save(final boolean isSubmit)
	{
		HashMap<String, String> box = bv.getBoxInfo();
		
		if( null == box )
		{
			return;
		}
		
		ArrayList<HashMap<String, String>> meters = bav.getMeters();
		
		if( null == meters )
		{
			meters = new ArrayList<HashMap<String, String>>();
		}
		
		String lock = lv.getLockCode();
		
		box.put(MeterSurvey.LOCKER_NO, null == lock ? "" : lock);
		
		String loginNo = getLoginNo();
		
		box.put(SurveyForm.OPERATER, null == loginNo ? "" : loginNo );
		
		box.put(SurveyForm.OPERATE_DATE, DateHelper.getDate("-") );
		
		box.put(SurveyForm.OPERATE_TIME, TimeHelper.getTime(":") );
		
		FullScreenWaitBar.show(this, R.layout.full_screen_wait_bar);
		
		SurveyActions.saveOneBoxMeterSurvey(new AbsCallback() 
		{
			@Override
			public void onResult(Object o) 
			{
				if(isSubmit)
				{
					submit();
				}
				else
				{
					ToastHelper.showToastShort(NewSurveyActivity.this, getString(R.string.save_sucess));
					
					FullScreenWaitBar.hide();
					
					finish();
				}
			}
		}, box, meters, mDistrictId);
	}
	
	private String getLoginNo()
	{
		 SharedPreferences settings = this.getSharedPreferences("userInfo", 0);
		 
		 return settings.getString("loginNo", "");
	}
	
	private OnPageChangeListener mOnPageChangeLis = new OnPageChangeListener()
	{
		@Override
		public void onPageSelected(int p) 
		{
			if( 1 == p )
			{
				bav.setRowAndColumn(bv.getRow(), bv.getColumn());
				
				mTabBoxBtn.setSelected(false);
				mTabAssetsBtn.setSelected(true);
				mTabLockBtn.setSelected(false);
				
				bav.onSelected();
			}
			else if( 2 == p)
			{
				mTabBoxBtn.setSelected(false);
				mTabAssetsBtn.setSelected(false);
				mTabLockBtn.setSelected(true);
				
				lv.onSelected();
			}
			else
			{
				mTabBoxBtn.setSelected(true);
				mTabAssetsBtn.setSelected(false);
				mTabLockBtn.setSelected(false);
				
				bv.onSelected();
			}
		}
		
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) 
		{
		}
		
		@Override
		public void onPageScrollStateChanged(int arg0) 
		{
		}
	};
	
	private android.view.View.OnClickListener mOnClickLis = new View.OnClickListener() 
	{
		@Override
		public void onClick(View v) 
		{
			switch(v.getId())
			{
				case R.id.tab_btn_box:
					
					mViewPaper.setCurrentItem(0);
					
					break;
					
				case R.id.tab_btn_box_assets:
					
					mViewPaper.setCurrentItem(1);
					
					break;
					
				case R.id.tab_btn_lock:
					
					mViewPaper.setCurrentItem(2);
					
					break;
			}
		}
	};
	
	protected void onDestroy() 
	{
		super.onDestroy();
		
		BarCodeHelper.clearListener();
		
		if( null != bv )
		{
			bv.onDestroy();
		}
		
		if( null != bav )
		{
			bav.onDestroy();
		}
		
		if( null != lv )
		{
			lv.onDestroy();
		}
	};
}
