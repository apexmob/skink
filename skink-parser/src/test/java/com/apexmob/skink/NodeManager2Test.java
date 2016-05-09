package com.apexmob.skink;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public abstract class NodeManager2Test extends ParsingTest {
	
	protected abstract NodeManager2 buildNodeManager();
	
	private NodeManager2 nodeMgr;
	private MockNodeListener mockListener;
	private MockNodeListener mockListener2;
	
	@Rule
	public ExpectedException thrown= ExpectedException.none();
	
	@Before
	public void setUp() {
		nodeMgr = buildNodeManager();
		mockListener = new MockNodeListener();
		mockListener2 = new MockNodeListener();
	}
	
	@After
	public void tearDown() {
		nodeMgr = null;
		mockListener = null;
		mockListener2 = null;
	}
	
	//START ELEMENT TESTS
	@Test
	public void addOnStartElementListenerThrowsIllegalArgumentExceptionWhenListenerIsNull() {
		thrown.expect(IllegalArgumentException.class);
		
		nodeMgr.addOnStartElementListener(null);
	}
	
	@Test
	public void removeOnStartElementListenerThrowsIllegalArgumentExceptionWhenListenerIsNull() {
		thrown.expect(IllegalArgumentException.class);
		
		nodeMgr.removeOnStartElementListener(null);
	}
	
	@Test
	public void startElementThrowsIllegalArgumentExceptionWhenEventIsNull() {
		thrown.expect(IllegalArgumentException.class);
		
		nodeMgr.startElement(null);
	}
	
	@Test
	public void oneOnStartElementListenerReceivesOneEventWhenAdded() {
		nodeMgr.addOnStartElementListener(mockListener);
		
		nodeMgr.startElement(buildStartElement("<div>"));
		
		assertEquals(1, mockListener.getNodeStack().size());
		assertStartElement(mockListener, 0, "div");
	}
	
	@Test
	public void oneOnStartElementListenerReceivesMultipleEventsWhenAdded() {
		nodeMgr.addOnStartElementListener(mockListener);
		
		nodeMgr.startElement(buildStartElement("<div>"));
		nodeMgr.startElement(buildStartElement("<span>"));
		
		assertEquals(2, mockListener.getNodeStack().size());
		assertStartElement(mockListener, 0, "div");
		assertStartElement(mockListener, 1, "span");
	}
	
	@Test
	public void multipleOnStartElementListenersReceiveOneEventWhenAdded() {
		nodeMgr.addOnStartElementListener(mockListener);
		nodeMgr.addOnStartElementListener(mockListener2);
		
		nodeMgr.startElement(buildStartElement("<div>"));
		
		assertEquals(1, mockListener.getNodeStack().size());
		assertStartElement(mockListener, 0, "div");
		
		assertEquals(1, mockListener2.getNodeStack().size());
		assertStartElement(mockListener2, 0, "div");
	}
	
	@Test
	public void multipleOnStartElementListenersReceiveMultipleEventsWhenAdded() {
		nodeMgr.addOnStartElementListener(mockListener);
		nodeMgr.addOnStartElementListener(mockListener2);
		
		nodeMgr.startElement(buildStartElement("<div>"));
		nodeMgr.startElement(buildStartElement("<span>"));
		
		assertEquals(2, mockListener.getNodeStack().size());
		assertStartElement(mockListener, 0, "div");
		assertStartElement(mockListener, 1, "span");
		
		assertEquals(2, mockListener2.getNodeStack().size());
		assertStartElement(mockListener2, 0, "div");
		assertStartElement(mockListener2, 1, "span");
	}
	
	@Test
	public void removeOnStartElementListenerReturnsFalseWhenListenerWasNotAdded() {
		assertFalse(nodeMgr.removeOnStartElementListener(mockListener));
	}
	
	@Test
	public void removeOnStartElementListenerReturnsFalseWhenAnotherListenerHasBeenAddedButListenerWasNotAdded() {
		nodeMgr.addOnStartElementListener(mockListener);
		
		assertFalse(nodeMgr.removeOnStartElementListener(mockListener2));
	}
	
	@Test
	public void removeOnStartElementListenerReturnsTrueWhenListenerWasAdded() {
		nodeMgr.addOnStartElementListener(mockListener);
		
		assertTrue(nodeMgr.removeOnStartElementListener(mockListener));
	}
	
	@Test
	public void removeOnStartElementListenerReturnsTrueWhenAnotherListenerHasBeenAddedAndListenerWasAdded() {
		nodeMgr.addOnStartElementListener(mockListener);
		nodeMgr.addOnStartElementListener(mockListener2);
		
		assertTrue(nodeMgr.removeOnStartElementListener(mockListener));
	}
	
	@Test
	public void oneOnStartElementListenerStopsReceivingEventsWhenRemoved() {
		nodeMgr.addOnStartElementListener(mockListener);
		
		nodeMgr.startElement(buildStartElement("<div>"));
		
		nodeMgr.removeOnStartElementListener(mockListener);
		
		nodeMgr.startElement(buildStartElement("<span>"));
		
		assertEquals(1, mockListener.getNodeStack().size());
		assertStartElement(mockListener, 0, "div");
	}
	
	@Test
	public void multipleOnStartElementListenersStopReceivingEventsWhenRemoved() {
		nodeMgr.addOnStartElementListener(mockListener);
		nodeMgr.addOnStartElementListener(mockListener2);
		
		nodeMgr.startElement(buildStartElement("<div>"));
		
		nodeMgr.removeOnStartElementListener(mockListener);
		nodeMgr.removeOnStartElementListener(mockListener2);
		
		nodeMgr.startElement(buildStartElement("<span>"));
		
		assertEquals(1, mockListener.getNodeStack().size());
		assertStartElement(mockListener, 0, "div");
		
		assertEquals(1, mockListener2.getNodeStack().size());
		assertStartElement(mockListener2, 0, "div");
	}
	
	@Test
	public void OneStartElementPassesThroughStartElementWhenNoListenersAdded() {
		nodeMgr.startElement(buildStartElement("<div>"));
		assertTrue(true);
	}
	
	@Test
	public void MultipleStartElementsPassThroughStartElementWhenNoListenersAdded() {
		nodeMgr.startElement(buildStartElement("<div>"));
		nodeMgr.startElement(buildStartElement("<span>"));
		assertTrue(true);
	}
	

	@Test
	public void addOnStartElementListenerCanAddTheSameListenerMoreThanOnce() {
		nodeMgr.addOnStartElementListener(mockListener);
		nodeMgr.addOnStartElementListener(mockListener);
	}
	
	@Test
	public void startElementSendsOneEventToTheSameListenerForEachTimeItsAdded() {
		nodeMgr.addOnStartElementListener(mockListener);
		nodeMgr.addOnStartElementListener(mockListener);
		
		nodeMgr.startElement(buildStartElement("<div>"));
		
		assertEquals(2, mockListener.getNodeStack().size());
		assertStartElement(mockListener, 0, "div");
		assertStartElement(mockListener, 1, "div");
	}
	
	@Test
	public void removeOnStartElementListenerRemovesOneListenerOccurrenceWhenListenerIsAddedMoreThanOnce() {
		nodeMgr.addOnStartElementListener(mockListener);
		nodeMgr.addOnStartElementListener(mockListener);
		
		nodeMgr.startElement(buildStartElement("<div>"));
		
		nodeMgr.removeOnStartElementListener(mockListener);
		
		nodeMgr.startElement(buildStartElement("<span>"));
		
		assertEquals(3, mockListener.getNodeStack().size());
		assertStartElement(mockListener, 0, "div");
		assertStartElement(mockListener, 1, "div");
		assertStartElement(mockListener, 2, "span");
	}
	
	@Test
	public void multipleOnStartElementListenersReceiveEventsWhenAddedButNotRemoved() {
		nodeMgr.startElement(buildStartElement("<div>"));
		
		nodeMgr.addOnStartElementListener(mockListener); //add 1
		
		nodeMgr.startElement(buildStartElement("<span>"));
		
		nodeMgr.addOnStartElementListener(mockListener2); //add 2
		
		nodeMgr.startElement(buildStartElement("<img>"));
		
		nodeMgr.removeOnStartElementListener(mockListener); //remove 1
		
		nodeMgr.startElement(buildStartElement("<br>"));
		
		nodeMgr.removeOnStartElementListener(mockListener2); //remove 2
		nodeMgr.addOnStartElementListener(mockListener); //add 1
		
		nodeMgr.startElement(buildStartElement("<input>"));
		
		assertEquals(3, mockListener.getNodeStack().size());
		assertStartElement(mockListener, 0, "span");
		assertStartElement(mockListener, 1, "img");
		assertStartElement(mockListener, 2, "input");
		
		assertEquals(2, mockListener2.getNodeStack().size());
		assertStartElement(mockListener2, 0, "img");
		assertStartElement(mockListener2, 1, "br");
	}
	
	//END ELEMENT TESTS
	@Test
	public void addOnEndElementListenerThrowsIllegalArgumentExceptionWhenListenerIsNull() {
		thrown.expect(IllegalArgumentException.class);
		
		nodeMgr.addOnEndElementListener(null);
	}
	
	@Test
	public void removeOnEndElementListenerThrowsIllegalArgumentExceptionWhenListenerIsNull() {
		thrown.expect(IllegalArgumentException.class);
		
		nodeMgr.removeOnEndElementListener(null);
	}
	
	@Test
	public void endElementThrowsIllegalArgumentExceptionWhenEventIsNull() {
		thrown.expect(IllegalArgumentException.class);
		
		nodeMgr.endElement(null);
	}
	
	@Test
	public void oneOnEndElementListenerReceivesOneEventWhenAdded() {
		nodeMgr.addOnEndElementListener(mockListener);
		
		nodeMgr.endElement(buildEndElement("<div/>"));
		
		assertEquals(1, mockListener.getNodeStack().size());
		assertEndElement(mockListener, 0, "div");
	}
	
	@Test
	public void oneOnEndElementListenerReceivesMultipleEventsWhenAdded() {
		nodeMgr.addOnEndElementListener(mockListener);
		
		nodeMgr.endElement(buildEndElement("<div/>"));
		nodeMgr.endElement(buildEndElement("<span/>"));
		
		assertEquals(2, mockListener.getNodeStack().size());
		assertEndElement(mockListener, 0, "div");
		assertEndElement(mockListener, 1, "span");
	}
	
	@Test
	public void multipleOnEndElementListenersReceiveOneEventWhenAdded() {
		nodeMgr.addOnEndElementListener(mockListener);
		nodeMgr.addOnEndElementListener(mockListener2);
		
		nodeMgr.endElement(buildEndElement("<div/>"));
		
		assertEquals(1, mockListener.getNodeStack().size());
		assertEndElement(mockListener, 0, "div");
		
		assertEquals(1, mockListener2.getNodeStack().size());
		assertEndElement(mockListener2, 0, "div");
	}
	
	@Test
	public void multipleOnEndElementListenersReceiveMultipleEventsWhenAdded() {
		nodeMgr.addOnEndElementListener(mockListener);
		nodeMgr.addOnEndElementListener(mockListener2);
		
		nodeMgr.endElement(buildEndElement("<div/>"));
		nodeMgr.endElement(buildEndElement("<span/>"));
		
		assertEquals(2, mockListener.getNodeStack().size());
		assertEndElement(mockListener, 0, "div");
		assertEndElement(mockListener, 1, "span");
		
		assertEquals(2, mockListener2.getNodeStack().size());
		assertEndElement(mockListener2, 0, "div");
		assertEndElement(mockListener2, 1, "span");
	}
	
	@Test
	public void removeOnEndElementListenerReturnsFalseWhenListenerWasNotAdded() {
		assertFalse(nodeMgr.removeOnEndElementListener(mockListener));
	}
	
	@Test
	public void removeOnEndElementListenerReturnsFalseWhenAnotherListenerHasBeenAddedButListenerWasNotAdded() {
		nodeMgr.addOnEndElementListener(mockListener);
		
		assertFalse(nodeMgr.removeOnEndElementListener(mockListener2));
	}
	
	@Test
	public void removeOnEndElementListenerReturnsTrueWhenListenerWasAdded() {
		nodeMgr.addOnEndElementListener(mockListener);
		
		assertTrue(nodeMgr.removeOnEndElementListener(mockListener));
	}
	
	@Test
	public void removeOnEndElementListenerReturnsTrueWhenAnotherListenerHasBeenAddedAndListenerWasAdded() {
		nodeMgr.addOnEndElementListener(mockListener);
		nodeMgr.addOnEndElementListener(mockListener2);
		
		assertTrue(nodeMgr.removeOnEndElementListener(mockListener));
	}
	
	@Test
	public void oneOnEndElementListenerStopsReceivingEventsWhenRemoved() {
		nodeMgr.addOnEndElementListener(mockListener);
		
		nodeMgr.endElement(buildEndElement("<div/>"));
		
		nodeMgr.removeOnEndElementListener(mockListener);
		
		nodeMgr.endElement(buildEndElement("<span/>"));
		
		assertEquals(1, mockListener.getNodeStack().size());
		assertEndElement(mockListener, 0, "div");
	}
	
	@Test
	public void multipleOnEndElementListenersStopReceivingEventsWhenRemoved() {
		nodeMgr.addOnEndElementListener(mockListener);
		nodeMgr.addOnEndElementListener(mockListener2);
		
		nodeMgr.endElement(buildEndElement("<div/>"));
		
		nodeMgr.removeOnEndElementListener(mockListener);
		nodeMgr.removeOnEndElementListener(mockListener2);
		
		nodeMgr.endElement(buildEndElement("<span/>"));
		
		assertEquals(1, mockListener.getNodeStack().size());
		assertEndElement(mockListener, 0, "div");
		
		assertEquals(1, mockListener2.getNodeStack().size());
		assertEndElement(mockListener2, 0, "div");
	}
	
	@Test
	public void OneEndElementPassesThroughEndElementWhenNoListenersAdded() {
		nodeMgr.endElement(buildEndElement("<div/>"));
		assertTrue(true);
	}
	
	@Test
	public void MultipleEndElementsPassThroughEndElementWhenNoListenersAdded() {
		nodeMgr.endElement(buildEndElement("<div/>"));
		nodeMgr.endElement(buildEndElement("<span/>"));
		assertTrue(true);
	}
	

	@Test
	public void addOnEndElementListenerCanAddTheSameListenerMoreThanOnce() {
		nodeMgr.addOnEndElementListener(mockListener);
		nodeMgr.addOnEndElementListener(mockListener);
	}
	
	@Test
	public void endElementSendsOneEventToTheSameListenerForEachTimeItsAdded() {
		nodeMgr.addOnEndElementListener(mockListener);
		nodeMgr.addOnEndElementListener(mockListener);
		
		nodeMgr.endElement(buildEndElement("<div/>"));
		
		assertEquals(2, mockListener.getNodeStack().size());
		assertEndElement(mockListener, 0, "div");
		assertEndElement(mockListener, 1, "div");
	}
	
	@Test
	public void removeOnEndElementListenerRemovesOneListenerOccurrenceWhenListenerIsAddedMoreThanOnce() {
		nodeMgr.addOnEndElementListener(mockListener);
		nodeMgr.addOnEndElementListener(mockListener);
		
		nodeMgr.endElement(buildEndElement("<div/>"));
		
		nodeMgr.removeOnEndElementListener(mockListener);
		
		nodeMgr.endElement(buildEndElement("<span/>"));
		
		assertEquals(3, mockListener.getNodeStack().size());
		assertEndElement(mockListener, 0, "div");
		assertEndElement(mockListener, 1, "div");
		assertEndElement(mockListener, 2, "span");
	}
	
	@Test
	public void multipleOnEndElementListenersReceiveEventsWhenAddedButNotRemoved() {
		nodeMgr.endElement(buildEndElement("<div/>"));
		
		nodeMgr.addOnEndElementListener(mockListener); //add 1
		
		nodeMgr.endElement(buildEndElement("<span/>"));
		
		nodeMgr.addOnEndElementListener(mockListener2); //add 2
		
		nodeMgr.endElement(buildEndElement("<img/>"));
		
		nodeMgr.removeOnEndElementListener(mockListener); //remove 1
		
		nodeMgr.endElement(buildEndElement("<br/>"));
		
		nodeMgr.removeOnEndElementListener(mockListener2); //remove 2
		nodeMgr.addOnEndElementListener(mockListener); //add 1
		
		nodeMgr.endElement(buildEndElement("<input/>"));
		
		assertEquals(3, mockListener.getNodeStack().size());
		assertEndElement(mockListener, 0, "span");
		assertEndElement(mockListener, 1, "img");
		assertEndElement(mockListener, 2, "input");
		
		assertEquals(2, mockListener2.getNodeStack().size());
		assertEndElement(mockListener2, 0, "img");
		assertEndElement(mockListener2, 1, "br");
	}
	
	//TEXT TESTS
	@Test
	public void addOnTextListenerThrowsIllegalArgumentExceptionWhenListenerIsNull() {
		thrown.expect(IllegalArgumentException.class);
		
		nodeMgr.addOnTextListener(null);
	}
	
	@Test
	public void removeOnTextListenerThrowsIllegalArgumentExceptionWhenListenerIsNull() {
		thrown.expect(IllegalArgumentException.class);
		
		nodeMgr.removeOnTextListener(null);
	}
	
	@Test
	public void textThrowsIllegalArgumentExceptionWhenEventIsNull() {
		thrown.expect(IllegalArgumentException.class);
		
		nodeMgr.text(null);
	}
	
	@Test
	public void oneOnTextListenerReceivesOneEventWhenAdded() {
		nodeMgr.addOnTextListener(mockListener);
		
		nodeMgr.text(buildText("text"));
		
		assertEquals(1, mockListener.getNodeStack().size());
		assertText(mockListener, 0, "text");
	}
	
	@Test
	public void oneOnTextListenerReceivesMultipleEventsWhenAdded() {
		nodeMgr.addOnTextListener(mockListener);
		
		nodeMgr.text(buildText("text1"));
		nodeMgr.text(buildText("text2"));
		
		assertEquals(2, mockListener.getNodeStack().size());
		assertText(mockListener, 0, "text1");
		assertText(mockListener, 1, "text2");
	}
	
	@Test
	public void multipleOnTextListenersReceiveOneEventWhenAdded() {
		nodeMgr.addOnTextListener(mockListener);
		nodeMgr.addOnTextListener(mockListener2);
		
		nodeMgr.text(buildText("text1"));
		
		assertEquals(1, mockListener.getNodeStack().size());
		assertText(mockListener, 0, "text1");
		
		assertEquals(1, mockListener2.getNodeStack().size());
		assertText(mockListener2, 0, "text1");
	}
	
	@Test
	public void multipleOnTextListenersReceiveMultipleEventsWhenAdded() {
		nodeMgr.addOnTextListener(mockListener);
		nodeMgr.addOnTextListener(mockListener2);
		
		nodeMgr.text(buildText("text1"));
		nodeMgr.text(buildText("text2"));
		
		assertEquals(2, mockListener.getNodeStack().size());
		assertText(mockListener, 0, "text1");
		assertText(mockListener, 1, "text2");
		
		assertEquals(2, mockListener2.getNodeStack().size());
		assertText(mockListener2, 0, "text1");
		assertText(mockListener2, 1, "text2");
	}
	
	@Test
	public void removeOnTextListenerReturnsFalseWhenListenerWasNotAdded() {
		assertFalse(nodeMgr.removeOnTextListener(mockListener));
	}
	
	@Test
	public void removeOnTextListenerReturnsFalseWhenAnotherListenerHasBeenAddedButListenerWasNotAdded() {
		nodeMgr.addOnTextListener(mockListener);
		
		assertFalse(nodeMgr.removeOnTextListener(mockListener2));
	}
	
	@Test
	public void removeOnTextListenerReturnsTrueWhenListenerWasAdded() {
		nodeMgr.addOnTextListener(mockListener);
		
		assertTrue(nodeMgr.removeOnTextListener(mockListener));
	}
	
	@Test
	public void removeOnTextListenerReturnsTrueWhenAnotherListenerHasBeenAddedAndListenerWasAdded() {
		nodeMgr.addOnTextListener(mockListener);
		nodeMgr.addOnTextListener(mockListener2);
		
		assertTrue(nodeMgr.removeOnTextListener(mockListener));
	}
	
	@Test
	public void oneOnTextListenerStopsReceivingEventsWhenRemoved() {
		nodeMgr.addOnTextListener(mockListener);
		
		nodeMgr.text(buildText("text1"));
		
		nodeMgr.removeOnTextListener(mockListener);
		
		nodeMgr.text(buildText("text2"));
		
		assertEquals(1, mockListener.getNodeStack().size());
		assertText(mockListener, 0, "text1");
	}
	
	@Test
	public void multipleOnTextListenersStopReceivingEventsWhenRemoved() {
		nodeMgr.addOnTextListener(mockListener);
		nodeMgr.addOnTextListener(mockListener2);
		
		nodeMgr.text(buildText("text1"));
		
		nodeMgr.removeOnTextListener(mockListener);
		nodeMgr.removeOnTextListener(mockListener2);
		
		nodeMgr.text(buildText("text2"));
		
		assertEquals(1, mockListener.getNodeStack().size());
		assertText(mockListener, 0, "text1");
		
		assertEquals(1, mockListener2.getNodeStack().size());
		assertText(mockListener2, 0, "text1");
	}
	
	@Test
	public void OneTextPassesThroughTextWhenNoListenersAdded() {
		nodeMgr.text(buildText("text"));
		assertTrue(true);
	}
	
	@Test
	public void MultipleTextsPassThroughTextWhenNoListenersAdded() {
		nodeMgr.text(buildText("text1"));
		nodeMgr.text(buildText("text2"));
		assertTrue(true);
	}
	

	@Test
	public void addOnTextListenerCanAddTheSameListenerMoreThanOnce() {
		nodeMgr.addOnTextListener(mockListener);
		nodeMgr.addOnTextListener(mockListener);
	}
	
	@Test
	public void textSendsOneEventToTheSameListenerForEachTimeItsAdded() {
		nodeMgr.addOnTextListener(mockListener);
		nodeMgr.addOnTextListener(mockListener);
		
		nodeMgr.text(buildText("text1"));
		
		assertEquals(2, mockListener.getNodeStack().size());
		assertText(mockListener, 0, "text1");
		assertText(mockListener, 1, "text1");
	}
	
	@Test
	public void removeOnTextListenerRemovesOneListenerOccurrenceWhenListenerIsAddedMoreThanOnce() {
		nodeMgr.addOnTextListener(mockListener);
		nodeMgr.addOnTextListener(mockListener);
		
		nodeMgr.text(buildText("text1"));
		
		nodeMgr.removeOnTextListener(mockListener);
		
		nodeMgr.text(buildText("text2"));
		
		assertEquals(3, mockListener.getNodeStack().size());
		assertText(mockListener, 0, "text1");
		assertText(mockListener, 1, "text1");
		assertText(mockListener, 2, "text2");
	}
	
	@Test
	public void multipleOnTextListenersReceiveEventsWhenAddedButNotRemoved() {
		nodeMgr.text(buildText("text1"));
		
		nodeMgr.addOnTextListener(mockListener); //add 1
		
		nodeMgr.text(buildText("text2"));
		
		nodeMgr.addOnTextListener(mockListener2); //add 2
		
		nodeMgr.text(buildText("text3"));
		
		nodeMgr.removeOnTextListener(mockListener); //remove 1
		
		nodeMgr.text(buildText("text4"));
		
		nodeMgr.removeOnTextListener(mockListener2); //remove 2
		nodeMgr.addOnTextListener(mockListener); //add 1
		
		nodeMgr.text(buildText("text5"));
		
		assertEquals(3, mockListener.getNodeStack().size());
		assertText(mockListener, 0, "text2");
		assertText(mockListener, 1, "text3");
		assertText(mockListener, 2, "text5");
		
		assertEquals(2, mockListener2.getNodeStack().size());
		assertText(mockListener2, 0, "text3");
		assertText(mockListener2, 1, "text4");
	}
	
}
