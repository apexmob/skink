package com.apexmob.skink;

import java.util.ArrayList;
import java.util.List;

public class MockDataEventManager implements DataManager {
	
	private List<Data> dataReadEvents = new ArrayList<Data>();
	private List<DataComplete> readCompletedEvents = new ArrayList<DataComplete>();
	private List<ListenerComplete> listenerCompletedEvents = new ArrayList<ListenerComplete>();
	private List<DataListener> dataEventListeners = new ArrayList<DataListener>();

	public void registerListener(DataListener listener) {
		dataEventListeners.add(listener);
	}

	public void deregisterListener(DataListener listener) {
		dataEventListeners.remove(listener);
	}
	
	public void data(Data evt) {
		dataReadEvents.add(evt);
	}

	public void dataComplete(DataComplete evt) {
		readCompletedEvents.add(evt);
	}

	public void listenerComplete(ListenerComplete evt) {
		listenerCompletedEvents.add(evt);
	}
	
	public List<Data> getDataReadEventStack() {
		return dataReadEvents;
	}

}
