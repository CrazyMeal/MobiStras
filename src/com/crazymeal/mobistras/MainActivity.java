package com.crazymeal.mobistras;


import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Messenger;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.view.Menu;

public class MainActivity extends Activity {

	private Messenger mService = null;
	private boolean mBound;
	private SharedPreferences myPrefs;
	
	
	private ServiceConnection mConnection = new ServiceConnection() {
		public void onServiceConnected(ComponentName className, IBinder service) {
			setmService(new Messenger(service));
			setmBound(true);
		}

		public void onServiceDisconnected(ComponentName className) {
			setmService(null);
			setmBound(false);
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		try {
			startService(new Intent(MainActivity.this, MainService.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		this.getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void onStart() {
		super.onStart();
		bindService(new Intent(this, MainService.class), mConnection,
				Context.BIND_AUTO_CREATE);

		final String PREFS_NAME = "MyPrefsFile";
		setMyPrefs(this.getSharedPreferences(PREFS_NAME, MODE_PRIVATE));
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (mBound) {
			unbindService(mConnection);
			mBound = false;
		}
	}
	
	@Override
	public void onPause() {
		super.onPause();
	}
	
	public boolean isConnected(Activity activity) 
    {
      ConnectivityManager connectivityManager = (ConnectivityManager)  activity.getSystemService(Context.CONNECTIVITY_SERVICE);
      NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
      if (networkInfo != null) 
      {
        State networkState = networkInfo.getState();
        if (networkState.compareTo(State.CONNECTED) == 0) 
          return true;
      }
      return false;
    }

	public Messenger getmService() {
		return mService;
	}

	public void setmService(Messenger mService) {
		this.mService = mService;
	}

	public boolean ismBound() {
		return mBound;
	}

	public void setmBound(boolean mBound) {
		this.mBound = mBound;
	}

	public SharedPreferences getMyPrefs() {
		return myPrefs;
	}

	public void setMyPrefs(SharedPreferences myPrefs) {
		this.myPrefs = myPrefs;
	}
}
