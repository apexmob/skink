package com.apexmob.skink;

/**
 * An event listener that listens for StartElement events that occur during the processing of a document tree.
 */
public interface OnStartElementListener {
	
	/**
	 * Perform functionality when a StartElement event occurs.
	 * @param start The StartElement.
	 */
	public void onStartElement(StartElement start);

}
