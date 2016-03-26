package com.apexmob.skink.listeners;

import java.util.Collection;

import com.apexmob.skink.EndElement;
import com.apexmob.skink.NodeListener;
import com.apexmob.skink.StartElement;
import com.apexmob.skink.Text;

public abstract class FilteringNodeListener extends NodeListenerWrapper {
	
	private int elementCount = 0;
	private boolean isFiltering = true;
	private PropagationPolicy policy = PropagationPolicy.All;
	private String elementName = null;
	private int openElementCount = 0;

	public FilteringNodeListener(Collection<NodeListener> listeners) {
		super(listeners);
	}

	public FilteringNodeListener(NodeListener listener) {
		super(listener);
	}
	
	public FilteringNodeListener() {
	}
	
	public void setPropagationPolicy(PropagationPolicy policy) {
		if (policy == null) {
			throw new IllegalArgumentException("The policy provided is null");
		}
		this.policy = policy;
	}
	
	public PropagationPolicy getPropagationPolicy() {
		return policy;
	}

	public void onStartElement(StartElement start) {
		if (isFiltering) {
			if (include(start)) {
				isFiltering = false;
				elementCount++;
				processStartTag(start);
			}
		} else {
			elementCount++;
			processStartTag(start);
		}
	}

	public void onText(Text text) {
		if (!isFiltering && include(text)) {
			processText(text);
		}
	}

	public void onEndElement(EndElement end) {
		if (!isFiltering) {
			processEndTag(end);
			elementCount--;
			if (elementCount == 0) {
				isFiltering = true;
			}
		}
	}
	
	private void processStartTag(StartElement start) {
		if (policy == PropagationPolicy.All || policy == PropagationPolicy.TagsOnly) {
			super.onStartElement(start);
		} else {
			//policy == PropagationPolicy.ParentTagOnly
			if (openElementCount == 0) {
				elementName = start.getName();
				super.onStartElement(start);
			}
			openElementCount++;
		}
	}
	
	private void processEndTag(EndElement end) {
		if (policy == PropagationPolicy.All || policy == PropagationPolicy.TagsOnly) {
			super.onEndElement(end);
		} else {
			//policy == PropagationPolicy.ParentTagOnly
			if (elementName == null) {
				throw new IllegalStateException("Parent element name was not identified. This may be due to changing PropagationPolicy during processing.");
			}
			
			openElementCount--;
			if (openElementCount == 0) {
				if (elementName.equalsIgnoreCase(end.getName())) {
					super.onEndElement(end);
					elementName = null;
				} else {
					throw new IllegalStateException("The end element does not have the same name as the start element.");
				}
			}
		}
	}
	
	private void processText(Text text) {
		if (policy == PropagationPolicy.All) {
			super.onText(text);
		}
	}
	
	protected abstract boolean include(StartElement start);

	protected abstract boolean include(Text text);
}
