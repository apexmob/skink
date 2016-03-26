package com.apexmob.skink;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The default implementation of the DataManager interface.
 */
public class DefaultDataManager implements DataManager {
	
	private static final Logger logger = LoggerFactory.getLogger(DefaultDataManager.class);
	
	private final List<DataListener> listeners = new ArrayList<DataListener>();
	
	/**
	 * Construct a new DefaultDataManager.
	 */
	public DefaultDataManager() {
	}

	/**
	 * Register a new listener to start receiving data events.
	 * @param listener The listener that will receive the events.
	 * @throws IllegalArgumentException if the listener provided is null. 
	 */
	public void registerListener(DataListener listener) {
		if (listener == null) {
			throw new IllegalArgumentException("The listener provided is null");
		}
		listeners.add(listener);
	}
	
	/**
	 * De-register a listener from receiving data events.
	 * @param listener The listener that will no longer receive the events. 
	 * @throws IllegalArgumentException if the listener provided is null.
	 * @throws IllegalArgumentException if the listener was not previously registered.
	 */
	public void deregisterListener(DataListener listener) {
		if (listener == null) {
			throw new IllegalArgumentException("The listener provided is null");
		}
		boolean wasPresent = listeners.remove(listener);
		if (!wasPresent) {
			throw new IllegalArgumentException("The provided listener was not previously registered");
		}
	}

	/**
	 * Publish a Data event to all listeners.
	 * @param data The Data event to publish.
	 * @throws IllegalArgumentException if the Data provided is null.
	 */
	public void data(Data data) {
		if (data == null) {
			throw new IllegalArgumentException("The event provided is null");
		}
		for (int i=0; i < listeners.size(); i++) {
			try {
				listeners.get(i).onData(data);
			} catch (Exception e) {
				logger.error("An exception occurred while processing data listeners", e);
			}
		}
	}

	/**
	 * Publish a DataComplete event to all listeners.
	 * @param dataComplete The DataComplete event to publish.
	 * @throws IllegalArgumentException if the DataComplete provided is null.
	 */
	public void dataComplete(DataComplete dataComplete) {
		if (dataComplete == null) {
			throw new IllegalArgumentException("The event provided is null");
		}
		for (int i=0; i < listeners.size(); i++) {
			try {
				listeners.get(i).onDataComplete(dataComplete);
			} catch (Exception e) {
				logger.error("An exception occurred while processing dataComplete listeners", e);
			}
		}
	}

	/**
	 * Publish a ListenerComplete event to all listeners.
	 * @param listenerComplete The ListenerComplete event to publish.
	 * @throws IllegalArgumentException if the ListenerComplete provided is null.
	 */
	public void listenerComplete(ListenerComplete listenerComplete) {
		if (listenerComplete == null) {
			throw new IllegalArgumentException("The event provided is null");
		}
		for (int i=0; i < listeners.size(); i++) {
			try {
				listeners.get(i).onListenerComplete(listenerComplete);
			} catch (Exception e) {
				logger.error("An exception occurred while processing listenerComplete listeners", e);
			}
		}
	}

}
