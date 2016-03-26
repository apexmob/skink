package com.apexmob.skink;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class DefaultNodeManagerTest extends ParsingTest {
	
	private DefaultNodeManager mgr = null;
	private MockNodeListener mock1 = null;
	private MockNodeListener mock2 = null;
	
	private StartElement start = null;
	private EndElement end = null;
	private Text text = null;
	
	@Rule
	public ExpectedException thrown= ExpectedException.none();
	
	@Before
	public void setUp() {
		mgr = new DefaultNodeManager();
		
		mock1 = new MockNodeListener();
		mock2 = new MockNodeListener();
		
		start = buildStartElement("<div>");
		end = buildEndElement("</div>");
		text = buildText("text");
	}
	
	@After
	public void tearDown() {
		mgr = null;
		
		mock1 = null;
		mock2 = null;
		
		start = null;
		end = null;
		text = null;
	}
	
	
	@Test
	public void throwsIllegalArgumentExceptionRegisterListenerIsNull() {
		thrown.expect(IllegalArgumentException.class);
		
		mgr.registerListener(null);
	}
	
	@Test
	public void throwsIllegalArgumentExceptionDeregisterListenerIsNull() {
		thrown.expect(IllegalArgumentException.class);
		
		mgr.deregisterListener(null);
	}
	
	@Test
	public void throwsIllegalArgumentExceptionWhenStartElementIsNull() {
		thrown.expect(IllegalArgumentException.class);
		
		mgr.startElement(null);
	}
	
	@Test
	public void throwsIllegalArgumentExceptionWhenEndElementIsNull() {
		thrown.expect(IllegalArgumentException.class);
		
		mgr.endElement(null);
	}
	
	@Test
	public void throwsIllegalArgumentExceptionWhenTextIsNull() {
		thrown.expect(IllegalArgumentException.class);
		
		mgr.text(null);
	}
	
	@Test
	public void managerWorksWithNoListeners() {
		mgr.startElement(start);
		mgr.endElement(end);
		mgr.text(text);
	}
	
	@Test
	public void managerWorksWithOneListener() {
		mgr.registerListener(mock1);
		
		mgr.startElement(start);
		mgr.text(text);
		mgr.endElement(end);
		
		assertEquals(3, mock1.getNodeStack().size());
		assertStartElement(mock1, 0, "div");
		assertText(mock1, 1, "text");
		assertEndElement(mock1, 2, "div");
	}
	
	@Test
	public void managerWorksWithTwoListeners() {
		mgr.registerListener(mock1);
		mgr.registerListener(mock2);
		
		mgr.startElement(start);
		mgr.text(text);
		mgr.endElement(end);
		
		assertEquals(3, mock1.getNodeStack().size());
		assertStartElement(mock1, 0, "div");
		assertText(mock1, 1, "text");
		assertEndElement(mock1, 2, "div");
		
		assertEquals(3, mock2.getNodeStack().size());
		assertStartElement(mock2, 0, "div");
		assertText(mock2, 1, "text");
		assertEndElement(mock2, 2, "div");
	}
	
	@Test
	public void deregisterListenerRemovesListener() {
		mgr.registerListener(mock1);
		mgr.registerListener(mock2);
		mgr.deregisterListener(mock1);
		
		mgr.startElement(start);
		mgr.text(text);
		mgr.endElement(end);
		
		assertEquals(0, mock1.getNodeStack().size());
		
		assertEquals(3, mock2.getNodeStack().size());
		assertStartElement(mock2, 0, "div");
		assertText(mock2, 1, "text");
		assertEndElement(mock2, 2, "div");
	}
	
	@Test
	public void throwsIllegalArgumentExceptionWhenDeregisteringAnUnregisteredListener() {
		thrown.expect(IllegalArgumentException.class);
		
		mgr.deregisterListener(mock1);
	}
	
	@Test
	public void listenerThrowingExceptionDoesNotStopStartElementListeners() {
		mgr.registerListener(new ExceptionThrowingListener());
		mgr.registerListener(mock1);
		
		mgr.startElement(start);
		
		assertEquals(1, mock1.getNodeStack().size());
		assertStartElement(mock1, 0, "div");
	}
	
	@Test
	public void listenerThrowingExceptionDoesNotStopEndElementListeners() {
		mgr.registerListener(new ExceptionThrowingListener());
		mgr.registerListener(mock1);
		
		mgr.endElement(end);
		
		assertEquals(1, mock1.getNodeStack().size());
		assertEndElement(mock1, 0, "div");
	}
	
	@Test
	public void listenerThrowingExceptionDoesNotStopTextListeners() {
		mgr.registerListener(new ExceptionThrowingListener());
		mgr.registerListener(mock1);
		
		mgr.text(text);
		
		assertEquals(1, mock1.getNodeStack().size());
		assertText(mock1, 0, "text");
	}
	
	private static class ExceptionThrowingListener implements NodeListener {

		public void onStartElement(StartElement start) {
			throw new RuntimeException();
		}

		public void onText(Text text) {
			throw new RuntimeException();
		}

		public void onEndElement(EndElement end) {
			throw new RuntimeException();
		}
		
	}
	
}
