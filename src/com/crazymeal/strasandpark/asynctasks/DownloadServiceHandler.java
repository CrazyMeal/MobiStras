package com.crazymeal.strasandpark.asynctasks;

import java.util.concurrent.ExecutionException;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.ResultReceiver;

import com.crazymeal.strasandpark.R;
import com.crazymeal.strasandpark.parsers.JsonLocationParser;
import com.crazymeal.strasandpark.parsers.JsonParkingParser;

public class DownloadServiceHandler extends Handler {
	private JsonLocationParser locationParser;
	private JsonParkingParser parkingParser;
	private Context originContext;
	private ResultReceiver resultReceiver;
	
	public DownloadServiceHandler(Context originContext,ResultReceiver resultReceiver, JsonParkingParser parkingParser, JsonLocationParser locationParser){
		this.originContext = originContext;
		this.resultReceiver = resultReceiver;
		this.parkingParser = parkingParser;
		this.locationParser = locationParser;
	}
	
	@Override
	public void handleMessage(Message msg) {
		int action = msg.arg1;
		
		switch (action) {
		case 0: // Download everything
			AsyncTaskListener listener = new AsyncTaskListener(
					this.originContext, 
					this.locationParser, 
					this.parkingParser, 
					this.resultReceiver);
			
			JsonDownloadTask jsonLocationTask = new JsonDownloadTask(listener);
			JsonDownloadTask jsonParkingTask = new JsonDownloadTask(listener);
			if(!jsonLocationTask.isHasBeenExecuted() && !jsonParkingTask.isHasBeenExecuted()){
				jsonLocationTask.execute(new String[]{this.originContext.getString(R.string.urlLocation)});
				jsonParkingTask.execute(new String[]{this.originContext.getString(R.string.urlParking)});
			
				try {
					
					locationParser.setDataToParse(jsonLocationTask.get());
					parkingParser.setDataToParse(jsonParkingTask.get());
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
			break;
		case 1:
			break;
		default:
			break;
		}
	}
	
}