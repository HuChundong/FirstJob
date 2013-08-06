package com.jdd.powermanager.basic;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

public class BaseFragmentActivity extends FragmentActivity 
{
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	}
}
