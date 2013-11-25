package com.jdd.powermanager.ui.abnormalelimination.unsubmitpage;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

public class FilterAdapter extends BaseAdapter implements Filterable 
{
	private List<String> mOriginalValues;
	
	private List<String> mObjects;
	
	private final Object mLock = new Object();
	
	private int mResource;
	
	private int mDropDownResource;
	
	private MyFilter mFilter = null;
	
	private LayoutInflater mInflater = null;

	public FilterAdapter(Context context, int textViewResourceId,
			List<String> objects) 
	{
		init(context, textViewResourceId, objects);
	}

	private void init(Context context, int resource, List<String> objects) 
	{
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		mResource = mDropDownResource = resource;
		
		mObjects = new ArrayList<String>(objects);
		
		mFilter = new MyFilter();
	}

	@Override
	public int getCount() 
	{
		return mObjects.size();
	}

	@Override
	public String getItem(int position) 
	{
		return mObjects.get(position);
	}

	@Override
	public long getItemId(int position) 
	{
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		return createViewFromResource(position, convertView, parent, mResource);
	}

	private View createViewFromResource(int position, View convertView, ViewGroup parent, int resource) 
	{
		View view;
		
		TextView text;

		if (convertView == null) 
		{
			view = mInflater.inflate(resource, parent, false);
		} 
		else 
		{
			view = convertView;
		}

		try 
		{
			text = (TextView) view;
		} 
		catch (ClassCastException e)
		{
			throw new IllegalStateException("ArrayAdapter requires the resource ID to be a TextView", e);
		}

		String item = getItem(position);
		
		if (item instanceof CharSequence) 
		{
			text.setText((CharSequence) item);
		} 
		else 
		{
			text.setText(item.toString());
		}

		return view;
	}

	@Override
	public Filter getFilter()
	{
		return mFilter;
	}

	public void setDropDownViewResource(int resource)
	{
		this.mDropDownResource = resource;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) 
	{
		return createViewFromResource(position, convertView, parent,mDropDownResource);
	}

	private class MyFilter extends Filter 
	{
		@Override
		protected FilterResults performFiltering(CharSequence constraint) 
		{
			FilterResults results = new FilterResults();
			
			if (mOriginalValues == null) 
			{
				synchronized (mLock)
				{
					mOriginalValues = new ArrayList<String>(mObjects);
				}
			}
			
			int count = mOriginalValues.size();
			
			ArrayList<String> values = new ArrayList<String>();

			for (int i = 0; i < count; i++)
			{
				String value = mOriginalValues.get(i);
				
				String valueText = value.toString();
				
				if (null != valueText && null != constraint && valueText.contains(constraint)) 
				{
					values.add(value);
				}
			}
			
			results.values = values;
			
			results.count = values.size();
			
			return results;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void publishResults(CharSequence constraint, FilterResults results)
		{
			mObjects = (List<String>) results.values;
			
			if (results.count > 0) 
			{
				notifyDataSetChanged();
			} 
			else 
			{
				notifyDataSetInvalidated();
			}
		}
	}
}

