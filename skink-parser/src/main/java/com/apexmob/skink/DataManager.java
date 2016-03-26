package com.apexmob.skink;

/**
 * A DataManager centralizes the listener registration and event processing when data events occur.  
 * It allows event sources a single place to communicate data events and a single place for event 
 * listeners to register themselves.  A DataManager orchestrates the execution of event processing 
 * within the collection of listeners.
 */
public interface DataManager {
	
	/**
	 * Register a new listener to start receiving data events.
	 * @param listener The listener that will receive the events. 
	 */
	public void registerListener(DataListener listener);
	
	/**
	 * De-register a listener from receiving data events.
	 * @param listener The listener that will no longer receive the events. 
	 */
	public void deregisterListener(DataListener listener);
	
	/**
	 * Publish a Data event to all listeners.
	 * @param data The Data event to publish.
	 */
	public void data(Data data);
	
	/**
	 * Publish a DataComplete event to all listeners.
	 * @param dataComplete The DataComplete event to publish.
	 */
	public void dataComplete(DataComplete dataComplete);
	
	/**
	 * Publish a ListenerComplete event to all listeners.
	 * @param listenerComplete The ListenerComplete event to publish.
	 */
	public void listenerComplete(ListenerComplete listenerComplete);

}
