package com.crazymeal.mobistras;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import description.Parking;

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
		
		Parking parking = this.getParkingList().get(position).get(position+1);
		
		if(parking != null){
			TextView parkingName = (TextView) view.findViewById(R.id.list_item_parking_name);
			TextView avaiblePlaces = (TextView) view.findViewById(R.id.list_item_avaibleplaces);
			if(parkingName != null){
				parkingName.setText(parking.getName());
			}
			if(avaiblePlaces != null){
				avaiblePlaces.setText(String.valueOf(parking.getAvaiblePlaces()+" / "+parking.getFullPlaces()));
			}
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
