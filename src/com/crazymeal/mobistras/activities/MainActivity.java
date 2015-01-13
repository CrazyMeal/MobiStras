package com.crazymeal.mobistras.activities;


import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Messenger;
import android.view.Menu;
import android.widget.Button;

import com.crazymeal.mobistras.MainService;
import com.crazymeal.mobistras.R;

public class MainActivity extends Activity {

	private Messenger mService = null;
	private boolean mBound;
	private SharedPreferences myPrefs;
	private Button listButton;
	
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
		//setContentView(R.layout.activity_main);
		
		try {
			Intent intent = new Intent(MainActivity.this, MainService.class);
			startService(intent);
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*
		this.listButton = (Button) findViewById(R.id.button_list);
		
		this.listButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {				
				Intent intent = new Intent(MainActivity.this, ParkingListActivity.class);
				startActivity(intent);
			}
		});
		*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		this.getMenuInflater().inflate(R.menu.activity_parking_list, menu);
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

	public Button getListButton() {
		return listButton;
	}

	public void setListButton(Button listButton) {
		this.listButton = listButton;
	}
}
