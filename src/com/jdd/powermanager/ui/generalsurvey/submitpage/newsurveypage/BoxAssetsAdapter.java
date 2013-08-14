package com.jdd.powermanager.ui.generalsurvey.submitpage.newsurveypage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.jdd.powermanager.R;
import com.jdd.powermanager.model.MeterSurvey.MeterSurveyForm.MeterSurvey;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

public class BoxAssetsAdapter extends BaseAdapter
{
	private String[] mList;
	
	private Context mContext;
	
	private int mRow;
	
	private int mColumn;
	
	private int mSelectedIndex = 0;
	
	private List<String> mBlockIndexs = new ArrayList<String>();
	
	private List<String> mOkIndexs = new ArrayList<String>();
	
	public BoxAssetsAdapter(Context context)
	{
		mContext = context;
	}
	
	public void setRowAndColumn(int r,int c)
	{
		if( mRow == r && mColumn == c )
		{
			return;
		}
		
		mRow = r;
		mColumn = c;
		
		int count = mRow * mColumn;
		
		mList = new String[count];
		
		mOkIndexs.clear();
		
		mBlockIndexs.clear();
		
		notifyDataSetChanged();
	}
	
	public void setSelectedCode(String code)
	{
		if( null == mList || mList.length == 0 )
		{
			return;
		}
		
		mList[mSelectedIndex] = code;
		
		notifyDataSetChanged();
	}
	
	public void delSelectedCode()
	{
		if( null == mList ||  mSelectedIndex >= mList.length  )
		{
			return;
		}
		
		mList[mSelectedIndex] = "";
		String index = mSelectedIndex+"";
		
		if( mOkIndexs.contains(index) )
		{
			mOkIndexs.remove(index);
		}
		
		if( mBlockIndexs.contains(index) )
		{
			mBlockIndexs.remove(index);
		}
		
		notifyDataSetChanged();
	}
	
	public void setOk(int i)
	{
		mOkIndexs.add(i + "");
		
		notifyDataSetChanged();
	}
	
	public void setBlock(int i)
	{
		mBlockIndexs.add(i + "");
		
		mOkIndexs.remove(i+"");
		
		if( null == mList ||  mSelectedIndex >= mList.length  )
		{
			return;
		}
		
		mList[mSelectedIndex] = "";
		
		notifyDataSetChanged();
	}
	
	public void setSelected(int i)
	{
		mSelectedIndex = i;
		
		notifyDataSetChanged();
	}
	
	public void setSelectedBlock()
	{
		setBlock(mSelectedIndex);
	}
	
	public void setSelectedOk(String code)
	{
		if( null == mList ||  mSelectedIndex >= mList.length || code == null )
		{
			return;
		}
		
		mList[mSelectedIndex] = code;
		
		setOk(mSelectedIndex);
	}
	
	public ArrayList<HashMap<String,String>> getCodes()
	{
		if( null == mList || mList.length == 0 )
		{
			return null;
		}
		
		ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
		
		HashMap<String,String> m;
		
		String code ;
		
		for( int i =0 ; i < mList.length ; i++ )
		{
			code = mList[i];
			
			if( null != code && !code.equals("") )
			{
				m = new HashMap<String,String>();
				
				m.put(MeterSurvey.D_ASSET_NO, code);
				
				m.put(MeterSurvey.IN_ROW, getRow(i) + "");
				
				m.put(MeterSurvey.IN_COLUMN, getColumn(i) + "");
				
				list.add(m);
			}
		}
		
		return list;
	}
	
	public void clear()
	{
		if( null == mList)
		{
			return;
		}
		
		mList = new String[0];
		
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() 
	{
		return null == mList ? 0 : mList.length;
	}

	@Override
	public Object getItem(int arg0) 
	{
		return null == mList ? null : mList[arg0];
	}
	
	@Override
	public long getItemId(int arg0) 
	{
		return arg0;
	}
	
	public int getRow(int p)
	{
		if( 0 == mColumn )
		{
			return 0;
		}
		
	    int row = p / mColumn  + 1;
		
		return row;
	}
	
	public int getColumn(int p)
	{
		if( 0 == mColumn )
		{
			return 0;
		}
		
		int column = p % mColumn + 1;
		
		return column;
	}
	
	private void showDialog()
	{
		View v = LayoutInflater.from(mContext).inflate(R.layout.edittext_dialog_view, null);
		
		final EditText et = (EditText) v.findViewById(R.id.edit_text);
		
		et.setInputType(InputType.TYPE_CLASS_NUMBER);
		
		AlertDialog.Builder builder = new Builder(mContext);
		
		builder.setView(v);
		
		builder.setTitle(mContext.getString(R.string.input_meter_code));
		
		builder.setPositiveButton(mContext.getString(R.string.ok), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{
				dialog.dismiss();
				
				String code = et.getText().toString();
				
				if( null != code && !code.equals("") )
				{
					setSelectedOk(code);
				}
				
				notifyDataSetChanged();
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
	public View getView(final int pos, View view, ViewGroup parent) 
	{
		Holder h = null;
		
		if( null == view )
		{
			view = LayoutInflater.from(mContext).inflate(R.layout.survey_meter_item, null);
			
			h = new Holder();
			
			h.index = (TextView) view.findViewById(R.id.meter_index);
			
			h.title = (TextView) view.findViewById(R.id.meter_title);
			
			h.fg = (View) view.findViewById(R.id.fg);
			
			h.code = (TextView) view.findViewById(R.id.meter_code);
			
			view.setTag(h);
		}
		else
		{
			h = (Holder) view.getTag();
		}
		
		if( null == mList || mList.length == 0 )
		{
			return null;
		}
		
		h.title.setText(R.string.table_name);
		
		int row = pos / mColumn  + 1;
		int column = pos % mColumn + 1 ;
		
		h.index.setText(row + "-" + column);
		
		String code  = mList[pos];
		
		if( null == code || code.equals("") )
		{
			h.code.setText(R.string.input_or_scan_barcode);
		}
		else
		{
			h.code.setText(code);
		}
		
		if( mOkIndexs.contains(pos+"") )
		{
			view.setBackgroundColor(Color.GREEN);
			
			h.code.setVisibility(View.VISIBLE);
		}
		else if
		( mBlockIndexs.contains(pos+"")  )
		{
			view.setBackgroundColor(Color.GRAY);
			
			h.title.setText(R.string.block);
			
			h.code.setVisibility(View.INVISIBLE);
		}
		else
		{
			view.setBackgroundColor(Color.RED);
			
			h.code.setVisibility(View.VISIBLE);
		}
		
		if( pos == mSelectedIndex )
		{
			view.setBackgroundColor(Color.BLUE);
		
			h.fg.setVisibility(View.INVISIBLE);
		}
		else
		{
			h.fg.setVisibility(View.VISIBLE);
		}
		
		h.fg.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				((InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(((Activity)mContext)
                                .getCurrentFocus().getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
				
				setSelected(pos);
				
				((View) v.getParent()).setBackgroundColor(Color.BLUE);
				
				v.setVisibility(View.INVISIBLE);
				
				notifyDataSetChanged();
			}
		});
		
		h.code.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				showDialog();
			}
		});
		
		return view;
	}
	
	@Override
	public boolean areAllItemsEnabled() 
	{
		return true;
	}

	@Override
	public boolean isEnabled(int position)
	{
		return true;
	}
}

class Holder
{
	TextView index;
	
	TextView title;
	
	View fg;	
	
	TextView code;
}
