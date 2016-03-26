package com.apexmob.skink;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.apexmob.skink.StartElement;

public class StartElementTest extends ParsingTest {
	
	@Rule
	public ExpectedException thrown= ExpectedException.none();
	
	@Test
	public void throwsIllegalArgumentExceptionWhenBufferIsNull() {
		thrown.expect(IllegalArgumentException.class);
		
		new StartElement(null);
	}
	
	@Test
	public void canReadElementNameInStartElement() throws IOException {		
		StartElement start = buildStartElement("<div>");
		
		assertEquals("div", start.getName());
	}
	
	@Test
	public void canReadElementNameInStartEndElement() throws IOException {		
		StartElement start = buildStartElement("<div/>");
		
		assertEquals("div", start.getName());
	}
	
	@Test
	public void throwsIllegalArgumentExceptionWhenAttributeNameIsNull() {
		thrown.expect(IllegalArgumentException.class);
		
		StartElement start = new StartElement(new StringBuilder("<div a=\"b\">"));
		start.getAttribute(null);
	}
	
	@Test
	public void throwsIllegalArgumentExceptionWhenAttributeNameIsEmtpy() {
		thrown.expect(IllegalArgumentException.class);
		
		StartElement start = new StartElement(new StringBuilder("<div a=\"b\">"));
		start.getAttribute("");
	}
	
	@Test
	public void throwsIllegalArgumentExceptionWhenAttributeNameIsWhitespace() {
		thrown.expect(IllegalArgumentException.class);
		
		StartElement start = new StartElement(new StringBuilder("<div a=\"b\">"));
		start.getAttribute(" ");
	}
	
	@Test
	public void canReadSingleSimpleAttribute() throws IOException {		
		StartElement start = buildStartElement("<div a=\"b\">");
		
		assertEquals("b", start.getAttribute("a"));
	}
	
	@Test
	public void canReadSingleLongAttribute() throws IOException {
		StartElement start = buildStartElement("<div ab=\"cd\">");
		
		assertEquals("cd", start.getAttribute("ab"));
	}
	
	@Test
	public void canReadMultipleSimpleAttributes() throws IOException {		
		StartElement start = buildStartElement("<div a=\"c\" b=\"d\">");
		
		assertEquals("c", start.getAttribute("a"));
		assertEquals("d", start.getAttribute("b"));
	}
	
	@Test
	public void canReadEmptyAttributes() throws IOException {		
		StartElement start = buildStartElement("<div a=\"\">");
		
		assertEquals("", start.getAttribute("a"));
	}
	
	@Test
	public void canReadNoValueAttributesAtTheEnd() throws IOException {
		StartElement start = buildStartElement("<div a>");
		
		assertEquals("", start.getAttribute("a"));
	}
	
	@Test
	public void canReadNoValueAttributesInTheMiddle() throws IOException {
		StartElement start = buildStartElement("<div a b=\"c\">");
		
		assertEquals("", start.getAttribute("a"));
	}
	
	@Test
	public void typeIsStartElement() {
		assertSame(NodeType.START_ELEMENT, new StartElement(new StringBuilder()).getType());
	}
	
}
