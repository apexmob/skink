package com.apexmob.skink.listeners;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.apexmob.skink.EndElement;
import com.apexmob.skink.NodeListener;
import com.apexmob.skink.StartElement;

public class SingleTagClosingListener extends NodeListenerWrapper {

	private List<String> autoCloseNames = new ArrayList<String>();
	private String forceClosedName = null;

	public SingleTagClosingListener() {
		super();
	}
	
	public SingleTagClosingListener(Collection<NodeListener> listeners) {
		super(listeners);
	}

	public SingleTagClosingListener(NodeListener listener) {
		super(listener);
	}
	
	public void addTag(String tagName) {
		if (tagName == null) {
			throw new IllegalArgumentException("The tag name provided is null");
		}
		autoCloseNames.add(tagName.toLowerCase());
	}
	
	@Override
	public void onStartElement(StartElement start) {
		super.onStartElement(start);
		
		String elementName = start.getName();
		if (autoCloseNames.contains(elementName.toLowerCase())) {
			StringBuilder buffer = new StringBuilder();
			buffer.append("</").append(elementName).append(">");
			
			super.onEndElement(new EndElement(buffer));
			
			forceClosedName = elementName;
		} else {
			forceClosedName = null;
		}
	}

	@Override
	public void onEndElement(EndElement end) {
		if (forceClosedName == null) {
			super.onEndElement(end);
		} 
		else if (!forceClosedName.equalsIgnoreCase(end.getName())) {
			super.onEndElement(end);
			forceClosedName = null;
		}
	}
	
}
