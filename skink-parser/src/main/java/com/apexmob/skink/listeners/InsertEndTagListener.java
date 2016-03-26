package com.apexmob.skink.listeners;

import com.apexmob.skink.EndElement;
import com.apexmob.skink.Parser;
import com.apexmob.skink.StartElement;

public class InsertEndTagListener extends AbstractNodeListener {
	
	private final Parser parser;
	
	public InsertEndTagListener(Parser parser) {
		if (parser == null) {
			throw new IllegalArgumentException("The parser provided is null");
		}
		this.parser = parser;
	}

	@Override
	public void onStartElement(StartElement start) {
		StringBuilder buffer = new StringBuilder();
		buffer.append("</").append(start.getName()).append(">");
		parser.insert(new EndElement(buffer));
	}

}
