package com.crazymeal.strasandpark.alarms;


import com.crazymeal.strasandpark.R;

import android.app.AlarmManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

public class AlarmService extends Service{
	private Messenger mMessenger;
	private AlarmProgrammingHandler alarmHandler;
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		this.alarmHandler = new AlarmProgrammingHandler((AlarmManager)getSystemService(Context.ALARM_SERVICE), this.getBaseContext());
		this.mMessenger = new Messenger(this.alarmHandler);
		
		if(intent.getAction().equals("reprogAlarms")){
			SharedPreferences myPrefs = this.getSharedPreferences(this.getString(R.string.alarm_preferences_name), Context.MODE_PRIVATE);
			int hour = myPrefs.getInt("programmed_hour", -1);
			int minute = myPrefs.getInt("programmed_minute", -1);
			boolean isRecurrent = myPrefs.getBoolean("programmed_recurrent", false);
			
			if(hour != -1 && minute != -1){
				
				try {
					Message message = Message.obtain();
					AlarmInfos infos = new AlarmInfos(hour, minute, isRecurrent);
					if(isRecurrent){
						infos.setReccurencyDay((int) myPrefs.getInt("programmed_day", 0));
					}
					message.arg1 = 1;
					message.obj = infos;
					this.mMessenger.send(message);
					
					Log.d("ALARM_REPROG", "Sended reprograming message to service");
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		}
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		if(this.alarmHandler == null && this.mMessenger == null){
			this.alarmHandler = new AlarmProgrammingHandler((AlarmManager)getSystemService(Context.ALARM_SERVICE), this.getBaseContext());
			this.mMessenger = new Messenger(this.alarmHandler);
		}
		
		return mMessenger.getBinder();
	}
}
