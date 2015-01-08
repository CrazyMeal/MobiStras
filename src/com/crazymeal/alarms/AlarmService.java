package com.crazymeal.alarms;

import android.app.AlarmManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class AlarmService extends Service {
	private AlarmManager am;
	
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

}
