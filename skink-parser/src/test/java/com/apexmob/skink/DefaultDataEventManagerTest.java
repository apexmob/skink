package com.apexmob.skink;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class DefaultDataEventManagerTest {
	
	private DefaultDataManager mgr = null;
	private MockNodeListener listener = null;
	
	private Data dataRead = null;
	private DataComplete readCompleted = null;
	private ListenerComplete listenerCompleted = null;
	
	private MockDataEventListener mock1 = null;
	private MockDataEventListener mock2 = null;
	
	@Rule
	public ExpectedException thrown= ExpectedException.none();
	
	
	@Before
	public void setUp() {
		mgr = new DefaultDataManager();
		listener = new MockNodeListener();
		
		dataRead = new Data(1, "value", null);
		readCompleted = new DataComplete();
		listenerCompleted = new ListenerComplete(listener);
		
		mock1 = new MockDataEventListener();
		mock2 = new MockDataEventListener();
	}
	
	@After
	public void tearDown() {
		mgr = null;
		listener = null;
		
		dataRead = null;
		readCompleted = null;
		listenerCompleted = null;
		
		mock1 = null;
		mock2 = null;
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
	public void throwsIllegalArgumentExceptionDataReadEventIsNull() {
		thrown.expect(IllegalArgumentException.class);
		
		mgr.data(null);
	}
	
	@Test
	public void throwsIllegalArgumentExceptionListenerCompletedEventIsNull() {
		thrown.expect(IllegalArgumentException.class);
		
		mgr.listenerComplete(null);
	}
	
	@Test
	public void throwsIllegalArgumentExceptionReadCompletedEventIsNull() {
		thrown.expect(IllegalArgumentException.class);
		
		mgr.dataComplete(null);
	}
	
	@Test
	public void managerWorksWithNoListeners() {
		mgr.data(dataRead);
		mgr.dataComplete(readCompleted);
		mgr.listenerComplete(listenerCompleted);
	}
	
	@Test
	public void managerWorksWithOneListener() {
		mgr.registerListener(mock1);
		
		mgr.data(dataRead);
		mgr.dataComplete(readCompleted);
		mgr.listenerComplete(listenerCompleted);
		
		assertSame(dataRead, mock1.getDataRead());
		assertSame(readCompleted, mock1.getReadCompleted());
		assertSame(listenerCompleted, mock1.getListenerCompleted());
	}
	
	@Test
	public void managerWorksWithTwoListeners() {
		mgr.registerListener(mock1);
		mgr.registerListener(mock2);
		
		mgr.data(dataRead);
		mgr.dataComplete(readCompleted);
		mgr.listenerComplete(listenerCompleted);
		
		assertSame(dataRead, mock1.getDataRead());
		assertSame(readCompleted, mock1.getReadCompleted());
		assertSame(listenerCompleted, mock1.getListenerCompleted());
		
		assertSame(dataRead, mock2.getDataRead());
		assertSame(readCompleted, mock2.getReadCompleted());
		assertSame(listenerCompleted, mock2.getListenerCompleted());
	}
	
	@Test
	public void deregisterListenerRemovesListener() {
		mgr.registerListener(mock1);
		mgr.registerListener(mock2);
		mgr.deregisterListener(mock1);
		
		mgr.data(dataRead);
		mgr.dataComplete(readCompleted);
		mgr.listenerComplete(listenerCompleted);
		
		assertNull(mock1.getDataRead());
		assertNull(mock1.getReadCompleted());
		assertNull(mock1.getListenerCompleted());
		
		assertSame(dataRead, mock2.getDataRead());
		assertSame(readCompleted, mock2.getReadCompleted());
		assertSame(listenerCompleted, mock2.getListenerCompleted());
	}
	
	@Test
	public void throwsIllegalArgumentExceptionWhenDeregisteringAnUnregisteredListener() {
		thrown.expect(IllegalArgumentException.class);
		
		mgr.deregisterListener(mock1);
	}
	
	@Test
	public void listenerThrowingExceptionDoesNotStopReadCompletedListeners() {
		mgr.registerListener(new ExceptionThrowingListener());
		mgr.registerListener(mock1);
		
		DataComplete evt = new DataComplete();
		
		mgr.dataComplete(evt);
		
		assertSame(evt, mock1.getReadCompleted());
	}
	
	@Test
	public void listenerThrowingExceptionDoesNotStopDataReadListeners() {
		mgr.registerListener(new ExceptionThrowingListener());
		mgr.registerListener(mock1);
		
		Data evt = new Data(1, "", new MockNodeListener());
		
		mgr.data(evt);
		
		assertSame(evt, mock1.getDataRead());
	}
	
	@Test
	public void listenerThrowingExceptionDoesNotStopListenerCompletedListeners() {
		mgr.registerListener(new ExceptionThrowingListener());
		mgr.registerListener(mock1);
		
		ListenerComplete evt = new ListenerComplete(new MockNodeListener());
		
		mgr.listenerComplete(evt);
		
		assertSame(evt, mock1.getListenerCompleted());
	}
	
	private static class ExceptionThrowingListener implements DataListener {

		public void onData(Data evt) {
			throw new RuntimeException();
		}

		public void onDataComplete(DataComplete evt) {
			throw new RuntimeException();
		}

		public void onListenerComplete(ListenerComplete evt) {
			throw new RuntimeException();
		}
		
	}
	
}
