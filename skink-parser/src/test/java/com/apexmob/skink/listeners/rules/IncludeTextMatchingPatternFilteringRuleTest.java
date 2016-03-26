package com.apexmob.skink.listeners.rules;

import static org.junit.Assert.assertTrue;

import java.util.regex.Pattern;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.apexmob.skink.ParsingTest;
import com.apexmob.skink.Text;

public class IncludeTextMatchingPatternFilteringRuleTest extends ParsingTest {
	
	@Rule
	public ExpectedException thrown= ExpectedException.none();
	
	@Test
	public void throwsIllegalArgumentExceptionWhenPatternIsNull() {
		thrown.expect(IllegalArgumentException.class);
		
		new IncludeTextMatchingPatternFilteringRule(null);
	}
	
	@Test
	public void includesTextMatchingThePattern() {
		IncludeTextMatchingPatternFilteringRule rule = new IncludeTextMatchingPatternFilteringRule(Pattern.compile("test.*"));
		
		Text text = buildText("testing");
		
		assertTrue(rule.include(text));
	}
	
	@Test
	public void excludesTextNotMatchingThePattern() {
		IncludeTextMatchingPatternFilteringRule rule = new IncludeTextMatchingPatternFilteringRule(Pattern.compile("test1.*"));
		
		Text text = buildText("testing");
		
		assertTrue(!rule.include(text));
	}

}
