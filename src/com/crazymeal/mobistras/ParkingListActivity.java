package com.crazymeal.mobistras;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import manager.ParkingManager;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import asynctasks.DownloadLocationTask;
import asynctasks.DownloadParkingDataTask;
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
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		this.setThisContext(this);
		this.setParkingDataParser(new ParkingDataParser());
		this.setLocationParser(new LocationParser());
		this.setParkingManager(new ParkingManager());
		
		this.setListener(new AsyncTaskListener());
		
		this.setParkingTask(new DownloadParkingDataTask(this.getListener()));
		this.getParkingTask().execute(new String[]{"http://carto.strasmap.eu/remote.amf.json/Parking.status"});
		
		this.setLocationTask(new DownloadLocationTask(this.getListener()));
		this.getLocationTask().execute(new String[]{"http://carto.strasmap.eu/remote.amf.json/Parking.geometry"});
		
		try {
			locationString = this.getLocationTask().get();
			parkingDataString = this.getParkingTask().get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
	public class AsyncTaskListener{
		private HashMap<AsyncTask, Boolean> tasks;
		public AsyncTaskListener(){
			this.tasks = new HashMap<AsyncTask,Boolean>();
		}
		public void waitFor(AsyncTask<String, Void, String> task) {
			this.tasks.put(task,false);
		}
// z
		public void notify(AsyncTask<String, Void, String> task) {
			if(this.tasks.containsKey(task)){
				this.tasks.remove(task);
			}
			this.tasks.put(task,true);

			if(!this.tasks.containsValue(false)){
				pdp.parse(parkingDataString);
				lp.execute(locationString);
				pm.buildList(pdp);
				pm.merge(lp);
						
				parkingList = pm.getArrayParkingList();
				listView = (ListView) findViewById(R.id.listviewperso);
				
				adapter = new ParkingAdapter(getThisContext(), R.layout.list_item_display,parkingList);
				listView.setAdapter(adapter);
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
