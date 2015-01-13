package com.crazymeal.mobistras.alarms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class SimpleAlarm extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d("SIMPLE_ALARM", "JE SONNE");
	}

}
