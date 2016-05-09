package com.apexmob.skink;


public class MockNodeManager implements NodeManager {
	
	private final MockNodeListener delegate = new MockNodeListener();

	public void registerListener(NodeListener listener) {
		
	}

	public void deregisterListener(NodeListener listener) {
		
	}
	
	@Override
	public void addOnStartElementListener(OnStartElementListener listener) {

	}

	@Override
	public boolean removeOnStartElementListener(OnStartElementListener listener) {
		return false;
	}

	@Override
	public void addOnEndElementListener(OnEndElementListener listener) {
		
	}

	@Override
	public boolean removeOnEndElementListener(OnEndElementListener listener) {
		return false;
	}

	@Override
	public void addOnTextListener(OnTextListener listener) {
		
	}

	@Override
	public boolean removeOnTextListener(OnTextListener listener) {
		return false;
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
