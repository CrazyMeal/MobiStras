package com.crazymeal.mobistras.alarms;

public class AlarmInfos {
	private int hour, minutes;
	private boolean recurrent;
	private int reccurencyDay;
	
	public AlarmInfos(int hour, int minutes, boolean recurrent){
		this.hour = hour;
		this.minutes = minutes;
		this.recurrent = recurrent;
	}
	
	public AlarmInfos(int hour, int minutes, boolean recurrent, int reccurencyDay){
		this.hour = hour;
		this.minutes = minutes;
		this.recurrent = recurrent;
		this.reccurencyDay = reccurencyDay;
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

	public int getReccurencyDay() {
		return reccurencyDay;
	}

	public void setReccurencyDay(int reccurencyDay) {
		this.reccurencyDay = reccurencyDay;
	}
}
