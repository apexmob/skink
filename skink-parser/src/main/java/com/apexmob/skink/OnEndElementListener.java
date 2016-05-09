package com.apexmob.skink;

/**
 * An event listener that listens for EndElement events that occur during the processing of a document tree.
 */
public interface OnEndElementListener {
	
	/**
	 * Perform functionality when a EndElement event occurs.
	 * @param end The EndElement.
	 */
	public void onEndElement(EndElement end);

}
