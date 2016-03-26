package com.apexmob.skink;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ElementTest {
	
	@Rule
	public ExpectedException thrown= ExpectedException.none();

	@Test
	public void throwsIllegalArgumentExceptionWhenBufferIsNull() {
		thrown.expect(IllegalArgumentException.class);
		
		new TestElement(NodeType.START_ELEMENT, null);
	}

	@Test
	public void throwsIllegalArgumentExceptionWhenTypeIsNull() {
		thrown.expect(IllegalArgumentException.class);
		
		new TestElement(null, new StringBuilder());
	}
	
	@Test
	public void throwsIllegalArgumentExceptionWhenTypeIsInvalid() {
		thrown.expect(IllegalArgumentException.class);
		
		new TestElement(NodeType.TEXT, new StringBuilder());
	}
	
	@Test
	public void getContentReturnsCorrectValue() {
		StringBuilder buffer = new StringBuilder("test");
		
		Element element = new TestElement(NodeType.END_ELEMENT, buffer);
		
		assertEquals("test", element.getContent());
	}
	
	@Test
	public void getBufferReturnsTheStringBuilder() {
		StringBuilder buffer = new StringBuilder("test");
		
		Node node = new TestElement(NodeType.START_ELEMENT, buffer);
		
		assertSame(buffer, node.getBuffer());
	}
	
	public static class TestElement extends Element {

		protected TestElement(NodeType type, StringBuilder content) {
			super(type, content);
		}

		@Override
		protected String parseName() {
			return null;
		}
		
	}

}
