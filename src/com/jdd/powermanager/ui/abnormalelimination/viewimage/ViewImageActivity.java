package com.jdd.powermanager.ui.abnormalelimination.viewimage;

import com.jdd.powermanager.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Gallery;
import android.widget.ImageButton;

public class ViewImageActivity extends Activity
{
	private Gallery mImgs;
	
	private ImageAdapter mImgAdapter;
	
	private ImageButton back;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		String path = getIntent().getStringExtra("path");
		
		int index = getIntent().getIntExtra("index", 0);
		
		setContentView(R.layout.full_show_img);
		
		back = (ImageButton) findViewById(R.id.back);
		
		mImgs = (Gallery) findViewById(R.id.img_gallery);
		
		mImgAdapter = new ImageAdapter(this);
		
		mImgs.setAdapter(mImgAdapter);
		
		mImgAdapter.setPath(path);
		
		mImgs.setSelection(index);
		
		back.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View arg0)
			{
				finish();
			}
		});
	}
}
