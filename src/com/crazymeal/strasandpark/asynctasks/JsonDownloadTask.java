package com.crazymeal.strasandpark.asynctasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import android.os.AsyncTask;

public class JsonDownloadTask extends AsyncTask<String,Void, String>{
	private AsyncTaskListener listener;
	private boolean hasBeenExecuted;
	
	public JsonDownloadTask(AsyncTaskListener listener){
		this.listener = listener;
		this.listener.waitFor(this);
		this.hasBeenExecuted = false;
	}
	
	public JsonDownloadTask(){
		this.listener = null;
		this.hasBeenExecuted = false;
	}
	
	@Override
	protected String doInBackground(String... params) {
		URL url;
		InputStream in = null;
		String finalString = null;
		try{		
			url = new URL(params[0]);
				
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.connect();
			in = connection.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			StringBuffer sb = new StringBuffer();
			String tmpLine;
			while((tmpLine=reader.readLine()) != null){
				sb.append(tmpLine);
			}
			finalString = sb.toString();
			
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return finalString;
	}
	 @Override
	 protected void onPostExecute(String result) {
		 this.hasBeenExecuted = true;
		 if(this.listener != null){
			 this.listener.notify(this);
		 }
	   }

	public boolean isHasBeenExecuted() {
		return hasBeenExecuted;
	}

	public void setHasBeenExecuted(boolean hasBeenExecuted) {
		this.hasBeenExecuted = hasBeenExecuted;
	}
}
