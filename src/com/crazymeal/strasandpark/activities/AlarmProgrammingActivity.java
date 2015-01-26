package com.crazymeal.strasandpark.activities;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.crazymeal.mobistras.R;
import com.crazymeal.strasandpark.alarms.AlarmInfos;
import com.crazymeal.strasandpark.alarms.AlarmService;

public class AlarmProgrammingActivity extends Activity{
	private Button validateButton;
	private TimePicker timePicker;
	private Spinner spinner;
	private CheckBox isRecurrentCheckbox;
	
	private Messenger serviceMessenger;
	private boolean serviceBinded;
	private ServiceConnection mConnection;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_parking_alarm);
		
		this.spinner = (Spinner) findViewById(R.id.spinner_recurrence);
		
		this.timePicker = (TimePicker)findViewById(R.id.timePicker);
		
		this.validateButton = (Button)findViewById(R.id.button_validate_alarm);
		this.validateButton.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				try {
					Message message = Message.obtain();
					AlarmInfos infos = new AlarmInfos(timePicker.getCurrentHour(),timePicker.getCurrentMinute(), isRecurrentCheckbox.isChecked());
					if(isRecurrentCheckbox.isChecked()){
						infos.setReccurencyDay((int) spinner.getSelectedItemId());
					}
					message.obj = infos;
					serviceMessenger.send(message);
				} catch (RemoteException e) {
					e.printStackTrace();
				} finally {
					finish();
				}
			}
		});
		
		this.isRecurrentCheckbox = (CheckBox)findViewById(R.id.checkBox_isRecurrent);
		this.isRecurrentCheckbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked)
					findViewById(R.id.layout_recurrence).setVisibility(View.VISIBLE);
				else
					findViewById(R.id.layout_recurrence).setVisibility(View.GONE);
			}
		});
		
		this.mConnection = new ServiceConnection() {
			public void onServiceConnected(ComponentName className, IBinder service) {
				serviceMessenger = new Messenger(service);
				serviceBinded = true;
			}

			public void onServiceDisconnected(ComponentName className) {
				serviceMessenger = null;
				serviceBinded = false;
			}
		};
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.recurrence_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		this.spinner.setAdapter(adapter);
		
		bindService(new Intent(this, AlarmService.class), mConnection, Context.BIND_AUTO_CREATE);
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		if (serviceBinded) {
			unbindService(mConnection);
			serviceBinded = false;
		}
	}
}
