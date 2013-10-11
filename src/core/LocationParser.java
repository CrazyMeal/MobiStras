package core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

public class LocationParser {
	private URL url;
	private StringBuffer extractedData;
	private StringReader reader;
	private ArrayList<HashMap<String,String>> parkingList;
	
	public LocationParser(){
		this.setExtractedData(new StringBuffer());
	}

	public void extract(){
		try {
			URLConnection connection = this.getUrl().openConnection();
			InputStreamReader isr = new InputStreamReader(connection.getInputStream());
			BufferedReader br = new BufferedReader(isr);
			String inputLine;
			while((inputLine=br.readLine()) != null){
				extractedData.append(inputLine);
				System.out.println(inputLine);
			}
			br.close();
			isr.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void parse(){
		ArrayList<HashMap<String,String>> parkingList = new ArrayList<HashMap<String,String>>();
		try {
			
			String dataToParse = extractedData.toString();
			dataToParse = dataToParse.replace("\"d\":[],\"s\":[{","");
			dataToParse = dataToParse.replaceAll("\"go\":","");
			
			String[] buffer = dataToParse.split("\\},\\{");
			StringBuffer sb = new StringBuffer();
			
			for(int i=0;i<buffer.length;i++){
				buffer[i] = buffer[i].replaceAll("\\{", "");
				buffer[i] = buffer[i].replaceAll("\\}]", "");
				buffer[i] = buffer[i].replaceAll("\\}", "");
				
				sb.append(buffer[i]);
				sb.append("\n");
			}
			
			BufferedReader reader = new BufferedReader(new StringReader(sb.toString()));
			String tmpLine;
			while ((tmpLine=reader.readLine()) != null){
				// tmpLine = toute la ligne d'un parking
				HashMap<String,String> tmpParkingInfos = new HashMap<String,String>();
				
				String[] tmpBuffer = tmpLine.split(",");
				
				for(int j=0;j<tmpBuffer.length;j++){
					String[] infoBuffer = tmpBuffer[j].split(":");
					// infoBuffer[0] => le champs
					// infoBuffer[1] => la value du champ
					infoBuffer[0] = infoBuffer[0].replaceAll("\"","");
					infoBuffer[1] = infoBuffer[1].replaceAll("\"","");
					tmpParkingInfos.put(infoBuffer[0],infoBuffer[1]);
				}
				parkingList.add(tmpParkingInfos);
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.setParkingList(parkingList);
	}
	public void execute(){
		this.extract();
		this.parse();
	}
	public String toString(){
		StringBuffer sb = new StringBuffer();
		for (int i=0; i<this.getParkingList().size(); i++){
			sb.append(this.getParkingList().get(i).toString());
			sb.append("\n");
		}
		
		return sb.toString();
		
	}
	public URL getUrl() {
		return url;
	}
	public void setUrl(String url) {
		try {
			this.url = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public StringBuffer getExtractedData() {
		return extractedData;
	}

	public void setExtractedData(StringBuffer extractedData) {
		this.extractedData = extractedData;
	}

	public StringReader getReader() {
		return reader;
	}

	public void setReader(StringReader reader) {
		this.reader = reader;
	}

	public ArrayList<HashMap<String,String>> getParkingList() {
		return parkingList;
	}

	public void setParkingList(ArrayList<HashMap<String,String>> parkingList) {
		this.parkingList = parkingList;
	}
	
	public HashMap<String,String> getParking(int id){
		return this.getParkingList().get(id);
	}
}
