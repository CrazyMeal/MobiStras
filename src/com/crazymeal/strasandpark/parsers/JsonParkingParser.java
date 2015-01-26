package com.crazymeal.strasandpark.parsers;

import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import android.annotation.SuppressLint;

import com.crazymeal.strasandpark.model.Parking;

@SuppressLint("UseSparseArrays")
public class JsonParkingParser extends JsonParser{
	
	public JsonParkingParser(){
		this.extractedDatas = new StringBuffer();
	}
	
	@Override
	public HashMap<Integer, Parking> parse(String stringToParse) {
		JSONParser parser = new JSONParser();
		HashMap<Integer, Parking> parkingList = new HashMap<Integer,Parking>();
		
		String datas = stringToParse;

		try {
			Object obj = parser.parse(datas);
			JSONObject obj2=(JSONObject)obj;
			JSONArray parkings = (JSONArray)obj2.get("s");
			
			//int i = 0;
			for(int i = 0; i < parkings.size(); i++){
				JSONObject tmpJsonParking = (JSONObject)parkings.get(i);
				Parking tmpParking = new Parking(Integer.parseInt((String) tmpJsonParking.get("id")), 
						Integer.parseInt((String)tmpJsonParking.get("df")), 
						Integer.parseInt((String)tmpJsonParking.get("dt")), 
						"default", 
						(String)tmpJsonParking.get("ds"));
				parkingList.put(Integer.parseInt((String)tmpJsonParking.get("id")), tmpParking);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return parkingList;
	}
}
