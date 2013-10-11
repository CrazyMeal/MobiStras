package manager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import core.ParkingDataParser;
import core.LocationParser;

import description.Parking;

public class ParkingManager {
	private HashMap<Integer,Parking> parkingList;
	
	public ParkingManager(){
		setParkingList(new HashMap<Integer,Parking>());
	}
	
	public void buildList(ParkingDataParser parser){
		ArrayList<HashMap<String,String>> list = parser.getParkingList();
		for(int i=0; i<list.size(); i++){
			Parking tmpParking = new Parking();
			tmpParking.setId(Integer.parseInt(list.get(i).get("id")));
			tmpParking.setAvaiblePlaces(Integer.parseInt(list.get(i).get("df")));
			tmpParking.setStatus(list.get(i).get("ds"));
			tmpParking.setFullPlaces(Integer.parseInt(list.get(i).get("dt")));
			
			parkingList.put(tmpParking.getId(),tmpParking);
		}
	}
	public void merge(LocationParser lp){
		ArrayList<HashMap<String,String>> locationList = lp.getParkingList();
		for(int i=1; i<locationList.size(); i++){
			this.getParkingList().get(i).setName(locationList.get(i).get("ln"));
			this.getParkingList().get(i).setLongitude(Float.parseFloat(locationList.get(i).get("x")));
			this.getParkingList().get(i).setLatitude(Float.parseFloat(locationList.get(i).get("y")));
		}
	}
	public List<Parking> sortByAvaiblePlaces(){
		List<Parking> sortedList = new ArrayList<Parking>();
		for(int i=1; i<this.getParkingList().size(); i++){
			sortedList.add(this.getParkingList().get(i));
		}
		Collections.sort(sortedList);
		this.printSortedList(sortedList);
		
		return sortedList;
	}
	public List<Parking> getParkingByStatus(String status){
		List<Parking> list = new ArrayList<Parking>();
		for(int i=1; i<this.getParkingList().size(); i++){
			if(this.getParkingList().get(i).getStatus().equals(status)){
				list.add(this.getParkingList().get(i));
			}
		}
		this.printStatusList(list);
		return list;
	}
	public Parking getParking(int id){
		return this.getParkingList().get(id);
	}
	public HashMap<Integer,Parking> getParkingList() {
		return parkingList;
	}

	public void setParkingList(HashMap<Integer,Parking> parkingList) {
		this.parkingList = parkingList;
	}
	public void addParking(int id,Parking parking){
		this.getParkingList().put(id, parking);
	}
	public void removeParking(int id){
		this.getParkingList().remove(id);
	}
	public String toString(){
		StringBuffer sb = new StringBuffer();
		int i=1;
		while(i!=this.getParkingList().size()){
			sb.append("Name:" + this.getParkingList().get(i).getName());
			sb.append("\n");
			sb.append("Longitude:" + this.getParkingList().get(i).getLongitude());
			sb.append("\n");
			sb.append("Latitude:" + this.getParkingList().get(i).getLatitude());
			sb.append("\n");
			sb.append("Pl dispo:" + this.getParkingList().get(i).getAvaiblePlaces());
			sb.append("\n");
			sb.append("Pl total:" + this.getParkingList().get(i).getFullPlaces());
			sb.append("\n");
			sb.append("Status:" + this.getParkingList().get(i).getStatus());
			sb.append("\n");
			sb.append("\n");
			i++;
		}
		return sb.toString();
	}
	public void printSortedList(List<Parking> list){
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<list.size();i++){
			sb.append(list.get(i).getAvaiblePlaces());
			sb.append("\n");
		}
		System.out.println(sb.toString());
	}
	public void printStatusList(List<Parking> list){
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<list.size();i++){
			sb.append(list.get(i).getStatus());
			sb.append("\n");
		}
		System.out.println(sb.toString());
	}
}
