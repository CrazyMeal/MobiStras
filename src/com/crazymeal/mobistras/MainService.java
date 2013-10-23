package com.crazymeal.mobistras;


import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.widget.Toast;

public class MainService extends Service{
	final Messenger mMessenger = new Messenger(new IncomingHandler());
	
	@Override
	public IBinder onBind(Intent arg0) {
		Toast.makeText(getApplicationContext(), "Bienvenue", Toast.LENGTH_SHORT).show();
		return mMessenger.getBinder();
	}

	
	
	class IncomingHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
		
		}
	}
}
