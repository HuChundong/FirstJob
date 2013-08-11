package com.jdd.powermanager.action;


public abstract class AbsAction  extends Thread
{
	private boolean mIsCancel;
	
	private AbsCallback mHandler;
	
	private AbsAction()
	{
	}

	public AbsAction(AbsCallback h)
	{
		this();
		
		mHandler = h;
	}
	
	@Override
	public void run() 
	{
		Object o = null;
		
		if(!mIsCancel)
		{
			o = doJob();
		}
		
		if(!mIsCancel && null != mHandler)
		{
			mHandler.onResult(o);
		}
	}
	
	public void cancel()
	{
		mIsCancel = true;
		
		if( null != mHandler )
		{
			mHandler.removeCallbacksAndMessages(null);
			
			mHandler = null;
		}
	}
	
	protected abstract Object doJob();
}
