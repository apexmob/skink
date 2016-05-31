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
import com.apexmob.skink.OnTextListener;
import com.apexmob.skink.ParsingTest;
import com.apexmob.skink.Text;

public class FindWithinTextListenerTest extends ParsingTest {
	
	@Rule
	public ExpectedException thrown= ExpectedException.none();
	
	@Test
	public void fieldIdConstructorThrowsIllegalArgumentExceptionWhenManagerIsNull() {
		thrown.expect(IllegalArgumentException.class);
		
		new FindWithinTextListener(null, 1, Pattern.compile(""));
	}
	
	@Test
	public void fieldIdConstructorThrowsIllegalArgumentExceptionWhenPatternIsNull() {
		thrown.expect(IllegalArgumentException.class);
		
		new FindWithinTextListener(new DefaultDataManager(), 1, null);
	}
	
	@Test
	public void readTextSendsDataEventWithFieldId() {
		DataManager mgr = new DefaultDataManager();
		
		OnTextListener listener = new FindWithinTextListener(mgr, 1, Pattern.compile("(es)"));
		Text text = buildText("test");
		
		MockDataEventListener dataListener = new MockDataEventListener();
		mgr.registerListener(dataListener);
		
		listener.onText(text);
		
		Data evt = dataListener.getDataRead();
		assertEquals(1, evt.getFieldId());
		assertEquals("es", evt.getData());
		assertSame(listener, evt.getSource());
	}
	
	@Test
	public void readNoPatternedData() {
		DataManager mgr = new DefaultDataManager();
		
		OnTextListener listener = new FindWithinTextListener(mgr, 1, Pattern.compile("(es)"));
		Text text = buildText("abc");
		
		MockDataEventListener dataListener = new MockDataEventListener();
		mgr.registerListener(dataListener);
		
		listener.onText(text);
		
		assertNull(dataListener.getDataRead());

	}

}
