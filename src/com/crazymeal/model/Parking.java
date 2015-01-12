package com.crazymeal.model;

import org.json.simple.JSONAware;
import org.json.simple.JSONObject;

import android.content.ContentValues;
import android.database.Cursor;

public class Parking implements JSONAware {
	private int id, avaiblePlaces, fullPlaces;
	private double longitude, latitude;
	private String name;
	private Status status;
	private boolean favorite;
	
	public Parking(){
		this.id = 0;
		this.avaiblePlaces = 0;
		this.fullPlaces = 0;
		this.longitude = 0;
		this.latitude = 0;
		this.name = "default";
		this.status = Status.CLOSE;
		this.favorite = false;
	}
	
	public Parking(int id, int avaiblePlaces, int fullPlaces, String name, String status){
		this.id = id;
		this.avaiblePlaces = avaiblePlaces;
		this.fullPlaces = fullPlaces;
		this.name = name;
		this.status = this.setStatus(status);
	}
	public Parking(int id, int avaiblePlaces, int fullPlaces, String name, Status status, Double longitude, Double latitude, int favorite){
		this.id = id;
		this.avaiblePlaces = avaiblePlaces;
		this.fullPlaces = fullPlaces;
		this.name = name;
		this.longitude = longitude;
		this.latitude = latitude;
		this.status = status;
		this.favorite = (favorite == 0) ? false : true;
	}
	
	public Parking(Cursor c){
		this.id = c.getInt(1);
		this.name = c.getString(2);
		this.avaiblePlaces = c.getInt(3);
		this.fullPlaces = c.getInt(4);
		this.status = this.setStatus(c.getString(5));
		this.longitude = c.getDouble(6);
		this.latitude = c.getDouble(7);
		this.favorite = (c.getInt(8) == 0) ? false : true;
	}
	private Status setStatus(String status) {
		if(status.equals("status_1"))
			return this.status = Status.OPEN;
		if(status.equals("status_2"))
			return this.status = Status.FULL;
		if(status.equals("status_3"))
			return this.status = Status.UNAVAIBLE;
		if(status.equals("status_4"))
			return this.status = Status.CLOSE;
		
		return null;
	}
	
	public void mergeDatas(String name, Double longitude, Double latitude){
		this.name = name;
		this.longitude = longitude;
		this.latitude = latitude;
	}
	
	public ContentValues getAsContentValues(){
		ContentValues cv = new ContentValues();
		cv.put("id",this.id);
		cv.put("name",this.name);
		cv.put("avaible",this.avaiblePlaces);
		cv.put("full",this.fullPlaces);
		cv.put("status",this.getStringStatus());
		cv.put("longitude",this.longitude);
		cv.put("latitude",this.latitude);
		cv.put("favorite", (this.favorite) ? 1 : 0);
		return cv;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String toJSONString() {
		JSONObject obj = new JSONObject();
		obj.put("id", this.id);
	    obj.put("name", this.name);
	    obj.put("avaiblePlaces", this.avaiblePlaces);
	    obj.put("fullPlaces", this.fullPlaces);
	    obj.put("longitude", this.longitude);
	    obj.put("latitude", this.latitude);
	    obj.put("status", this.status);
	    return obj.toString();

	}
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("Parking(" + this.id + "): " + this.name + "\n");
		sb.append("Avaible places: " + this.avaiblePlaces + "/" + this.fullPlaces +"\n");
		sb.append("Status: " + this.status + "\n");
		sb.append("Long: " + this.longitude + "\n");
		sb.append("Latit: " + this.latitude + "\n");
		return sb.toString();
	}
	
	private String getStringStatus(){
		String stringStatus = "";
		switch(this.status){
		case OPEN:
			stringStatus = "status_1";
			break;
		case FULL:
			stringStatus = "status_2";
			break;
		case UNAVAIBLE:
			stringStatus = "status_3";
			break;
		case CLOSE:
			stringStatus = "status_4";
			break;
		}
		return stringStatus;
	}
	public int getId() {
		return id;
	}
	public void setId(int id){
		this.id = id;
	}
	public int getAvaiblePlaces() {
		return avaiblePlaces;
	}

	public int getFullPlaces() {
		return fullPlaces;
	}

	public double getLongitude() {
		return longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public String getName() {
		return name;
	}

	public Status getStatus() {
		return status;
	}
}
