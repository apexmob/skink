package com.apexmob.skink.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.apexmob.skink.EndElement;
import com.apexmob.skink.NodeListener;
import com.apexmob.skink.StartElement;
import com.apexmob.skink.Text;

public class DebugNodeListener implements NodeListener {
	
	private static final Logger logger = LoggerFactory.getLogger(DebugNodeListener.class);

	public void onStartElement(StartElement start) {
		logger.debug(start.getContent());
	}

	public void onText(Text text) {
		logger.debug(text.getContent());
	}

	public void onEndElement(EndElement end) {
		logger.debug(end.getContent());
	}

}
