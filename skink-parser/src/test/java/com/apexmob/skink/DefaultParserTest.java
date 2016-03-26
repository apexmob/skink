package com.apexmob.skink;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class DefaultParserTest extends ParsingTest {
	
	@Rule
	public ExpectedException thrown= ExpectedException.none();
	
	private DefaultParser parser = null;
	
	MockNodeManager mockNodeManager = null;
	
	@Before
	public void setUp() {
		mockNodeManager = new MockNodeManager();
		
		parser = new DefaultParser(mockNodeManager);
	}
	
	@After
	public void tearDown() {
		mockNodeManager = null;
		
		parser = null;
	}
	
	@Test
	public void constructorThrowsIllegalArgumentExceptionWhenNodeManagerIsNull() {
		thrown.expect(IllegalArgumentException.class);
		
		new DefaultParser(null);
	}
	
	@Test
	public void insertStartElementThrowsIllegalArgumentExceptionWhenStartIsNull() {
		thrown.expect(IllegalArgumentException.class);
		
		parser.insert((StartElement) null);
	}
	
	@Test
	public void insertEndElementThrowsIllegalArgumentExceptionWhenEndIsNull() {
		thrown.expect(IllegalArgumentException.class);
		
		parser.insert((EndElement) null);
	}
	
	@Test
	public void insertTextThrowsIllegalArgumentExceptionWhenTextIsNull() {
		thrown.expect(IllegalArgumentException.class);
		
		parser.insert((Text) null);
	}
	
	@Test
	public void listenerCompletedThrowsIllegalArgumentExceptionWhenTextIsNull() {
		thrown.expect(IllegalArgumentException.class);
		
		parser.onListenerComplete(null);
	}
	
	@Test
	public void canReadSelfEndingStartElement() throws IOException {
		parse("<div/>");
		
		assertEquals(1, mockNodeManager.getDelegate().getStartElementCount());
		assertEquals("div", mockNodeManager.getDelegate().getFirstStartElementName());
		
		assertEquals(0, mockNodeManager.getDelegate().getTextCount());
		
		assertEquals(1, mockNodeManager.getDelegate().getEndElementCount());
		assertEquals("div", mockNodeManager.getDelegate().getFirstEndElementName());
	}
	
	@Test
	public void canReadStandardElements() throws IOException {
		parse("<div></div>");
		
		assertEquals(1, mockNodeManager.getDelegate().getStartElementCount());
		assertEquals("div", mockNodeManager.getDelegate().getFirstStartElementName());
		
		assertEquals(0, mockNodeManager.getDelegate().getTextCount());
		
		assertEquals(1, mockNodeManager.getDelegate().getEndElementCount());
		assertEquals("div", mockNodeManager.getDelegate().getFirstEndElementName());
	}
	
	@Test
	public void canReadStandardElementsWithAttributes() throws IOException {
		parse("<div a=\"b\"></div>");
		
		assertEquals(1, mockNodeManager.getDelegate().getStartElementCount());
		assertEquals("div", mockNodeManager.getDelegate().getFirstStartElementName());
		
		assertEquals(0, mockNodeManager.getDelegate().getTextCount());
		
		assertEquals(1, mockNodeManager.getDelegate().getEndElementCount());
		assertEquals("div", mockNodeManager.getDelegate().getFirstEndElementName());
	}
	
	@Test
	public void canReadMultipleElements() throws IOException {
		parse("<div><span></span></div>");
		
		assertEquals(2, mockNodeManager.getDelegate().getStartElementCount());
		assertEquals("div", mockNodeManager.getDelegate().getStartElementName(0));
		assertEquals("span", mockNodeManager.getDelegate().getStartElementName(1));
		
		assertEquals(0, mockNodeManager.getDelegate().getTextCount());
		
		assertEquals(2, mockNodeManager.getDelegate().getEndElementCount());
		assertEquals("span", mockNodeManager.getDelegate().getEndElementName(0));
		assertEquals("div", mockNodeManager.getDelegate().getEndElementName(1));
	}
	
	@Test
	public void canReadStandardElementsWithText() throws IOException {
		parse("<div>test</div>");
		
		assertEquals(1, mockNodeManager.getDelegate().getStartElementCount());
		assertEquals("div", mockNodeManager.getDelegate().getFirstStartElementName());
		
		assertEquals(1, mockNodeManager.getDelegate().getTextCount());
		assertEquals("test", mockNodeManager.getDelegate().getFirstText());
		
		assertEquals(1, mockNodeManager.getDelegate().getEndElementCount());
		assertEquals("div", mockNodeManager.getDelegate().getFirstEndElementName());
	}
	
	@Test
	public void canReadMultipleElementsWithText() throws IOException {
		parse("<div>test1<span>test2</span>test3</div>");
		
		assertEquals(2, mockNodeManager.getDelegate().getStartElementCount());
		assertEquals("div", mockNodeManager.getDelegate().getStartElementName(0));
		assertEquals("span", mockNodeManager.getDelegate().getStartElementName(1));
		
		assertEquals(3, mockNodeManager.getDelegate().getTextCount());
		assertEquals("test1", mockNodeManager.getDelegate().getText(0));
		assertEquals("test2", mockNodeManager.getDelegate().getText(1));
		assertEquals("test3", mockNodeManager.getDelegate().getText(2));
		
		assertEquals(2, mockNodeManager.getDelegate().getEndElementCount());
		assertEquals("span", mockNodeManager.getDelegate().getEndElementName(0));
		assertEquals("div", mockNodeManager.getDelegate().getEndElementName(1));
	}
	
	@Test
	public void ignoresElementsInAttributeValues() throws IOException {
		parse("<div a=\"a<br>b\"></div>");
		
		assertEquals(2, mockNodeManager.getDelegate().getNodeStack().size());
		assertStartElement(mockNodeManager.getDelegate(), 0, "div");
		assertStartElementAttribute(mockNodeManager.getDelegate(), 0, "a", "a<br>b");
		assertEndElement(mockNodeManager.getDelegate(), 1, "div");
	}
	
	@Test
	public void parserInsertsStartElements() throws IOException {
		final DefaultParser parser = new DefaultParser();
		
		MockNodeListener listener = new MockNodeListener();
		parser.getNodeManager().registerListener(listener);
		
		parser.getNodeManager().registerListener(new NodeListener() {
			
			public void onText(Text text) { }
			
			public void onStartElement(StartElement start) {
				if ("div".equals(start.getName())) {
					parser.insert(new StartElement(new StringBuilder("<span>")));
				}
			}
			
			public void onEndElement(EndElement end) { }
		});
		
		
		
		parse(parser, "<div></div>");
		
		assertStartElement(listener, 0, "div");
		assertStartElement(listener, 1, "span");
		assertEndElement(listener, 2, "div");
	}
	
	@Test
	public void parserInsertsEndElements() throws IOException {
		final DefaultParser parser = new DefaultParser();
		
		MockNodeListener listener = new MockNodeListener();
		parser.getNodeManager().registerListener(listener);
		
		parser.getNodeManager().registerListener(new NodeListener() {
			
			public void onText(Text text) { }
			
			public void onStartElement(StartElement start) {
				if ("div".equals(start.getName())) {
					parser.insert(new EndElement(new StringBuilder("</span>")));
				}
			}
			
			public void onEndElement(EndElement end) { }
		});
		
		parse(parser, "<div></div>");
		
		assertStartElement(listener, 0, "div");
		assertEndElement(listener, 1, "span");
		assertEndElement(listener, 2, "div");
	}
	
	@Test
	public void parserInsertsText() throws IOException {
		final DefaultParser parser = new DefaultParser();
		
		MockNodeListener listener = new MockNodeListener();
		parser.getNodeManager().registerListener(listener);
		
		parser.getNodeManager().registerListener(new NodeListener() {
			
			public void onText(Text text) { }
			
			public void onStartElement(StartElement start) {
				if ("div".equals(start.getName())) {
					parser.insert(new Text(new StringBuilder("test")));
				}
			}
			
			public void onEndElement(EndElement end) { }
		});
		
		parse(parser, "<div></div>");
		
		assertStartElement(listener, 0, "div");
		assertText(listener, 1, "test");
		assertEndElement(listener, 2, "div");
	}
	
	@Test
	public void parserInsertsAllNodes() throws IOException {
		final DefaultParser parser = new DefaultParser();
		
		MockNodeListener listener = new MockNodeListener();
		parser.getNodeManager().registerListener(listener);
		
		parser.getNodeManager().registerListener(new NodeListener() {
			
			public void onText(Text text) { }
			
			public void onStartElement(StartElement start) {
				if ("div".equals(start.getName())) {
					parser.insert(new StartElement(new StringBuilder("<span>")));
					parser.insert(new Text(new StringBuilder("test")));
					parser.insert(new EndElement(new StringBuilder("</span>")));
				}
			}
			
			public void onEndElement(EndElement end) { }
		});
		
		parse(parser, "<div></div>");
		
		assertStartElement(listener, 0, "div");
		assertStartElement(listener, 1, "span");
		assertText(listener, 2, "test");
		assertEndElement(listener, 3, "span");
		assertEndElement(listener, 4, "div");
	}
	
	@Test
	public void parserInsertsNodesInMultiplePlaces() throws IOException {
		final DefaultParser parser = new DefaultParser();
		
		MockNodeListener listener = new MockNodeListener();
		parser.getNodeManager().registerListener(listener);
		
		parser.getNodeManager().registerListener(new NodeListener() {
			
			public void onText(Text text) { }
			
			public void onStartElement(StartElement start) {
				if ("div".equals(start.getName())) {
					parser.insert(new StartElement(new StringBuilder("<span>")));
					parser.insert(new Text(new StringBuilder("test")));
					parser.insert(new EndElement(new StringBuilder("</span>")));
				}
			}
			
			public void onEndElement(EndElement end) { }
		});
		
		parse(parser, "<div></div><div></div>");
		
		assertStartElement(listener, 0, "div");
		assertStartElement(listener, 1, "span");
		assertText(listener, 2, "test");
		assertEndElement(listener, 3, "span");
		assertEndElement(listener, 4, "div");
		
		assertStartElement(listener, 5, "div");
		assertStartElement(listener, 6, "span");
		assertText(listener, 7, "test");
		assertEndElement(listener, 8, "span");
		assertEndElement(listener, 9, "div");
	}
	
	@Test
	public void dataReadEventsDoNotAlterFunctionalty() throws IOException {
		final DefaultDataManager evtMgr = new DefaultDataManager();
		
		evtMgr.registerListener(parser);
		
		parser.getNodeManager().registerListener(new NodeListener() {
			
			public void onText(Text text) { }
			
			public void onStartElement(StartElement start) {
				if ("div".equals(start.getName())) {
					evtMgr.data(new Data(1, "data", mockNodeManager.getDelegate()));
				}
			}
			
			public void onEndElement(EndElement end) { }
		});
		
		parse("<div></div>");
		
		assertEquals(2, mockNodeManager.getDelegate().getNodeStack().size());
		assertStartElement(mockNodeManager.getDelegate(), 0, "div");
		assertEndElement(mockNodeManager.getDelegate(), 1, "div");
	}
	
	@Test
	public void listenerCompletedEventsRemoveTheListenerIfPresent() throws IOException {
		final DefaultParser parser = new DefaultParser();
		
		MockNodeListener listener = new MockNodeListener();
		parser.getNodeManager().registerListener(listener);
		
		final DefaultDataManager evtMgr = new DefaultDataManager();
		
		evtMgr.registerListener(parser);
		
		parser.getNodeManager().registerListener(new NodeListener() {
			
			public void onText(Text text) { }
			
			public void onStartElement(StartElement start) {
				if ("div".equals(start.getName())) {
					evtMgr.dataComplete(new DataComplete());
				}
			}
			
			public void onEndElement(EndElement end) { }
		});
		
		parse(parser, "<div></div>");
		
		assertEquals(1, listener.getNodeStack().size());
		assertStartElement(listener, 0, "div");
	}
	
	@Test
	public void readCompletedEventsStopAllParsing() throws IOException {
		final DefaultParser parser = new DefaultParser();
		
		MockNodeListener listener = new MockNodeListener();
		parser.getNodeManager().registerListener(listener);
		
		DefaultDataManager evtMgr = new DefaultDataManager();
		
		evtMgr.registerListener(parser);
		
		evtMgr.listenerComplete(new ListenerComplete(listener));
		
		parse(parser, "<div></div>");
		
		assertEquals(0, listener.getNodeStack().size());
	}
	
	private InputStream buildInputStream(String str) {
		return new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8));
	}
	
	private void parse(String str) throws IOException {
		parser.parse(buildInputStream(str));
	}
	
	private void parse(Parser parser, String str) throws IOException {
		parser.parse(buildInputStream(str));
	}

}
