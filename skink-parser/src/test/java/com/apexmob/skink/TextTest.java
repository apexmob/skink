package com.apexmob.skink;

import static org.junit.Assert.assertSame;

import org.junit.Test;


public class TextTest {

	@Test
	public void typeIsText() {
		assertSame(NodeType.TEXT, new Text(new StringBuilder()).getType());
	}
}
