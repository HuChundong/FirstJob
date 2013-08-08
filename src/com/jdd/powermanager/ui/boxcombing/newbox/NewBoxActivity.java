package com.jdd.powermanager.ui.boxcombing.newbox;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import com.jdd.powermanager.R;
import com.jdd.powermanager.basic.BaseActivity;

public class NewBoxActivity extends BaseActivity 
{
	private String mDistrictId;
	
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
	
	private String mExceptionInfo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.combing_add_new_box_page);
		
		mDistrictId = getIntent().getStringExtra("DistrictId");
		
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
		
		mNewBtn.setOnClickListener(mOnClickLis);
		mSaveBtn.setOnClickListener(mOnClickLis);
		mSubmitBtn.setOnClickListener(mOnClickLis);
		mExcBtn.setOnClickListener(mOnClickLis);
		mBackBtn.setOnClickListener(mOnClickLis);
	}
	
	public void submit()
	{
		save();
		
		// TODO
	}
	
	public void save()
	{
		// TODO
	}
	
	private void exc()
	{
		showDialog();
	}
	
	private void back()
	{
		finish();
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
