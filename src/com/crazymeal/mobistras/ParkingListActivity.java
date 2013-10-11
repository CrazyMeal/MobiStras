package com.crazymeal.mobistras;

import java.util.ArrayList;
import java.util.HashMap;

import description.Parking;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;

public class ParkingListActivity extends Activity{
	private ArrayList<HashMap<Integer,Parking>> parkingList;
	private Runnable viewParts;
	private ParkingAdapter adapter;
	private ListView listView;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		this.parkingList = new ArrayList<HashMap<Integer,Parking>>();
		this.listView = (ListView) findViewById(R.id.listviewperso);
		
		fillParkingList();
		adapter = new ParkingAdapter(this, R.layout.list_item_display,this.parkingList);
		this.listView.setAdapter(adapter);
		
		/*
		viewParts = new Runnable(){
			public void run(){
				handler.sendEmptyMessage(0);
			}
		};
		
		Thread thread = new Thread(null, viewParts,"MagentoBackground");
		thread.start();
		*/
	}
	
	/*
	private Handler handler = new Handler(){
		public void handleMessage(Message msg){
			fillParkingList();
			adapter = new ParkingAdapter(ParkingListActivity.this, R.layout.list_item_display,parkingList);
			
			listView.setAdapter(adapter);
		}
	};
	*/
	
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
}
