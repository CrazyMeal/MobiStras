package com.crazymeal.mobistras;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.crazymeal.alarms.AlarmProgrammerActivity;
import com.crazymeal.alarms.CustomClickListener;
import com.crazymeal.model.Parking;
import com.crazymeal.model.Status;

public class ParkingMapAdapter extends ArrayAdapter<com.crazymeal.model.Parking>{
	private ArrayList<com.crazymeal.model.Parking> parkingList;
	private Resources resources;
	
	public ParkingMapAdapter(Context context, Resources resources, int textViewRessourceId, ArrayList<com.crazymeal.model.Parking> parkingList){
		super(context, textViewRessourceId, parkingList);
		this.parkingList = parkingList;
		this.resources = resources;
	}
	
	public View getView(int position, View convertView, ViewGroup parentView){
		View view = convertView;
		
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.list_item_display, null);
		}
		
		final Parking parking = this.parkingList.get(position);
		
		if(parking != null){
			TextView parkingName = (TextView) view.findViewById(R.id.list_item_parking_name);
			TextView avaiblePlaces = (TextView) view.findViewById(R.id.list_item_avaibleplaces);
			if(parkingName != null){
				parkingName.setText(parking.getName());
			}
			if(avaiblePlaces != null){
				avaiblePlaces.setText(String.valueOf(parking.getAvaiblePlaces()+" / "+parking.getFullPlaces()));
			}
			if(parking.getStatus() == Status.CLOSE){
				ImageView image = (ImageView) view.findViewById(R.id.image_parking_open);
				image.setImageBitmap(BitmapFactory.decodeResource(this.resources, R.drawable.parking_close));
			}
			
			view.setOnClickListener(new CustomClickListener(this.getContext()));
		}
		return view;
	}
}
