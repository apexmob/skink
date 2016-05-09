package com.apexmob.skink;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * The DefaultParser is the default implementation of the Parser interface.  It also implements
 * the DataListener interface so that it can listen for DataComplete events.
 */
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
	
	/**
	 * Construct a new DefaultParser with a DefaultNodeManager.
	 */
	public DefaultParser() {
		this(new DefaultNodeManager());
	}
	
	/**
	 * Construct a new DefaultParser with the provided NodeManager.
	 * @param nodeManager The NodeManager to use within the parser.
	 * @throws IllegalArgumentException if the node manager provided is null.
	 */
	public DefaultParser(NodeManager nodeManager) {
		if (nodeManager == null) {
			throw new IllegalArgumentException("The NodeManager provided is null");
		}
		this.nodeManager = nodeManager;
	}
	
	/**
	 * Retrieve the NodeManager used within the parser.
	 * @return The NodeManager.
	 */
	private NodeManager getNodeManager() {
		return nodeManager;
	}
	
	/**
	 * {@inheritDoc}
	 * @throws IllegalArgumentException if the input stream provided is null.
	 */
	public void parse(InputStream in) throws IOException {
		this.parse(in, Charset.defaultCharset());
	}
	
	/**
	 * {@inheritDoc}
	 * @throws IllegalArgumentException if the input stream provided is null.
	 * @throws IllegalArgumentException if the character set provided is null.
	 */
	public void parse(InputStream in, Charset charSet) throws IOException {
		if (in == null) {
			throw new IllegalArgumentException("The input stream provided is null");
		}
		if (charSet == null) {
			throw new IllegalArgumentException("The character set provided is null");
		}
		BufferedReader reader = new BufferedReader(new InputStreamReader(in, charSet));
		
		int ch;
		while ((ch = reader.read()) != -1) {
			if (isReadCompleted) {
				break;
			}
			updateParseStateForCharacter((char) ch);
		}
	}
	
	/**
	 * Update the state based on the provided character
	 * @param ch The next character.
	 */
	private void updateParseStateForCharacter(char ch) {
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

	/**
	 * {@inheritDoc}
	 */
	public void onData(Data evt) {
		//do nothing
	}

	/**
	 * {@inheritDoc}
	 * Stops parsing when received.
	 */
	public void onDataComplete(DataComplete evt) {
		isReadCompleted = true;
	}

	/**
	 * {@inheritDoc}
	 * De-registers listener when received.
	 */
	public void onListenerComplete(ListenerComplete evt) {
		if (evt == null) {
			throw new IllegalArgumentException("The event provided is null");
		}
		getNodeManager().deregisterListener(evt.getListener());
	}

	/**
	 * {@inheritDoc}
	 * @throws IllegalArgumentException if the listener provided is null.
	 */
	@Override
	public void registerListener(NodeListener listener) {
		getNodeManager().registerListener(listener);
	}

	/**
	 * {@inheritDoc}
	 * @throws IllegalArgumentException if the listener provided is null.
	 */
	@Override
	public void deregisterListener(NodeListener listener) {
		getNodeManager().deregisterListener(listener);
	}

}
