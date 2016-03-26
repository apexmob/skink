package com.apexmob.skink;

import java.io.IOException;
import java.io.InputStream;

public class MockParser implements Parser {
	
	private StartElement startElement = null;
	private EndElement endElement = null;
	private Text text = null;

	public void parse(InputStream in) throws IOException {

	}

	public void insert(StartElement start) {
		startElement = start;
	}

	public void insert(EndElement end) {
		endElement = end;
	}

	public void insert(Text text) {
		this.text = text;
	}

	public StartElement getStartElement() {
		return startElement;
	}
	
	public EndElement getEndElement() {
		return endElement;
	}
	
	public Text getText() {
		return text;
	}

	public NodeManager getNodeManager() {
		return null;
	}
}
