package com.apexmob.skink.listeners;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.apexmob.skink.Data;
import com.apexmob.skink.MockDataEventManager;
import com.apexmob.skink.ParsingTest;
import com.apexmob.skink.data.ObjectLifecycleListener;

public class ObjectLifecycleListenerTest extends ParsingTest {
	
	@Rule
	public ExpectedException thrown= ExpectedException.none();
	
	@Test
	public void throwsIllegalArgumentExceptionWhenEventManagerIsNull() {
		thrown.expect(IllegalArgumentException.class);
		
		new ObjectLifecycleListener(null, 1, 2);
	}
	
	@Test
	public void throwsIllegalStateExceptionWhenEndElementIsCalledBeforeStartElement() {
		thrown.expect(IllegalStateException.class);
		
		ObjectLifecycleListener listener = new ObjectLifecycleListener(new MockDataEventManager(), 1, 2);
		listener.onEndElement(buildEndElement("</div>"));
	}
	
	@Test
	public void throwsIllegalStateExceptionWhenEndElementNameDoesNotMatchStartElementName() {
		thrown.expect(IllegalStateException.class);
		
		ObjectLifecycleListener listener = new ObjectLifecycleListener(new MockDataEventManager(), 1, 2);
		listener.onStartElement(buildStartElement("<div>"));
		listener.onEndElement(buildEndElement("</span>"));
	}
	
	@Test
	public void managesSingleElementLifecycle() {
		MockDataEventManager evtMgr = new MockDataEventManager();
		ObjectLifecycleListener listener = new ObjectLifecycleListener(evtMgr, 1, 2);
		
		listener.onStartElement(buildStartElement("<div>"));
		listener.onEndElement(buildEndElement("</div>"));
		
		assertEquals(2, evtMgr.getDataReadEventStack().size());
		
		Data start = evtMgr.getDataReadEventStack().get(0);
		assertEquals(1, start.getFieldId());
		assertEquals("", start.getData());
		assertSame(listener, start.getSource());
		
		Data end = evtMgr.getDataReadEventStack().get(1);
		assertEquals(2, end.getFieldId());
		assertEquals("", end.getData());
		assertSame(listener, end.getSource());
	}
	
	@Test
	public void managesNestedElementLifecycle() {
		MockDataEventManager evtMgr = new MockDataEventManager();
		ObjectLifecycleListener listener = new ObjectLifecycleListener(evtMgr, 1, 2);
		
		listener.onStartElement(buildStartElement("<div>"));
		listener.onStartElement(buildStartElement("<span>"));
		listener.onEndElement(buildEndElement("</span>"));
		listener.onEndElement(buildEndElement("</div>"));
		
		assertEquals(2, evtMgr.getDataReadEventStack().size());
		
		Data start = evtMgr.getDataReadEventStack().get(0);
		assertEquals(1, start.getFieldId());
		assertEquals("", start.getData());
		assertSame(listener, start.getSource());
		
		Data end = evtMgr.getDataReadEventStack().get(1);
		assertEquals(2, end.getFieldId());
		assertEquals("", end.getData());
		assertSame(listener, end.getSource());
	}
	
	@Test
	public void managesSingleElementLifecycleWithElementNamesInDifferingCases() {
		MockDataEventManager evtMgr = new MockDataEventManager();
		ObjectLifecycleListener listener = new ObjectLifecycleListener(evtMgr, 1, 2);
		
		listener.onStartElement(buildStartElement("<div>"));
		listener.onEndElement(buildEndElement("</DIV>"));
		
		assertEquals(2, evtMgr.getDataReadEventStack().size());
		
		Data start = evtMgr.getDataReadEventStack().get(0);
		assertEquals(1, start.getFieldId());
		assertEquals("", start.getData());
		assertSame(listener, start.getSource());
		
		Data end = evtMgr.getDataReadEventStack().get(1);
		assertEquals(2, end.getFieldId());
		assertEquals("", end.getData());
		assertSame(listener, end.getSource());
	}

}
