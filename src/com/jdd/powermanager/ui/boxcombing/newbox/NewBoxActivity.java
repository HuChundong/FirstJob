package com.jdd.powermanager.ui.boxcombing.newbox;

import java.util.ArrayList;
import java.util.HashMap;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.jdd.common.utils.barcode.BarCodeHelper;
import com.jdd.common.utils.barcode.OnBarCodeScanedListener;
import com.jdd.common.utils.gps.GpsHelper;
import com.jdd.common.utils.gps.LocationInfo;
import com.jdd.common.utils.toast.ToastHelper;
import com.jdd.powermanager.R;
import com.jdd.powermanager.action.AbsCallback;
import com.jdd.powermanager.action.combing.CombingActions;
import com.jdd.powermanager.basic.BaseActivity;
import com.jdd.powermanager.model.MeterSurvey.BoxSurveyForm.BoxSurvey;
import com.jdd.powermanager.model.MeterSurvey.SurveyForm;
import com.jdd.powermanager.ui.widgt.FullScreenWaitBar;

public class NewBoxActivity extends BaseActivity 
{
	private EditText mBarCodeEdit;
	
	private EditText mAddressEdit;
	
	private EditText mRowEdit;
	
	private EditText mColumnEdit;
	
	private EditText mMeterCountEdit;
	
	private TextView mNewBtn;
	
	private TextView mSaveBtn;
	
	private TextView mSubmitBtn;
	
	private TextView mExcBtn;
	
	private TextView mBackBtn;
	
	private EditText mLoEdit;
	
	private EditText mLaEdit;
	
	private Button mGpsBtn;
	
	private String mExceptionInfo;
	
	private HashMap<String,String> mDistrict;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.combing_add_new_box_page);
		
		mDistrict = new HashMap<String,String>();
		
		String id = getIntent().getStringExtra("DistrictId");
		String logo = getIntent().getStringExtra("DistrictLogo");
		
		mDistrict.put(BoxSurvey.DISTRICT_ID, id);
		mDistrict.put(BoxSurvey.DISTRICT_LOGO, logo);
		
		initViews();
	}
	
	private void initViews()
	{
		TextView title = (TextView) findViewById(R.id.title_text);
		
		title.setText(R.string.box_combing);
		
		mBarCodeEdit = (EditText) findViewById(R.id.bar_code_edit);
		
		mAddressEdit = (EditText) findViewById(R.id.address_edit);
		
		mRowEdit = (EditText) findViewById(R.id.row_edit);
		
		mColumnEdit = (EditText) findViewById(R.id.column_edit);
		
		mMeterCountEdit = (EditText) findViewById(R.id.meter_count_edit);
		
		mNewBtn = (TextView) findViewById(R.id.add_new);
		
		mSaveBtn = (TextView) findViewById(R.id.save);
		
		mSubmitBtn = (TextView) findViewById(R.id.submit);
		
		mExcBtn = (TextView) findViewById(R.id.exception);
		
		mBackBtn = (TextView) findViewById(R.id.back_btn);
		
		mLoEdit = (EditText) findViewById(R.id.lo_edit);
		
		mLaEdit = (EditText) findViewById(R.id.la_edit);
		
		mGpsBtn = (Button) findViewById(R.id.gps_btn);
		
		mGpsBtn.setOnClickListener(mOnClickLis);
		
		mNewBtn.setOnClickListener(mOnClickLis);
		mSaveBtn.setOnClickListener(mOnClickLis);
		mSubmitBtn.setOnClickListener(mOnClickLis);
		mExcBtn.setOnClickListener(mOnClickLis);
		mBackBtn.setOnClickListener(mOnClickLis);
		
		BarCodeHelper.addListener(mBarCodeLis);
	}
	
	@SuppressLint("HandlerLeak")
	private Handler mHander = new Handler()
	{
		public void handleMessage(android.os.Message msg) 
		{
			mIsGPSing = false;
			
			mGpsBtn.setClickable(true);
			
			LocationInfo li = null == msg.obj ? null :  (LocationInfo) msg.obj;
			
			if( null != li )
			{
				mLoEdit.setText(""+li.getLongitude());
				
				mLaEdit.setText(""+li.getLatitude());
			}
		};
	};
	
	private boolean mIsGPSing;
	
	private class GpsRunnable implements Runnable
	{
		private boolean mIsCancelGps;
		
		public void run() 
		{
			LocationInfo li = GpsHelper.getCurLocation();
			
			while( null == li  && !mIsCancelGps )
			{
				try 
				{
					Thread.sleep(5000);
				} 
				catch (InterruptedException e) 
				{
					e.printStackTrace();
				}
				
				if(mIsCancelGps)
				{
					break;
				}
				
				li = GpsHelper.getCurLocation();
				
				Log.d("", "zhou  -- boxview -- gps = " + li);
			}
			
			if( !mIsCancelGps && null != mHander )
			{
				mHander.obtainMessage(0, li).sendToTarget();
			}
		};
	}
	
	private GpsRunnable mRunnable = new GpsRunnable();
	
	private void gps()
	{
		if(mIsGPSing)
		{
			ToastHelper.showToastShort(this, this.getString(R.string.gps_get_location_wait_tip));
			
			return;
		}
		
		ToastHelper.showToastShort(this, this.getString(R.string.gps_get_location_wait_tip));
		
		mIsGPSing = true;
		
		mGpsBtn.setClickable(false);
		
		mRunnable.mIsCancelGps = false;
		
		new Thread(mRunnable).start();
	}
	
	private OnBarCodeScanedListener mBarCodeLis = new OnBarCodeScanedListener() 
	{
		@Override
		public void onScaned(String code) 
		{
			mBarCodeEdit.setText(code);
		}
	};
	
	public void submit()
	{
		String barcode = mBarCodeEdit.getText().toString();
		
		String[] ids = {barcode};
		
		CombingActions.commitBoxesSurvey(new AbsCallback() 
		{
			@Override
			public void onResult(Object o) 
			{
				ToastHelper.showToastShort(NewBoxActivity.this, getString(R.string.commit_sucess));
				
				FullScreenWaitBar.hide();
				
				back();
			}
		} , ids);
	}
	
	public void save(final boolean isSubmit)
	{
		String barcode = mBarCodeEdit.getText().toString();
		
		if( null == barcode || barcode.equals("") )
		{
			ToastHelper.showToastShort(this, getString(R.string.barcode_is_null));
			
			return;
		}
		
		FullScreenWaitBar.show(this, R.layout.full_screen_wait_bar);
		
		ArrayList<HashMap<String, String>> boxList = new ArrayList<HashMap<String, String>>();
		
		HashMap<String, String> box = new HashMap<String, String>();
		
		box.put(BoxSurvey.ASSET_NO, mBarCodeEdit.getText().toString());
		
		box.put(BoxSurvey.INST_LOC, mAddressEdit.getText().toString());
		
		box.put(BoxSurvey.BOX_ROWS, mRowEdit.getText().toString());
		
		box.put(BoxSurvey.BOX_COLS, mColumnEdit.getText().toString());
		
		box.put(BoxSurvey.METER_NUM, mMeterCountEdit.getText().toString());
		
		box.put(BoxSurvey.LONGITUDE, mLoEdit.getText().toString());
		
		box.put(BoxSurvey.LATITUDE, mLaEdit.getText().toString());
		
		box.put(SurveyForm.ABNORMAL_COMMENT, mExceptionInfo);
		
		boxList.add(box);
		
		CombingActions.saveBoxSurvey(new AbsCallback() 
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
					ToastHelper.showToastShort(NewBoxActivity.this, getString(R.string.save_sucess));
					
					FullScreenWaitBar.hide();
					
					back();
				}
			}
		}, mDistrict, boxList);
	}
	
	private void exc()
	{
		showDialog();
	}
	
	private void back()
	{
		finish();
	}
	
	@Override
	public void finish() 
	{
		super.finish();
		
		mRunnable.mIsCancelGps = true;
		
		mHander.removeCallbacksAndMessages(null);
		
		mHander = null;
		
		mRunnable = null;
		
		BarCodeHelper.clearListener();
	}
	
	private void addNew()
	{
		mBarCodeEdit.setText("");
	}
	
	private OnClickListener mOnClickLis = new OnClickListener() 
	{
		@Override
		public void onClick(View v) 
		{
			switch(v.getId())
			{
				case R.id.gps_btn:
					
					gps();
					
					break;
			
				case R.id.add_new:
					
					addNew();
					
					break;
					
				case R.id.save:
					
					save(false);
					
					break;
					
				case R.id.submit:
					
					save(true);
					
					break;
					
				case R.id.exception:
					
					exc();
					
					break;
					
				case R.id.back_btn:
					
					back();
					
					break;
			}
		}
	};
	
	private void showDialog()
	{
		View v = LayoutInflater.from(this).inflate(R.layout.edittext_dialog_view, null);
		
		final EditText et = (EditText) v.findViewById(R.id.edit_text);
		
		AlertDialog.Builder builder = new Builder(this);
		
		builder.setView(v);
		
		builder.setTitle(this.getString(R.string.exception_title));
		
		builder.setPositiveButton(this.getString(R.string.ok), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{
				dialog.dismiss();
				
				mExceptionInfo = et.getText().toString();
			}
		});
		
		builder.setNegativeButton(this.getString(R.string.cancel), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{
				dialog.dismiss();
			}
		});

		builder.create().show();
	}
}
