package com.apexmob.skink.listeners;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import static org.junit.Assert.*;

import com.apexmob.skink.MockParser;
import com.apexmob.skink.ParsingTest;
import com.apexmob.skink.StartElement;

public class InsertEndTagListenerTest extends ParsingTest {
	
	@Rule
	public ExpectedException thrown= ExpectedException.none();
	
	@Test
	public void constructorThrowsIllegalArgumentExceptionWhenParserIsNull() {
		thrown.expect(IllegalArgumentException.class);
		
		new InsertEndTagListener(null);
	}
	
	@Test
	public void insertsEndElementForStartElement() {
		MockParser parser = new MockParser();
		InsertEndTagListener listener = new InsertEndTagListener(parser);
		
		StartElement start = buildStartElement("<div>");
		
		listener.onStartElement(start);
		
		assertEquals("</div>", parser.getEndElement().getContent());
	}
}
