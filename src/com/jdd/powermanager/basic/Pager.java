package com.jdd.powermanager.basic;

import android.view.View;

public interface Pager 
{
	void onSelected();
	
	void onCancelSelected();
	
	View getView();
	
	void onDestroy();
}
