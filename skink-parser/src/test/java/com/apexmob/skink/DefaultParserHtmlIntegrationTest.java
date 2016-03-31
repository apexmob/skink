package com.apexmob.skink;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.apexmob.skink.listeners.NodeListenerBuilder;

public class DefaultParserHtmlIntegrationTest {
	
	private Parser parser = null;
	
	@Before
	public void setUp() {
		parser = new DefaultParser();
	}
	
	@After
	public void tearDown() {
		parser = null;
	}
	
	protected Parser getParser() {
		return parser;
	}
	
	@Test
	public void test() {
		DataManager dataMgr = new DefaultDataManager();
		
		MockDataListener dataListener = new MockDataListener();
		dataMgr.registerListener(dataListener);
		
		Parser parser = getParser();
		parser.getNodeManager().registerListener(
				NodeListenerBuilder.builder()
				.setDataEventManager(dataMgr)
				.filterToAllTagsWithName("title")
				.readText(1)
				.build());
		
		parser.getNodeManager().registerListener(
				NodeListenerBuilder.builder()
				.setDataEventManager(dataMgr)
				.filterToAllTagsWithName("div")
				.filterToAllTagsWithAttribute("style", "movie")
				.addChildListener()
					.filterToAllTagsWithName("div")
					.filterToAllTagsWithAttribute("style", "title")
					.readText(2)
					.buildChild()
				.build());
		
		
		parse(parser, "com/apexmob/skink/DefaultParserIntegration.html");
		
		assertEquals(2, dataListener.getEventCount());
		
		assertEquals(1, dataListener.getDataEvent(0).getFieldId());
		assertEquals("My 10 Favorite Things of All Time!", dataListener.getDataEvent(0).getData());
		
		assertEquals(2, dataListener.getDataEvent(1).getFieldId());
		assertEquals("Star Wars", dataListener.getDataEvent(1).getData());
	}
	

	private void parse(Parser parser, String resource) {
		InputStream inStr = getClass().getClassLoader().getResourceAsStream(resource);
		
		try {
			parser.parse(inStr);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inStr != null) {
				try {
					inStr.close();
				} catch (IOException e) {
					//do nothing
				}
			}
		}
	}
}
