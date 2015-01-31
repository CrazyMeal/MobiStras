package com.crazymeal.strasandpark.asynctasks;

import java.util.HashMap;

import android.content.Context;
import android.os.AsyncTask;
import android.os.ResultReceiver;
import android.util.Log;

import com.crazymeal.strasandpark.ParkingDatabase;
import com.crazymeal.strasandpark.model.Parking;
import com.crazymeal.strasandpark.parsers.JsonLocationParser;
import com.crazymeal.strasandpark.parsers.JsonParkingParser;
import com.crazymeal.strasandpark.util.Util;

public class AsyncTaskListener {
	private HashMap<AsyncTask<String, Void, String>, Boolean> tasks;
	private JsonLocationParser locationParser;
	private JsonParkingParser parkingParser;
	private Context context;
	
	private ResultReceiver resultReceiver;
	
	public AsyncTaskListener(Context context, JsonLocationParser locationParser, JsonParkingParser parkingParser, ResultReceiver resultReceiver){
		this.locationParser = locationParser;
		this.parkingParser = parkingParser;
		this.context = context;
		this.resultReceiver = resultReceiver;
		this.tasks = new HashMap<AsyncTask<String,Void, String>,Boolean>();
	}
	public void waitFor(AsyncTask<String, Void, String> task) {
		this.tasks.put(task,false);
	}

	public void notify(AsyncTask<String, Void, String> task) {
		if(this.tasks.containsKey(task)){
			this.tasks.remove(task);
		}
		
		if(!this.tasks.containsValue(false)){
			HashMap<Integer, Parking> locationMap = this.locationParser.parse();
			HashMap<Integer, Parking> parkingMap = this.parkingParser.parse();
			HashMap<Integer, Parking> finalMap = Util.merge(parkingMap, locationMap);
			
			ParkingDatabase db = new ParkingDatabase(this.context);
			if(db.isEmpty()){
				db.addParkings(finalMap);
			}
			else{
				db.updateParkings(finalMap);
			}
			db.close();
			this.resultReceiver.send(200, null);
			Log.d("SERVICE", "sended result code");
			//this.listview.setAdapter(new ParkingMapAdapter(this.context,context.getResources(), R.layout.new_list_item_display, db.getAllAsList()));
		}
	}	
}
