package com.apexmob.skink.listeners;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.apexmob.skink.EndElement;
import com.apexmob.skink.NodeListener;
import com.apexmob.skink.StartElement;
import com.apexmob.skink.Text;

public class NodeListenerWrapper implements NodeListener {
	
	private final List<NodeListener> listeners = new ArrayList<NodeListener>(); 
	
	public NodeListenerWrapper() {
	}
	
	public NodeListenerWrapper(NodeListener listener) {
		if (listener == null) {
			throw new IllegalArgumentException("The listener provided is null");
		}
		listeners.add(listener);
	}
	
	public NodeListenerWrapper(Collection<NodeListener> listeners) {
		if (listeners == null) {
			throw new IllegalArgumentException("The listener collection provided is null");
		}
		this.listeners.addAll(listeners);
	}
	
	public void addListener(NodeListener listener) {
		listeners.add(listener);
	}
	
	public void onStartElement(StartElement start) {
		for (int i=0; i < listeners.size(); i++) {
			listeners.get(i).onStartElement(start);
		}
	}
	
	
	public void onEndElement(EndElement end) {
		for (int i=0; i < listeners.size(); i++) {
			listeners.get(i).onEndElement(end);
		}
	}
	
	
	public void onText(Text text) {
		for (int i=0; i < listeners.size(); i++) {
			listeners.get(i).onText(text);
		}
	}
	
	protected List<NodeListener> getListeners() {
		return Collections.unmodifiableList(listeners);
	}

}
