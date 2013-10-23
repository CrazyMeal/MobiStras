package com.crazymeal.mobistras;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import manager.ParkingManager;
import core.LocationParser;
import core.ParkingDataParser;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.os.Bundle;
import android.widget.ListView;
import description.Parking;

public class ParkingListActivity extends Activity{
	private ArrayList<HashMap<Integer,Parking>> parkingList;
	private Runnable viewParts;
	private ParkingAdapter adapter;
	private ListView listView;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		
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
	}
	public ArrayList<HashMap<Integer,Parking>> getParkingList() {
		return parkingList;
	}
	public void setParkingList(ArrayList<HashMap<Integer,Parking>> parkingList) {
		this.parkingList = parkingList;
	}
	public Runnable getViewParts() {
		return viewParts;
	}
	public void setViewParts(Runnable viewParts) {
		this.viewParts = viewParts;
	}
	public ParkingAdapter getAdapter() {
		return adapter;
	}
	public void setAdapter(ParkingAdapter adapter) {
		this.adapter = adapter;
	}
	
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
	}
	*/
}
