package com.apexmob.skink.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.apexmob.skink.DataManager;
import com.apexmob.skink.Data;
import com.apexmob.skink.DefaultDataManager;
import com.apexmob.skink.MockDataEventListener;
import com.apexmob.skink.NodeListener;
import com.apexmob.skink.ParsingTest;
import com.apexmob.skink.Text;
import com.apexmob.skink.data.ReadTextListener;

public class ReadTextListenerTest extends ParsingTest {
	@Rule
	public ExpectedException thrown= ExpectedException.none();
	
	@Test
	public void fieldIdConstructorThrowsIllegalArgumentExceptionWhenManagerIsNull() {
		thrown.expect(IllegalArgumentException.class);
		
		new ReadTextListener(null, 1);
	}
	
	@Test
	public void readTextSendsDataEventWithFieldId() {
		DataManager mgr = new DefaultDataManager();
		
		NodeListener listener = new ReadTextListener(mgr, 1);
		Text text = buildText("test");
		
		MockDataEventListener dataListener = new MockDataEventListener();
		mgr.registerListener(dataListener);
		
		listener.onText(text);
		
		Data evt = dataListener.getDataRead();
		assertEquals(1, evt.getFieldId());
		assertEquals("test", evt.getData());
		assertSame(listener, evt.getSource());
	}
	
}
