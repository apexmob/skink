package com.apexmob.skink;

import com.apexmob.skink.DataListener;
import com.apexmob.skink.Data;
import com.apexmob.skink.ListenerComplete;
import com.apexmob.skink.DataComplete;

public class MockDataEventListener implements DataListener {

	private Data dataRead = null;
	private DataComplete readCompleted = null;
	private ListenerComplete listenerCompleted = null;

	public void onData(Data evt) {
		dataRead = evt;
	}

	public void onDataComplete(DataComplete evt) {
		readCompleted = evt;
	}

	public void onListenerComplete(ListenerComplete evt) {
		listenerCompleted = evt;
	}

	public Data getDataRead() {
		return dataRead;
	}

	public DataComplete getReadCompleted() {
		return readCompleted;
	}

	public ListenerComplete getListenerCompleted() {
		return listenerCompleted;
	}

}
