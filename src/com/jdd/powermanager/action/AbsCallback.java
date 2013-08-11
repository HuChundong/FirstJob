package com.jdd.powermanager.action;

import android.os.Handler;

public abstract class AbsCallback extends Handler
{
	public abstract void onResult(Object o);
}
