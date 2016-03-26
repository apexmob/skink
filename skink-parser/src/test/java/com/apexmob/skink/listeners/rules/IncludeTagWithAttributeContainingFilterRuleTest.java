package com.apexmob.skink.listeners.rules;

import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.apexmob.skink.StartElement;

public class IncludeTagWithAttributeContainingFilterRuleTest {
	
	@Rule
	public ExpectedException thrown= ExpectedException.none();
	
	@Test
	public void throwsIllegalArgumentExceptionWhenNameIsNull() {
		thrown.expect(IllegalArgumentException.class);
		
		new IncludeTagsWithAttributeContainingFilterRule(null, "test");
	}
	
	@Test
	public void throwsIllegalArgumentExceptionWhenValueIsNull() {
		thrown.expect(IllegalArgumentException.class);
		
		new IncludeTagsWithAttributeContainingFilterRule("test", null);
	}
	
	@Test
	public void listensForElementWithMatchingAttribute() {
		IncludeTagsWithAttributeContainingFilterRule rule = new IncludeTagsWithAttributeContainingFilterRule("name", "value");
		
		StringBuilder buffer = new StringBuilder();
		buffer.append("<div name=\"value\">");
		
		StartElement start = new StartElement(buffer);
		
		assertTrue(rule.include(start));
	}
	
	@Test
	public void listensForElementContainingAttributeValue() {
		IncludeTagsWithAttributeContainingFilterRule rule = new IncludeTagsWithAttributeContainingFilterRule("name", "value");
		
		StringBuilder buffer = new StringBuilder();
		buffer.append("<div name=\"value\">");
		
		StartElement start = new StartElement(buffer);
		
		assertTrue(rule.include(start));
	}
	
	@Test
	public void doesNotListenForNonMatchingAttribute() {
		IncludeTagsWithAttributeContainingFilterRule rule = new IncludeTagsWithAttributeContainingFilterRule("name", "value");
		
		StringBuilder buffer = new StringBuilder();
		buffer.append("<div name=\"Value\">");
		
		StartElement start = new StartElement(buffer);
		
		assertTrue(!rule.include(start));
	}
	
	@Test
	public void doesNotListenForNoAttribute() {
		IncludeTagsWithAttributeContainingFilterRule rule = new IncludeTagsWithAttributeContainingFilterRule("name", "value");
		
		StringBuilder buffer = new StringBuilder();
		buffer.append("<div>");
		
		StartElement start = new StartElement(buffer);
		
		assertTrue(!rule.include(start));
	}

}
