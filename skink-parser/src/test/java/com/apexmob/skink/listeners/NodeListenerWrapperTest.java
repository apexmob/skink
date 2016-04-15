package com.apexmob.skink.listeners;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.apexmob.skink.MockNodeListener;
import com.apexmob.skink.NodeListener;
import com.apexmob.skink.ParsingTest;
import com.apexmob.skink.listeners.NodeListenerWrapper;

public class NodeListenerWrapperTest extends ParsingTest {

	MockNodeListener mock1 = null;
	MockNodeListener mock2 = null;
	NodeListenerWrapper listener = null;
	NodeListenerWrapper listener2 = null;
	
	@Rule
    public ExpectedException thrown= ExpectedException.none();
	
	@Before
	public void setUp() {
		mock1 = new  MockNodeListener();
		mock2 = new  MockNodeListener();
		listener = new NodeListenerWrapper(mock1);
		
		List<NodeListener> mocks = new ArrayList<NodeListener>();
		mocks.add(mock1);
		mocks.add(mock2);
		listener2 = new NodeListenerWrapper(mocks);
	}
	
	@After
	public void tearDown() {
		mock1 = null;
		mock2 = null;
		listener = null;
		listener2 = null;
	}
	
	@Test
	public void testSingleElementIncludeWithOneListener() {
		listener.onStartElement(buildStartElement("<div/>"));
		listener.onText(buildText("test"));
		listener.onEndElement(buildEndElement("<div/>"));
		assertEquals(1, mock1.getStartElementCount());
		assertEquals(1, mock1.getEndElementCount());
		assertEquals(1, mock1.getTextCount());
	}
	
	@Test
	public void testMultipleElementsIncludeParentWithOneListener() {
		listener.onStartElement(buildStartElement("<div/>"));
		listener.onStartElement(buildStartElement("<span/>"));
		listener.onEndElement(buildEndElement("<span/>"));
		listener.onEndElement(buildEndElement("<div/>"));
		assertEquals(2, mock1.getStartElementCount());
		assertEquals(2, mock1.getEndElementCount());
		assertEquals(0, mock1.getTextCount());
	}
	
	@Test
	public void testSingleElementIncludeWithTwoListeners() {
		listener2.onStartElement(buildStartElement("<div/>"));
		listener2.onText(buildText("test"));
		listener2.onEndElement(buildEndElement("<div/>"));
		assertEquals(1, mock1.getStartElementCount());
		assertEquals(1, mock1.getEndElementCount());
		assertEquals(1, mock1.getTextCount());
		
		assertEquals(1, mock2.getStartElementCount());
		assertEquals(1, mock2.getEndElementCount());
		assertEquals(1, mock2.getTextCount());
	}
	
	@Test
	public void testMultipleElementsIncludeParentWithTwoListeners() {
		listener2.onStartElement(buildStartElement("<div/>"));
		listener2.onStartElement(buildStartElement("<span/>"));
		listener2.onEndElement(buildEndElement("<span/>"));
		listener2.onEndElement(buildEndElement("<div/>"));
		assertEquals(2, mock1.getStartElementCount());
		assertEquals(2, mock1.getEndElementCount());
		assertEquals(0, mock1.getTextCount());
		
		assertEquals(2, mock2.getStartElementCount());
		assertEquals(2, mock2.getEndElementCount());
		assertEquals(0, mock2.getTextCount());
	}
	
	@Test
	public void removeListenerReturnsFalseWhenNoChangeMade() {
		assertFalse(listener.removeListener(null));
		assertFalse(listener.removeListener(mock2));
	}
	
	@Test
	public void removeListenerReturnsTrueWhenAChangeIsMade() {
		assertTrue(listener.removeListener(mock1));
	}
	
	@Test
	public void removeListenerRemovesTheListener() {
		assertEquals(1, listener.getListeners().size());
		assertTrue(listener.removeListener(mock1));
		assertEquals(0, listener.getListeners().size());
	}
	
	@Test
	public void removeListenerDoesNotRemoveTheListenerWhenNoChangeIsMade() {
		assertEquals(1, listener.getListeners().size());
		listener.removeListener(mock2);
		assertEquals(1, listener.getListeners().size());
	}
	
	@Test
	public void addListenerThrowsIllegalArgumentExceptionWhenListenerIsNull() {
		thrown.expect(IllegalArgumentException.class);
		
		listener.addListener(null);
	}
	
}
