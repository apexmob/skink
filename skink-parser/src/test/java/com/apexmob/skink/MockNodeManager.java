package com.apexmob.skink;


public class MockNodeManager implements NodeManager {
	
	private final MockNodeListener delegate = new MockNodeListener();

	public void registerListener(NodeListener listener) {
		
	}

	public void deregisterListener(NodeListener listener) {
		
	}

	public void startElement(StartElement start) {
		delegate.onStartElement(start);
	}

	public void endElement(EndElement end) {
		delegate.onEndElement(end);
	}

	public void text(Text text) {
		delegate.onText(text);
	}
	
	public MockNodeListener getDelegate() {
		return delegate;
	}

}
