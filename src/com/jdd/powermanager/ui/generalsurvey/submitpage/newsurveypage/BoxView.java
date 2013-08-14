package com.jdd.powermanager.ui.generalsurvey.submitpage.newsurveypage;

import java.util.HashMap;
import com.jdd.common.utils.barcode.BarCodeHelper;
import com.jdd.common.utils.barcode.OnBarCodeScanedListener;
import com.jdd.common.utils.date.DateHelper;
import com.jdd.common.utils.gps.GpsHelper;
import com.jdd.common.utils.gps.LocationInfo;
import com.jdd.common.utils.property.PropertyHelper;
import com.jdd.common.utils.toast.ToastHelper;
import com.jdd.powermanager.R;
import com.jdd.powermanager.basic.Pager;
import com.jdd.powermanager.basic.SimpleSpinnerAdapter;
import com.jdd.powermanager.model.MeterSurvey.SurveyForm;
import com.jdd.powermanager.model.MeterSurvey.MeterSurveyForm.MeterSurvey;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class BoxView implements Pager
{
	private Context mContext;
	
	private View mRoot;
	
	private EditText mBarCodeEdit;
	
	private Spinner mBoxTypeSpinner;
	
	private Spinner mCateGorySpinner;
	
	private Spinner mAssetsSpinner;
	
	private EditText mAddressEdit;
	
	private EditText mRowEdit;
	
	private EditText mColumnEdit;
	
	private EditText mLoEdit;
	
	private EditText mLaEdit;
	
	private Button mGpsBtn;
	
	private TextView mNewBtn;
	
	private TextView mSaveBtn;
	
	private TextView mSubmitBtn;
	
	private TextView mExcBtn;
	
	private TextView mBackBtn;
	
	private String mExceptionInfo;
	
	public BoxView(Context context)
	{
		mContext = context;
	}
	
	public View getView()
	{
		mRoot = LayoutInflater.from(mContext).inflate(R.layout.survey_box_page, null);
		
		initViews();
		
		return mRoot;
	}
	
	private void initViews()
	{
		mBarCodeEdit = (EditText) mRoot.findViewById(R.id.bar_code_edit);
		
		mBoxTypeSpinner = (Spinner) mRoot.findViewById(R.id.box_type_spinner);
		
		String typemenus[] = getConfigs("device_type");
		
		SimpleSpinnerAdapter sta = new SimpleSpinnerAdapter(mContext);
		
		sta.setItems(typemenus);
		
		mBoxTypeSpinner.setAdapter(sta);
		
		String categorymenus[] = getConfigs("device_category");
		
		mCateGorySpinner = (Spinner) mRoot.findViewById(R.id.category_spinner);
		
		SimpleSpinnerAdapter sca = new SimpleSpinnerAdapter(mContext);
		
		sca.setItems(categorymenus);
		
		mCateGorySpinner.setAdapter(sca);
		
		String assetsmenus[] = getConfigs("device_property");
		
		mAssetsSpinner = (Spinner) mRoot.findViewById(R.id.assets_spinner);
		
		SimpleSpinnerAdapter saa = new SimpleSpinnerAdapter(mContext);
		
		saa.setItems(assetsmenus);
		
		mAssetsSpinner.setAdapter(saa);
		
		mAddressEdit = (EditText) mRoot.findViewById(R.id.address_edit);
		
		mRowEdit = (EditText) mRoot.findViewById(R.id.row_edit);
		
		mColumnEdit = (EditText) mRoot.findViewById(R.id.column_edit);
		
		mLoEdit = (EditText) mRoot.findViewById(R.id.lo_edit);
		
		mLaEdit = (EditText) mRoot.findViewById(R.id.la_edit);
		
		mGpsBtn = (Button) mRoot.findViewById(R.id.gps_btn);
		
		mNewBtn = (TextView) mRoot.findViewById(R.id.add_new);
		
		mSaveBtn = (TextView) mRoot.findViewById(R.id.save);
		
		mSubmitBtn = (TextView) mRoot.findViewById(R.id.submit);
		
		mExcBtn = (TextView) mRoot.findViewById(R.id.exception);
		
		mBackBtn = (TextView) mRoot.findViewById(R.id.back_btn);
		
		mGpsBtn.setOnClickListener(mOnClickLis);
		mNewBtn.setOnClickListener(mOnClickLis);
		mSaveBtn.setOnClickListener(mOnClickLis);
		mSubmitBtn.setOnClickListener(mOnClickLis);
		mExcBtn.setOnClickListener(mOnClickLis);
		mBackBtn.setOnClickListener(mOnClickLis);
	}
	
	private String[] getConfigs(String s)
	{
		String ss = PropertyHelper.getProperty(s,mContext.getResources().openRawResource(R.raw.config));
		
		if( null == ss )
		{
			return new String[]{};
		}
		
		String menus[] = ss.split("_");
		
		return menus;
	}
	
	@SuppressLint("HandlerLeak")
	private Handler mHander = new Handler()
	{
		public void handleMessage(android.os.Message msg) 
		{
			mIsGPSing = false;
			
			mGpsBtn.setClickable(true);
			
			mGpsBtn.setText(R.string.btn_get);
			
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
			ToastHelper.showToastShort(mContext, mContext.getString(R.string.gps_get_location_wait_tip));
			
			return;
		}
		
		ToastHelper.showToastShort(mContext, mContext.getString(R.string.gps_get_location_wait_tip));
		
		mIsGPSing = true;
		
		mGpsBtn.setClickable(false);
		
		mGpsBtn.setText(R.string.get_gps);
		
		mRunnable.mIsCancelGps = false;
		
		new Thread(mRunnable).start();
	}
	
	public HashMap<String,String> getBoxInfo()
	{
		HashMap<String,String> m = new HashMap<String,String>();
		
		String barcode = mBarCodeEdit.getText().toString();
		
		if( null == barcode || barcode.equals("") )
		{
			ToastHelper.showToastShort(mContext, mContext.getString(R.string.barcode_is_null));
			
			return null;
		}
		
		m.put(MeterSurvey.BAR_CODE, barcode);
		
		String categroy = (String) mCateGorySpinner.getSelectedItem();
		
		m.put(MeterSurvey.SORT_CODE, "" + categroy);
		
		String asset = (String) mAssetsSpinner.getSelectedItem();
		
		m.put(MeterSurvey.PR_CODE, "" + asset);
		
		String address = mAddressEdit.getText().toString();
		
		m.put(MeterSurvey.INST_LOC, "" + address);
		
		String row = mRowEdit.getText().toString();
		
		m.put(MeterSurvey.BOX_ROWS, "" + row);
		
		String column = mColumnEdit.getText().toString();
		
		m.put(MeterSurvey.BOX_COLS, "" + column);
		
		String lo = mLoEdit.getText().toString();
		
		m.put(MeterSurvey.LONGITUDE, "" + lo);
		
		String la = mLaEdit.getText().toString();
		
		m.put(MeterSurvey.LATITUDE, "" + la);
		
		m.put(SurveyForm.ABNORMAL_COMMENT, null == mExceptionInfo ? "" : mExceptionInfo);
		
		m.put(MeterSurvey.SURVEY_TIME, DateHelper.getDate("-"));
		
		return m;
	}
	
	public int getRow()
	{
		String row = mRowEdit.getText().toString();
		
		try
		{
			return Integer.parseInt(row);
		}
		catch(NumberFormatException  e)
		{
		}
		
		return 0;
	}
	
	public int getColumn()
	{
		String c = mColumnEdit.getText().toString();
		
		try
		{
			return Integer.parseInt(c);
		}
		catch(NumberFormatException  e)
		{
		}
		
		return 0;
	}
	
	private void addNew()
	{
		mBarCodeEdit.setText("");
	}
	
	private void save()
	{
		String barcode = mBarCodeEdit.getText().toString();
		
		if( null == barcode || barcode.equals("") )
		{
			ToastHelper.showToastShort(mContext, mContext.getString(R.string.barcode_is_null));
			
			return;
		}
		
		((NewSurveyActivity)mContext).save(false);
	}
	
	private void submit()
	{
		String barcode = mBarCodeEdit.getText().toString();
		
		if( null == barcode || barcode.equals("") )
		{
			ToastHelper.showToastShort(mContext, mContext.getString(R.string.barcode_is_null));
			
			return;
		}
		
		((NewSurveyActivity)mContext).save(true);
	}
	
	private void exc()
	{
		showDialog();
	}
	
	private void back()
	{
		((Activity)mContext).finish();
	}
	
	private OnBarCodeScanedListener mBarCodeLis = new OnBarCodeScanedListener() 
	{
		@Override
		public void onScaned(String code) 
		{
			Log.d("", "zhou -- boxview -- barcode " + code);
			
			mBarCodeEdit.setText(code);
		}
	};
	
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
					
					save();
					
					break;
					
				case R.id.submit:
					
					submit();
					
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
		View v = LayoutInflater.from(mContext).inflate(R.layout.edittext_dialog_view, null);
		
		final EditText et = (EditText) v.findViewById(R.id.edit_text);
		
		AlertDialog.Builder builder = new Builder(mContext);
		
		builder.setView(v);
		
		builder.setTitle(mContext.getString(R.string.exception_title));
		
		builder.setPositiveButton(mContext.getString(R.string.ok), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{
				dialog.dismiss();
				
				mExceptionInfo = et.getText().toString();
			}
		});
		
		builder.setNegativeButton(mContext.getString(R.string.cancel), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{
				dialog.dismiss();
			}
		});

		builder.create().show();
	}

	@Override
	public void onSelected() 
	{
		BarCodeHelper.addListener(mBarCodeLis);
	}

	@Override
	public void onCancelSelected() 
	{
	}

	@Override
	public void onDestroy() 
	{
		Log.d("", "zhou -- boxview -- ondestroy");
		
		mContext = null;
		
		mRoot = null;
		
		mRunnable.mIsCancelGps = true;
		
		mHander.removeCallbacksAndMessages(null);
		
		mHander = null;
		
		mRunnable = null;
		
		BarCodeHelper.clearListener();
	}
}
