package com.crazymeal.mobistras.alarms;

import java.util.Calendar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d("SIMPLE_ALARM", "JE SONNE");
		int day = intent.getIntExtra("day", -1);
		if(day != -1){
			Calendar calendar = Calendar.getInstance();
			int androidDay = calendar.get(Calendar.DAY_OF_WEEK);
			if(day == androidDay || day == 0){
				Log.d("SIMPLE_ALARM", "Today we ring> " + String.valueOf(day));
			} else
				Log.d("SIMPLE_ALARM", "Not today");
			
		}
		
	}
}
