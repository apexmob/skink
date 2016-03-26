package com.apexmob.skink.listeners;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.apexmob.skink.EndElement;
import com.apexmob.skink.Node;
import com.apexmob.skink.NodeListener;
import com.apexmob.skink.StartElement;
import com.apexmob.skink.Text;

public class MultiTagClosingListener extends NodeListenerWrapper {
	
	private List<Node> stack = new ArrayList<Node>();
	private List<StartElement> startElements = new ArrayList<StartElement>();
	private List<EndElement> endElements = new ArrayList<EndElement>();
	private List<String> expectedEndElementNames = new ArrayList<String>();

	public MultiTagClosingListener() {
		super();
	}
	
	public MultiTagClosingListener(Collection<NodeListener> listeners) {
		super(listeners);
	}

	public MultiTagClosingListener(NodeListener listener) {
		super(listener);
	}
	
	@Override
	public void onStartElement(StartElement start) {
		StartElement startElem = new StartElement(new StringBuilder(start.getContent())); 
		
		push(startElem);
	}

	@Override
	public void onEndElement(EndElement end) {
		if (expectedEndElementNames.contains(end.getName())) {
			//push to matching start/end tags
			while (expectedEndElementNames.size() > 0 && !end.getName().equalsIgnoreCase(expectedEndElementNames.get(0))) {
				EndElement newEnd = new EndElement(new StringBuilder("</" + expectedEndElementNames.remove(0) + ">"));
				push(newEnd);
			}
			
			EndElement newEnd = new EndElement(new StringBuilder("</" + expectedEndElementNames.remove(0) + ">"));
			push(newEnd);
		} else {
			if (expectedEndElementNames.size() > 0) {
				//push one end tag, assume just misnamed
				EndElement newEnd = new EndElement(new StringBuilder("</" + expectedEndElementNames.remove(0) + ">"));
				push(newEnd);
			}
		}
		
		decide();
	}
	
	@Override
	public void onText(Text text) {
		Text newText = new Text(new StringBuilder(text.getContent())); 
		
		push(newText);
	}
	
	private void push(StartElement start) {
		stack.add(start);
		startElements.add(start);
		expectedEndElementNames.add(0, start.getName());
	}
	
	private void push(EndElement end) {
		stack.add(end);
		endElements.add(0, end);
	}
	
	private void push(Text text) {
		stack.add(text);
	}
	
	private void pop() {
		Node node = stack.remove(0);
		switch (node.getType()) {
		case START_ELEMENT:
			super.onStartElement((StartElement) node);
			startElements.remove(startElements.size()-1);
			break;
		case END_ELEMENT:
			super.onEndElement((EndElement) node);
			endElements.remove(0);
			break;
		case TEXT:
			super.onText((Text) node); 
			break;
		}
	}
	
	private void decide() {
		if (startElements.size() == endElements.size()) {
			while (stack.size() > 0) {
				pop();
			}
		}
	}
	
}
