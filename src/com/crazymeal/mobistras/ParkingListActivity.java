package com.crazymeal.mobistras;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.crazymeal.asynctasks.JsonDownloadTask;
import com.crazymeal.database.ParkingDatabase;
import com.crazymeal.model.Parking;
import com.crazymeal.parsers.JsonLocationParser;
import com.crazymeal.parsers.JsonParkingParser;
import com.crazymeal.util.Util;

public class ParkingListActivity extends Activity{
	private ParkingMapAdapter adapter;
	private ListView listView;
	private AsyncTaskListener listener;
	private JsonLocationParser locationParser;
	private JsonParkingParser parkingParser;
	private JsonDownloadTask jsonLocationTask, jsonParkingTask;
	private String jsonLocationString, jsonParkingString;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		
		this.locationParser = new JsonLocationParser();
		this.parkingParser = new JsonParkingParser();
		this.listener = new AsyncTaskListener(this, this.locationParser, this.parkingParser);
		this.jsonLocationTask = new JsonDownloadTask(this.listener);
		this.jsonParkingTask = new JsonDownloadTask(this.listener);
	}
	
	
	@Override
	protected void onStart() {
		super.onStart();
		this.jsonLocationTask.execute(new String[]{getString(R.string.urlLocation)});
		this.jsonParkingTask.execute(new String[]{getString(R.string.urlParking)});
		
		try {
			
			this.jsonLocationString = this.jsonLocationTask.get();
			this.jsonParkingString = this.jsonParkingTask.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}


	public class AsyncTaskListener{
		private HashMap<AsyncTask<String, Void, String>, Boolean> tasks;
		private JsonLocationParser locationParser;
		private JsonParkingParser parkingParser;
		private Context context;
		
		public AsyncTaskListener(Context context, JsonLocationParser locationParser, JsonParkingParser parkingParser){
			this.locationParser = locationParser;
			this.parkingParser = parkingParser;
			this.context = context;
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
				HashMap<Integer, Parking> locationMap = this.locationParser.parse(jsonLocationString);
				HashMap<Integer, Parking> parkingMap = this.parkingParser.parse(jsonParkingString);
				HashMap<Integer, Parking> finalMap = Util.merge(parkingMap, locationMap);
				
				listView = (ListView) findViewById(R.id.listviewperso);
				adapter = new ParkingMapAdapter(this.context,getResources(), R.layout.list_item_display, new ArrayList<com.crazymeal.model.Parking>(finalMap.values()));
				listView.setAdapter(adapter);
				listView.setVisibility(View.VISIBLE);
				ParkingDatabase db = new ParkingDatabase(this.context);
				//db.clear();
				
				if(db.isEmpty()){
					db.addParkings(finalMap);
				}
				else{
					db.updateParkings(finalMap);
				}
				
				
			}
		}	
	}
}
