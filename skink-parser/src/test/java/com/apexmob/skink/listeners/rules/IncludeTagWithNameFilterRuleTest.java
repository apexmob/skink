package com.apexmob.skink.listeners.rules;

import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.apexmob.skink.ParsingTest;
import com.apexmob.skink.StartElement;
import com.apexmob.skink.listeners.rules.IncludeTagWithNameFilterRule;

public class IncludeTagWithNameFilterRuleTest extends ParsingTest {
	
	@Rule
    public ExpectedException thrown= ExpectedException.none();
	
	@Test
	public void throwsIllegalArgumentExceptionWhenNameIsNull() {
		thrown.expect(IllegalArgumentException.class);
		
		new IncludeTagWithNameFilterRule(null);
	}
	
	@Test
	public void listensForMatchingCaseSingleElement() {
		IncludeTagWithNameFilterRule rule = new IncludeTagWithNameFilterRule("div");
		
		StartElement start = buildStartElement("<div>");
		
		assertTrue(rule.include(start));
	}
	
	@Test
	public void listensForMixedCaseSingleElement() {
		IncludeTagWithNameFilterRule rule = new IncludeTagWithNameFilterRule("Div");
		
		StartElement start = buildStartElement("<div>");
		
		assertTrue(rule.include(start));
	}
	
	@Test
	public void doesNotlistenForNonMatchingSingleElement() {
		IncludeTagWithNameFilterRule rule = new IncludeTagWithNameFilterRule("di v");
		
		StartElement start = buildStartElement("<div>");
		
		assertTrue(!rule.include(start));
	}
}
