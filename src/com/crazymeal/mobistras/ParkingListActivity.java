package com.crazymeal.mobistras;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
		this.listener = new AsyncTaskListener(this, this.getBaseContext(), this.locationParser, this.parkingParser);
		
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
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == 1){
			if(resultCode == RESULT_OK){
				int hour = data.getIntExtra("hour", 0);
				int minute = data.getIntExtra("minute", 0);
				String recurrence = data.getStringExtra("recurrence");
			}
		}
	}



	public class AsyncTaskListener{
		private HashMap<AsyncTask<String, Void, String>, Boolean> tasks;
		private JsonLocationParser locationParser;
		private JsonParkingParser parkingParser;
		private Context context;
		private Activity originActivity;
		
		public AsyncTaskListener(Activity originActivity, Context context, JsonLocationParser locationParser, JsonParkingParser parkingParser){
			this.locationParser = locationParser;
			this.parkingParser = parkingParser;
			this.context = context;
			this.originActivity = originActivity;
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
				adapter = new ParkingMapAdapter(this.originActivity, this.context,getResources(), R.layout.list_item_display, db.getAllAsList());
				listView.setAdapter(adapter);
				listView.setVisibility(View.VISIBLE);
			}
		}	
	}
}
