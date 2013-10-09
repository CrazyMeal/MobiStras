package com.crazymeal.mobistras;


import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.widget.Toast;

public class MainService extends Service{
	final Messenger mMessenger = new Messenger(new IncomingHandler());
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), "Bienvenue", Toast.LENGTH_SHORT).show();
		return mMessenger.getBinder();
	}

	
	
	class IncomingHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			/*
			MessageInformations infos = (MessageInformations) msg.obj;
			
			Calendar AlarmCal = Calendar.getInstance();
			AlarmCal.setTimeInMillis(System.currentTimeMillis());
			AlarmCal.set(Calendar.HOUR_OF_DAY, infos.getHour());
			AlarmCal.set(Calendar.MINUTE, infos.getMinute());
			AlarmCal.set(Calendar.SECOND, 0);
			
			am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
			
			Intent intent = new Intent(SenderService.this, TimeAlarm.class);
			
			intent.putExtra("destinator", infos.getDestinator());
			intent.putExtra("messageToSend", infos.getMessageToSend());
			
			int random = (int)(Math.random() * 10) + 9;
			PendingIntent pendingIntent = PendingIntent.getBroadcast(SenderService.this, random,intent,PendingIntent.FLAG_ONE_SHOT);
			am.set(AlarmManager.RTC_WAKEUP,AlarmCal.getTimeInMillis(),pendingIntent);
		*/
		}
	}
}
