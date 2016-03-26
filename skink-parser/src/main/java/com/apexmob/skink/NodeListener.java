package com.apexmob.skink;

/**
 * An event listener that can listen to one or more Node events that occur during the processing of a document tree.
 */
public interface NodeListener {
	
	/**
	 * Perform functionality when a StartElement event occurs.
	 * @param start The StartElement.
	 */
	public void onStartElement(StartElement start);
	
	/**
	 * Perform functionality when a Text event occurs.
	 * @param text The Text.
	 */
	public void onText(Text text);
	
	/**
	 * Perform functionality when a EndElement event occurs.
	 * @param end The EndElement.
	 */
	public void onEndElement(EndElement end);

}
