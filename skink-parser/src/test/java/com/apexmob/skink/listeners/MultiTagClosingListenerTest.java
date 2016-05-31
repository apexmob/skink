package com.apexmob.skink.listeners;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.apexmob.skink.MockNodeListener;
import com.apexmob.skink.NodeListener;
import com.apexmob.skink.ParsingTest;

public class MultiTagClosingListenerTest extends ParsingTest {
	
	@Rule
	public ExpectedException thrown= ExpectedException.none();
	
	@Test
	public void constructorThrowsIllegalArgumentExceptionWhenListIsNull() {
		thrown.expect(IllegalArgumentException.class);
		
		new MultiTagClosingListener((Collection<NodeListener>) null);
	}
	
	@Test
	public void constructorRegistersListeners() {
		MockNodeListener mock = new MockNodeListener();
		
		List<NodeListener> children = new ArrayList<NodeListener>();
		children.add(mock);
		
		MultiTagClosingListener listener = new MultiTagClosingListener(children);
		listener.onStartElement(buildStartElement("<div>"));
		listener.onEndElement(buildEndElement("</div>"));
		
		assertEquals(2, mock.getNodeStack().size());
		assertStartElement(mock, 0, "div");
		assertEndElement(mock, 1, "div");
	}
	
	@Test
	public void constructorThrowsIllegalArgumentExceptionWhenListenerIsNull() {
		thrown.expect(IllegalArgumentException.class);
		
		new MultiTagClosingListener((NodeListener) null);
	}
	
	@Test
	public void addListenerdefaultConstructor() {
		MultiTagClosingListener listener = new MultiTagClosingListener();
		
		MockNodeListener mock = new MockNodeListener();
		listener.addListener(mock);
		
		listener.onStartElement(buildStartElement("<div>"));
		listener.onEndElement(buildEndElement("</div>"));
		
		assertEquals(2, mock.getNodeStack().size());
		assertStartElement(mock, 0, "div");
		assertEndElement(mock, 1, "div");
	}
	
	@Test
	public void constructorRegistersListener() {
		MockNodeListener mock = new MockNodeListener();
		
		MultiTagClosingListener listener = new MultiTagClosingListener(mock);
		listener.onStartElement(buildStartElement("<div>"));
		listener.onEndElement(buildEndElement("</div>"));
		
		assertEquals(2, mock.getNodeStack().size());
		assertStartElement(mock, 0, "div");
		assertEndElement(mock, 1, "div");
	}
	
	@Test
	public void cleansASingleUnclosedElement() throws IOException {
		MockNodeListener mock = new MockNodeListener();
		MultiTagClosingListener listener = new MultiTagClosingListener(mock);
		
		listener.onStartElement(buildStartElement("<div>"));
		listener.onStartElement(buildStartElement("<meta a=\"b\">"));
		listener.onEndElement(buildEndElement("</div>"));
		
		assertStartElement(mock, 0, "div");
		
		assertStartElement(mock, 1, "meta");
		assertStartElementAttribute(mock, 1, "a", "b");
		
		assertEndElement(mock, 2, "meta");
		
		assertEndElement(mock, 3, "div");
	}
	
	@Test
	public void cleansMultipleLevelsOfUnclosedElements() throws IOException {
		MockNodeListener mock = new MockNodeListener();
		MultiTagClosingListener listener = new MultiTagClosingListener(mock);
		
		listener.onStartElement(buildStartElement("<span>"));
		listener.onStartElement(buildStartElement("<meta d=\"e\">"));
		listener.onStartElement(buildStartElement("<meta c=\"d\">"));
		listener.onEndElement(buildEndElement("</span>"));
		
		assertStartElement(mock, 0, "span");
		
		assertStartElement(mock, 1, "meta");
		assertStartElementAttribute(mock, 1, "d", "e");
		
		assertStartElement(mock, 2, "meta");
		assertStartElementAttribute(mock, 2, "c", "d");
		
		assertEndElement(mock, 3, "meta");
		
		assertEndElement(mock, 4, "meta");
		
		assertEndElement(mock, 5, "span");
	}
	
	@Test
	public void validXmlFlowsThroughUnaltered() throws IOException {
		MockNodeListener mock = new MockNodeListener();
		
		MultiTagClosingListener listener = new MultiTagClosingListener(mock);
		
		listener.onStartElement(buildStartElement("<div>"));
		listener.onText(buildText("test1"));
		listener.onStartElement(buildStartElement("<meta a=\"b\">"));
		listener.onText(buildText("test2"));
		listener.onEndElement(buildEndElement("</meta>"));
		listener.onText(buildText("test3"));
		listener.onEndElement(buildEndElement("</div>"));		
		
		assertEquals(7, mock.getNodeStack().size());
		assertStartElement(mock, 0, "div");
		assertText(mock, 1, "test1");
		
		assertStartElement(mock, 2, "meta");
		assertStartElementAttribute(mock, 2, "a", "b");
		assertText(mock, 3, "test2");
		
		assertEndElement(mock, 4, "meta");
		
		assertText(mock, 5, "test3");
		
		assertEndElement(mock, 6, "div");
	}
	
	@Test
	public void invalidNestedCloseElementsAreCleaned() {
		MockNodeListener mock = new MockNodeListener();
		
		MultiTagClosingListener listener = new MultiTagClosingListener(mock);
		listener.onStartElement(buildStartElement("<div>"));
		listener.onText(buildText("test1"));
		listener.onStartElement(buildStartElement("<div>"));
		listener.onText(buildText("test2"));
		listener.onEndElement(buildEndElement("</span>"));
		listener.onText(buildText("test3"));
		listener.onEndElement(buildEndElement("</div>"));
		
		assertEquals(7, mock.getNodeStack().size());
		assertStartElement(mock, 0, "div");
		assertText(mock, 1, "test1");
		
		assertStartElement(mock, 2, "div");
		assertText(mock, 3, "test2");
		
		assertEndElement(mock, 4, "div");
		
		assertText(mock, 5, "test3");
		
		assertEndElement(mock, 6, "div");
	}
	
	@Test
	public void invalidRootCloseElementsAreCleaned() {
		MockNodeListener mock = new MockNodeListener();
		
		MultiTagClosingListener listener = new MultiTagClosingListener(mock);
		listener.onStartElement(buildStartElement("<div>"));
		listener.onText(buildText("test1"));
		listener.onEndElement(buildEndElement("</span>"));
		
		assertEquals(3, mock.getNodeStack().size());
		assertStartElement(mock, 0, "div");
		assertText(mock, 1, "test1");
		
		assertEndElement(mock, 2, "div");
	}
	
	@Test
	public void extraCloseElementsAreCleaned() {
		MockNodeListener mock = new MockNodeListener();
		
		MultiTagClosingListener listener = new MultiTagClosingListener(mock);
		listener.onStartElement(buildStartElement("<div>"));
		listener.onText(buildText("test1"));
		listener.onEndElement(buildEndElement("</div>"));
		listener.onEndElement(buildEndElement("</div>"));
		
		assertEquals(3, mock.getNodeStack().size());
		assertStartElement(mock, 0, "div");
		assertText(mock, 1, "test1");
		
		assertEndElement(mock, 2, "div");
	}

}
