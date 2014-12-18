package util;

import java.util.HashMap;
import java.util.Map.Entry;

import org.json.simple.JSONArray;

import com.crazymeal.model.Parking;

public class Util {
	
	public static HashMap<Integer,Parking> merge(HashMap<Integer,Parking> parkingList, HashMap<Integer,Parking> locationList){
		HashMap<Integer,Parking> finalList = new HashMap<Integer,Parking>();
		
		for(Entry<Integer, Parking> p : parkingList.entrySet()){
			Parking parkingValue = p.getValue();
			Parking locationParking = locationList.get(p.getKey());
			if(locationParking != null){
				Parking tmpParking = new Parking(parkingValue.getId(), 
						parkingValue.getAvaiblePlaces(),
						parkingValue.getFullPlaces(), 
						locationParking.getName(), 
						parkingValue.getStatus(),
						locationParking.getLongitude() ,
						locationParking.getLatitude());
				finalList.put(p.getKey(), tmpParking);
			}
		}
		return finalList;
	}
	
	@SuppressWarnings("unchecked")
	public static String getJSONString(HashMap<Integer,Parking> parkingList){
		StringBuilder sb = new StringBuilder();
		JSONArray list = new JSONArray();
		
		for(Parking p : parkingList.values()){
			list.add(p);
		}
		sb.append(list);
		return sb.toString();
	}
}
