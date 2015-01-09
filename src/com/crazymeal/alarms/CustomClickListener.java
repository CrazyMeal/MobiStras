package com.crazymeal.alarms;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class CustomClickListener implements OnClickListener{
	private Context context;
	private Activity originActivity;
	private static int REQUEST_ALARM = 1;
	
	public CustomClickListener(Activity originactivity, Context context){
		this.context = context;
		this.originActivity = originactivity;
	}
	@Override
	public void onClick(View v) {
		Intent intent = new Intent(this.context, AlarmProgrammerActivity.class);
		this.originActivity.startActivityForResult(intent, CustomClickListener.REQUEST_ALARM);
	}
	
}
