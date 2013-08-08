package com.jdd.powermanager.ui.generalsurvey.submitpage.newsurveypage;

import java.util.ArrayList;
import java.util.HashMap;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.TextView;
import com.jdd.common.utils.barcode.BarCodeHelper;
import com.jdd.powermanager.R;
import com.jdd.powermanager.basic.BaseActivity;
import com.jdd.powermanager.basic.MyViewPagerAdapter;
import com.jdd.powermanager.model.MeterSurvey.MeterSurveyDataManager;
import com.jdd.powermanager.model.MeterSurvey.MeterSurveyForm.MeterSurvey;

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
	
	public void submit()
	{
		save();
		
		HashMap<String, String> box = bv.getBoxInfo();
		
		String boxid = box.get(MeterSurvey.BAR_CODE);
		
		MeterSurveyDataManager.getInstance().commitOneBoxMeterSurvey(boxid);
	}
	
	public void save()
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
		
		MeterSurveyDataManager.getInstance().saveOneBoxMeterSurvey(box, meters,mDistrictId );
	}
	
	private OnPageChangeListener mOnPageChangeLis = new OnPageChangeListener()
	{
		@Override
		public void onPageSelected(int p) 
		{
			BarCodeHelper.clearListener();
			
			if( 1 == p )
			{
				bav.setRowAndColumn(bv.getRow(), bv.getColumn());
				
				mTabBoxBtn.setSelected(false);
				mTabAssetsBtn.setSelected(true);
				mTabLockBtn.setSelected(false);
			}
			else if( 2 == p)
			{
				mTabBoxBtn.setSelected(false);
				mTabAssetsBtn.setSelected(false);
				mTabLockBtn.setSelected(true);
			}
			else
			{
				mTabBoxBtn.setSelected(true);
				mTabAssetsBtn.setSelected(false);
				mTabLockBtn.setSelected(false);
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
