package com.crazymeal.strasandpark.alarms;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.crazymeal.mobistras.R;
import com.crazymeal.strasandpark.ParkingDatabase;
import com.crazymeal.strasandpark.asynctasks.JsonDownloadTask;
import com.crazymeal.strasandpark.model.Parking;
import com.crazymeal.strasandpark.parsers.JsonParkingParser;

public class AlarmReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		if(intent.getAction().equals("alarmProgrammation")){
			this.handleAlarm(context, intent);
		}
		if(intent.getAction().equals("android.intent.action.BOOT_COMPLETED")){
			Log.d("ALARM_REPROG", "Device just booted");
			SharedPreferences myPrefs = context.getSharedPreferences(context.getString(R.string.alarm_preferences_name), context.MODE_PRIVATE);
		}
		
	}
	private void handleAlarm(Context context, Intent intent){
		Log.d("SIMPLE_ALARM", "JE SONNE");
		int day = intent.getIntExtra("day", -1);
		if(day != -1){
			Calendar calendar = Calendar.getInstance();
			int androidDay = calendar.get(Calendar.DAY_OF_WEEK);
			if(day == androidDay || day == 0){
				Log.d("SIMPLE_ALARM", "Today we ring> " + String.valueOf(day));
				
				ParkingDatabase db = new ParkingDatabase(context);
				HashMap<Integer, String> parkingIds = db.getAllFavorites();
				db.close();
				JsonDownloadTask task = new JsonDownloadTask();
				task.execute(new String[]{context.getString(R.string.urlParking)});
				String taskResult = "";
				try {
					taskResult = task.get();	
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
				StringBuffer sb = new StringBuffer();
				
				HashMap<Integer, Parking> parkingMap = new JsonParkingParser().parse(taskResult);
				for(Entry<Integer, String> parkingEntry : parkingIds.entrySet()){
					
					if(parkingMap.containsKey(parkingEntry.getKey())){
						Parking newParking = parkingMap.get(parkingEntry.getKey());
						
						Log.d("SIMPLE_ALARM", "parking with id "+ newParking.getId() + " has " + newParking.getAvaiblePlaces() + " places");
						switch (newParking.getStatus()) {
						case CLOSE:
							sb.append(parkingEntry.getValue() + " est fermé");
							break;
						case FULL:
							sb.append(parkingEntry.getValue() + " est complet");
							break;
						default:
							sb.append(parkingEntry.getValue() + "> reste " + newParking.getAvaiblePlaces() + " places");
							break;
						}
						sb.append(System.getProperty("line.separator"));
					}
				}
				this.buildNotification(sb.toString(), context);
			} else
				Log.d("SIMPLE_ALARM", "Not today");
		}
	}
	private void buildNotification(String parkingText, Context context) {
		Notification.Builder mBuilder = new Notification.Builder(context)
		        .setSmallIcon(android.R.drawable.ic_dialog_info)
		        .setContentTitle(context.getString(R.string.app_name))
		        .setStyle(new Notification.BigTextStyle().bigText(parkingText))
		        .setContentText(parkingText)
		        .setAutoCancel(true);
		/*
		Intent resultIntent = new Intent(context, ParkingListActivity.class);

		TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
		stackBuilder.addParentStack(ParkingListActivity.class);
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
		mBuilder.setContentIntent(resultPendingIntent);
		*/
		Notification notif = mBuilder.build();
		NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

		int random = (int)(Math.random() * 10) + 9;
		mNotificationManager.notify(random, notif);
	}
}
