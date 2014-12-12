package model;

import org.json.simple.JSONAware;
import org.json.simple.JSONObject;

public class Parking implements JSONAware {
	private int id, avaiblePlaces, fullPlaces;
	private double longitude, latitude;
	private String name;
	private Status status;
	
	public Parking(){
		this.id = 0;
		this.avaiblePlaces = 0;
		this.fullPlaces = 0;
		this.longitude = 0;
		this.latitude = 0;
		this.name = "default";
		this.status = Status.CLOSE;
	}
	
	public Parking(int id, int avaiblePlaces, int fullPlaces, String name, String status){
		this.id = id;
		this.avaiblePlaces = avaiblePlaces;
		this.fullPlaces = fullPlaces;
		this.name = name;
		this.status = this.setStatus(status);
	}
	public Parking(int id, int avaiblePlaces, int fullPlaces, String name, Status status, Double longitude, Double latitude){
		this.id = id;
		this.avaiblePlaces = avaiblePlaces;
		this.fullPlaces = fullPlaces;
		this.name = name;
		this.longitude = longitude;
		this.latitude = latitude;
		this.status = status;
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
