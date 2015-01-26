package com.crazymeal.strasandpark.parsers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;

import com.crazymeal.strasandpark.model.Parking;

public abstract class JsonParser {
	protected URL urlToParse;
	protected StringBuffer extractedDatas;
	
	public abstract HashMap<Integer, Parking> parse(String stringToParse);
	
	public void setUrlToParse(String urlToParse) {
		try {
			this.urlToParse = new URL(urlToParse);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	public void getDatas() {
		try {
			HttpURLConnection connection = (HttpURLConnection) this.urlToParse.openConnection();
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setReadTimeout(10000);
			connection.setRequestMethod("GET");
			connection.connect();
			
			InputStream in = connection.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			
			String inputLine;
			while((inputLine=reader.readLine()) != null){
				extractedDatas.append(inputLine);
			}
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
