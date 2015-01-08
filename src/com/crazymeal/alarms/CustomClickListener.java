package com.crazymeal.alarms;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class CustomClickListener implements OnClickListener{
	private Context context;
	
	public CustomClickListener(Context context){
		this.context = context;
	}
	@Override
	public void onClick(View v) {
		Intent intent = new Intent(this.context, AlarmProgrammerActivity.class);
		this.context.startActivity(intent);
	}

}
