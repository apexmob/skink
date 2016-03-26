package com.apexmob.skink;

/**
 * An event listener that can listen to one or more Data events.
 */
public interface DataListener {

	/**
	 * Perform functionality when a Data event occurs.
	 * @param data The Data.
	 */
	public void onData(Data data);
	
	/**
	 * Perform functionality when a DataComplete event occurs.
	 * @param dataComplete The DataComplete.
	 */
	public void onDataComplete(DataComplete dataComplete);
	
	/**
	 * Perform functionality when a ListenerComplete event occurs.
	 * @param listenerComplete The ListenerComplete.
	 */
	public void onListenerComplete(ListenerComplete listenerComplete);
}
