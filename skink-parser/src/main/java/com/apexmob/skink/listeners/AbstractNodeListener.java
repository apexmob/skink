package com.apexmob.skink.listeners;

import com.apexmob.skink.EndElement;
import com.apexmob.skink.NodeListener;
import com.apexmob.skink.OnEndElementListener;
import com.apexmob.skink.OnStartElementListener;
import com.apexmob.skink.OnTextListener;
import com.apexmob.skink.StartElement;
import com.apexmob.skink.Text;

/**
 * The AbstractNodeListener is an empty implementation of the NodeListener interface. It is
 * recommended that all listeners extend it, overriding methods as necessary, to ensure forward
 * compatibility with handling any new node events that might be added.
 */
public abstract class AbstractNodeListener implements OnStartElementListener, OnEndElementListener, OnTextListener {
	
	/**
	 * Construct a new instance.
	 */
	protected AbstractNodeListener() {
		
	}

	/**
	 * {@inheritDoc}
	 */
	public void onStartElement(StartElement start) {
		//do nothing
	}

	/**
	 * {@inheritDoc}
	 */
	public void onText(Text text) {
		//do nothing
	}

	/**
	 * {@inheritDoc}
	 */
	public void onEndElement(EndElement end) {
		//do nothing
	}

}
