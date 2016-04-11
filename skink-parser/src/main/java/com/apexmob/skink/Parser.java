package com.apexmob.skink;

import java.io.IOException;
import java.io.InputStream;

/**
 * A component that parses HTML files and issues SAX-style events for element and text nodes.
 */
public interface Parser {
	
	/**
	 * Parse the contents of the provided input stream.
	 * @param in The input stream to  parse.
	 * @throws IOException if an exception occurs during reading of the stream.
	 */
	public void parse(InputStream in) throws IOException;
	
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

}
