package com.jdd.powermanager.ui.abnormalelimination.eliminate;

import java.io.File;
import java.io.FilenameFilter;
import com.jdd.common.utils.file.FileHelper;
import com.jdd.common.utils.image.ImageHelper;
import com.jdd.powermanager.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;

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
		int c = null == imgFiles ? 1 : imgFiles.length + 1;
		
		return c;
	}

	@Override
	public Object getItem(int position) 
	{
		if( null == imgFiles || position == getCount() -1 )
		{
			return null;
		}
		
		return null == imgFiles ? null : imgFiles[position];
	}

	@Override
	public long getItemId(int position) 
	{
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) 
	{
		if( position == getCount() - 1 )
		{
			View v = LayoutInflater.from(mContext).inflate(R.layout.eliminate_img_default, null);
			
			v.setLayoutParams(new Gallery.LayoutParams(160, 160));
			
			return v;
		}
		
		View v = LayoutInflater.from(mContext).inflate(R.layout.eliminate_img_item, null);
		
		v.setLayoutParams(new Gallery.LayoutParams(160, 160));
		
		ImageView img = (ImageView) v.findViewById(R.id.img);
		
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
			Bitmap bt = ImageHelper.getImageThumbnail(f.getAbsolutePath(), 120, 120);
			
			img.setImageBitmap(bt);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			
			return v;
		}
		
		ImageButton del = (ImageButton) v.findViewById(R.id.del);
		
		final String path = f.getAbsolutePath();
		
		del.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View arg0) 
			{
				FileHelper.delFileonExist(path);
				
				refreshImgs();
			}
		});
		
		return v;
	}
}
