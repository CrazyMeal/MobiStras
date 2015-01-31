package com.crazymeal.strasandpark.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.crazymeal.strasandpark.ParkingDatabase;
import com.crazymeal.strasandpark.ParkingMapAdapter;
import com.crazymeal.strasandpark.R;
import com.crazymeal.strasandpark.asynctasks.DownloadService;
import com.crazymeal.strasandpark.model.Parking;

public class ParkingListActivity extends Activity{
	private ParkingMapAdapter adapter;
	private ListView listView;
	private LinearLayout internetLayout;
	private Context thatContext;
	
	private Messenger serviceMessenger;
	private ServiceConnection mConnection;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		this.thatContext = this;
		
		this.mConnection = new ServiceConnection() {
			public void onServiceConnected(ComponentName className, IBinder service) {
				serviceMessenger = new Messenger(service);
				
				if(isOnline()){
					try {
						Message msg = Message.obtain();
						msg.arg1 = 0;
						serviceMessenger.send(msg);
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				} else {
					
					internetLayout = (LinearLayout) findViewById(R.id.layout_internet_unavailable);
					internetLayout.setVisibility(View.VISIBLE);
					((ImageView) findViewById(R.id.imageView_delete_banner)).setOnTouchListener(new OnTouchListener() {
						
						@Override
						public boolean onTouch(View v, MotionEvent event) {
							internetLayout.setVisibility(View.GONE);
							v.performClick();
							return false;
						}
					});
					
					ParkingDatabase db = new ParkingDatabase(thatContext);
					ArrayList<Parking> finalList = db.getAllAsList();
					db.close();
					
					adapter = new ParkingMapAdapter(thatContext,thatContext.getResources(), R.layout.new_list_item_display, finalList);
					listView.setAdapter(adapter);
					listView.setVisibility(View.VISIBLE);
					
				}
			}

			public void onServiceDisconnected(ComponentName className) {
				serviceMessenger = null;
			}
		};
		this.listView = (ListView) findViewById(R.id.listviewperso);
		
		
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		
		Intent serviceIntent = new Intent(this, DownloadService.class);
		serviceIntent.putExtra("receiver", new DownloadResultReceiver(null, this));
		bindService(serviceIntent, mConnection, Context.BIND_AUTO_CREATE);
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		this.getMenuInflater().inflate(R.menu.activity_parking_list, menu);
		return true;
	}

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_settings:
			startActivity(new Intent(ParkingListActivity.this, AlarmProgrammingActivity.class));
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public boolean isOnline() {
	    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    return netInfo != null && netInfo.isConnectedOrConnecting();
	}
	
	public class DownloadResultReceiver extends ResultReceiver{

		private Context context;

		public DownloadResultReceiver(Handler handler, Context context) {
			super(handler);
			this.context = context;
		}

		@Override
		protected void onReceiveResult(int resultCode, Bundle resultData) {
			if(resultCode == 200){
				ParkingDatabase db = new ParkingDatabase(this.context);
				ArrayList<Parking> finalList = db.getAllAsList();
				db.close();
				listView.setAdapter(new ParkingMapAdapter(this.context, context.getResources(), R.layout.new_list_item_display, finalList));
				listView.setVisibility(View.VISIBLE);
			}
		}
		
	}
}
