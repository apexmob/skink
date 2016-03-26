package com.apexmob.skink.data;

import static org.junit.Assert.assertSame;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.apexmob.skink.DataManager;
import com.apexmob.skink.Data;
import com.apexmob.skink.MockDataEventManager;

public class AbstractDataNodeListenerTest {
	
	@Rule
	public ExpectedException thrown= ExpectedException.none();
	
	@Test
	public void throwsIllegalArgumentExceptionWhenEventManagerIsNull() {
		thrown.expect(IllegalArgumentException.class);
		
		new MockDataNodeListener(null);
	}
	
	@Test
	public void throwsIllegalArgumentExceptionWhenEventIsNull() {
		thrown.expect(IllegalArgumentException.class);
		
		new MockDataNodeListener(new MockDataEventManager()).send(null);
	}
	
	@Test
	public void sendsDataReadEvent() {
		MockDataEventManager dataEventManager = new MockDataEventManager();
		
		
		MockDataNodeListener mock = new MockDataNodeListener(dataEventManager);
		Data evt = new Data(1, "", mock);
		
		mock.send(evt);
		
		assertSame(evt, dataEventManager.getDataReadEventStack().get(0));
	}

	private static class MockDataNodeListener extends AbstractDataNodeListener {

		protected MockDataNodeListener(DataManager dataEventManager) {
			super(dataEventManager);
		}
		
	}
}
