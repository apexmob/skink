package com.apexmob.skink;

import static org.junit.Assert.assertSame;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ListenerCompletedEventTest {
	
	@Rule
	public ExpectedException thrown= ExpectedException.none();
	
	@Test
	public void throwsIllegalArgumentExceptionWhenListerIsNull() {
		thrown.expect(IllegalArgumentException.class);
		
		new ListenerComplete(null);
	}
	
	@Test
	public void canGetListener() {
		MockNodeListener listener = new MockNodeListener();
		
		ListenerComplete evt = new ListenerComplete(listener);
		assertSame(listener, evt.getListener());
	}

}
