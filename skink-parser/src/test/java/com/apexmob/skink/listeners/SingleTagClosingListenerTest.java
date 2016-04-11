package com.apexmob.skink.listeners;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.apexmob.skink.DefaultParser;
import com.apexmob.skink.MockNodeListener;
import com.apexmob.skink.NodeListener;
import com.apexmob.skink.Parser;
import com.apexmob.skink.ParsingTest;

public class SingleTagClosingListenerTest extends ParsingTest {
	
	private Parser parser = null;
	
	@Rule
    public ExpectedException thrown= ExpectedException.none();
	
	@Before
	public void setUp() {
		parser = new DefaultParser();
	}
	
	@After
	public void tearDown() {
		parser = null;
	}
	
	@Test
	public void throwsIllegalArgumentExceptionWhenListenerIsNull() {
		thrown.expect(IllegalArgumentException.class);
		
		new SingleTagClosingListener((NodeListener)null);
	}
	
	@Test
	public void throwsIllegalArgumentExceptionWhenListenerCollectionIsNull() {
		thrown.expect(IllegalArgumentException.class);
		
		new SingleTagClosingListener((Collection<NodeListener>)null);
	}
	
	@Test
	public void addTagThrowsIllegalArgumentExceptionWhenNameIsNull() {
		thrown.expect(IllegalArgumentException.class);
		
		new SingleTagClosingListener().addTag(null);
	}
	
	@Test
	public void cleansASingleUnclosedElement() throws IOException {
		String content = "<div><meta a=\"b\"></div>";
		
		MockNodeListener mock = new MockNodeListener();
		SingleTagClosingListener listener = new SingleTagClosingListener(mock);
		listener.addTag("meta");
		
		parser.registerListener(listener);
		parse(content);
		
		assertStartElement(mock, 0, "div");
		
		assertStartElement(mock, 1, "meta");
		assertStartElementAttribute(mock, 1, "a", "b");
		
		assertEndElement(mock, 2, "meta");
		
		assertEndElement(mock, 3, "div");
	}
	
	@Test
	public void cleansASingleUnclosedElementWhenSetupFromDefaultConstructor() throws IOException {
		String content = "<div><meta a=\"b\"></div>";
		
		MockNodeListener mock = new MockNodeListener();
		SingleTagClosingListener listener = new SingleTagClosingListener();
		listener.addListener(mock);
		listener.addTag("meta");
		
		parser.registerListener(listener);
		parse(content);
		
		assertStartElement(mock, 0, "div");
		
		assertStartElement(mock, 1, "meta");
		assertStartElementAttribute(mock, 1, "a", "b");
		
		assertEndElement(mock, 2, "meta");
		
		assertEndElement(mock, 3, "div");
	}
	
	@Test
	public void cleansASingleUnclosedElementWhenSetupFromCollectionConstructor() throws IOException {
		String content = "<div><meta a=\"b\"></div>";
		
		MockNodeListener mock = new MockNodeListener();
		List<NodeListener> listeners = new ArrayList<NodeListener>();
		listeners.add(mock);
		
		SingleTagClosingListener listener = new SingleTagClosingListener(listeners);
		listener.addTag("meta");
		
		parser.registerListener(listener);
		parse(content);
		
		assertStartElement(mock, 0, "div");
		
		assertStartElement(mock, 1, "meta");
		assertStartElementAttribute(mock, 1, "a", "b");
		
		assertEndElement(mock, 2, "meta");
		
		assertEndElement(mock, 3, "div");
	}
	
	@Test
	public void cleansConsecutiveUnclosedElements() throws IOException {
		String content = "<span><meta d=\"e\"><meta c=\"d\"></span>";

		MockNodeListener mock = new MockNodeListener();
		SingleTagClosingListener listener = new SingleTagClosingListener(mock);
		listener.addTag("meta");
		
		parser.registerListener(listener);
		parse(content);
		
		assertStartElement(mock, 0, "span");
		
		assertStartElement(mock, 1, "meta");
		assertStartElementAttribute(mock, 1, "d", "e");
		
		assertEndElement(mock, 2, "meta");
		
		assertStartElement(mock, 3, "meta");
		assertStartElementAttribute(mock, 3, "c", "d");
		
		assertEndElement(mock, 4, "meta");
		
		assertEndElement(mock, 5, "span");
	}
	
	@Test
	public void cleansTwoUnclosedElementOfDifferentNames() throws IOException {
		String content = "<div><meta d=\"e\"><span c=\"d\"></div>";

		MockNodeListener mock = new MockNodeListener();
		SingleTagClosingListener listener = new SingleTagClosingListener(mock);
		listener.addTag("meta");
		listener.addTag("span");
		
		parser.registerListener(listener);
		parse(content);
		
		assertStartElement(mock, 0, "div");
		
		assertStartElement(mock, 1, "meta");
		assertStartElementAttribute(mock, 1, "d", "e");
		
		assertEndElement(mock, 2, "meta");
		
		assertStartElement(mock, 3, "span");
		assertStartElementAttribute(mock, 3, "c", "d");
		
		assertEndElement(mock, 4, "span");
		
		assertEndElement(mock, 5, "div");
	}
	
	@Test
	public void validXmlFlowsThroughUnaltered() throws IOException {
		String content = "<div><meta a=\"b\"></meta></div>";
		
		MockNodeListener mock = new MockNodeListener();
		
		SingleTagClosingListener listener = new SingleTagClosingListener(mock);
		listener.addTag("meta");
		
		parser.registerListener(listener);
		parse(content);
		
		assertStartElement(mock, 0, "div");
		
		assertStartElement(mock, 1, "meta");
		assertStartElementAttribute(mock, 1, "a", "b");
		
		assertEndElement(mock, 2, "meta");
		
		assertEndElement(mock, 3, "div");
		
	}
	
	@Test
	public void test() throws IOException {
		String content = "<div><meta a=\"b\"></span></div>";
		
		MockNodeListener mock = new MockNodeListener();
		
		SingleTagClosingListener listener = new SingleTagClosingListener(mock);
		listener.addTag("meta");
		
		parser.registerListener(listener);
		parse(content);
		
		assertStartElement(mock, 0, "div");
		
		assertStartElement(mock, 1, "meta");
		assertStartElementAttribute(mock, 1, "a", "b");
		
		assertEndElement(mock, 2, "meta");
		
		assertEndElement(mock, 3, "span");
		
		assertEndElement(mock, 4, "div");
		
	}
	
	private InputStream buildInputStream(String str) {
		return new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8));
	}
	
	private void parse(String str) throws IOException {
		parser.parse(buildInputStream(str));
	}

}
