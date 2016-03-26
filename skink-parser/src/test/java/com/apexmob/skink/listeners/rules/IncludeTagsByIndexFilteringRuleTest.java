package com.apexmob.skink.listeners.rules;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.apexmob.skink.ParsingTest;
import com.apexmob.skink.listeners.TagFilteringRule;

public class IncludeTagsByIndexFilteringRuleTest extends ParsingTest {
	@Rule
    public ExpectedException thrown= ExpectedException.none();
	
	@Test
	public void throwsIllegalArgumentExceptionWhenIndexIsNegative() {
		thrown.expect(IllegalArgumentException.class);
		
		new IncludeTagsByIndexFilteringRule(-1);
	}
	
	@Test
	public void throwsIllegalArgumentExceptionWhenIndexIsZero() {
		thrown.expect(IllegalArgumentException.class);
		
		new IncludeTagsByIndexFilteringRule(0);
	}
	
	@Test
	public void throwsIllegalArgumentExceptionWhenStartIndexIsNegative() {
		thrown.expect(IllegalArgumentException.class);
		
		new IncludeTagsByIndexFilteringRule(-1, 1);
	}
	
	@Test
	public void throwsIllegalArgumentExceptionWhenStartIndexIsZero() {
		thrown.expect(IllegalArgumentException.class);
		
		new IncludeTagsByIndexFilteringRule(0,1);
	}
	
	@Test
	public void throwsIllegalArgumentExceptionWhenEndIndexIsNegative() {
		thrown.expect(IllegalArgumentException.class);
		
		new IncludeTagsByIndexFilteringRule(1, -1);
	}
	
	@Test
	public void throwsIllegalArgumentExceptionWhenEndIndexIsZero() {
		thrown.expect(IllegalArgumentException.class);
		
		new IncludeTagsByIndexFilteringRule(1,0);
	}
	
	@Test
	public void throwsIllegalArgumentExceptionWhenStartIndexIsGreaterThanEndIndex() {
		thrown.expect(IllegalArgumentException.class);
		
		new IncludeTagsByIndexFilteringRule(2,1);
	}
	
	@Test
	public void includesOnlyASingleIndexedElement() {
		TagFilteringRule rule = new IncludeTagsByIndexFilteringRule(2);
		
		assertTrue(!rule.include(buildStartElement("<div>")));
		assertTrue(rule.include(buildStartElement("<div>")));
		assertTrue(!rule.include(buildStartElement("<div>")));
	}
	
	@Test
	public void includesARangeOfIndexedElements() {
		TagFilteringRule rule = new IncludeTagsByIndexFilteringRule(2,3);
		
		assertTrue(!rule.include(buildStartElement("<div>")));
		assertTrue(rule.include(buildStartElement("<div>")));
		assertTrue(rule.include(buildStartElement("<div>")));
		assertTrue(!rule.include(buildStartElement("<div>")));
	}
}
