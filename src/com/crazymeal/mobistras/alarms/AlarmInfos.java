package com.crazymeal.mobistras.alarms;

public class AlarmInfos {
	private int hour, minutes;
	private boolean recurrent;
	
	public AlarmInfos(int hour, int minutes, boolean recurrent){
		this.hour = hour;
		this.minutes = minutes;
		this.recurrent = recurrent;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	public boolean isRecurrent() {
		return recurrent;
	}

	public void setRecurrent(boolean recurrent) {
		this.recurrent = recurrent;
	}
}
