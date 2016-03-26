package com.apexmob.skink;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class DefaultParser implements Parser, DataListener {
	
	//TODO parse comments or other non xml tag structures
	
	private boolean isReadCompleted = false;
	
	private boolean isElementStarted = false;
	private boolean isStartElement = false;
	private boolean isEndElement = false;
	private boolean isInAttribute = false;
	
	private char lastCharacter;
	
	private StringBuilder nodeBuffer = new StringBuilder();
	private StartElement startElement = new StartElement(nodeBuffer);
	private EndElement endElement = new EndElement(nodeBuffer);
	private Text text = new Text(nodeBuffer);

	private final NodeManager nodeManager;
	
	private boolean insertedNodesPresent = false;
	private List<Node> insertedNodes = new ArrayList<Node>();
	
	public DefaultParser() {
		this(new DefaultNodeManager());
	}
	
	public DefaultParser(NodeManager nodeManager) {
		if (nodeManager == null) {
			throw new IllegalArgumentException("The NodeManager provided is null");
		}
		this.nodeManager = nodeManager;
	}
	
	public NodeManager getNodeManager() {
		return nodeManager;
	}
	
	//TODO move Charset to input parameter
	public void parse(InputStream in) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
		
		int ch;
		while ((ch = reader.read()) != -1) {
			if (isReadCompleted) {
				break;
			}
			readCharacter((char) ch);
			if (insertedNodesPresent) {
				playInsertedNodes();
			}
		}
	}
	
	private void readCharacter(char ch) {
		if (isElementStarted) {
			nodeBuffer.append(ch);
			if (ch == '>' && !isInAttribute) {
				if (lastCharacter =='/') { //if (buffer.charAt(index-1) == '/') {
					isEndElement = true;
				}
				if (isStartElement) {
					nodeManager.startElement(startElement);
				}
				if (isEndElement) {
					nodeManager.endElement(endElement);
				}
				
				//cleanup
				startElement.clear();
				endElement.clear();
				
				isStartElement = false;
				isEndElement = false;
				isElementStarted = false;
			} else if (ch == '"') {
				isInAttribute = !isInAttribute;
			}else if (ch == '/' && lastCharacter == '<') {// buffer.charAt(index-1) == '<') {
				isEndElement = true;
				isStartElement = false;
			}
		} else {
			if (ch == '<') {
				if (nodeBuffer.length() > 0) {
					nodeManager.text(text);
					
					text.clear();
				}
				nodeBuffer.append(ch);
				
				isElementStarted = true;
				isStartElement = true;
			} else {
				nodeBuffer.append(ch);
			}
		}
		
		lastCharacter = ch;
	}

	public void onData(Data evt) {
		//do nothing
	}

	public void onDataComplete(DataComplete evt) {
		isReadCompleted = true;
	}

	public void onListenerComplete(ListenerComplete evt) {
		if (evt == null) {
			throw new IllegalArgumentException("The event provided is null");
		}
		getNodeManager().deregisterListener(evt.getListener());
	}
	
	public void insert(EndElement end) {
		if (end == null) {
			throw new IllegalArgumentException("The end element provided is null");
		}
		insertedNodesPresent = true;
		insertedNodes.add(end);
	}
	
	public void insert(StartElement start) {
		if (start == null) {
			throw new IllegalArgumentException("The start element provided is null");
		}
		insertedNodesPresent = true;
		insertedNodes.add(start);
	}
	
	public void insert(Text text) {
		if (text == null) {
			throw new IllegalArgumentException("The text provided is null");
		}
		insertedNodesPresent = true;
		insertedNodes.add(text);
	}
	
	private void playInsertedNodes() {
		Node node = null;
		for (int i=0; i < insertedNodes.size(); i++) {
			node = insertedNodes.get(i);
			switch (node.getType()) {
			case END_ELEMENT:
				nodeManager.endElement((EndElement) node);
				break;
			case START_ELEMENT:
				nodeManager.startElement((StartElement) node);
				break;
			case TEXT:
				nodeManager.text((Text) node);
				break;
			}
		}
		
		insertedNodes.clear();
		insertedNodesPresent = false;
	}

}
