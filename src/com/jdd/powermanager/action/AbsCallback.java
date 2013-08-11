package com.jdd.powermanager.action;

import android.os.Handler;
import android.os.Message;

public abstract class AbsCallback extends Handler
{
	public void result(Object o)
	{
		obtainMessage(-1, o).sendToTarget();
	}
	
	@Override
	public void handleMessage(Message msg) 
	{
		onResult(msg.obj);
	}
	
	protected abstract void onResult(Object o);
}
