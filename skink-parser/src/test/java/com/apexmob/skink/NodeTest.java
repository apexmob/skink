package com.apexmob.skink;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.apexmob.skink.Node;
import com.apexmob.skink.NodeType;

import static org.junit.Assert.*;

public class NodeTest {
	
	@Rule
	public ExpectedException thrown= ExpectedException.none();

	@Test
	public void throwsIllegalArgumentExceptionWhenBufferIsNull() {
		thrown.expect(IllegalArgumentException.class);
		
		new TestNode(NodeType.TEXT, null);
	}
	
	@Test
	public void throwsIllegalArgumentExceptionWhenTypeIsNull() {
		thrown.expect(IllegalArgumentException.class);
		
		new TestNode(null, new StringBuilder());
	}
	
	@Test
	public void getContentReturnsCorrectValue() {
		StringBuilder buffer = new StringBuilder("test");
		
		Node node = new TestNode(NodeType.TEXT, buffer);
		
		assertEquals("test", node.getContent());
	}
	
	@Test
	public void getBufferReturnsTheStringBuilder() {
		StringBuilder buffer = new StringBuilder("test");
		
		Node node = new TestNode(NodeType.TEXT, buffer);
		
		assertSame(buffer, node.getBuffer());
	}
	
	private static class TestNode extends Node {

		protected TestNode(NodeType type, StringBuilder buffer) {
			super(type, buffer);
		}
		
	}

}
