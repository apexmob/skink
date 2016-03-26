package com.apexmob.skink;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class DataTest {
	
	@Rule
	public ExpectedException thrown= ExpectedException.none();
	
	@Test
	public void createdSuccessfullyWithFieldId() {
		Data evt = new Data(1);
		
		assertEquals(1, evt.getFieldId());
		assertFalse(evt.hasData());
		assertFalse(evt.hasNonEmptyData());
		assertNull(evt.getSource());
	}
	
	@Test
	public void createdSuccessfullyWithFieldIdAndSource() {
		MockNodeListener listener = new MockNodeListener();
		Data evt = new Data(1, listener);
		
		assertEquals(1, evt.getFieldId());
		assertFalse(evt.hasData());
		assertFalse(evt.hasNonEmptyData());
		assertSame(listener, evt.getSource());
	}
	
	@Test
	public void createdSuccessfullyWithMinimumOptionalData() {
		Data evt = new Data(1, null, null);
		
		assertEquals(1, evt.getFieldId());
		assertFalse(evt.hasData());
		assertFalse(evt.hasNonEmptyData());
		assertFalse(evt.hasNonWhitespaceData());
		assertNull(evt.getSource());
	}
	
	@Test
	public void createdWithMaxData() {
		MockNodeListener listener = new MockNodeListener();
		
		Data evt = new Data(1, "value", listener);
		
		assertEquals(1, evt.getFieldId());
		assertTrue(evt.hasData());
		assertTrue(evt.hasNonEmptyData());
		assertTrue(evt.hasNonWhitespaceData());
		assertEquals("value", evt.getData());
		assertSame(listener, evt.getSource());
	}
	
	@Test
	public void createdWithEmptyData() {
		Data evt = new Data(1, "", null);
		
		assertEquals(1, evt.getFieldId());
		assertTrue(evt.hasData());
		assertFalse(evt.hasNonEmptyData());
		assertFalse(evt.hasNonWhitespaceData());
		assertEquals("", evt.getData());
	}
	
	@Test
	public void createdWithWhitespaceData() {
		Data evt = new Data(1, " ", null);
		
		assertEquals(1, evt.getFieldId());
		assertTrue(evt.hasData());
		assertTrue(evt.hasNonEmptyData());
		assertFalse(evt.hasNonWhitespaceData());
		assertEquals(" ", evt.getData());
	}
	
	@Test
	public void canParseByte() {
		Data evt = new Data(1, "3", null);
		
		assertEquals(3, evt.getDataAsByte().byteValue());
	}
	
	@Test
	public void returnsNullWhenCanNotParseByte() {
		Data evt = new Data(1, "a", null);
		
		assertNull(evt.getDataAsByte());
	}
	
	@Test
	public void canParseShort() {
		Data evt = new Data(1, "3", null);
		
		assertEquals(3, evt.getDataAsShort().shortValue());
	}
	
	@Test
	public void returnsNullWhenCanNotParseShort() {
		Data evt = new Data(1, "a", null);
		
		assertNull(evt.getDataAsShort());
	}
	
	@Test
	public void canParseInteger() {
		Data evt = new Data(1, "3", null);
		
		assertEquals(3, evt.getDataAsInteger().intValue());
	}
	
	@Test
	public void returnsNullWhenCanNotParseInteger() {
		Data evt = new Data(1, "a", null);
		
		assertNull(evt.getDataAsInteger());
	}
	
	@Test
	public void canParseLong() {
		Data evt = new Data(1, "3", null);
		
		assertEquals(3, evt.getDataAsLong().longValue());
	}
	
	@Test
	public void returnsNullWhenCanNotParseLong() {
		Data evt = new Data(1, "a", null);
		
		assertNull(evt.getDataAsLong());
	}
	
	@Test
	public void canParseFloat() {
		Data evt = new Data(1, "3.1", null);
		
		assertEquals(3.1, evt.getDataAsFloat().floatValue(), .0001f);
	}
	
	@Test
	public void returnsNullWhenCanNotParseFloat() {
		Data evt = new Data(1, "a", null);
		
		assertNull(evt.getDataAsFloat());
	}
	
	@Test
	public void canParseDouble() {
		Data evt = new Data(1, "3.1", null);
		
		assertEquals(3.1, evt.getDataAsDouble().doubleValue(), .0001f);
	}
	
	@Test
	public void returnsNullWhenCanNotParseDouble() {
		Data evt = new Data(1, "a", null);
		
		assertNull(evt.getDataAsDouble());
	}
	
	@Test
	public void canParseDate() throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		String dateAsString = "01/02/2016";
		Data evt = new Data(1, dateAsString, null);
		
		assertEquals(format.parse(dateAsString), evt.getDataAsDate(format));
	}
	
	@Test
	public void returnsNullWhenCanNotParseDate() {
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		Data evt = new Data(1, "a", null);
		
		assertNull(evt.getDataAsDate(format));
	}
	
	@Test
	public void getDataAsDateThrowsIllegalArgumentExceptionWhenFormatIsNull() {
		thrown.expect(IllegalArgumentException.class);
	
	Data evt = new Data(1, "a", null);
	
	evt.getDataAsDate(null);
	}
	
	@Test
	public void canParseBooleanTrue() {
		Data evt = new Data(1, "True", null);
		assertTrue(evt.getDataAsBoolean().booleanValue());
	}
	
	@Test
	public void canParseBooleanFalse() {
		Data evt = new Data(1, "False", null);
		assertFalse(evt.getDataAsBoolean().booleanValue());
	}
	
	@Test
	public void canParseBooleanInAnyCase() {
		Data evt = new Data(1, "trUE", null);
		assertTrue(evt.getDataAsBoolean().booleanValue());
	}
	
	@Test
	public void returnsNullWhenCanNotParseBoolean() {
		Data evt = new Data(1, "a", null);
		
		assertNull(evt.getDataAsBoolean());
	}
	
	@Test
	public void returnsNullWhenNoData() {
		Data evt = new Data(1, "", null);
		
		assertNull(evt.getDataAsBoolean());
	}

}
