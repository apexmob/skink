package com.apexmob.skink.data;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.apexmob.skink.DataManager;
import com.apexmob.skink.DefaultDataManager;

public class AbstractDataListenerTest {

	@Rule
	public ExpectedException thrown= ExpectedException.none();
	
	@Test
	public void throwsIllegalArgumentExceptionWhenDataManagerIsNull() {
		thrown.expect(IllegalArgumentException.class);
		
		new MockDataListener(null);
	}
	
	@Test
	public void canCreateNullDataManagerWithEmptyconstructor() {
		MockDataListener listener = new MockDataListener();
		
		assertFalse(listener.hasDataManager());
		assertNull(listener.getDataManager());
	}
	
	@Test
	public void canCreateInstanceWithDataManager() {
		DataManager dataManager = new DefaultDataManager();
		MockDataListener listener = new MockDataListener(dataManager);
		
		assertTrue(listener.hasDataManager());
		assertSame(dataManager, listener.getDataManager());
	}
	
	private static class MockDataListener extends AbstractDataListener {

		protected MockDataListener(DataManager dataEventManager) {
			super(dataEventManager);
		}
		
		protected MockDataListener() {
			super();
		}
		
	}
}
