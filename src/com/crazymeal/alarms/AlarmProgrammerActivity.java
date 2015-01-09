package com.crazymeal.alarms;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.crazymeal.mobistras.R;

public class AlarmProgrammerActivity extends Activity{
	private Button validateButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_parking_alarm);
		
		Spinner spinner = (Spinner) findViewById(R.id.spinner_recurrence);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.recurrence_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		
		this.validateButton = (Button)findViewById(R.id.button_validate_alarm);
		this.validateButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = getIntent();
				intent.putExtra("test", "ceci est un test");
				setResult(RESULT_OK, intent);
				finish();
			}
		});
	}
}
