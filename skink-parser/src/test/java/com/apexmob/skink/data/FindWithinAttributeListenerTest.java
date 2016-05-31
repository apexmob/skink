package com.apexmob.skink.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import java.util.regex.Pattern;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.apexmob.skink.DataManager;
import com.apexmob.skink.Data;
import com.apexmob.skink.DefaultDataManager;
import com.apexmob.skink.MockDataEventListener;
import com.apexmob.skink.NodeListener;
import com.apexmob.skink.OnStartElementListener;
import com.apexmob.skink.ParsingTest;
import com.apexmob.skink.StartElement;

public class FindWithinAttributeListenerTest extends ParsingTest {
	
	@Rule
	public ExpectedException thrown= ExpectedException.none();
	
	@Test
	public void fieldIdConstructorThrowsIllegalArgumentExceptionWhenManagerIsNull() {
		thrown.expect(IllegalArgumentException.class);
		
		new FindWithinAttributeListener(null, 1, "", Pattern.compile(""));
	}
	
	@Test
	public void fieldIdConstructorThrowsIllegalArgumentExceptionWhenAttributeNameIsNull() {
		thrown.expect(IllegalArgumentException.class);
		
		new FindWithinAttributeListener(new DefaultDataManager(), 1, null, Pattern.compile(""));
	}
	
	@Test
	public void fieldIdConstructorThrowsIllegalArgumentExceptionWhenPatternIsNull() {
		thrown.expect(IllegalArgumentException.class);
		
		new FindWithinAttributeListener(new DefaultDataManager(), 1, "", null);
	}
	
	
	
	@Test
	public void readStartElementSendsPatternedAttributeDataEventWithFieldId() {
		DataManager mgr = new DefaultDataManager();
		
		OnStartElementListener listener = new FindWithinAttributeListener(mgr, 1, "c", Pattern.compile("(es)"));
		StartElement start = buildStartElement("<div a=\"b\" c=\"desk\">");
		
		MockDataEventListener dataListener = new MockDataEventListener();
		mgr.registerListener(dataListener);
		
		listener.onStartElement(start);
		
		Data evt = dataListener.getDataRead();
		assertEquals(1, evt.getFieldId());
		assertEquals("es", evt.getData());
		assertSame(listener, evt.getSource());
	}
	
	@Test
	public void readNoPatternedData() {
		DataManager mgr = new DefaultDataManager();
		
		OnStartElementListener listener = new FindWithinAttributeListener(mgr, 1, "c", Pattern.compile("(es)"));
		StartElement start = buildStartElement("<div a=\"b\" c=\"abc\">");
		
		MockDataEventListener dataListener = new MockDataEventListener();
		mgr.registerListener(dataListener);
		
		listener.onStartElement(start);
		
		assertNull(dataListener.getDataRead());

	}
	
}
