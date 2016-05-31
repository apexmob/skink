package com.apexmob.skink;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MockNodeListener implements OnStartElementListener, OnEndElementListener, OnTextListener {
	
	public List<String> startElementNames = new ArrayList<String>();
	public List<String> endElementNames = new ArrayList<String>();
	public List<String> texts = new ArrayList<String>();
	
	public List<Node> nodes = new ArrayList<Node>();
	
	private List<String> attributesToRead = new ArrayList<String>();
	private Map<String, String> attributesRead = new HashMap<String, String>();

	public void onStartElement(StartElement start) {
		startElementNames.add(start.getName());
		nodes.add(copy(start));
		
		for (String name: attributesToRead) {
			attributesRead.put(name, start.getAttribute(name));
		}
	}
	
	public int getStartElementCount() {
		return startElementNames.size();
	}
	
	public String getStartElementName(int index) {
		String retVal = null;
		if (index < startElementNames.size()) {
			retVal = startElementNames.get(index);
		}
		return retVal;
	}
	
	public String getFirstStartElementName() {
		return getStartElementName(0);
	}
	
	public void addAttributeToRead(String name) {
		attributesToRead.add(name);
	}
	
	public String getAttribute(String name) {
		return attributesRead.get(name);
	}

	public void onEndElement(EndElement end) {
		endElementNames.add(end.getName());
		nodes.add(copy(end));
	}
	
	public int getEndElementCount() {
		return endElementNames.size();
	}
	
	public String getEndElementName(int index) {
		String retVal = null;
		if (index < endElementNames.size()) {
			retVal = endElementNames.get(index);
		}
		return retVal;
	}
	
	public String getFirstEndElementName() {
		return getEndElementName(0);
	}
	
	public int getTextCount() {
		return texts.size();
	}
	
	public String getText(int index) {
		String retVal = null;
		if (index < texts.size()) {
			retVal = texts.get(index);
		}
		return retVal;
	}
	
	public String getFirstText() {
		return getText(0);
	}

	public void onText(Text text) {
		texts.add(text.getContent());
		nodes.add(copy(text));
	}
	
	public List<Node> getNodeStack() {
		return nodes;
	}
	
	private StartElement copy(StartElement start) {
		return new StartElement(new StringBuilder(start.getContent()));
	}
	
	private EndElement copy(EndElement end) {
		return new EndElement(new StringBuilder(end.getContent()));
	}
	
	private Text copy(Text text) {
		return new Text(new StringBuilder(text.getContent()));
	}
}
