package com.apexmob.skink.listeners;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.apexmob.skink.MockNodeListener;
import com.apexmob.skink.NodeListener;
import com.apexmob.skink.ParsingTest;

public class RuleBasedAndFilteringNodeListenerTest extends ParsingTest {
	
	MockNodeListener mock1 = null;
	MockNodeListener mock2 = null;
	RuleBasedAndFilteringNodeListener listener = null;
	RuleBasedAndFilteringNodeListener listener2 = null;
	RuleBasedAndFilteringNodeListener listener3 = null;
	RuleBasedAndFilteringNodeListener listener4 = null;
	RuleBasedAndFilteringNodeListener listener5 = null;
	
	MockTagFilteringRule tagRule1 = null;
	MockTagFilteringRule tagRule2 = null;
	
	MockTextFilteringRule textRule1 = null;
	
	@Rule
	public ExpectedException thrown= ExpectedException.none();
	
	@Before
	public void setUp() {
		mock1 = new  MockNodeListener();
		mock2 = new  MockNodeListener();
		listener = new RuleBasedAndFilteringNodeListener(mock1);
		
		List<NodeListener> mocks = new ArrayList<NodeListener>();
		mocks.add(mock1);
		mocks.add(mock2);
		listener2 = new RuleBasedAndFilteringNodeListener(mocks);
		
		tagRule1 = new MockTagFilteringRule();
		listener.addTagRule(tagRule1);
		listener2.addTagRule(tagRule1);
		
		tagRule2 = new MockTagFilteringRule();
		
		listener3 = new RuleBasedAndFilteringNodeListener(mock1);
		listener3.addTagRule(tagRule1);
		listener3.addTagRule(tagRule2);
		
		listener4 = new RuleBasedAndFilteringNodeListener(mock1);
		
		textRule1 = new MockTextFilteringRule();
		listener5 = new RuleBasedAndFilteringNodeListener(mock1);
		listener5.addTagRule(tagRule1);
		listener5.addTextRule(textRule1);
		
	}
	
	@After
	public void tearDown() {
		mock1 = null;
		mock2 = null;
		listener = null;
		listener2 = null;
		listener3 = null;
		listener4 = null;
		
		tagRule1 = null;
		tagRule2 = null;
		
		textRule1 = null;
	}
	
	
	@Test
	public void addTagRuleThrowsIllegalArgumentExceptionWhenRuleIsNull() {
		thrown.expect(IllegalArgumentException.class);
		
		new RuleBasedAndFilteringNodeListener().addTagRule(null);
	}
	
	@Test
	public void addTextRuleThrowsIllegalArgumentExceptionWhenRuleIsNull() {
		thrown.expect(IllegalArgumentException.class);
		
		new RuleBasedAndFilteringNodeListener().addTextRule(null);
	}
	
	@Test
	public void testSingleElementIncludeWithOneListener() {
		tagRule1.includeElement = true;
		listener.onStartElement(buildStartElement("<div/>"));
		listener.onText(buildText("test"));
		listener.onEndElement(buildEndElement("<div/>"));
		assertEquals(1, mock1.getStartElementCount());
		assertEquals(1, mock1.getEndElementCount());
		assertEquals(1, mock1.getTextCount());
	}
	
	@Test
	public void testSingleElementExcludeWithOneListener() {
		tagRule1.includeElement = false;
		listener.onStartElement(buildStartElement("<div/>"));
		listener.onText(buildText("test"));
		listener.onEndElement(buildEndElement("<div/>"));
		assertEquals(0, mock1.getStartElementCount());
		assertEquals(0, mock1.getEndElementCount());
		assertEquals(0, mock1.getTextCount());
	}
	
	@Test
	public void testMultipleElementsIncludeParentWithOneListener() {
		tagRule1.includeElement = true;
		listener.onStartElement(buildStartElement("<div/>"));
		listener.onStartElement(buildStartElement("<span/>"));
		listener.onEndElement(buildEndElement("<span/>"));
		listener.onEndElement(buildEndElement("<div/>"));
		assertEquals(2, mock1.getStartElementCount());
		assertEquals(2, mock1.getEndElementCount());
		assertEquals(0, mock1.getTextCount());
	}
	
	@Test
	public void testMultipleElementsIncludeChildWithOneListener() {
		tagRule1.includeElement = false;
		listener.onStartElement(buildStartElement("<div/>"));
		tagRule1.includeElement = true;
		listener.onStartElement(buildStartElement("<span/>"));
		listener.onEndElement(buildEndElement("<span/>"));
		listener.onEndElement(buildEndElement("<div/>"));
		assertEquals(1, mock1.getStartElementCount());
		assertEquals(1, mock1.getEndElementCount());
		assertEquals(0, mock1.getTextCount());
		
		assertEquals("span", mock1.getFirstStartElementName());
		assertEquals("span", mock1.getFirstEndElementName());
	}
	
	@Test
	public void testSingleElementIncludeWithTwoListeners() {
		tagRule1.includeElement = true;
		listener2.onStartElement(buildStartElement("<div/>"));
		listener2.onText(buildText("test"));
		listener2.onEndElement(buildEndElement("<div/>"));
		assertEquals(1, mock1.getStartElementCount());
		assertEquals(1, mock1.getEndElementCount());
		assertEquals(1, mock1.getTextCount());
		
		assertEquals(1, mock2.getStartElementCount());
		assertEquals(1, mock2.getEndElementCount());
		assertEquals(1, mock2.getTextCount());
	}
	
	@Test
	public void testSingleElementExcludeWithTwoListeners() {
		tagRule1.includeElement = false;
		listener2.onStartElement(buildStartElement("<div/>"));
		listener2.onText(buildText("test"));
		listener2.onEndElement(buildEndElement("<div/>"));
		assertEquals(0, mock1.getStartElementCount());
		assertEquals(0, mock1.getEndElementCount());
		assertEquals(0, mock1.getTextCount());
		
		assertEquals(0, mock2.getStartElementCount());
		assertEquals(0, mock2.getEndElementCount());
		assertEquals(0, mock2.getTextCount());
	}
	
	@Test
	public void testMultipleElementsIncludeParentWithTwoListeners() {
		tagRule1.includeElement = true;
		listener2.onStartElement(buildStartElement("<div/>"));
		listener2.onStartElement(buildStartElement("<span/>"));
		listener2.onEndElement(buildEndElement("<span/>"));
		listener2.onEndElement(buildEndElement("<div/>"));
		assertEquals(2, mock1.getStartElementCount());
		assertEquals(2, mock1.getEndElementCount());
		assertEquals(0, mock1.getTextCount());
		
		assertEquals(2, mock2.getStartElementCount());
		assertEquals(2, mock2.getEndElementCount());
		assertEquals(0, mock2.getTextCount());
	}
	
	@Test
	public void testMultipleElementsIncludeChildWithTwoListeners() {
		tagRule1.includeElement = false;
		listener2.onStartElement(buildStartElement("<div/>"));
		tagRule1.includeElement = true;
		listener2.onStartElement(buildStartElement("<span/>"));
		listener2.onEndElement(buildEndElement("<span/>"));
		listener2.onEndElement(buildEndElement("<div/>"));
		assertEquals(1, mock1.getStartElementCount());
		assertEquals(1, mock1.getEndElementCount());
		assertEquals(0, mock1.getTextCount());
		
		assertEquals("span", mock1.getFirstStartElementName());
		assertEquals("span", mock1.getFirstEndElementName());
		
		assertEquals(1, mock2.getStartElementCount());
		assertEquals(1, mock2.getEndElementCount());
		assertEquals(0, mock2.getTextCount());
		
		assertEquals("span", mock2.getFirstStartElementName());
		assertEquals("span", mock2.getFirstEndElementName());
	}
	
	@Test
	public void testSingleElementIncludeWithTwoPositiveRules() {
		tagRule1.includeElement = true;
		tagRule2.includeElement = true;
		listener3.onStartElement(buildStartElement("<div/>"));
		listener3.onText(buildText("test"));
		listener3.onEndElement(buildEndElement("<div/>"));
		assertEquals(1, mock1.getStartElementCount());
		assertEquals(1, mock1.getEndElementCount());
		assertEquals(1, mock1.getTextCount());
	}
	
	@Test
	public void testSingleElementIncludeWithOnePositiveAndOneNegativeRule() {
		tagRule1.includeElement = true;
		tagRule2.includeElement = false;
		listener3.onStartElement(buildStartElement("<div/>"));
		listener3.onText(buildText("test"));
		listener3.onEndElement(buildEndElement("<div/>"));
		assertEquals(0, mock1.getStartElementCount());
		assertEquals(0, mock1.getEndElementCount());
		assertEquals(0, mock1.getTextCount());
	}
	
	@Test
	public void testSingleElementIncludeWithNoRules() {
		listener4.onStartElement(buildStartElement("<div/>"));
		listener4.onText(buildText("test"));
		listener4.onEndElement(buildEndElement("<div/>"));
		assertEquals(1, mock1.getStartElementCount());
		assertEquals(1, mock1.getEndElementCount());
		assertEquals(1, mock1.getTextCount());
	}
	
	@Test
	public void includeTextListensForText() {
		textRule1.includeText = true;
		listener5.onStartElement(buildStartElement("<div>"));
		listener5.onText(buildText("test"));
		listener5.onStartElement(buildStartElement("</div>"));
		
		assertEquals(1, mock1.getTextCount());
		assertEquals("test", mock1.getFirstText());
	}
	
	@Test
	public void excludeTextDoesNotListenForText() {
		textRule1.includeText = false;
		listener5.onStartElement(buildStartElement("<div>"));
		listener5.onText(buildText("test"));
		listener5.onStartElement(buildStartElement("</div>"));
		
		assertEquals(0, mock1.getTextCount());
	}
}
