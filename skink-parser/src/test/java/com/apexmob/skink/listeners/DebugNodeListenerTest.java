package com.apexmob.skink.listeners;

import org.junit.Test;

import com.apexmob.skink.ParsingTest;

public class DebugNodeListenerTest extends ParsingTest {

	@Test
	public void logData() {
		DebugNodeListener listener = new DebugNodeListener();
		listener.onStartElement(buildStartElement("<div>"));
		listener.onText(buildText("text"));
		listener.onEndElement(buildEndElement("</div>"));
	}
}
