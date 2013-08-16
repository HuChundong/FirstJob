package com.jdd.powermanager.ui.generalsurvey.submitpage.newsurveypage;

import java.util.ArrayList;
import java.util.HashMap;
import com.jdd.common.utils.barcode.BarCodeHelper;
import com.jdd.common.utils.barcode.OnBarCodeScanedListener;
import com.jdd.common.utils.toast.ToastHelper;
import com.jdd.powermanager.R;
import com.jdd.powermanager.basic.Pager;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class BoxAssetsView implements Pager
{
	public class UserInfoEdit
	{
		public String getUserName()
		{
			return mUserName.getText().toString();
		}
		
		public String getAddress()
		{
			return mAddress.getText().toString();
		}
		
		public void setUserInfo(String n, String a)
		{
			mUserName.setText( null == n ? "" : n );
			
			mAddress.setText( null == a ? "" : a );
		}
		
		public void onIndex(int i)
		{
			// TODO
			Log.d("", "zhou -- onIndex -- " + i);
			
			mGridView.setSelection(i);
			
			isShow(i);
		}
	}
	
	private Context mContext;
	
	private int mRow;
	
	private int mColumn;
	
	private View mRoot;
	
	private GridView mGridView;
	
	private BoxAssetsAdapter mAdapter;
	
	private TextView mBlockBtn;
	
	private TextView mDelBtn;
	
	private TextView mTableBtn;
	
	private TextView mBackBtn;
	
	private EditText mUserName;
	
	private EditText mAddress;
	
	private UserInfoEdit mEdit = new UserInfoEdit();
	
	public BoxAssetsView(Context c)
	{
		mContext = c;
	}
	
	private void isShow(int i)
	{
		mGridView.smoothScrollToPosition(i);
		
		int c = i % mGridView.getNumColumns();
		
		Log.d("", "zhou -- isShow -- " + " c " + c);
		
		HorizontalScrollView hs = (HorizontalScrollView) mRoot.findViewById(R.id.hs);
		
		int sx = hs.getScrollX();
		
		Log.d("", "zhou -- isShow -- sx " + sx);
		
		float w = mContext.getResources().getDimension(R.dimen.box_meter_column_width);
		
		Log.d("", "zhou -- isShow -- w " + w);
		
		int ws = hs.getWidth();
		
		Log.d("", "zhou -- isShow -- ws " + ws);
		
		int rl = (int) (  w * c );
		
		int rr = (int) (  w * ( c + 1 ) );
		
		Log.d("", "zhou -- isShow -- rl " + rl + " rr " + rr);
		
		float cx = hs.getScrollX();
		
		Log.d("", "zhou -- isShow -- cx " + cx);
		
		if( cx <= rl && cx + ws >= rr )
		{
			Log.d("", "zhou -- isShow -- true ");
		}
		else
		{
			Log.d("", "zhou -- isShow -- false " + rl);
			
			if( rl + ws > mGridView.getWidth() )
			{
				rl = mGridView.getWidth() - ws;
			}
			
			Log.d("", "zhou -- isShow -- w " + mGridView.getWidth());
			
			Log.d("", "zhou -- isShow -- x " + rl);
			
			hs.scrollTo(rl, 0);
		}
		
	}
	
	public void setRowAndColumn(int r,int c)
	{
		mRow = r;
		mColumn = c;
		
		if( r == 0 || c == 0   )
		{
			ToastHelper.showToastShort(mContext, mContext.getString(R.string.row_or_column_is_null));
		}
		
		float w = mContext.getResources().getDimension(R.dimen.box_meter_column_width);
		
		LayoutParams params = new LayoutParams((int) (c * w) + 5 * (c - 1), LayoutParams.WRAP_CONTENT); 
		 
		mGridView.setLayoutParams(params);  
		
		mGridView.setColumnWidth((int) w);  
		
		mGridView.setVerticalSpacing(5);
		
		mGridView.setHorizontalSpacing(5);
        
		mGridView.setStretchMode(GridView.NO_STRETCH); 
        
		mGridView.setNumColumns(mColumn);
		
		mAdapter.setRowAndColumn(mRow, mColumn);
	}
	
	public ArrayList<HashMap<String,String>> getMeters()
	{
		return mAdapter.getCodes();
	}
	
	@Override
	public View getView() 
	{
		mRoot = LayoutInflater.from(mContext).inflate(R.layout.survey_box_assets_page, null);
		
		initViews();
		
		return mRoot;
	}
	
	private void initViews()
	{
		mGridView = (GridView) mRoot.findViewById(R.id.context_grid);
		
		mAdapter = new BoxAssetsAdapter(mContext,mEdit);
		
		mGridView.setAdapter(mAdapter);
		
		mBlockBtn = (TextView) mRoot.findViewById(R.id.block_btn);
		
		mDelBtn = (TextView) mRoot.findViewById(R.id.del_btn);
		
		mTableBtn = (TextView) mRoot.findViewById(R.id.table_btn);
		
		mBackBtn = (TextView) mRoot.findViewById(R.id.back_btn);
		
		mUserName = (EditText) mRoot.findViewById(R.id.user_name);
		
		mAddress = (EditText) mRoot.findViewById(R.id.use_address);
		
		mBlockBtn.setOnClickListener(mOnClickLis);
		
		mTableBtn.setOnClickListener(mOnClickLis);
		
		mDelBtn.setOnClickListener(mOnClickLis);
		
		mBackBtn.setOnClickListener(mOnClickLis);
	}
	
	private OnBarCodeScanedListener mBarCodeLis = new OnBarCodeScanedListener() 
	{
		@Override
		public void onScaned(String code) 
		{
			ok(code);
		}
	};
	
	private void block()
	{
		mAdapter.setSelectedBlock();
	}
	
	private void ok(String code)
	{
		mAdapter.setSelectedOk(code,true);
	}
	
	private void del()
	{
		mAdapter.delSelectedCode();
	}
	
	private void back()
	{
		Activity a = (Activity) mContext;
		
		a.finish();
	}
	
	private void table()
	{
	}
	
	private OnClickListener mOnClickLis = new OnClickListener() 
	{
		@Override
		public void onClick(View v) 
		{
			switch(v.getId())
			{
				case R.id.block_btn:
				
					block();
					
					break;
				
				case R.id.del_btn:
				
					del();
					
					break;
					
				case R.id.table_btn:
					
					table();
					
					break;
					
				case R.id.back_btn:
				
					back();
					
					break;
			}
		}
	};
	
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
		mContext = null;
		
		mRoot = null;
		
		BarCodeHelper.clearListener();
	}
}
