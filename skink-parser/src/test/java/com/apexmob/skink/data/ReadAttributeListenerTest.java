package com.apexmob.skink.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.apexmob.skink.Data;
import com.apexmob.skink.DataManager;
import com.apexmob.skink.DefaultDataManager;
import com.apexmob.skink.MockDataEventListener;
import com.apexmob.skink.OnStartElementListener;
import com.apexmob.skink.ParsingTest;
import com.apexmob.skink.StartElement;

public class ReadAttributeListenerTest extends ParsingTest {
	
	@Rule
	public ExpectedException thrown= ExpectedException.none();
	
	@Test
	public void fieldIdConstructorThrowsIllegalArgumentExceptionWhenManagerIsNull() {
		thrown.expect(IllegalArgumentException.class);
		
		new ReadAttributeListener(null, 1, "");
	}
	
	@Test
	public void fieldIdConstructorThrowsIllegalArgumentExceptionWhenAttributeNameIsNull() {
		thrown.expect(IllegalArgumentException.class);
		
		new ReadAttributeListener(new DefaultDataManager(), 1, null);
	}
	
	@Test
	public void readStartElementSendsAttributeDataEventWithFieldId() {
		DataManager mgr = new DefaultDataManager();
		
		OnStartElementListener listener = new ReadAttributeListener(mgr, 1, "c");
		StartElement start = buildStartElement("<div a=\"b\" c=\"d\">");
		
		MockDataEventListener dataListener = new MockDataEventListener();
		mgr.registerListener(dataListener);
		
		listener.onStartElement(start);
		
		Data evt = dataListener.getDataRead();
		assertEquals(1, evt.getFieldId());
		assertEquals("d", evt.getData());
		assertSame(listener, evt.getSource());
	}
	
	@Test
	public void readStartElementSendsNoDataWhenAttributeIsMissing() {
		DataManager mgr = new DefaultDataManager();
		
		OnStartElementListener listener = new ReadAttributeListener(mgr, 1, "c");
		StartElement start = buildStartElement("<div a=\"b\">");
		
		MockDataEventListener dataListener = new MockDataEventListener();
		mgr.registerListener(dataListener);
		
		listener.onStartElement(start);
		
		assertNull(dataListener.getDataRead());
	}
}
