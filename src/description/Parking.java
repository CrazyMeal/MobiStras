package description;

public class Parking implements Comparable<Parking>{
	private int id, avaiblePlaces, fullPlaces;
	private float longitude, latitude;
	private String name;
	private String status;
	
	public Parking(){
		setId(0);
		setAvaiblePlaces(0);
		setFullPlaces(0);
		setLongitude(0);
		setLatitude(0);
		setName("default");
		setStatus("close");
	}
	
	public int getId() {
		return id;
	}
	public int getAvaiblePlaces() {
		return avaiblePlaces;
	}
	public int getFullPlaces() {
		return fullPlaces;
	}
	public float getLongitude() {
		return longitude;
	}
	public float getLatitude() {
		return latitude;
	}
	public String getName() {
		return name;
	}
	public String getStatus() {
		return status;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setAvaiblePlaces(int avaiblePlaces) {
		this.avaiblePlaces = avaiblePlaces;
	}
	public void setFullPlaces(int fullPlaces) {
		this.fullPlaces = fullPlaces;
	}
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setStatus(String status) {
		if(status.equals("status_1"))
			this.status = "ouvert";
		if(status.equals("status_2"))
			this.status = "complet";
		if(status.equals("status_3"))
			this.status = "indisponible";
		if(status.equals("status_4"))
			this.status = "fermé";
	}

	@Override
	public int compareTo(Parking o) {
		int placesParking1 = o.getAvaiblePlaces();
		int placesParking2 = this.getAvaiblePlaces();
		
		if(placesParking1 < placesParking2){
			return -1;
		}
		else if (placesParking1 == placesParking2){
			return 0;
		}
		else{
			return 1;
		}
	}
}
