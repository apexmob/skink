package com.apexmob.skink;

/**
 * A NodeManager centralizes the listener registration and event processing when node events occur.  
 * It allows event sources a single place to communicate node events and a single place for event 
 * listeners to register themselves.  A NodeManager orchestrates the execution of event processing 
 * within the collection of listeners.
 */
public interface NodeManager {
	
	/**
	 * Register a new listener to start receiving node events.
	 * @param listener The listener that will receive the events. 
	 */
	public void registerListener(NodeListener listener);
	
	/**
	 * De-register a listener from receiving node events.
	 * @param listener The listener that will no longer receive the events. 
	 */
	public void deregisterListener(NodeListener listener);
	
	/**
	 * Publish a StartElement event to all listeners.
	 * @param start The StartElement event to publish.
	 */
	public void startElement(StartElement start);
	
	/**
	 * Publish a EndElement event to all listeners.
	 * @param end The EndElement event to publish.
	 */
	public void endElement(EndElement end);
	
	/**
	 * Publish a Text event to all listeners.
	 * @param text The Text event to publish.
	 */
	public void text(Text text);

}
