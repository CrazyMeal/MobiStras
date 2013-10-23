package com.crazymeal.mobistras;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import manager.ParkingManager;
<<<<<<< HEAD
import core.LocationParser;
import core.ParkingDataParser;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.os.Bundle;
import android.widget.ListView;
=======
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import asynctasks.DownloadLocationTask;
import asynctasks.DownloadParkingDataTask;
import core.LocationParser;
import core.ParkingDataParser;
>>>>>>> Finish list + refactoring
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
<<<<<<< HEAD
		
		ParkingDataParser pdp = new ParkingDataParser(); 
		LocationParser lp = new LocationParser();
		ParkingManager pm = new ParkingManager();
		
		pdp.execute(connectParser(pdp));
		//pdp.setUrl("http://carto.strasmap.eu/remote.amf.json/Parking.status");
		//pdp.execute();
		
		lp.setUrl("http://carto.strasmap.eu/remote.amf.json/Parking.geometry");
		lp.execute();
		
		pm.buildList(pdp);
		pm.merge(lp);
		
		this.parkingList = pm.getArrayParkingList();
		this.listView = (ListView) findViewById(R.id.listviewperso);

		adapter = new ParkingAdapter(this, R.layout.list_item_display,this.parkingList);
		this.listView.setAdapter(adapter);
	
	}
	public InputStream connectParser(ParkingDataParser pdp){
		URL url;
		InputStream in = null;
		try{		
			url = new URL("http://carto.strasmap.eu/remote.amf.json/Parking.status");
				
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setReadTimeout(10000);
			connection.setRequestMethod("GET");
			connection.connect();
			in = connection.getInputStream();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return in;
=======
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
>>>>>>> Finish list + refactoring
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
<<<<<<< HEAD
	
	/*
	private void fillParkingList(){
		Parking parking1 = new Parking();
		Parking parking2 = new Parking();
		HashMap<Integer,Parking> map1 = new HashMap<Integer,Parking>();
		HashMap<Integer,Parking> map2 = new HashMap<Integer,Parking>();
		
		
		parking1.setId(1);
		parking1.setAvaiblePlaces(10);
		parking1.setName("parking1");
		
		parking2.setId(2);
		parking2.setAvaiblePlaces(20);
		parking2.setName("parking2");
		
		map1.put(parking1.getId(),parking1);
		map2.put(parking2.getId(),parking2);
		
		this.getParkingList().add(map1);
		this.getParkingList().add(map2);
=======
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
>>>>>>> Finish list + refactoring
	}
	*/
}
