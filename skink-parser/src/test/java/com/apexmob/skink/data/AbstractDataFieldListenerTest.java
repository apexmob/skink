package com.apexmob.skink.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.apexmob.skink.DataManager;
import com.apexmob.skink.Data;
import com.apexmob.skink.MockDataEventManager;

public class AbstractDataFieldListenerTest {
	
	@Rule
	public ExpectedException thrown= ExpectedException.none();
	
	@Test
	public void throwsIllegalArgumentExceptionWhenEventManagerIsNull() {
		thrown.expect(IllegalArgumentException.class);
		
		new MockDataFieldListener(null, 1);
	}
	
	@Test
	public void throwsIllegalArgumentExceptionWhenFieldValueIsNull() {
		thrown.expect(IllegalArgumentException.class);
		
		new MockDataFieldListener(new MockDataEventManager(), 1).sendDataReadEvent(null);
	}
	
	@Test
	public void sendsDataReadEvent() {
		MockDataEventManager dataEventManager = new MockDataEventManager();
		
		MockDataFieldListener mock = new MockDataFieldListener(dataEventManager, 1);
		mock.sendDataReadEvent("test");
		
		Data evt = dataEventManager.getDataReadEventStack().get(0); 
		assertEquals(1, evt.getFieldId());
		assertEquals("test", evt.getData());
		assertSame(mock, evt.getSource());
	}

	private static class MockDataFieldListener extends AbstractDataFieldListener {

		protected MockDataFieldListener(DataManager dataEventManager, int fieldId) {
			super(dataEventManager, fieldId);
		}
		
	}
}
