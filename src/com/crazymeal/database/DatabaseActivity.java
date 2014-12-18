package com.crazymeal.database;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class DatabaseActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ParkingDatabase db = new ParkingDatabase(this);
		Log.d("DATABASE_ACTIVITY","Database activity launched");
		
		Log.d("DATABASE_ACTIVITY","Database name: " + db.getDatabaseName());
		Log.d("DATABASE_ACTIVITY","Datas: " + db.getAllAsList());
	}
	
}
