package asynctasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import android.os.AsyncTask;

import com.crazymeal.mobistras.ParkingListActivity.AsyncTaskListener;

public class DownloadLocationTask extends AsyncTask<String,Void, String>{
	private AsyncTaskListener listener;
	public DownloadLocationTask(AsyncTaskListener listener){
		this.setListener(listener);
		this.getListener().waitFor(this);
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
        //locationString = result;
    	this.getListener().notify(this);
   }
	public AsyncTaskListener getListener() {
		return listener;
	}
	public void setListener(AsyncTaskListener listener) {
		this.listener = listener;
	}

}