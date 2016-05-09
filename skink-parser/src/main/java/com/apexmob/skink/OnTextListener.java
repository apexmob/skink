package com.apexmob.skink;

/**
 * An event listener that listens for Text events that occur during the processing of a document tree.
 */
public interface OnTextListener {
	
	/**
	 * Perform functionality when a Text event occurs.
	 * @param text The Text.
	 */
	public void onText(Text text);

}
