package com.jdd.powermanager.ui.abnormalelimination.viewimage;

import java.io.File;
import java.io.FilenameFilter;
import com.jdd.common.utils.file.FileHelper;
import com.jdd.common.utils.image.ImageHelper;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class ImageAdapter extends BaseAdapter
{
	private Context mContext;
	
	private String mPath;
	
	private File[] imgFiles;
	
	public ImageAdapter(Context c)
	{
		mContext = c;
	}
	
	public void setPath(String path)
	{
		mPath = path;
		
		refreshImgs();
	}
	
	public String getPath()
	{
		return mPath;
	}
	
	public void refreshImgs()
	{
		if( null == mPath || !FileHelper.isExist(mPath) )
		{
			if( null != imgFiles )
			{
				imgFiles = null;
			}
			
			return;
		}
		
		imgFiles = FileHelper.listFiles(mPath, new FilenameFilter() 
		{
			@Override
			public boolean accept(File dir, String filename) 
			{
				if( filename.endsWith(".png") || filename.endsWith(".jpg") )
				{
					return true;
				}
				
				return false;
			}
		});
		
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() 
	{
		int c = null == imgFiles ? 0 : imgFiles.length;
		
		return c;
	}

	@Override
	public Object getItem(int position) 
	{
		return null == imgFiles ? null : imgFiles[position];
	}

	@Override
	public long getItemId(int position) 
	{
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		ImageView v = new ImageView(mContext);
		
		v.setScaleType(ScaleType.FIT_XY);
		
		v.setLayoutParams(new Gallery.LayoutParams(400, Gallery.LayoutParams.MATCH_PARENT));
		
		Object o  = getItem(position);
		
		if( null == o )
		{
			return v;
		}
		
		final File f = (File) o;
		
		if( !f.exists() )
		{
			return v;
		}
		
		try 
		{
			Bitmap bt = ImageHelper.getImageThumbnail(f.getAbsolutePath(), 400, 650);
			
			v.setImageBitmap(bt);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			
			return v;
		}
		
		return v;
	}
}
