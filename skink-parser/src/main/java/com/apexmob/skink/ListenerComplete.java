package com.apexmob.skink;

/**
 * An event signaling that a listener has read all of the data it needs to and no longer needs to 
 * receive events.
 * 
 * <p>ListenerComplete events are immutable, and therefore threadsafe.</p>
 */
public class ListenerComplete {
	
	private final NodeListener listener;
	
	/**
	 * Construct a new ListenerComplete event to signal the provided listener needs receive no more events.
	 * @param listener The listener that is complete.
	 * @throws IllegalArgumentException if the listener provided is null.
	 */
	public ListenerComplete(NodeListener listener) {
		if (listener == null) {
			throw new IllegalArgumentException("The listener provided is null");
		}
		this.listener = listener;
	}
	
	/**
	 * Get the listener that is complete.
	 * @return The listener that is complete.
	 */
	public NodeListener getListener() {
		return listener;
	}

}
