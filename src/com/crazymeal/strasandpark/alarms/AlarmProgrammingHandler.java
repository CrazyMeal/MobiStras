package com.crazymeal.strasandpark.alarms;

import java.util.Calendar;

import com.crazymeal.strasandpark.R;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class AlarmProgrammingHandler extends Handler{
	private AlarmManager am;
	private Context baseContext;
	private Calendar calendar;
	
	public AlarmProgrammingHandler(AlarmManager am, Context baseContext) {
		this.am = am;
		this.baseContext = baseContext;
		this.calendar = Calendar.getInstance();
		
	}

	@Override
	public void handleMessage(Message msg) {
		switch (msg.arg1) {
		case 0:
			this.toggleReceiverActivation(false);
			Toast.makeText(this.baseContext, this.baseContext.getString(R.string.toast_text_canceled_alarm), Toast.LENGTH_LONG).show();
			Log.d("SIMPLE_ALARM", "Alarm receiver disabled");
			break;
			
		case 1:
			this.toggleReceiverActivation(true);
			AlarmInfos infos = (AlarmInfos) msg.obj;
			this.calendar.setTimeInMillis(System.currentTimeMillis());
			this.calendar.set(Calendar.HOUR_OF_DAY, infos.getHour());
			this.calendar.set(Calendar.MINUTE, infos.getMinutes());
			this.calendar.set(Calendar.SECOND, 0);
			
			SharedPreferences myPrefs = this.baseContext.getSharedPreferences(this.baseContext.getString(R.string.alarm_preferences_name), this.baseContext.MODE_PRIVATE);
			Editor prefEditor = myPrefs.edit();
			prefEditor.putInt("programmed_hour", infos.getHour());
			prefEditor.putInt("programmed_minute", infos.getMinutes());
			if(infos.isRecurrent()){
				prefEditor.putBoolean("programmed_recurrent", infos.isRecurrent());
				prefEditor.putInt("programmed_day", infos.getReccurencyDay());
			}
			prefEditor.commit();
			
			int random = (int)(Math.random() * 10) + 9;
			
			Intent alarmIntent = new Intent(this.baseContext, AlarmReceiver.class);
			alarmIntent.setAction("alarmProgrammation");
			if(infos.isRecurrent()){
				alarmIntent.putExtra("day", infos.getReccurencyDay());
				Log.d("SIMPLE_ALARM", "Handler program day to> " + String.valueOf(infos.getReccurencyDay()));
				
				PendingIntent pendingIntent = PendingIntent.getBroadcast(this.baseContext, 0, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);
				this.am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000 * 60 * 60 * 24, pendingIntent);
				
				Log.d("SIMPLE_ALARM", "Programmed repeating alarm to> " + infos.getHour() + ":" + infos.getMinutes());
				
			} else {
				alarmIntent.putExtra("day", 0);
				PendingIntent pendingIntent = PendingIntent.getBroadcast(this.baseContext, random, alarmIntent, PendingIntent.FLAG_ONE_SHOT);
				this.am.set(AlarmManager.RTC_WAKEUP, this.calendar.getTimeInMillis(), pendingIntent);
				
				Log.d("SIMPLE_ALARM", "Programmed one shot alarm to> "  + infos.getHour() + ":" + infos.getMinutes());
			}
			Toast.makeText(this.baseContext, this.baseContext.getString(R.string.toast_text_programmed_alarm), Toast.LENGTH_LONG).show();
			break;
		default:
			break;
		}
	}
	
	private void toggleReceiverActivation(boolean activation){
		if(activation){
			ComponentName receiver = new ComponentName(this.baseContext, AlarmReceiver.class);
			PackageManager pm = this.baseContext.getPackageManager();

			pm.setComponentEnabledSetting(receiver,PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
		} else {
			ComponentName receiver = new ComponentName(this.baseContext, AlarmReceiver.class);
			PackageManager pm = this.baseContext.getPackageManager();

			pm.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
		}
	}
}
