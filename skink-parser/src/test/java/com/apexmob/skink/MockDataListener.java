package com.apexmob.skink;

import java.util.ArrayList;
import java.util.List;

public class MockDataListener implements DataListener {
	
	private final List<Object> events = new ArrayList<Object>();

	@Override
	public void onData(Data data) {
		events.add(data);
	}

	@Override
	public void onDataComplete(DataComplete dataComplete) {
		events.add(dataComplete);
	}

	@Override
	public void onListenerComplete(ListenerComplete listenerComplete) {
		events.add(listenerComplete);
	}
	
	public Object getEvent(int index) {
		return events.get(index);
	}
	
	public int getEventCount() {
		return events.size();
	}
	
	public Data getDataEvent(int index) {
		return (Data) getEvent(index);
	}
	
	public DataComplete getDataCompleteEvent(int index) {
		return (DataComplete) getEvent(index);
	}
	
	public ListenerComplete getListenerCompleteEvent(int index) {
		return (ListenerComplete) getEvent(index);
	}

}
