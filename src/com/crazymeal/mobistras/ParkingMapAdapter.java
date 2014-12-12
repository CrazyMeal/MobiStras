package com.crazymeal.mobistras;

import java.util.ArrayList;
import java.util.HashMap;

import model.Parking;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ParkingMapAdapter extends ArrayAdapter<model.Parking>{
	private ArrayList<model.Parking> parkingList;
	
	public ParkingMapAdapter(Context context, int textViewRessourceId, ArrayList<model.Parking> parkingList){
		super(context, textViewRessourceId, parkingList);
		this.parkingList = parkingList;
	}
	
	public View getView(int position, View convertView, ViewGroup parentView){
		View view = convertView;
		
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.list_item_display, null);
		}
		
		Parking parking = this.parkingList.get(position);
		
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
}
