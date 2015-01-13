package com.crazymeal.detailed;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.crazymeal.mobistras.R;

public class DetailedParkingActivity extends Activity{
	private Button validateButton;
	private TimePicker timePicker;
	private Spinner spinner;
	private CheckBox isRecurrentCheckbox;
	
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
				if(!isRecurrentCheckbox.isChecked())
					programSimpleAlarm(timePicker.getCurrentHour(), timePicker.getCurrentMinute());
				finish();
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
	}
	
	
	@Override
	protected void onStart() {
		super.onStart();
		
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.recurrence_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		this.spinner.setAdapter(adapter);
	}


	private void programSimpleAlarm(int hour, int min){
		
	}
}