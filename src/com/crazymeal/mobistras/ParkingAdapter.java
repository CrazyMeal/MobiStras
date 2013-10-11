package com.crazymeal.mobistras;

import java.util.ArrayList;
import java.util.HashMap;

import description.Parking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ParkingAdapter extends ArrayAdapter<HashMap<Integer,Parking>>{
	private ArrayList<HashMap<Integer,Parking>> parkingList;
	
	public ParkingAdapter(Context context, int textViewRessourceId,ArrayList<HashMap<Integer,Parking>> parkingList){
		super(context, textViewRessourceId, parkingList);
		this.setParkingList(parkingList);
	}
	
	public View getView(int position, View convertView, ViewGroup parentView){
		View view = convertView;
		
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.list_item_display, null);
		}
		
		Parking parking = this.getParkingList().get(position).get(position);
		
		if(parking != null){
			TextView parkingName = (TextView) view.findViewById(R.id.list_item_parking_name);
			//TextView parkingPlaces = (TextView) view.findViewById(R.id.list_item_parking_places);
			
			if(parkingName != null){
				parkingName.setText(parking.getName());
			}
			//if(parkingPlaces != null){
			//	parkingPlaces.setText(parking.getAvaiblePlaces());
			//}
		}
		
		return view;
	}

	public ArrayList<HashMap<Integer,Parking>> getParkingList() {
		return parkingList;
	}

	public void setParkingList(ArrayList<HashMap<Integer,Parking>> parkingList) {
		this.parkingList = parkingList;
	}
}
