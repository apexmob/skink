package com.apexmob.skink.listeners;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.apexmob.skink.MockNodeListener;
import com.apexmob.skink.NodeListener;
import com.apexmob.skink.ParsingTest;
import com.apexmob.skink.StartElement;
import com.apexmob.skink.Text;

public class FilteringNodeListenerTest extends ParsingTest {
	
	MockNodeListener mock1 = null;
	MockNodeListener mock2 = null;
	MockFilteringNodeListener listener = null;
	MockFilteringNodeListener listener2 = null;
	
	@Rule
	public ExpectedException thrown= ExpectedException.none();
	
	@Before
	public void setUp() {
		mock1 = new  MockNodeListener();
		mock2 = new  MockNodeListener();
		listener = new MockFilteringNodeListener(mock1);
		
		List<NodeListener> mocks = new ArrayList<NodeListener>();
		mocks.add(mock1);
		mocks.add(mock2);
		listener2 = new MockFilteringNodeListener(mocks);
	}
	
	@After
	public void tearDown() {
		mock1 = null;
		mock2 = null;
		listener = null;
		listener2 = null;
	}
	
	@Test
	public void testSingleElementIncludeWithOneListener_PolicyDefault() {
		listener.includeElement = true;
		listener.onStartElement(buildStartElement("<div>"));
		listener.onText(buildText("test"));
		listener.onEndElement(buildEndElement("</div>"));
		assertEquals(1, mock1.getStartElementCount());
		assertEquals(1, mock1.getEndElementCount());
		assertEquals(1, mock1.getTextCount());
	}
	
	@Test
	public void testSingleElementIncludeWithOneListener_PolicyAll() {
		listener.setPropagationPolicy(PropagationPolicy.All);
		
		listener.includeElement = true;
		listener.onStartElement(buildStartElement("<div>"));
		listener.onText(buildText("test"));
		listener.onEndElement(buildEndElement("</div>"));
		assertEquals(1, mock1.getStartElementCount());
		assertEquals(1, mock1.getEndElementCount());
		assertEquals(1, mock1.getTextCount());
	}
	
	@Test
	public void testSingleElementIncludeWithOneListener_PolicyTagsOnly() {
		listener.setPropagationPolicy(PropagationPolicy.ElementsOnly);
		
		listener.includeElement = true;
		listener.onStartElement(buildStartElement("<div>"));
		listener.onText(buildText("test"));
		listener.onEndElement(buildEndElement("</div>"));
		assertEquals(1, mock1.getStartElementCount());
		assertEquals(1, mock1.getEndElementCount());
		assertEquals(0, mock1.getTextCount());
	}
	
	@Test
	public void testSingleElementIncludeWithOneListener_PolicyParentTagOnly() {
		listener.setPropagationPolicy(PropagationPolicy.ParentElementOnly);
		
		listener.includeElement = true;
		listener.onStartElement(buildStartElement("<div>"));
		listener.onText(buildText("test"));
		listener.onEndElement(buildEndElement("</div>"));
		assertEquals(1, mock1.getStartElementCount());
		assertEquals(1, mock1.getEndElementCount());
		assertEquals(0, mock1.getTextCount());
	}
	
	@Test
	public void testSingleElementExcludeWithOneListenert() {
		listener.includeElement = false;
		listener.onStartElement(buildStartElement("<div>"));
		listener.onText(buildText("test"));
		listener.onEndElement(buildEndElement("</div>"));
		assertEquals(0, mock1.getStartElementCount());
		assertEquals(0, mock1.getEndElementCount());
		assertEquals(0, mock1.getTextCount());
	}
	
	@Test
	public void testMultipleElementsIncludeParentWithOneListener_PolicyDefault() {
		listener.includeElement = true;
		listener.onStartElement(buildStartElement("<div>"));
		listener.onText(buildText("one"));
		listener.onStartElement(buildStartElement("<span>"));
		listener.onText(buildText("two"));
		listener.onEndElement(buildEndElement("</span>"));
		listener.onText(buildText("three"));
		listener.onEndElement(buildEndElement("</div>"));
		assertEquals(2, mock1.getStartElementCount());
		assertEquals(2, mock1.getEndElementCount());
		assertEquals(3, mock1.getTextCount());
	}
	
	@Test
	public void testMultipleElementsIncludeParentWithOneListener_PolicyAll() {
		listener.setPropagationPolicy(PropagationPolicy.All);
		listener.includeElement = true;
		listener.onStartElement(buildStartElement("<div>"));
		listener.onText(buildText("one"));
		listener.onStartElement(buildStartElement("<span>"));
		listener.onText(buildText("two"));
		listener.onEndElement(buildEndElement("</span>"));
		listener.onText(buildText("three"));
		listener.onEndElement(buildEndElement("</div>"));
		assertEquals(2, mock1.getStartElementCount());
		assertEquals(2, mock1.getEndElementCount());
		assertEquals(3, mock1.getTextCount());
	}
	
	@Test
	public void testMultipleElementsIncludeParentWithOneListener_PolicyTagsOnly() {
		listener.setPropagationPolicy(PropagationPolicy.ElementsOnly);
		listener.includeElement = true;
		listener.onStartElement(buildStartElement("<div>"));
		listener.onText(buildText("one"));
		listener.onStartElement(buildStartElement("<span>"));
		listener.onText(buildText("two"));
		listener.onEndElement(buildEndElement("</span>"));
		listener.onText(buildText("three"));
		listener.onEndElement(buildEndElement("</div>"));
		assertEquals(2, mock1.getStartElementCount());
		assertEquals(2, mock1.getEndElementCount());
		assertEquals(0, mock1.getTextCount());
	}
	
	@Test
	public void testMultipleElementsIncludeParentWithOneListener_PolicyParentTagOnly() {
		listener.setPropagationPolicy(PropagationPolicy.ParentElementOnly);
		listener.includeElement = true;
		listener.onStartElement(buildStartElement("<div>"));
		listener.onText(buildText("one"));
		listener.onStartElement(buildStartElement("<span>"));
		listener.onText(buildText("two"));
		listener.onEndElement(buildEndElement("</span>"));
		listener.onText(buildText("three"));
		listener.onEndElement(buildEndElement("</div>"));
		assertEquals(1, mock1.getStartElementCount());
		assertEquals(1, mock1.getEndElementCount());
		assertEquals(0, mock1.getTextCount());
		assertStartElement(mock1, 0, "div");
		assertEndElement(mock1, 1, "div");
	}
	
	@Test
	public void testMultipleElementsIncludeChildWithOneListener() {
		listener.includeElement = false;
		listener.onStartElement(buildStartElement("<div>"));
		listener.includeElement = true;
		listener.onStartElement(buildStartElement("<span>"));
		listener.onEndElement(buildEndElement("</span>"));
		listener.onEndElement(buildEndElement("</div>"));
		assertEquals(1, mock1.getStartElementCount());
		assertEquals(1, mock1.getEndElementCount());
		assertEquals(0, mock1.getTextCount());
		
		assertEquals("span", mock1.getFirstStartElementName());
		assertEquals("span", mock1.getFirstEndElementName());
	}
	
	@Test
	public void testSingleElementIncludeWithTwoListeners() {
		listener2.includeElement = true;
		listener2.onStartElement(buildStartElement("<div>"));
		listener2.onText(buildText("test"));
		listener2.onEndElement(buildEndElement("</div>"));
		assertEquals(1, mock1.getStartElementCount());
		assertEquals(1, mock1.getEndElementCount());
		assertEquals(1, mock1.getTextCount());
		
		assertEquals(1, mock2.getStartElementCount());
		assertEquals(1, mock2.getEndElementCount());
		assertEquals(1, mock2.getTextCount());
	}
	
	@Test
	public void testSingleElementExcludeWithTwoListeners() {
		listener2.includeElement = false;
		listener2.onStartElement(buildStartElement("<div>"));
		listener2.onText(buildText("test"));
		listener2.onEndElement(buildEndElement("</div>"));
		assertEquals(0, mock1.getStartElementCount());
		assertEquals(0, mock1.getEndElementCount());
		assertEquals(0, mock1.getTextCount());
		
		assertEquals(0, mock2.getStartElementCount());
		assertEquals(0, mock2.getEndElementCount());
		assertEquals(0, mock2.getTextCount());
	}
	
	@Test
	public void testMultipleElementsIncludeParentWithTwoListeners() {
		listener2.includeElement = true;
		listener2.onStartElement(buildStartElement("<div>"));
		listener2.onStartElement(buildStartElement("<span>"));
		listener2.onEndElement(buildEndElement("</span>"));
		listener2.onEndElement(buildEndElement("</div>"));
		assertEquals(2, mock1.getStartElementCount());
		assertEquals(2, mock1.getEndElementCount());
		assertEquals(0, mock1.getTextCount());
		
		assertEquals(2, mock2.getStartElementCount());
		assertEquals(2, mock2.getEndElementCount());
		assertEquals(0, mock2.getTextCount());
	}
	
	@Test
	public void testMultipleElementsIncludeChildWithTwoListeners() {
		listener2.includeElement = false;
		listener2.onStartElement(buildStartElement("<div>"));
		listener2.includeElement = true;
		listener2.onStartElement(buildStartElement("<span>"));
		listener2.onEndElement(buildEndElement("</span>"));
		listener2.onEndElement(buildEndElement("</div>"));
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
	public void includeTextListensForText() {
		listener.includeText = true;
		listener.onStartElement(buildStartElement("<div>"));
		listener.onText(buildText("test"));
		listener.onStartElement(buildStartElement("</div>"));
		
		assertEquals(1, mock1.getTextCount());
		assertEquals("test", mock1.getFirstText());
	}
	
	@Test
	public void excludeTextDoesNotListenForText() {
		listener.includeText = false;
		listener.onStartElement(buildStartElement("<div>"));
		listener.onText(buildText("test"));
		listener.onStartElement(buildStartElement("</div>"));
		
		assertEquals(0, mock1.getTextCount());
	}
	
	@Test
	public void throwsIllegalArgumentExceptionWhenPropagationPolicyIsNull() {
		thrown.expect(IllegalArgumentException.class);
		
		listener.setPropagationPolicy(null);
	}
	
	@Test
	public void defaultPropagationIsAll() {
		assertSame(PropagationPolicy.All, listener.getPropagationPolicy());
	}
	
	@Test
	public void canGetPropagationPolicy() {
		listener.setPropagationPolicy(PropagationPolicy.ElementsOnly);
		assertSame(PropagationPolicy.ElementsOnly, listener.getPropagationPolicy());
	}
	
	@Test
	public void throwsIllegalStateExceptionWhenStateIsInconsistentFromPolicyChanges() {
		thrown.expect(IllegalStateException.class);
		
		listener.onStartElement(buildStartElement("<div>"));
		
		listener.setPropagationPolicy(PropagationPolicy.ParentElementOnly);
		
		listener.onEndElement(buildEndElement("</div>"));
	}
	
	@Test
	public void throwsIllegalStateExceptionWhenEndElementNameDoesNotMatchStartElementName() {
		thrown.expect(IllegalStateException.class);
		
		listener.setPropagationPolicy(PropagationPolicy.ParentElementOnly);
		listener.onStartElement(buildStartElement("<div>"));
		listener.onEndElement(buildEndElement("</span>"));
	}
	
	public static class MockFilteringNodeListener extends FilteringNodeListener {
		
		public boolean includeElement = true;
		public boolean includeText = true;
		
		public MockFilteringNodeListener(NodeListener listener) {
			super(listener);
		}

		@Override
		protected boolean include(Text text) {
			return includeText;
		}

		public MockFilteringNodeListener(Collection<NodeListener> listeners) {
			super(listeners);
		}

		@Override
		protected boolean include(StartElement start) {
			return includeElement;
		}
	}		
}
