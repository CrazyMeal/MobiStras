package com.crazymeal.mobistras.activities;


import java.util.Calendar;

import com.crazymeal.mobistras.alarms.SimpleAlarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.widget.Toast;

public class MainService extends Service{
	final Messenger mMessenger = new Messenger(new IncomingHandler());
	
	@Override
	public IBinder onBind(Intent arg0) {
		Toast.makeText(getApplicationContext(), "Bienvenue", Toast.LENGTH_SHORT).show();
		return mMessenger.getBinder();
	}

	
	
	class IncomingHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			int[] infos = (int[]) msg.obj;
			
			Calendar AlarmCal = Calendar.getInstance();
			AlarmCal.setTimeInMillis(System.currentTimeMillis());
			AlarmCal.set(Calendar.HOUR_OF_DAY, infos[0]);
			AlarmCal.set(Calendar.MINUTE, infos[1]);
			AlarmCal.set(Calendar.SECOND, 0);
			
			AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
			
			Intent intent = new Intent(MainService.this, SimpleAlarm.class);
			
			int random = (int)(Math.random() * 10) + 9;
			PendingIntent pendingIntent = PendingIntent.getBroadcast(MainService.this, random,intent,PendingIntent.FLAG_ONE_SHOT);
			am.set(AlarmManager.RTC_WAKEUP,AlarmCal.getTimeInMillis(),pendingIntent);
			
			Log.d("SIMPLE_ALARM", "programmed> "  + infos[0] + ":" + infos[1]);
		}
	}
}
