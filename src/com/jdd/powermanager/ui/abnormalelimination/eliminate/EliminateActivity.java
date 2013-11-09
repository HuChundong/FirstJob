package com.jdd.powermanager.ui.abnormalelimination.eliminate;

import java.io.File;
import java.util.HashMap;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.Spinner;
import android.widget.TextView;
import com.jdd.common.utils.barcode.BarCodeHelper;
import com.jdd.common.utils.barcode.OnBarCodeScanedListener;
import com.jdd.common.utils.date.DateHelper;
import com.jdd.common.utils.gps.GpsHelper;
import com.jdd.common.utils.gps.LocationInfo;
import com.jdd.common.utils.property.PropertyHelper;
import com.jdd.common.utils.time.TimeHelper;
import com.jdd.common.utils.toast.ToastHelper;
import com.jdd.powermanager.R;
import com.jdd.powermanager.action.AbsCallback;
import com.jdd.powermanager.action.elimination.EliminationActions;
import com.jdd.powermanager.basic.BaseActivity;
import com.jdd.powermanager.bean.Meters;
import com.jdd.powermanager.model.MeterSurvey.EliminateAbnormalManager;
import com.jdd.powermanager.model.MeterSurvey.EliminateAbnormalForm.EliminateAbnormal;
import com.jdd.powermanager.ui.abnormalelimination.viewimage.ViewImageActivity;
import com.jdd.powermanager.ui.widgt.FullScreenWaitBar;

public class EliminateActivity extends BaseActivity 
{
	private TextView mSaveBtn;
	
	private TextView mSubmitBtn;
	
	private TextView mBackBtn;
	
	private TextView mUserNo;
	
	private TextView mMpNO;
	
	private AutoCompleteTextView mAssetNo;
	
	private TextView mUserName;
	
	private TextView mAddress;
	
	private EditText mLo;
	
	private EditText mLa;
	
	private Button mGps;
	
	private TextView mBarcode;
	
	private TextView mR;
	
	private TextView mC;
	
	private Spinner mProblem;
	
	private LocalSpinnerAdapter mProblemAdapter;
	
	private Spinner mMethod;
	
	private LocalSpinnerAdapter mMethodAdapter;
	
	private Gallery mImgs;
	
	private ImageAdapter mImgAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.eliminate_page);
		
		final String meterCode = getIntent().getStringExtra("code");
		
		EliminationActions.getEliminateTaskWithSpecifiedAssetNO(meterCode, new AbsCallback() 
		{
			@Override
			protected void onResult(Object o) 
			{
				@SuppressWarnings("unchecked")
				HashMap<String, String> meter = null == o ? null : (HashMap<String, String>)o;
			 	
				if( null == meter || meter.isEmpty() )
				{
					FullScreenWaitBar.hide();
					
					ToastHelper.showToastShort(EliminateActivity.this, EliminateActivity.this.getString(R.string.no_need_elimination));
					
					finish();
				}
				else
				{
					EliminationActions.getAllMeterAssetNO( new AbsCallback() 
					{
						@Override
						protected void onResult(Object o)
						{
							FullScreenWaitBar.hide();
							
							Meters m = (Meters)o;
							
							mAssetNo.setText(meterCode);
							
							if( null != m.getAllNos() )
							{
								ArrayAdapter<String> adapter = new ArrayAdapter<String>( EliminateActivity.this,  R.layout.drop_down_list_item,  m.getAllNos()); 
								
								mAssetNo.setAdapter(adapter);
							}
						}
					});
				}
			}
		});
		
		initViews();
	}
	
	private void initViews()
	{
		TextView title = (TextView) findViewById(R.id.title_text);
		
		title.setText(R.string.abnormal_elimination);
		
		mSaveBtn = (TextView) findViewById(R.id.save);
		
		mSubmitBtn = (TextView) findViewById(R.id.submit);
		
		mBackBtn = (TextView) findViewById(R.id.back_btn);
		
		mSaveBtn.setOnClickListener(mOnClickLis);
		mSubmitBtn.setOnClickListener(mOnClickLis);
		mBackBtn.setOnClickListener(mOnClickLis);
		
		mUserNo = (TextView) findViewById(R.id.user_no_edit);
		
		mMpNO = (TextView) findViewById(R.id.mp_edit);
		
		mAssetNo = (AutoCompleteTextView) findViewById(R.id.asset_no_edit);
		
		mAssetNo.addTextChangedListener(new TextWatcher() 
		{
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) 
			{
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3)
			{
			}
			
			@Override
			public void afterTextChanged(Editable arg0) 
			{
				refreshBoxInfo(arg0.toString());
			}
		});
		
		mUserName = (TextView) findViewById(R.id.user_name_edit);
		
		mAddress = (TextView) findViewById(R.id.address_edit);
		
		mLo = (EditText) findViewById(R.id.lo_edit);
		
		mLa = (EditText) findViewById(R.id.la_edit);
		
		mGps = (Button) findViewById(R.id.gps_btn);
		
		mGps.setOnClickListener(mOnClickLis);
		
		mBarcode = (TextView) findViewById(R.id.barcode_edit);
		
		mR = (TextView) findViewById(R.id.row_edit);
		
		mC = (TextView) findViewById(R.id.colum_edit);
		
		mProblem = (Spinner) findViewById(R.id.phenomenon_spinner);
		
		mProblemAdapter = new LocalSpinnerAdapter(this);
		
		String problems[] = getConfigs("abnormal_phenomenon");
		
		mProblemAdapter.setItems(problems);
		
		mProblem.setAdapter(mProblemAdapter);
		
		if( null != problems && problems.length > 0 )
		{
			mProblem.setSelection(1);
			
			mProblemAdapter.setSelectedIndex(1);
		}
		
		mProblem.setOnItemSelectedListener(new OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) 
			{
				if( 0 == arg2 )
				{
					showDiyDialog( 0, mProblemAdapter.getSelectedIndex() );
				}
				else
				{
					mProblemAdapter.setSelectedIndex(arg2);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0)
			{
			}
		});
		
		mMethod = (Spinner) findViewById(R.id.method_spinner);
		
		mMethodAdapter = new LocalSpinnerAdapter(this);
		
		String methods[] = getConfigs("eliminate_method");
		
		mMethodAdapter.setItems(methods);
		
		mMethod.setAdapter(mMethodAdapter);
		
		if( null != methods && methods.length > 0 )
		{
			mMethod.setSelection(1);
			
			mMethodAdapter.setSelectedIndex(1);
		}
		
		mMethod.setOnItemSelectedListener(new OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) 
			{
				if( 0 == arg2 )
				{
					showDiyDialog( 1, mMethodAdapter.getSelectedIndex() );
				}
				else
				{
					mMethodAdapter.setSelectedIndex(arg2);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0)
			{
			}
		});
		
		mImgs = (Gallery) findViewById(R.id.img_gallery);
		
		mImgAdapter = new ImageAdapter(this);
		
		mImgs.setAdapter(mImgAdapter);
		
		mImgs.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) 
			{
				if( arg2 == mImgAdapter.getCount() - 1 )
				{
					String path  = mImgAdapter.getPath();
					
					if( null == path || path.equals("") )
					{
						ToastHelper.showToastShort(EliminateActivity.this,EliminateActivity.this.getString(R.string.can_not_creat_img_dir));
						
						return;
					}
					
					try 
					{ 
						Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
						
						File f = new File( path + "/" + System.currentTimeMillis() + ".png" );
						
						Uri localUri = Uri.fromFile(f);
						
						intent.putExtra("output", localUri);
						
						intent.putExtra("android.intent.extra.screenOrientation", false);
						
						startActivityForResult(intent, 1);
			        } 
					catch (Exception e)
					{ 
						e.printStackTrace();
			        } 
				}
				else
				{
					Intent i = new Intent();
					
					i.setClass(EliminateActivity.this, ViewImageActivity.class);
					
					i.putExtra("path", mImgAdapter.getPath());
					
					i.putExtra("index", arg2);
					
					EliminateActivity.this.startActivity(i);
				}
			}
		}
		);
		
		BarCodeHelper.addListener(mBarCodeLis);
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
    { 
        super.onActivityResult(requestCode, resultCode, data);
        
        mImgAdapter.refreshImgs();
    }
	
	private String[] getConfigs(String s)
	{
		String ss = PropertyHelper.getProperty(s,getResources().openRawResource(R.raw.config));
		
		if( null == ss )
		{
			return new String[]{};
		}
		
		String menus[] = ss.split("_");
		
		return menus;
	}
	
	private void refreshBoxInfo(String assetNo)
	{
		if( null == assetNo || assetNo.equals("") )
		{
			return;
		}
		
		HashMap<String, String> meter = EliminateAbnormalManager.getInstance().getEliminateTaskWithSpecifiedAssetNO(assetNo);
	 	
		if( null == meter || meter.isEmpty() )
		{
//			ToastHelper.showToastShort(EliminateActivity.this, EliminateActivity.this.getString(R.string.no_need_elimination));
			
//			finish();
		}
		
		mUserNo.setText(meter.get(EliminateAbnormal.CONS_NO));
		
		mMpNO.setText(meter.get(EliminateAbnormal.MP_NO));
		
		mUserName.setText(meter.get(EliminateAbnormal.USER_NAME));
		
		mAddress.setText(meter.get(EliminateAbnormal.USER_ADDRESS));
		
		mLo.setText(meter.get(EliminateAbnormal.LONGITUDE));
		
		mLa.setText(meter.get(EliminateAbnormal.LATITUDE));
		
		mBarcode.setText(meter.get(EliminateAbnormal.BAR_CODE));
		
		mR.setText(meter.get(EliminateAbnormal.IN_ROW));
		
		mC.setText(meter.get(EliminateAbnormal.IN_COLUMN));
		
		String problem = meter.get(EliminateAbnormal.ABNORMAL_PHENOMENON);
		
		if( null != problem && !problem.equals(""))
		{
			int i = mProblemAdapter.addItem(problem);
			
			mProblem.setSelection(i);
			
			mProblemAdapter.setSelectedIndex(i);
		}
		else if(mProblemAdapter.getCount() > 1)
		{
			mProblem.setSelection(1);
			
			mProblemAdapter.setSelectedIndex(1);
		}
		
		String method = meter.get(EliminateAbnormal.ELIMINATE_METHOD);
		
		if( null != method && !method.equals(""))
		{
			int i = mMethodAdapter.addItem(method);
			
			mMethod.setSelection(i);
			
			mMethodAdapter.setSelectedIndex(i);
		}
		else if(mProblemAdapter.getCount() > 1)
		{
			mMethod.setSelection(1);
			
			mMethodAdapter.setSelectedIndex(1);
		}
		
		mImgAdapter.setPath(EliminateAbnormalManager.getInstance().getMeterAbnormalPhotoPath(assetNo));
	}
	
	private OnBarCodeScanedListener mBarCodeLis = new OnBarCodeScanedListener() 
	{
		@Override
		public void onScaned(String code) 
		{
			mAssetNo.setText(code);
		}
	};
	
	public void submit()
	{
		String code = mAssetNo.getText().toString();
		
		EliminationActions.commitOneMeter(code,new AbsCallback() 
		{
			@Override
			public void onResult(Object o) 
			{
				ToastHelper.showToastShort(EliminateActivity.this, getString(R.string.commit_sucess));
				
				FullScreenWaitBar.hide();
				
				back();
			}
		});
	}
	
	private String getLoginNo()
	{
		 SharedPreferences settings = this.getSharedPreferences("userInfo", 0);
		 
		 return settings.getString("loginNo", "");
	}
	
	public void save(final boolean isSubmit)
	{
		String problem = (String) mProblem.getSelectedItem();
		
		String method = (String) mMethod.getSelectedItem();
		
		String code = mAssetNo.getText().toString();
		
		String lo = mLo.getText().toString();
		
		String la= mLa.getText().toString();
		
		String loginNo = getLoginNo();
		
		if( null == code || code.equals("") )
		{
			ToastHelper.showToastShort(this, getString(R.string.asset_no_is_null));
			
			return;
		}
		
		if( null == problem || problem.equals("") )
		{
			ToastHelper.showToastShort(this, getString(R.string.problem_is_null));
			
			return;
		}
		
		if( null == method || method.equals("") )
		{
			ToastHelper.showToastShort(this, getString(R.string.method_is_null));
			
			return;
		}
		
		FullScreenWaitBar.show(this, R.layout.full_screen_wait_bar);
		
		String date = DateHelper.getDate("-");
		
		String time = TimeHelper.getTime(":");
		
		String logNo = getLoginNo();
		
		logNo = null == logNo ? "" : logNo;
		
		EliminationActions.updateProblemAndMethodWithMeterAssetNO(date , time , logNo , code, problem, method,lo,la,loginNo, new AbsCallback() 
		{
			@Override
			protected void onResult(Object o) 
			{
				if(isSubmit)
				{
					submit();
				}
				else
				{
					ToastHelper.showToastShort(EliminateActivity.this, getString(R.string.save_sucess));
					
					FullScreenWaitBar.hide();
					
					back();
				}
			}
		});
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
	
	private void showDiyDialog(final int type,final int curIndex)
	{
		View v = LayoutInflater.from(this).inflate(R.layout.edittext_dialog_view, null);
		
		final EditText et = (EditText) v.findViewById(R.id.edit_text);
		
		AlertDialog.Builder builder = new Builder(this);
		
		builder.setView(v);
		
		builder.setTitle(this.getString(0 == type ? R.string.problem_title : R.string.method_title));
		
		builder.setPositiveButton(this.getString(R.string.ok), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{
				dialog.dismiss();
				
				String text = et.getText().toString();
				
				if( 0 == type )
				{
					if( null == text || text.equals("") )
					{
						mProblem.setSelection(curIndex);
					}
					else
					{
						int i = mProblemAdapter.addItem(text);
						
						mProblem.setSelection(i);
						
						mProblemAdapter.setSelectedIndex(i);
					}
				}
				else
				{
					if( null == text || text.equals("") )
					{
						mMethod.setSelection(curIndex);
					}
					else
					{
						int i = mMethodAdapter.addItem(text);
						
						mMethod.setSelection(i);
						
						mMethodAdapter.setSelectedIndex(i);
					}
				}
			}
		});
		
		builder.setNegativeButton(this.getString(R.string.cancel), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{
				dialog.dismiss();
				
				if( 0 == type )
				{
					mProblem.setSelection(curIndex);
				}
				else
				{
					mMethod.setSelection(curIndex);
				}
			}
		});

		builder.create().show();
	}
	
	private boolean mIsGPSing;
	
	@SuppressLint("HandlerLeak")
	private Handler mHander = new Handler()
	{
		public void handleMessage(android.os.Message msg) 
		{
			mIsGPSing = false;
			
			mGps.setClickable(true);
			
			mGps.setText(R.string.btn_get);
			
			LocationInfo li = null == msg.obj ? null :  (LocationInfo) msg.obj;
			
			if( null != li )
			{
				mLo.setText(""+li.getLongitude());
				
				mLa.setText(""+li.getLatitude());
			}
		};
	};
	
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
		
		mGps.setClickable(false);
		
		mGps.setText(R.string.get_gps);
		
		mRunnable.mIsCancelGps = false;
		
		new Thread(mRunnable).start();
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
			
				case R.id.save:
					
					save(false);
					
					break;
					
				case R.id.submit:
					
					save(true);
					
					break;
					
				case R.id.back_btn:
					
					back();
					
					break;
			}
		}
	};
}
