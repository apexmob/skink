package com.apexmob.skink;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

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
	 * Parse the contents of the provided input stream and character set.
	 * @param in The input stream to  parse.
	 * @param charSet The character set for the content.
	 * @throws IOException if an exception occurs during reading of the stream.
	 */
	public void parse(InputStream in, Charset charSet) throws IOException;
	
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
