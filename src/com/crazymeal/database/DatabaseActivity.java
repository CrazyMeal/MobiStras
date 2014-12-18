package com.crazymeal.database;

import com.crazymeal.mobistras.ParkingMapAdapter;
import com.crazymeal.mobistras.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

public class DatabaseActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_database);
		
		
		ParkingDatabase db = new ParkingDatabase(this);
		Log.d("DATABASE_ACTIVITY","Database activity launched");
		
		Log.d("DATABASE_ACTIVITY","Database name: " + db.getDatabaseName());
		Log.d("DATABASE_ACTIVITY","Datas: " + db.getAllAsList());
		
		ListView view = (ListView) findViewById(R.id.listview_database);
		view.setAdapter(new ParkingMapAdapter(this, getResources(), R.layout.list_item_display, db.getAllAsList()));
	}
	
}
