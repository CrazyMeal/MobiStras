package com.crazymeal.mobistras.alarms;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import com.crazymeal.mobistras.ParkingDatabase;
import com.crazymeal.mobistras.R;
import com.crazymeal.mobistras.asynctasks.JsonDownloadTask;
import com.crazymeal.mobistras.model.Parking;
import com.crazymeal.mobistras.parsers.JsonParkingParser;

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
				
				ParkingDatabase db = new ParkingDatabase(context);
				ArrayList<Integer> parkingIds = db.getAllFavorites();
				
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
				
				HashMap<Integer, Parking> parkingMap = new JsonParkingParser().parse(taskResult);
				for(Parking p : parkingMap.values()){
					if(parkingIds.contains(p.getId())){
						Log.d("SIMPLE_ALARM", "parking with id "+ p.getId() + " has " + p.getAvaiblePlaces() + " places");
					}
				}
				
			} else
				Log.d("SIMPLE_ALARM", "Not today");
		}	
	}
}
