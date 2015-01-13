package com.crazymeal.detailed;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

public class AlarmService extends Service {
	private AlarmManager am;
	
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		this.am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
	}

	public void addAlarm(){
		// Set the alarm to start at approximately 2:00 p.m.
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.set(Calendar.HOUR_OF_DAY, 14);

		// With setInexactRepeating(), you have to use one of the AlarmManager interval
		// constants--in this case, AlarmManager.INTERVAL_DAY.
		Intent intent = new Intent(this, AlarmService.class);
		PendingIntent alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

		this.am.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, alarmIntent);
	}
}
