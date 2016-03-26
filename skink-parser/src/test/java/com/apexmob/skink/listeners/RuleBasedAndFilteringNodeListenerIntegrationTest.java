package com.apexmob.skink.listeners;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.apexmob.skink.DefaultParser;
import com.apexmob.skink.MockNodeListener;
import com.apexmob.skink.NodeListener;
import com.apexmob.skink.Parser;
import com.apexmob.skink.listeners.rules.IncludeTagWithAttributeFilterRule;
import com.apexmob.skink.listeners.rules.IncludeTagWithNameFilterRule;

public class RuleBasedAndFilteringNodeListenerIntegrationTest {
	
	private Parser parser = null;
	
	MockNodeListener mock1 = null;
	RuleBasedAndFilteringNodeListener listener1 = null;
	
	MockNodeListener mock2 = null;
	RuleBasedAndFilteringNodeListener listener2 = null;
	
	@Before
	public void setUp() {
		parser = new DefaultParser();
		
		mock1 = new MockNodeListener();
		listener1 = new RuleBasedAndFilteringNodeListener(mock1);
		
		mock2 = new MockNodeListener();
		
		List<NodeListener> listeners = new ArrayList<NodeListener>();
		listeners.add(mock2);
		listeners.add(listener1);
		listener2 = new RuleBasedAndFilteringNodeListener(listeners);
	}
	
	@After
	public void tearDown() {
		parser = null;
		
		mock1 = null;
		listener1 = null;
		
		mock2 = null;
		listener2 = null;
	}

	@Test
	public void test() throws IOException {
		parser.getNodeManager().registerListener(listener2);
		
		listener1.addTagRule(new IncludeTagWithNameFilterRule("span"));
		listener1.addTagRule(new IncludeTagWithAttributeFilterRule("id", "test"));
		
		listener2.addTagRule(new IncludeTagWithNameFilterRule("div"));
		listener2.addTagRule(new IncludeTagWithAttributeFilterRule("class", "cls"));
		
		parse("<h1><div class=\"cls\">testing<span id=\"test\"/></div></h1>");
		
		assertEquals(2, mock2.getStartElementCount());
		assertEquals(2, mock2.getEndElementCount());
		assertEquals(1, mock2.getTextCount());
		
		assertEquals(1, mock1.getStartElementCount());
		assertEquals(1, mock1.getEndElementCount());
		assertEquals(0, mock1.getTextCount());
	}
	
	private InputStream buildInputStream(String str) {
		return new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8));
	}
	
	private void parse(String str) throws IOException {
		parser.parse(buildInputStream(str));
	}
}
