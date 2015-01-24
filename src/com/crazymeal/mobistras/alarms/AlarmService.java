package com.crazymeal.mobistras.alarms;


import android.app.AlarmManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Messenger;

public class AlarmService extends Service{
	private Messenger mMessenger;
	private AlarmProgrammingHandler alarmHandler;
	
	@Override
	public IBinder onBind(Intent intent) {
		this.alarmHandler = new AlarmProgrammingHandler((AlarmManager)getSystemService(Context.ALARM_SERVICE), this.getBaseContext());
		this.mMessenger = new Messenger(this.alarmHandler);
		return mMessenger.getBinder();
	}
}
