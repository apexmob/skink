package com.apexmob.skink.listeners.rules;

import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.apexmob.skink.StartElement;
import com.apexmob.skink.listeners.rules.IncludeTagWithAttributeFilterRule;

public class IncludeTagWithAttributeFilterRuleTest {
	
	@Rule
	public ExpectedException thrown= ExpectedException.none();
	
	@Test
	public void throwsIllegalArgumentExceptionWhenNameIsNull() {
		thrown.expect(IllegalArgumentException.class);
		
		IncludeTagWithAttributeFilterRule rule = new IncludeTagWithAttributeFilterRule(null, "test");
	}
	
	@Test
	public void throwsIllegalArgumentExceptionWhenValueIsNull() {
		thrown.expect(IllegalArgumentException.class);
		
		IncludeTagWithAttributeFilterRule rule = new IncludeTagWithAttributeFilterRule("test", null);
	}
	
	@Test
	public void listensForElementWithMatchingAttribute() {
		IncludeTagWithAttributeFilterRule rule = new IncludeTagWithAttributeFilterRule("name", "value");
		
		StringBuilder buffer = new StringBuilder();
		buffer.append("<div name=\"value\">");
		
		StartElement start = new StartElement(buffer);
		
		assertTrue(rule.include(start));
	}
	
	@Test
	public void doesNotListenForNonMatchingAttribute() {
		IncludeTagWithAttributeFilterRule rule = new IncludeTagWithAttributeFilterRule("name", "value");
		
		StringBuilder buffer = new StringBuilder();
		buffer.append("<div name=\"novalue\">");
		
		StartElement start = new StartElement(buffer);
		
		assertTrue(!rule.include(start));
	}
	
	@Test
	public void doesNotListenForNoAttribute() {
		IncludeTagWithAttributeFilterRule rule = new IncludeTagWithAttributeFilterRule("name", "value");
		
		StringBuilder buffer = new StringBuilder();
		buffer.append("<div>");
		
		StartElement start = new StartElement(buffer);
		
		assertTrue(!rule.include(start));
	}

}
