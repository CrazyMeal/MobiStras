package com.crazymeal.mobistras;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import parsers.JsonLocationParser;
import parsers.JsonParkingParser;
import manager.ParkingManager;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import asynctasks.DownloadLocationTask;
import asynctasks.DownloadParkingDataTask;
import asynctasks.JsonDownloadTask;
import core.LocationParser;
import core.ParkingDataParser;
import description.Parking;

public class ParkingListActivity extends Activity{
	private ArrayList<HashMap<Integer,Parking>> parkingList;
	private ParkingDataParser pdp;
	private LocationParser lp;
	private ParkingManager pm;
	private ParkingAdapter adapter;
	private ListView listView;
	private String parkingDataString,locationString;
	private boolean parkingDataTaskFinished=false, locationTaskFinished=false;
	private Context thisContext;
	private AsyncTaskListener listener;
	private DownloadParkingDataTask parkingTask;
	private DownloadLocationTask locationTask;
	
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
		
		this.listener = new AsyncTaskListener(this.locationParser, this.parkingParser);
		this.jsonLocationTask = new JsonDownloadTask(this.listener);
		this.jsonParkingTask = new JsonDownloadTask(this.listener);
		
		this.jsonLocationTask.execute(new String[]{"http://carto.strasmap.eu/remote.amf.json/Parking.geometry"});
		this.jsonParkingTask.execute(new String[]{"http://carto.strasmap.eu/remote.amf.json/Parking.status"});
		
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
		
		public AsyncTaskListener(JsonLocationParser locationParser, JsonParkingParser parkingParser){
			this.locationParser = locationParser;
			this.parkingParser = parkingParser;
			this.tasks = new HashMap<AsyncTask<String,Void, String>,Boolean>();
		}
		public void waitFor(AsyncTask<String, Void, String> task) {
			this.tasks.put(task,false);
		}

		public void notify(AsyncTask<String, Void, String> task) {
			if(this.tasks.containsKey(task)){
				this.tasks.remove(task);
			}
			this.tasks.put(task,true);

			if(!this.tasks.containsValue(false)){
				
				/*
				pdp.parse(parkingDataString);
				lp.execute(locationString);
				pm.buildList(pdp);
				pm.merge(lp);
						
				parkingList = pm.getArrayParkingList();
				listView = (ListView) findViewById(R.id.listviewperso);
				
				adapter = new ParkingAdapter(getThisContext(), R.layout.list_item_display,parkingList);
				listView.setAdapter(adapter);
				*/
			}
		}	
	}
	public ArrayList<HashMap<Integer,Parking>> getParkingList() {
		return parkingList;
	}
	public void setParkingList(ArrayList<HashMap<Integer,Parking>> parkingList) {
		this.parkingList = parkingList;
	}
	public ParkingAdapter getAdapter() {
		return adapter;
	}
	public void setAdapter(ParkingAdapter adapter) {
		this.adapter = adapter;
	}
	public boolean isParkingDataTaskFinished() {
		return parkingDataTaskFinished;
	}
	public void setParkingDataTaskFinished(boolean parkingDataTaskFinished) {
		this.parkingDataTaskFinished = parkingDataTaskFinished;
	}
	public boolean isLocationTaskFinished() {
		return locationTaskFinished;
	}
	public void setLocationTaskFinished(boolean locationTaskFinished) {
		this.locationTaskFinished = locationTaskFinished;
	}
	public Context getThisContext() {
		return thisContext;
	}
	public void setThisContext(Context thisContext) {
		this.thisContext = thisContext;
	}
	public ParkingDataParser getParkingDataParser() {
		return pdp;
	}
	public void setParkingDataParser(ParkingDataParser pdp) {
		this.pdp = pdp;
	}
	public LocationParser getLocationParser() {
		return lp;
	}
	public void setLocationParser(LocationParser lp) {
		this.lp = lp;
	}
	public ParkingManager getParkingManager() {
		return pm;
	}
	public void setParkingManager(ParkingManager pm) {
		this.pm = pm;
	}
	public DownloadParkingDataTask getParkingTask() {
		return parkingTask;
	}
	public void setParkingTask(DownloadParkingDataTask parkingTask) {
		this.parkingTask = parkingTask;
	}
	public DownloadLocationTask getLocationTask() {
		return locationTask;
	}
	public void setLocationTask(DownloadLocationTask locationTask) {
		this.locationTask = locationTask;
	}
	public AsyncTaskListener getListener() {
		return listener;
	}
	public void setListener(AsyncTaskListener listener) {
		this.listener = listener;
	}
}
