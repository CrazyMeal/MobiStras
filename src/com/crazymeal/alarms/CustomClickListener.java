package com.crazymeal.alarms;

import com.crazymeal.model.Parking;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class CustomClickListener implements OnClickListener{
	private Context context;
	private Parking parking;
	private Activity originActivity;
	private static int REQUEST_DETAILS = 1;
	
	public CustomClickListener(Activity originactivity, Context context, Parking parking){
		this.context = context;
		this.originActivity = originactivity;
		this.parking = parking;
	}
	@Override
	public void onClick(View v) {
		Intent intent = new Intent(this.context, DetailedParkingActivity.class);
		intent.putExtra("parkingName", this.parking.getName());
		this.originActivity.startActivity(intent);
		//this.originActivity.startActivityForResult(intent, CustomClickListener.REQUEST_DETAILS);
	}
	
}
