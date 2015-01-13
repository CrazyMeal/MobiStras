package com.crazymeal.detailed;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.crazymeal.database.ParkingDatabase;
import com.crazymeal.model.Parking;

public class CustomClickListener implements OnClickListener{
	private Parking parking;
	private ImageView image;
	private Resources resources;
	private Context context;
	
	public CustomClickListener(Parking parking, ImageView image, Resources resources, Context context){
		this.parking = parking;
		this.image = image;
		this.resources = resources;
		this.context = context;
	}
	@Override
	public void onClick(View v) {
		this.parking.setFavorite(!this.parking.isFavorite());
		
		Log.d("FAVORITE_CLICK", "Set parking favorite to " + this.parking.isFavorite());
		
		ParkingDatabase db = new ParkingDatabase(this.context);
		db.setParkingFavorite(this.parking);
		db.close();
		
		if(this.parking.isFavorite())
			this.image.setImageBitmap(BitmapFactory.decodeResource(this.resources, android.R.drawable.star_big_on));
		else
			this.image.setImageBitmap(BitmapFactory.decodeResource(this.resources, android.R.drawable.star_big_off));
	}
	
}
