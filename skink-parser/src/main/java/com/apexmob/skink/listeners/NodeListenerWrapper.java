package com.apexmob.skink.listeners;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.apexmob.skink.EndElement;
import com.apexmob.skink.NodeListener;
import com.apexmob.skink.StartElement;
import com.apexmob.skink.Text;

/**
 * The NodeListenerWrapper class provides manages a collection of NodeListeners
 * and delegates each node event to each of the listeners for processing.
 */
public class NodeListenerWrapper extends AbstractNodeListener {
	
	private final List<NodeListener> listeners = new ArrayList<NodeListener>(); 
	
	/**
	 * Construct a new NodeListenerWrapper with no managed listeners.
	 */
	public NodeListenerWrapper() {
	}
	
	/**
	 * Construct a new NodeListenerWrapper with a single managed listener.
	 * @param listener The listener that will receive the node events.
	 * @throws IllegalArgumentException if the listener provided is null.
	 */
	public NodeListenerWrapper(NodeListener listener) {
		if (listener == null) {
			throw new IllegalArgumentException("The listener provided is null");
		}
		listeners.add(listener);
	}
	
	/**
	 * Construct a new NodeListenerWrapper to manage the collection of listeners provided.
	 * @param listeners The collection of listeners that will receive the node events.
	 * @throws IllegalArgumentException if the listener collection provided is null.
	 */
	public NodeListenerWrapper(Collection<NodeListener> listeners) {
		if (listeners == null) {
			throw new IllegalArgumentException("The listener collection provided is null");
		}
		this.listeners.addAll(listeners);
	}
	
	/**
	 * Add a listener to the collection of managed listeners. 
	 * @param listener The listener to add.
	 * @throws IllegalArgumentException if the listener provided is null.
	 */
	public void addListener(NodeListener listener) {
		if (listener == null) {
			throw new IllegalArgumentException("The listener provided is null");
		}
		listeners.add(listener);
	}
	
	/**
	 * Remove the listener provided from the collection of managed listeners so that
	 * it no longer receives node events.
	 * @param listener The listener to remove.
	 * @return True if the collection of managed listeners is changes, otherwise false.
	 */
	public boolean removeListener(NodeListener listener) {
		return listeners.remove(listener);
	}
	
	/**
	 * {@inheritDoc}
	 * Delegate events to the collection of managed listeners.
	 */
	@Override
	public void onStartElement(StartElement start) {
		for (int i=0; i < listeners.size(); i++) {
			listeners.get(i).onStartElement(start);
		}
	}
	
	/**
	 * {@inheritDoc}
	 * Delegate events to the collection of managed listeners.
	 */
	@Override
	public void onEndElement(EndElement end) {
		for (int i=0; i < listeners.size(); i++) {
			listeners.get(i).onEndElement(end);
		}
	}
	
	/**
	 * {@inheritDoc}
	 * Delegate events to the collection of managed listeners.
	 */
	@Override
	public void onText(Text text) {
		for (int i=0; i < listeners.size(); i++) {
			listeners.get(i).onText(text);
		}
	}
	
	/**
	 * Retrieve the current collection of managed listeners.
	 * @return The collection of managed listeners.
	 */
	protected List<NodeListener> getListeners() {
		return Collections.unmodifiableList(listeners);
	}

}
