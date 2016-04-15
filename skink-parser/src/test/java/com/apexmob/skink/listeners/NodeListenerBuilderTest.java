package com.apexmob.skink.listeners;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import java.util.regex.Pattern;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.apexmob.skink.Data;
import com.apexmob.skink.EndElement;
import com.apexmob.skink.MockDataEventManager;
import com.apexmob.skink.MockNodeListener;
import com.apexmob.skink.NodeListener;
import com.apexmob.skink.ParsingTest;
import com.apexmob.skink.StartElement;
import com.apexmob.skink.Text;

public class NodeListenerBuilderTest extends ParsingTest {
	
	@Rule
	public ExpectedException thrown= ExpectedException.none();
	
	MockNodeListener mock1 = null;
	MockNodeListener mock2 = null;
	MockNodeListener mock3 = null;
	
	MockTagFilteringRule tagRule1 = null;
	MockTagFilteringRule tagRule2 = null;
	MockTagFilteringRule tagRule3 = null;
	
	MockTextFilteringRule textRule1 = null;
	MockTextFilteringRule textRule2 = null;
	
	@Before
	public void setUp() {
		mock1 = new  MockNodeListener();
		mock2 = new  MockNodeListener();
		mock3 = new  MockNodeListener();
		
		tagRule1 = new MockTagFilteringRule();
		tagRule2 = new MockTagFilteringRule();
		tagRule3 = new MockTagFilteringRule();
		
		textRule1 = new MockTextFilteringRule();
		textRule2 = new MockTextFilteringRule();
	}
	
	@After
	public void tearDown() {
		mock1 = null;
		mock2 = null;
		mock3 = null;
		
		tagRule1 = null;
		tagRule2 = null;
		tagRule3 = null;
		
		textRule1 = null;
		textRule2 = null;
	}
	
	@Test
	public void noRulesPassesEverythingThru() {
		NodeListener listener = NodeListenerBuilder.builder()
				.addListener(mock1)
				.build();
		
		listener.onStartElement(buildStartElement("<div>"));
		listener.onText(buildText("test"));
		listener.onEndElement(buildEndElement("</div>"));
		
		assertEquals(1, mock1.getStartElementCount());
		assertEquals(1, mock1.getEndElementCount());
		assertEquals(1, mock1.getTextCount());
	}
	
	@Test
	public void includeElementRulePassesEverythingThru() {
		NodeListener listener = NodeListenerBuilder.builder()
				.addTagRule(tagRule1)
				.addListener(mock1)
				.build();
		
		tagRule1.includeElement = true;
		
		listener.onStartElement(buildStartElement("<div>"));
		listener.onText(buildText("test"));
		listener.onEndElement(buildEndElement("</div>"));
		
		assertEquals(1, mock1.getStartElementCount());
		assertEquals(1, mock1.getEndElementCount());
		assertEquals(1, mock1.getTextCount());
	}
	
	@Test
	public void excludeElementRulePassesNothingThru() {
		NodeListener listener = NodeListenerBuilder.builder()
				.addTagRule(tagRule1)
				.addListener(mock1)
				.build();
		
		tagRule1.includeElement = false;
		
		listener.onStartElement(buildStartElement("<div>"));
		listener.onText(buildText("test"));
		listener.onEndElement(buildEndElement("</div>"));
		
		assertEquals(0, mock1.getStartElementCount());
		assertEquals(0, mock1.getEndElementCount());
		assertEquals(0, mock1.getTextCount());
	}
	
	@Test
	public void includeTextRulePassesEverythingThru() {
		NodeListener listener = NodeListenerBuilder.builder()
				.addTextRule(textRule1)
				.addListener(mock1)
				.build();
		
		textRule1.includeText = true;
		
		listener.onStartElement(buildStartElement("<div>"));
		listener.onText(buildText("test"));
		listener.onEndElement(buildEndElement("</div>"));
		
		assertEquals(1, mock1.getStartElementCount());
		assertEquals(1, mock1.getEndElementCount());
		assertEquals(1, mock1.getTextCount());
	}
	
	@Test
	public void excludeTextRulePassesOnlyElementsThru() {
		NodeListener listener = NodeListenerBuilder.builder()
				.addTextRule(textRule1)
				.addListener(mock1)
				.build();
		
		textRule1.includeText = false;
		
		listener.onStartElement(buildStartElement("<div>"));
		listener.onText(buildText("test"));
		listener.onEndElement(buildEndElement("</div>"));
		
		assertEquals(1, mock1.getStartElementCount());
		assertEquals(1, mock1.getEndElementCount());
		assertEquals(0, mock1.getTextCount());
	}
	
	@Test
	public void includeElementRulesPassesEverythingThru() {
		NodeListener listener = NodeListenerBuilder.builder()
				.addTagRule(tagRule1)
				.addTagRule(tagRule2)
				.addListener(mock1)
				.build();
		
		tagRule1.includeElement = true;
		tagRule2.includeElement = true;
		
		listener.onStartElement(buildStartElement("<div>"));
		listener.onText(buildText("test"));
		listener.onEndElement(buildEndElement("</div>"));
		
		assertEquals(1, mock1.getStartElementCount());
		assertEquals(1, mock1.getEndElementCount());
		assertEquals(1, mock1.getTextCount());
	}
	
	@Test
	public void mixedElementRulesPassesNothingThru() {
		NodeListener listener = NodeListenerBuilder.builder()
				.addTagRule(tagRule1)
				.addTagRule(tagRule2)
				.addListener(mock1)
				.build();
		
		tagRule1.includeElement = true;
		tagRule2.includeElement = false;
		
		listener.onStartElement(buildStartElement("<div>"));
		listener.onText(buildText("test"));
		listener.onEndElement(buildEndElement("</div>"));
		
		assertEquals(0, mock1.getStartElementCount());
		assertEquals(0, mock1.getEndElementCount());
		assertEquals(0, mock1.getTextCount());
	}
	
	@Test
	public void includeTextRulesPassesEverythingThru() {
		NodeListener listener = NodeListenerBuilder.builder()
				.addTextRule(textRule1)
				.addTextRule(textRule2)
				.addListener(mock1)
				.build();
		
		textRule1.includeText = true;
		textRule2.includeText = true;
		
		listener.onStartElement(buildStartElement("<div>"));
		listener.onText(buildText("test"));
		listener.onEndElement(buildEndElement("</div>"));
		
		assertEquals(1, mock1.getStartElementCount());
		assertEquals(1, mock1.getEndElementCount());
		assertEquals(1, mock1.getTextCount());
	}
	
	@Test
	public void mixedTextRulesPassesOnlyElementsThru() {
		NodeListener listener = NodeListenerBuilder.builder()
				.addTextRule(textRule1)
				.addTextRule(textRule2)
				.addListener(mock1)
				.build();
		
		textRule1.includeText = true;
		textRule2.includeText = false;
		
		listener.onStartElement(buildStartElement("<div>"));
		listener.onText(buildText("test"));
		listener.onEndElement(buildEndElement("</div>"));
		
		assertEquals(1, mock1.getStartElementCount());
		assertEquals(1, mock1.getEndElementCount());
		assertEquals(0, mock1.getTextCount());
	}
	
	@Test
	public void childAddsSecondLayerElementFilter() {
		NodeListener listener = NodeListenerBuilder.builder()
				.addTagRule(tagRule1)
				.addListener(mock1)
				.addChildListener()
					.addTagRule(tagRule2)
					.addListener(mock2)
				.build();
		
		tagRule1.includeElement = false;
		tagRule2.includeElement = true;
		
		listener.onStartElement(buildStartElement("<div>"));
		tagRule1.includeElement = true;
		listener.onStartElement(buildStartElement("<span>"));
		listener.onText(buildText("test"));
		listener.onEndElement(buildEndElement("</span>"));
		tagRule1.includeElement = false;
		listener.onEndElement(buildEndElement("</div>"));
		
		assertEquals(1, mock1.getStartElementCount());
		assertEquals(1, mock1.getEndElementCount());
		assertEquals(1, mock1.getTextCount());
		
		assertEquals(1, mock2.getStartElementCount());
		assertEquals(1, mock2.getEndElementCount());
		assertEquals(1, mock2.getTextCount());
	}
	
	@Test
	public void childAddsSecondLayerTextFilter() {
		NodeListener listener = NodeListenerBuilder.builder()
				.addTextRule(textRule1)
				.addListener(mock1)
				.addChildListener()
					.addTextRule(textRule2)
					.addListener(mock2)
					.buildChild()
				.build();
		
		textRule1.includeText = false;
		textRule2.includeText = true;
		
		listener.onStartElement(buildStartElement("<div>"));
		listener.onText(buildText("test1"));
		textRule1.includeText = true;
		listener.onStartElement(buildStartElement("<span>"));
		listener.onText(buildText("test2"));
		listener.onEndElement(buildEndElement("</span>"));
		textRule1.includeText = false;
		listener.onText(buildText("test3"));
		listener.onEndElement(buildEndElement("</div>"));
		
		assertEquals(2, mock1.getStartElementCount());
		assertEquals(2, mock1.getEndElementCount());
		assertEquals(1, mock1.getTextCount());
		
		assertEquals(2, mock2.getStartElementCount());
		assertEquals(2, mock2.getEndElementCount());
		assertEquals(1, mock2.getTextCount());
	}

	@Test
	public void canAddSibling() {
		NodeListener listener = NodeListenerBuilder.builder()
				.addTagRule(tagRule1)
				.addListener(mock1)
				.addChildListener()
					.addTagRule(tagRule2)
					.addListener(mock2)
					.buildChild()
				.addChildListener()
					.addTagRule(tagRule3)
					.addListener(mock3)
					.buildChild()
				.build();
		
		tagRule1.includeElement = false;
		tagRule2.includeElement = false;
		tagRule3.includeElement = true;
		
		listener.onStartElement(buildStartElement("<div>"));
		tagRule1.includeElement = true;
		listener.onStartElement(buildStartElement("<span>"));
		listener.onText(buildText("test"));
		listener.onEndElement(buildEndElement("</span>"));
		tagRule1.includeElement = false;
		listener.onEndElement(buildEndElement("</div>"));
		
		assertEquals(1, mock1.getStartElementCount());
		assertEquals(1, mock1.getEndElementCount());
		assertEquals(1, mock1.getTextCount());
		
		assertEquals(0, mock2.getStartElementCount());
		assertEquals(0, mock2.getEndElementCount());
		assertEquals(0, mock2.getTextCount());
		
		assertEquals(1, mock3.getStartElementCount());
		assertEquals(1, mock3.getEndElementCount());
		assertEquals(1, mock3.getTextCount());
	}
	
	@Test
	public void filterToAllElementsWithNameWorks() {
		NodeListener listener = NodeListenerBuilder.builder()
				.filterToAllTagsWithName("span")
				.addListener(mock1)
				.build();
		
		listener.onStartElement(buildStartElement("<div>"));
		listener.onStartElement(buildStartElement("<span>"));
		listener.onText(buildText("test"));
		listener.onEndElement(buildEndElement("</span>"));
		listener.onEndElement(buildEndElement("</div>"));
		
		assertEquals(1, mock1.getStartElementCount());
		assertEquals(1, mock1.getEndElementCount());
		assertEquals(1, mock1.getTextCount());
	}
	
	@Test
	public void filterToAllElementsWithAttributeEqualsWorks() {
		NodeListener listener = NodeListenerBuilder.builder()
				.filterToAllTagsWithAttribute("a", "b")
				.addListener(mock1)
				.build();
		
		listener.onStartElement(buildStartElement("<div>"));
		listener.onStartElement(buildStartElement("<div a=\"b\">"));
		listener.onText(buildText("test"));
		listener.onEndElement(buildEndElement("</div>"));
		listener.onEndElement(buildEndElement("</div>"));
		
		assertEquals(1, mock1.getStartElementCount());
		assertEquals(1, mock1.getEndElementCount());
		assertEquals(1, mock1.getTextCount());
	}
	
	@Test
	public void canSetAndGetDataEventManager() {
		MockDataEventManager mock = new MockDataEventManager();
		
		NodeListenerBuilder builder = NodeListenerBuilder.builder()
			.setDataEventManager(mock);
		
		assertSame(mock, builder.getDataEventManager());
	}
	
	@Test
	public void addChildListenerSendsEventsToChild() {
		NodeListenerWrapper wrapper = new NodeListenerWrapper();
		wrapper.addListener(mock1);
		
		NodeListener listener = NodeListenerBuilder.builder()
			.addChildListener(wrapper)
			.build();
		
		StartElement startTag = buildStartElement("<div>");
		Text text = buildText("test");
		EndElement endTag = buildEndElement("</div>");
		
		listener.onStartElement(startTag);
		listener.onText(text);
		listener.onEndElement(endTag);
		
		assertEquals(startTag.getContent(), mock1.getNodeStack().get(0).getContent());
		assertEquals(text.getContent(), mock1.getNodeStack().get(1).getContent());
		assertEquals(endTag.getContent(), mock1.getNodeStack().get(2).getContent());
	}
	
	@Test
	public void buildChildSendsNextRulesAndListenersToParent() {
		tagRule1.includeElement = true;
		
		NodeListenerWrapper wrapper = new NodeListenerWrapper();
		wrapper.addListener(mock1);
		
		NodeListener listener = NodeListenerBuilder.builder()
			.addChildListener(wrapper)
				.addTagRule(tagRule1)
				.addListener(mock2)
				.buildChild()
			.addListener(mock3)
			.build();
		
		StartElement start = buildStartElement("<div>");
		EndElement end = buildEndElement("</div>");
		
		listener.onStartElement(start);
		listener.onEndElement(end);
		tagRule1.includeElement = false;
		listener.onStartElement(start);
		listener.onEndElement(end);
		
		assertEquals(2, mock1.getStartElementCount());
		assertEquals(1, mock2.getStartElementCount());
		assertEquals(2, mock3.getStartElementCount());
	}
	
	@Test
	public void readTextSendDataReadEventWithId() {
		MockDataEventManager mockEvtMgr = new MockDataEventManager();
		
		NodeListener listener = NodeListenerBuilder.builder()
			.setDataEventManager(mockEvtMgr)
			.readText(1)
			.build();
			
		StartElement start = buildStartElement("<div>");
		Text text = buildText("test");
		EndElement end = buildEndElement("</div>");
			
		listener.onStartElement(start);
		listener.onText(text);
		listener.onEndElement(end);
		
		Data evt = mockEvtMgr.getDataReadEventStack().get(0);
		assertEquals(1, evt.getFieldId());
		assertEquals("test", evt.getData());
		assertNotNull(evt.getSource());
	}
	
	@Test
	public void readAttributeSendDataReadEventWithId() {
		MockDataEventManager mockEvtMgr = new MockDataEventManager();
		
		NodeListener listener = NodeListenerBuilder.builder()
			.setDataEventManager(mockEvtMgr)
			.readAttribute(1, "a")
			.build();
			
		StartElement start = buildStartElement("<div a=\"b\">");
		EndElement end = buildEndElement("</div>");
			
		listener.onStartElement(start);
		listener.onEndElement(end);
		
		Data evt = mockEvtMgr.getDataReadEventStack().get(0);
		assertEquals(1, evt.getFieldId());
		assertEquals("b", evt.getData());
		assertNotNull(evt.getSource());
	}
	
	@Test
	public void setDataEventManagerThrowsIllegalArgumentExceptionWhenEventManagerIsNull() {
		thrown.expect(IllegalArgumentException.class);
		
		NodeListenerBuilder.builder().setDataEventManager(null);
	}
	
	@Test
	public void buildChildThrowsIllegalStateExceptionWhenWorkingOnTheRoot() {
		thrown.expect(IllegalStateException.class);
		
		NodeListenerBuilder.builder().buildChild();
	}
	
	@Test
	public void readTextWithIdThrowsIllegalStateExceptionWhenEventManagerIsNull() {
		thrown.expect(IllegalStateException.class);
		
		NodeListenerBuilder builder = NodeListenerBuilder.builder();
		builder.readText(1);
	}
	
	@Test
	public void readAttributeWithIdThrowsIllegalStateExceptionWhenEventManagerIsNull() {
		thrown.expect(IllegalStateException.class);
		
		NodeListenerBuilder builder = NodeListenerBuilder.builder();
		builder.readAttribute(1, "name");
	}

	@Test
	public void findWithinTextWithIdThrowsIllegalStateExceptionWhenEventManagerIsNull() {
		thrown.expect(IllegalStateException.class);
		
		NodeListenerBuilder builder = NodeListenerBuilder.builder();
		builder.findWithinText(1, Pattern.compile(""));
	}
	
	@Test
	public void findWithinAttributeWithIdThrowsIllegalStateExceptionWhenEventManagerIsNull() {
		thrown.expect(IllegalStateException.class);
		
		NodeListenerBuilder builder = NodeListenerBuilder.builder();
		builder.findWithinAttribute(1, "name", Pattern.compile(""));
	}
	
	@Test
	public void matchWithinTextWithIdThrowsIllegalStateExceptionWhenEventManagerIsNull() {
		thrown.expect(IllegalStateException.class);
		
		NodeListenerBuilder builder = NodeListenerBuilder.builder();
		builder.matchWithinText(1, Pattern.compile(""), 1);
	}
	
	@Test
	public void matchWithinAttributeWithIdThrowsIllegalStateExceptionWhenEventManagerIsNull() {
		thrown.expect(IllegalStateException.class);
		
		NodeListenerBuilder builder = NodeListenerBuilder.builder();
		builder.matchWithinAttribute(1, "name", Pattern.compile(""), 1);
	}
	
	@Test
	public void manageObjectLifecyleThrowsIllegalStateExceptionWhenEventManagerIsNull() {
		thrown.expect(IllegalStateException.class);
		
		NodeListenerBuilder builder = NodeListenerBuilder.builder();
		builder.manageObjectLifecyle(1, 2);
	}
	
	@Test
	public void manageObjectLifecyleAddsListenerCorrectly() {
		MockDataEventManager evtMgr = new MockDataEventManager();
		
		NodeListener listener = NodeListenerBuilder.builder()
				.setDataEventManager(evtMgr)
				.manageObjectLifecyle(1, 2)
				.build();
		listener.onStartElement(buildStartElement("<div>"));
		
		assertEquals(1, evtMgr.getDataReadEventStack().size());
		assertEquals(1, evtMgr.getDataReadEventStack().get(0).getFieldId());
		
		listener.onStartElement(buildStartElement("<span>"));
		
		assertEquals(1, evtMgr.getDataReadEventStack().size());
		
		listener.onEndElement(buildEndElement("</span>"));
		
		assertEquals(1, evtMgr.getDataReadEventStack().size());
		
		listener.onEndElement(buildEndElement("</div>"));
		
		assertEquals(2, evtMgr.getDataReadEventStack().size());
		assertEquals(2, evtMgr.getDataReadEventStack().get(1).getFieldId());
	}
	
	@Test
	public void filterToElementWithIndexWorksOnFirstIndex() {
		NodeListener listener = NodeListenerBuilder.builder()
			.filterToTagWithIndex(1)
			.addListener(mock1)
			.build();
		
		listener.onStartElement(buildStartElement("<div>"));
		listener.onEndElement(buildEndElement("</div>"));
		
		assertEquals(2, mock1.getNodeStack().size());
		assertStartElement(mock1, 0, "div");
		assertEndElement(mock1, 1, "div");
		
		listener.onStartElement(buildStartElement("<div>"));
		listener.onEndElement(buildEndElement("</div>"));
		
		assertEquals(2, mock1.getNodeStack().size());
	}
	
	@Test
	public void filterToElementWithIndexWorksOnNonFirstIndex() {
		NodeListener listener = NodeListenerBuilder.builder()
				.filterToTagWithIndex(2)
				.addListener(mock1)
				.build();
			
		listener.onStartElement(buildStartElement("<div>"));
		listener.onEndElement(buildEndElement("</div>"));
		
		assertEquals(0, mock1.getNodeStack().size());
		
		listener.onStartElement(buildStartElement("<div>"));
		listener.onEndElement(buildEndElement("</div>"));
		
		assertEquals(2, mock1.getNodeStack().size());
		assertStartElement(mock1, 0, "div");
		assertEndElement(mock1, 1, "div");
	}
	
	@Test
	public void filterToElementsBetweenIndexesWorksOnFirstIndex() {
		NodeListener listener = NodeListenerBuilder.builder()
				.filterToTagsBetweenIndexes(1, 2)
				.addListener(mock1)
				.build();
			
		listener.onStartElement(buildStartElement("<div>"));
		listener.onEndElement(buildEndElement("</div>"));
		
		assertEquals(2, mock1.getNodeStack().size());
		assertStartElement(mock1, 0, "div");
		assertEndElement(mock1, 1, "div");
		
		listener.onStartElement(buildStartElement("<div>"));
		listener.onEndElement(buildEndElement("</div>"));
		
		assertEquals(4, mock1.getNodeStack().size());
		assertStartElement(mock1, 2, "div");
		assertEndElement(mock1, 3, "div");
		
		listener.onStartElement(buildStartElement("<div>"));
		listener.onEndElement(buildEndElement("</div>"));
		
		assertEquals(4, mock1.getNodeStack().size());
	}
	
	@Test
	public void filterToElementsBetweenIndexesWorksOnNonFirstIndex() {
		NodeListener listener = NodeListenerBuilder.builder()
				.filterToTagsBetweenIndexes(2, 3)
				.addListener(mock1)
				.build();
			
		listener.onStartElement(buildStartElement("<div>"));
		listener.onEndElement(buildEndElement("</div>"));
		
		assertEquals(0, mock1.getNodeStack().size());
		
		listener.onStartElement(buildStartElement("<div>"));
		listener.onEndElement(buildEndElement("</div>"));
		
		assertEquals(2, mock1.getNodeStack().size());
		assertStartElement(mock1, 0, "div");
		assertEndElement(mock1, 1, "div");
		
		listener.onStartElement(buildStartElement("<div>"));
		listener.onEndElement(buildEndElement("</div>"));
		
		assertEquals(4, mock1.getNodeStack().size());
		assertStartElement(mock1, 2, "div");
		assertEndElement(mock1, 3, "div");
	}
	
	@Test
	public void findWithinTextThrowsIllegalStateExceptionWhenEventManagerIsNull() {
		thrown.expect(IllegalStateException.class);
		
		NodeListenerBuilder.builder().findWithinText(0, Pattern.compile(""));
	}
	
	@Test
	public void findWithinTextFindsText() {
		MockDataEventManager evtMgr = new MockDataEventManager();
		
		NodeListener listener = NodeListenerBuilder.builder()
			.setDataEventManager(evtMgr)
			.findWithinText(1, Pattern.compile("(es)"))
			.build();
		
		listener.onStartElement(buildStartElement("<div>"));
		listener.onText(buildText("test"));
		listener.onEndElement(buildEndElement("</div>"));
		
		assertEquals(1, evtMgr.getDataReadEventStack().size());
		assertEquals(1, evtMgr.getDataReadEventStack().get(0).getFieldId());
		assertEquals("es", evtMgr.getDataReadEventStack().get(0).getData());
		assertNotNull(evtMgr.getDataReadEventStack().get(0).getSource());
	}
	
	@Test
	public void findWithinTextDoesntFindText() {
		MockDataEventManager evtMgr = new MockDataEventManager();
		
		NodeListener listener = NodeListenerBuilder.builder()
			.setDataEventManager(evtMgr)
			.findWithinText(1, Pattern.compile("(es)"))
			.build();
		
		listener.onStartElement(buildStartElement("<div>"));
		listener.onText(buildText("text"));
		listener.onEndElement(buildEndElement("</div>"));
		
		assertEquals(0, evtMgr.getDataReadEventStack().size());
	}
	
	@Test
	public void findWithinAttributeThrowsIllegalStateExceptionWhenEventManagerIsNull() {
		thrown.expect(IllegalStateException.class);
		
		NodeListenerBuilder.builder().findWithinAttribute(0, "", Pattern.compile(""));
	}
	
	@Test
	public void findWithinAttributeFindsText() {
		MockDataEventManager evtMgr = new MockDataEventManager();
		
		NodeListener listener = NodeListenerBuilder.builder()
			.setDataEventManager(evtMgr)
			.findWithinAttribute(1, "a", Pattern.compile("(es)"))
			.build();
		
		listener.onStartElement(buildStartElement("<div a=\"test\">"));
		listener.onEndElement(buildEndElement("</div>"));
		
		assertEquals(1, evtMgr.getDataReadEventStack().size());
		assertEquals(1, evtMgr.getDataReadEventStack().get(0).getFieldId());
		assertEquals("es", evtMgr.getDataReadEventStack().get(0).getData());
		assertNotNull(evtMgr.getDataReadEventStack().get(0).getSource());
	}
	
	@Test
	public void findWithinAttributeDoesntFindText() {
		MockDataEventManager evtMgr = new MockDataEventManager();
		
		NodeListener listener = NodeListenerBuilder.builder()
			.setDataEventManager(evtMgr)
			.findWithinAttribute(1, "a", Pattern.compile("(es)"))
			.build();
		
		listener.onStartElement(buildStartElement("<div a=\"text\">"));
		listener.onEndElement(buildEndElement("</div>"));
		
		assertEquals(0, evtMgr.getDataReadEventStack().size());
	}
	
	@Test
	public void matchWithinTextThrowsIllegalStateExceptionWhenEventManagerIsNull() {
		thrown.expect(IllegalStateException.class);
		
		NodeListenerBuilder.builder().matchWithinText(0, Pattern.compile(""), 1);
	}
	
	@Test
	public void matchWithinTextFindsText() {
		MockDataEventManager evtMgr = new MockDataEventManager();
		
		NodeListener listener = NodeListenerBuilder.builder()
			.setDataEventManager(evtMgr)
			.matchWithinText(1, Pattern.compile(".*(es).*"), 1)
			.build();
		
		listener.onStartElement(buildStartElement("<div>"));
		listener.onText(buildText("test"));
		listener.onEndElement(buildEndElement("</div>"));
		
		assertEquals(1, evtMgr.getDataReadEventStack().size());
		assertEquals(1, evtMgr.getDataReadEventStack().get(0).getFieldId());
		assertEquals("es", evtMgr.getDataReadEventStack().get(0).getData());
		assertNotNull(evtMgr.getDataReadEventStack().get(0).getSource());
	}
	
	@Test
	public void matchWithinTextDoesntFindText() {
		MockDataEventManager evtMgr = new MockDataEventManager();
		
		NodeListener listener = NodeListenerBuilder.builder()
			.setDataEventManager(evtMgr)
			.matchWithinText(1, Pattern.compile(".*(es).*"), 1)
			.build();
		
		listener.onStartElement(buildStartElement("<div>"));
		listener.onText(buildText("text"));
		listener.onEndElement(buildEndElement("</div>"));
		
		assertEquals(0, evtMgr.getDataReadEventStack().size());
	}
	
	@Test
	public void matchWithinAttributeThrowsIllegalStateExceptionWhenEventManagerIsNull() {
		thrown.expect(IllegalStateException.class);
		
		NodeListenerBuilder.builder().matchWithinAttribute(0, "", Pattern.compile(""), 1);
	}
	
	@Test
	public void matchWithinAttributeFindsText() {
		MockDataEventManager evtMgr = new MockDataEventManager();
		
		NodeListener listener = NodeListenerBuilder.builder()
			.setDataEventManager(evtMgr)
			.matchWithinAttribute(1, "a", Pattern.compile(".*(es).*"), 1)
			.build();
		
		listener.onStartElement(buildStartElement("<div a=\"test\">"));
		listener.onEndElement(buildEndElement("</div>"));
		
		assertEquals(1, evtMgr.getDataReadEventStack().size());
		assertEquals(1, evtMgr.getDataReadEventStack().get(0).getFieldId());
		assertEquals("es", evtMgr.getDataReadEventStack().get(0).getData());
		assertNotNull(evtMgr.getDataReadEventStack().get(0).getSource());
	}
	
	@Test
	public void matchWithinAttributeDoesntFindText() {
		MockDataEventManager evtMgr = new MockDataEventManager();
		
		NodeListener listener = NodeListenerBuilder.builder()
			.setDataEventManager(evtMgr)
			.matchWithinAttribute(1, "a", Pattern.compile(".*(es).*"), 1)
			.build();
		
		listener.onStartElement(buildStartElement("<div a=\"text\">"));
		listener.onEndElement(buildEndElement("</div>"));
		
		assertEquals(0, evtMgr.getDataReadEventStack().size());
	}
	
	@Test
	public void forceElementsClosedThrowsIllegalStateExceptionWhenEventManagerIsNull() {
		thrown.expect(IllegalArgumentException.class);
		
		NodeListenerBuilder.builder().forceTagsClosed(null);
	}
	
	@Test
	public void forceElementsClosedClosesNoElements() {
		NodeListener listener = NodeListenerBuilder.builder()
			.forceTagsClosed(new String[]{})
			.addListener(mock1)
			.build();
		
		listener.onStartElement(buildStartElement("<div>"));
		
		assertEquals(1, mock1.getNodeStack().size());
		assertStartElement(mock1, 0, "div");
	}
	
	@Test
	public void forceElementsClosedClosesOneElement() {
		NodeListener listener = NodeListenerBuilder.builder()
			.forceTagsClosed(new String[]{"div"})
			.addListener(mock1)
			.build();
		
		listener.onStartElement(buildStartElement("<div>"));
		listener.onStartElement(buildStartElement("<span>"));
		
		assertEquals(3, mock1.getNodeStack().size());
		assertStartElement(mock1, 0, "div");
		assertEndElement(mock1, 1, "div");
		assertStartElement(mock1, 2, "span");
	}
	
	@Test
	public void forceElementsClosedClosesMultipleElements() {
		NodeListener listener = NodeListenerBuilder.builder()
			.forceTagsClosed(new String[]{"div", "span"})
			.addListener(mock1)
			.build();
		
		listener.onStartElement(buildStartElement("<div>"));
		listener.onStartElement(buildStartElement("<span>"));
		
		assertEquals(4, mock1.getNodeStack().size());
		assertStartElement(mock1, 0, "div");
		assertEndElement(mock1, 1, "div");
		assertStartElement(mock1, 2, "span");
		assertEndElement(mock1, 3, "span");
	}
	
	@Test
	public void forceElementsClosedClosesMultipleOfTheSameElement() {
		NodeListener listener = NodeListenerBuilder.builder()
			.forceTagsClosed(new String[]{"div"})
			.addListener(mock1)
			.build();
		
		listener.onStartElement(buildStartElement("<div>"));
		listener.onStartElement(buildStartElement("<div>"));
		listener.onStartElement(buildStartElement("<span>"));
		
		assertEquals(5, mock1.getNodeStack().size());
		assertStartElement(mock1, 0, "div");
		assertEndElement(mock1, 1, "div");
		assertStartElement(mock1, 2, "div");
		assertEndElement(mock1, 3, "div");
		assertStartElement(mock1, 4, "span");
	}
	
	@Test
	public void debugDoesNotCauseAnError() {
		NodeListener listener = NodeListenerBuilder.builder()
			.debug()
			.build();
		
		listener.onStartElement(buildStartElement("<div>"));
		listener.onText(buildText("test"));
		listener.onEndElement(buildEndElement("</div>"));
	}
	
	@Test
	public void filterToTextMatchingFiltersNonMatchingText() {
		NodeListener listener = NodeListenerBuilder.builder()
			.filterToTextMatching(Pattern.compile(".*es.*"))
			.addListener(mock1)
			.build();
		
		listener.onStartElement(buildStartElement("<div>"));
		listener.onText(buildText("text"));
		listener.onEndElement(buildEndElement("</div>"));
		
		assertEquals(2, mock1.getNodeStack().size());
		assertStartElement(mock1, 0, "div");
		assertEndElement(mock1, 1, "div");
	}
	
	@Test
	public void filterToTextMatchingMatchesMatchingText() {
		NodeListener listener = NodeListenerBuilder.builder()
			.filterToTextMatching(Pattern.compile(".*es.*"))
			.addListener(mock1)
			.build();
		
		listener.onStartElement(buildStartElement("<div>"));
		listener.onText(buildText("test"));
		listener.onEndElement(buildEndElement("</div>"));
		
		assertEquals(3, mock1.getNodeStack().size());
		assertStartElement(mock1, 0, "div");
		assertText(mock1, 1, "test");
		assertEndElement(mock1, 2, "div");
	}
	
	@Test
	public void filterToAllElementsWithAttributeContainingFiltersNonMatchingAttributeText() {
		NodeListener listener = NodeListenerBuilder.builder()
			.filterToAllTagsWithAttributeContaining("a", "es")
			.addListener(mock1)
			.build();
		
		listener.onStartElement(buildStartElement("<div a=\"text\">"));
		listener.onEndElement(buildEndElement("</div>"));
		
		assertEquals(0, mock1.getNodeStack().size());
	}
	
	@Test
	public void filterToAllElementsWithAttributeContainingMatchesAttributeText() {
		NodeListener listener = NodeListenerBuilder.builder()
			.filterToAllTagsWithAttributeContaining("a", "es")
			.addListener(mock1)
			.build();
		
		listener.onStartElement(buildStartElement("<div a=\"test\">"));
		listener.onEndElement(buildEndElement("</div>"));
		
		assertEquals(2, mock1.getNodeStack().size());
		assertStartElement(mock1, 0, "div");
		assertEndElement(mock1, 1, "div");
	}
	
	@Test
	public void setPropagationPolicyThrowsIllegalStateExceptionWhenPolicyIsNull() {
		thrown.expect(IllegalArgumentException.class);
		
		NodeListenerBuilder.builder().setPropagationPolicy(null);
	}
	
	@Test
	public void setPropagationPolicyAltersPropagation() {
		NodeListener listener = NodeListenerBuilder.builder()
			.setPropagationPolicy(PropagationPolicy.ElementsOnly)
			.addListener(mock1)
			.build();
		
		listener.onStartElement(buildStartElement("<div>"));
		listener.onText(buildText("test"));
		listener.onEndElement(buildEndElement("</div>"));
		
		assertEquals(2, mock1.getNodeStack().size());
		assertStartElement(mock1, 0, "div");
		assertEndElement(mock1, 1, "div");
	}
}
