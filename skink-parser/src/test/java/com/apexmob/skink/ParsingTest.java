package com.apexmob.skink;

import static org.junit.Assert.assertEquals;

import com.apexmob.skink.EndElement;
import com.apexmob.skink.StartElement;
import com.apexmob.skink.Text;

public class ParsingTest {
	
	protected StartElement buildStartElement(String content) {
		StringBuilder buffer = new StringBuilder();
		buffer.append(content);
		return new StartElement(buffer);
	}
	
	protected EndElement buildEndElement(String content) {
		StringBuilder buffer = new StringBuilder();
		buffer.append(content);
		return new EndElement(buffer);
	}
	
	protected Text buildText(String content) {
		StringBuilder buffer = new StringBuilder();
		buffer.append(content);
		return new Text(buffer);
	}
	
	protected StartElement assertStartElement(MockNodeListener mock, int index, String expectedName) {
		Node node = mock.getNodeStack().get(index);
		assertEquals(NodeType.START_ELEMENT, node.getType());
		assertEquals(expectedName, ((StartElement)node).getName());
		return (StartElement) node;
	}
	
	protected StartElement assertStartElementAttribute(MockNodeListener mock, int index, String attributeName, String expectedValue) {
		Node node = mock.getNodeStack().get(index);
		assertEquals(NodeType.START_ELEMENT, node.getType());
		assertEquals(expectedValue, ((StartElement)node).getAttribute(attributeName));
		return (StartElement) node;
	}
	
	protected EndElement assertEndElement(MockNodeListener mock, int index, String expectedName) {
		Node node = mock.getNodeStack().get(index);
		assertEquals(NodeType.END_ELEMENT, node.getType());
		assertEquals(expectedName, ((EndElement)node).getName());
		return (EndElement) node;
	}
	
	protected Text assertText(MockNodeListener mock, int index, String expectedText) {
		Node node = mock.getNodeStack().get(index);
		assertEquals(NodeType.TEXT, node.getType());
		assertEquals(expectedText, ((Text)node).getContent());
		return (Text) node;
	}

}
