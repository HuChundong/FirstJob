package com.jdd.powermanager.ui.abnormalelimination.elimination;

import java.util.HashMap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Gallery;
import android.widget.Spinner;
import android.widget.TextView;
import com.jdd.common.utils.barcode.BarCodeHelper;
import com.jdd.common.utils.barcode.OnBarCodeScanedListener;
import com.jdd.common.utils.toast.ToastHelper;
import com.jdd.powermanager.R;
import com.jdd.powermanager.action.AbsCallback;
import com.jdd.powermanager.action.elimination.EliminationActions;
import com.jdd.powermanager.basic.BaseActivity;
import com.jdd.powermanager.ui.widgt.FullScreenWaitBar;

public class EliminateActivity extends BaseActivity 
{
	private TextView mNewBtn;
	
	private TextView mSaveBtn;
	
	private TextView mSubmitBtn;
	
	private TextView mBackBtn;
	
	private String mMeterCode;
	
	private HashMap<String, String> mMeterInfo;
	
	private TextView mUserNo;
	
	private TextView mMpNO;
	
	private TextView mAssetNo;
	
	private TextView mUserName;
	
	private TextView mAddress;
	
	private TextView mLo;
	
	private TextView mLa;
	
	private TextView mBarcode;
	
	private TextView mR;
	
	private TextView mC;
	
	private Spinner mProblem;
	
	private Spinner mMethod;
	
	private Gallery mImgs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.eliminate_page);
		
		mMeterCode = getIntent().getStringExtra("code");
		
		FullScreenWaitBar.show(this, R.layout.full_screen_wait_bar);
		
		EliminationActions.getEliminateTaskWithSpecifiedAssetNO(mMeterCode, new AbsCallback() 
		{
			@Override
			protected void onResult(Object o) 
			{
				FullScreenWaitBar.hide();
				
				@SuppressWarnings("unchecked")
				HashMap<String, String> meter = null == o ? null : (HashMap<String, String>)o;
			 	
				if( null == meter )
				{
					ToastHelper.showToastShort(EliminateActivity.this, EliminateActivity.this.getString(R.string.no_need_elimination));
					
					finish();
				}
				else
				{
					mMeterInfo = meter; 
					
					refreshBoxInfo(mMeterInfo);
				}
			}
		});
		
		initViews();
	}
	
	private void initViews()
	{
		TextView title = (TextView) findViewById(R.id.title_text);
		
		title.setText(R.string.box_combing);
		
		mNewBtn = (TextView) findViewById(R.id.add_new);
		
		mSaveBtn = (TextView) findViewById(R.id.save);
		
		mSubmitBtn = (TextView) findViewById(R.id.submit);
		
		mBackBtn = (TextView) findViewById(R.id.back_btn);
		
		mNewBtn.setOnClickListener(mOnClickLis);
		mSaveBtn.setOnClickListener(mOnClickLis);
		mSubmitBtn.setOnClickListener(mOnClickLis);
		mBackBtn.setOnClickListener(mOnClickLis);
		
		mUserNo = (TextView) findViewById(R.id.user_no_edit);
		
		mMpNO = (TextView) findViewById(R.id.mp_edit);
		
		mAssetNo = (TextView) findViewById(R.id.asset_no_edit);
		
		mUserName = (TextView) findViewById(R.id.user_name_edit);
		
		mAddress = (TextView) findViewById(R.id.address_edit);
		
		mLo = (TextView) findViewById(R.id.lo_edit);
		
		mLa = (TextView) findViewById(R.id.la_edit);
		
		mBarcode = (TextView) findViewById(R.id.barcode_edit);
		
		mR = (TextView) findViewById(R.id.row_edit);
		
		mC = (TextView) findViewById(R.id.colum_edit);
		
		mProblem = (Spinner) findViewById(R.id.phenomenon_spinner);
		
		// TODO
		
		mMethod = (Spinner) findViewById(R.id.method_spinner);
		
		// TODO
		
		mImgs = (Gallery) findViewById(R.id.img_gallery);
		
		BarCodeHelper.addListener(mBarCodeLis);
	}
	
	private void refreshBoxInfo(HashMap<String, String> meter)
	{
		// TOOD
	}
	
	private void refreshBoxInfo(String assetNo)
	{
		if( null == assetNo || assetNo.equals("") )
		{
			return;
		}
		
//		HashMap<String, String> data = BoxSurveyDataManager.getInstance().getBoxWithAssetNo(assetNo);
//		
//		if( null == data || data.isEmpty() )
//		{
//			return;
//		}
//		
//		String cAddress = mAddressEdit.getText().toString();
//		
//		if( null == cAddress || cAddress.equals("") )
//		{
//			String address = data.get(BoxSurvey.INST_LOC);
//			
//			mAddressEdit.setText( null == address ? "" : address );
//		}
//		
//		String cRow = mRowEdit.getText().toString();
//		
//		if( null == cRow || cRow.equals("") )
//		{
//			String row = data.get(BoxSurvey.BOX_ROWS);
//			
//			mRowEdit.setText( null == row ? "" : row );
//		}
//		
//		String cCol = mColumnEdit.getText().toString();
//		
//		if( null == cCol || cCol.equals("") )
//		{
//			String col = data.get(BoxSurvey.BOX_COLS);
//			
//			mColumnEdit.setText( null == col ? "" : col );
//		}
//		
//		String cMCount = mMeterCountEdit.getText().toString();
//		
//		if( null == cMCount || cMCount.equals("") )
//		{
//			String count = data.get(BoxSurvey.METER_NUM);
//			
//			mMeterCountEdit.setText( null == count ? "" : count );
//		}
//		
//		String clo = mLoEdit.getText().toString();
//		
//		if( null == clo || clo.equals("") )
//		{
//			String lo = data.get(BoxSurvey.LONGITUDE);
//			
//			mLoEdit.setText( null == lo ? "" : lo );
//		}
//		
//		String cla = mLaEdit.getText().toString();
//		
//		if( null == cla || cla.equals("") )
//		{
//			String la = data.get(BoxSurvey.LATITUDE);
//			
//			mLaEdit.setText( null == la ? "" : la );
//		}
	}
	
	private OnBarCodeScanedListener mBarCodeLis = new OnBarCodeScanedListener() 
	{
		@Override
		public void onScaned(String code) 
		{
			// TODO
			
			refreshBoxInfo(code);
		}
	};
	
	public void submit()
	{
		// TODO
	}
	
	public void save(final boolean isSubmit)
	{
		// TODO
	}
	
	private void back()
	{
		finish();
	}
	
	@Override
	public void finish() 
	{
		super.finish();
		
		BarCodeHelper.clearListener();
	}
	
	private void addNew()
	{
		mAssetNo.setText("");
	}
	
	private OnClickListener mOnClickLis = new OnClickListener() 
	{
		@Override
		public void onClick(View v) 
		{
			switch(v.getId())
			{
				case R.id.add_new:
					
					addNew();
					
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
