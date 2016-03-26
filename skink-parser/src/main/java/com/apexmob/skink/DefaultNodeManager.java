package com.apexmob.skink;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultNodeManager implements NodeManager {
	
	private static final Logger logger = LoggerFactory.getLogger(DefaultNodeManager.class);
	
	private final List<NodeListener> listeners = new ArrayList<NodeListener>();

	/**
	 * Register a new listener to start receiving node events.
	 * @param listener The listener that will receive the events. 
	 * @throws IllegalArgumentException if the listener provided is null. 
	 */
	public void registerListener(NodeListener listener) {
		if (listener == null) {
			throw new IllegalArgumentException("The listener provided is null");
		}
		listeners.add(listener);
	}

	/**
	 * De-register a listener from receiving node events.
	 * @param listener The listener that will no longer receive the events. 
	 * @throws IllegalArgumentException if the listener provided is null. 
	 * @throws IllegalArgumentException if the listener was not previously registered.
	 */
	public void deregisterListener(NodeListener listener) {
		if (listener == null) {
			throw new IllegalArgumentException("The listener provided is null");
		}
		boolean wasPresent = listeners.remove(listener);
		if (!wasPresent) {
			throw new IllegalArgumentException("The provided listener was not previously registered");
		}
	}

	/**
	 * Publish a StartElement event to all listeners.
	 * @param start The StartElement event to publish.
	 * @throws IllegalArgumentException if the StartElement provided is null.
	 */
	public void startElement(StartElement start) {
		if (start == null) {
			throw new IllegalArgumentException("The event provided is null");
		}
		for (int i=0; i < listeners.size(); i++) {
			try {
				listeners.get(i).onStartElement(start);
			} catch (Exception e) {
				logger.error("An exception occurred while processing startElement listeners", e);
			}
		}
	}

	/**
	 * Publish a EndElement event to all listeners.
	 * @param end The EndElement event to publish.
	 * @throws IllegalArgumentException if the EndElement provided is null.
	 */
	public void endElement(EndElement end) {
		if (end == null) {
			throw new IllegalArgumentException("The event provided is null");
		}
		for (int i=0; i < listeners.size(); i++) {
			try {
				listeners.get(i).onEndElement(end);
			} catch (Exception e) {
				logger.error("An exception occurred while processing endElement listeners", e);
			}
		}
	}

	/**
	 * Publish a Text event to all listeners.
	 * @param text The Text event to publish.
	 * @throws IllegalArgumentException if the Text provided is null.
	 */
	public void text(Text text) {
		if (text == null) {
			throw new IllegalArgumentException("The event provided is null");
		}
		for (int i=0; i < listeners.size(); i++) {
			try {
				listeners.get(i).onText(text);
			} catch (Exception e) {
				logger.error("An exception occurred while processing text listeners", e);
			}
		}
	}

}
