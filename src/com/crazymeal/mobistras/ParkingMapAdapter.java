package com.crazymeal.mobistras;

import java.util.ArrayList;
import java.util.HashMap;

import model.Parking;
import android.content.Context;
import android.widget.ArrayAdapter;

public class ParkingMapAdapter extends ArrayAdapter<model.Parking>{
	
	public ParkingMapAdapter(Context context, int textViewRessourceId, HashMap<Integer,Parking> parkingMap){
		super(context, textViewRessourceId);
		ArrayList<Parking> parkingList = new ArrayList<Parking>(parkingMap.values());
		
		
	}
}
