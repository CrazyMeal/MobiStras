package com.crazymeal.strasandpark;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.crazymeal.strasandpark.R;
import com.crazymeal.strasandpark.model.Parking;
import com.crazymeal.strasandpark.model.Status;

public class ParkingMapAdapter extends ArrayAdapter<com.crazymeal.strasandpark.model.Parking>{
	private ArrayList<com.crazymeal.strasandpark.model.Parking> parkingList;
	private Resources resources;
	private ViewHolderItem viewHolder;
	
	static class ViewHolderItem {
	    TextView parkingNameView;
	    TextView avaiblePlacesView;
	    ImageView parkingLogo;
	    ImageView favoriteLogo;
	}
	
	public ParkingMapAdapter(Context context, Resources resources, int textViewRessourceId, ArrayList<com.crazymeal.strasandpark.model.Parking> parkingList){
		super(context, textViewRessourceId, parkingList);
		this.parkingList = parkingList;
		this.resources = resources;
	}
	
	public View getView(int position, View convertView, ViewGroup parentView){
		
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.new_list_item_display, parentView, false);
			
			viewHolder = new ViewHolderItem();
				
			viewHolder.parkingNameView = (TextView) convertView.findViewById(R.id.textView_parking_name);
			viewHolder.avaiblePlacesView = (TextView) convertView.findViewById(R.id.textView_available_places);
				
			viewHolder.parkingLogo = (ImageView) convertView.findViewById(R.id.imageView_parking_logo);
				
			viewHolder.favoriteLogo = (ImageView) convertView.findViewById(R.id.imageView_favorite);		
				
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolderItem) convertView.getTag();
		}
		
		final Parking parking = this.parkingList.get(position);
		if(parking != null){	
			viewHolder.parkingNameView.setText(parking.getName());
			viewHolder.avaiblePlacesView.setText(String.valueOf(parking.getAvaiblePlaces()+" / "+parking.getFullPlaces()));
			
			if(parking.getStatus() == Status.CLOSE || parking.getStatus() == Status.FULL || parking.getStatus() == Status.UNAVAIBLE)
				viewHolder.parkingLogo.setImageBitmap(BitmapFactory.decodeResource(this.resources, R.drawable.parking_close));
			else
				viewHolder.parkingLogo.setImageBitmap(BitmapFactory.decodeResource(this.resources, R.drawable.parking_open));
			
			if(parking.isFavorite())
				viewHolder.favoriteLogo.setImageBitmap(BitmapFactory.decodeResource(this.resources, android.R.drawable.star_big_on));
			else
				viewHolder.favoriteLogo.setImageBitmap(BitmapFactory.decodeResource(this.resources, android.R.drawable.star_big_off));
				
			viewHolder.favoriteLogo.setOnClickListener(new CustomClickListener(parking, viewHolder.favoriteLogo,this.resources, this.getContext()));
			
			Log.d("LIST_ADAPTER", Boolean.toString(parking.isFavorite()));
		}
		
		return convertView;
	}
}
