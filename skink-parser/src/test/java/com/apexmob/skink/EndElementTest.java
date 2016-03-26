package com.apexmob.skink;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.apexmob.skink.EndElement;

public class EndElementTest extends ParsingTest {
	
	@Rule
	public ExpectedException thrown= ExpectedException.none();
	
	@Test
	public void throwsIllegalArgumentExceptionWhenBufferIsNull() {
		thrown.expect(IllegalArgumentException.class);
		
		new EndElement(null);
	}
	
	@Test
	public void canReadElementNameInEndElement() throws IOException {		
		EndElement end = buildEndElement("</div>");
		
		assertEquals("div", end.getName());
	}
	
	@Test
	public void canReadElementNameInStartEndElement() throws IOException {		
		EndElement end = buildEndElement("<div/>");
		
		assertEquals("div", end.getName());
	}
	
	@Test
	public void canReadElementNameInStartEndElementWithAttributes() throws IOException {		
		EndElement end = buildEndElement("<div a=\"b\"/>");
		
		assertEquals("div", end.getName());
	}
	
	@Test
	public void typeIsEndElement() {
		assertSame(NodeType.END_ELEMENT, new EndElement(new StringBuilder()).getType());
	}

}
