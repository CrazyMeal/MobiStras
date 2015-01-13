package com.crazymeal.mobistras.parsers;

import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import android.annotation.SuppressLint;

import com.crazymeal.mobistras.model.Parking;

public class JsonLocationParser extends JsonParser{
	
	public JsonLocationParser(){
		this.extractedDatas = new StringBuffer();
	}
	
	@SuppressLint("UseSparseArrays")
	@Override
	public HashMap<Integer, Parking> parse(String stringToParse) {
		JSONParser parser = new JSONParser();
		HashMap<Integer, Parking> parkingList = new HashMap<Integer,Parking>();
		String datas = stringToParse;
		
		try {
			Object obj = parser.parse(datas);
			JSONObject obj2=(JSONObject)obj;
			JSONArray parkings = (JSONArray)obj2.get("s");
			
			for(int i = 0; i < parkings.size(); i++){
				JSONObject tmpJsonParking = (JSONObject)parkings.get(i);
				JSONObject coordinates = (JSONObject) tmpJsonParking.get("go");
				Parking tmpParking = new Parking();
				tmpParking.setId(Integer.parseInt((String)tmpJsonParking.get("id")));
				tmpParking.mergeDatas((String) tmpJsonParking.get("ln"), (Double)coordinates.get("x"), (Double)coordinates.get("y"));
				parkingList.put(Integer.parseInt((String)tmpJsonParking.get("id")), tmpParking);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return parkingList;
	}
}
