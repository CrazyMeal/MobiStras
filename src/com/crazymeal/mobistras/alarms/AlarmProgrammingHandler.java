package com.crazymeal.mobistras.alarms;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class AlarmProgrammingHandler extends Handler{
	private AlarmManager am;
	private Context baseContext;
	private Calendar calendar;
	private Intent alarmIntent;
	
	public AlarmProgrammingHandler(AlarmManager am, Context baseContext) {
		this.am = am;
		this.baseContext = baseContext;
		this.calendar = Calendar.getInstance();
		this.alarmIntent = new Intent(this.baseContext, AlarmReceiver.class);
	}

	@Override
	public void handleMessage(Message msg) {
		AlarmInfos infos = (AlarmInfos) msg.obj;
		this.calendar.setTimeInMillis(System.currentTimeMillis());
		this.calendar.set(Calendar.HOUR_OF_DAY, infos.getHour());
		this.calendar.set(Calendar.MINUTE, infos.getMinutes());
		this.calendar.set(Calendar.SECOND, 0);
		
		int random = (int)(Math.random() * 10) + 9;
		
		
		if(infos.isRecurrent()){
			PendingIntent pendingIntent = PendingIntent.getBroadcast(this.baseContext, 0, this.alarmIntent, 0);
			this.am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000 * 1 * 1, pendingIntent);
			Log.d("SIMPLE_ALARM", "Programmed repeating alarm to> " + infos.getHour() + ":" + infos.getMinutes());
		} else {
			PendingIntent pendingIntent = PendingIntent.getBroadcast(this.baseContext, random, this.alarmIntent, PendingIntent.FLAG_ONE_SHOT);
			this.am.set(AlarmManager.RTC_WAKEUP, this.calendar.getTimeInMillis(), pendingIntent);
			Log.d("SIMPLE_ALARM", "Programmed one shot alarm to> "  + infos.getHour() + ":" + infos.getMinutes());
		}
		
	}
}
