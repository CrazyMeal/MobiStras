package com.crazymeal.mobistras.activities;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.crazymeal.mobistras.ParkingDatabase;
import com.crazymeal.mobistras.ParkingMapAdapter;
import com.crazymeal.mobistras.R;
import com.crazymeal.mobistras.R.id;
import com.crazymeal.mobistras.R.layout;
import com.crazymeal.mobistras.R.menu;
import com.crazymeal.mobistras.R.string;
import com.crazymeal.mobistras.asynctasks.JsonDownloadTask;
import com.crazymeal.mobistras.model.Parking;
import com.crazymeal.mobistras.parsers.JsonLocationParser;
import com.crazymeal.mobistras.parsers.JsonParkingParser;
import com.crazymeal.mobistras.util.Util;

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
		this.listener = new AsyncTaskListener(this.getBaseContext(), this.locationParser, this.parkingParser);
		
		this.jsonLocationTask = new JsonDownloadTask(this.listener);
		this.jsonParkingTask = new JsonDownloadTask(this.listener);
		if(!this.jsonLocationTask.isHasBeenExecuted() && !jsonParkingTask.isHasBeenExecuted()){
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
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		this.getMenuInflater().inflate(R.menu.activity_parking_list, menu);
		return true;
	}

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_settings:
			startActivity(new Intent(ParkingListActivity.this, DetailedParkingActivity.class));
			return true;
		default:
			return super.onOptionsItemSelected(item);
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
				
				ParkingDatabase db = new ParkingDatabase(this.context);
				if(db.isEmpty()){
					db.addParkings(finalMap);
				}
				else{
					db.updateParkings(finalMap);
				}
				db.close();
				listView = (ListView) findViewById(R.id.listviewperso);
				adapter = new ParkingMapAdapter(this.context,getResources(), R.layout.new_list_item_display, db.getAllAsList());
				listView.setAdapter(adapter);
				listView.setVisibility(View.VISIBLE);
			}
		}	
	}
}
