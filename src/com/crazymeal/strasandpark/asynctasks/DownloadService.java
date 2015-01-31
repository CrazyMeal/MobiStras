package com.crazymeal.strasandpark.asynctasks;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Messenger;
import android.os.ResultReceiver;

import com.crazymeal.strasandpark.parsers.JsonLocationParser;
import com.crazymeal.strasandpark.parsers.JsonParkingParser;

public class DownloadService extends Service {
	private Messenger mMessenger;
	private JsonLocationParser locationParser;
	private JsonParkingParser parkingParser;
	private ResultReceiver resultReceiver;
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		if(this.locationParser == null && this.parkingParser == null && this.resultReceiver == null){
			this.locationParser = new JsonLocationParser();
			this.parkingParser = new JsonParkingParser();
		}
		this.mMessenger = new Messenger(new DownloadServiceHandler(
				this,
				(ResultReceiver) intent.getParcelableExtra("receiver"),
				this.parkingParser, 
				this.locationParser));
		return mMessenger.getBinder();
	}
}
