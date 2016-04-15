package com.apexmob.skink.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.apexmob.skink.EndElement;
import com.apexmob.skink.StartElement;
import com.apexmob.skink.Text;

/**
 * The DebugNodeListener logs the contents of all events at the debug level.
 */
public class DebugNodeListener extends AbstractNodeListener {
	
	private static final Logger logger = LoggerFactory.getLogger(DebugNodeListener.class);

	/**
	 * {@inheritDoc}
	 * Log the contents of the event at debug level.
	 */
	public void onStartElement(StartElement start) {
		logger.debug(start.getContent());
	}

	/**
	 * {@inheritDoc}
	 * Log the contents of the event at debug level.
	 */
	public void onText(Text text) {
		logger.debug(text.getContent());
	}

	/**
	 * {@inheritDoc}
	 * Log the contents of the event at debug level.
	 */
	public void onEndElement(EndElement end) {
		logger.debug(end.getContent());
	}

}
