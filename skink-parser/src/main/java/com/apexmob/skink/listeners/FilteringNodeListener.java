package com.apexmob.skink.listeners;

import java.util.Collection;

import com.apexmob.skink.EndElement;
import com.apexmob.skink.NodeListener;
import com.apexmob.skink.StartElement;
import com.apexmob.skink.Text;

/**
 * The FilteringNodeListener is an abstract NodeListenerWrapper that can filter the node
 * events that are communicated to the collection of managed listeners.  Subclasses provide
 * the logic to determine when to filter elements or text.
 */
public abstract class FilteringNodeListener extends NodeListenerWrapper {
	
	private int elementCount = 0;
	private boolean isFiltering = true;
	private PropagationPolicy policy = PropagationPolicy.All;
	private String elementName = null;
	private int openElementCount = 0;

	/**
	 * Construct a new FilteringNodeListener to manage the collection of listeners provided.
	 * @param listeners The collection of listeners that will receive the node events.
	 * @throws IllegalArgumentException if the listener collection provided is null.
	 */
	public FilteringNodeListener(Collection<NodeListener> listeners) {
		super(listeners);
	}

	/**
	 * Construct a new NodeListenerWrapper with a single managed listener.
	 * @param listener The listener that will receive the node events.
	 * @throws IllegalArgumentException if the listener provided is null.
	 */
	public FilteringNodeListener(NodeListener listener) {
		super(listener);
	}
	
	/**
	 * Construct a new NodeListenerWrapper with no managed listeners.
	 */
	public FilteringNodeListener() {
	}
	
	/**
	 * Set the propagation policy used when filtering node events.
	 * @param policy The propagation policy.
	 */
	public void setPropagationPolicy(PropagationPolicy policy) {
		if (policy == null) {
			throw new IllegalArgumentException("The policy provided is null");
		}
		this.policy = policy;
	}
	
	/**
	 * Retrieve the propagation policy used when filtering node events.
	 * @return The propagation policy.
	 */
	public PropagationPolicy getPropagationPolicy() {
		return policy;
	}

	/**
	 * {@inheritDoc}
	 * Delegation only occurs when not filtering.
	 */
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

	/**
	 * {@inheritDoc}
	 * Delegation only occurs when not filtering.
	 */
	public void onText(Text text) {
		if (!isFiltering && include(text)) {
			processText(text);
		}
	}

	/**
	 * {@inheritDoc}
	 * Delegation only occurs when not filtering.
	 */
	public void onEndElement(EndElement end) {
		if (!isFiltering) {
			processEndTag(end);
			elementCount--;
			if (elementCount == 0) {
				isFiltering = true;
			}
		}
	}
	
	/**
	 * Propagate the node event to the listeners if appropriate.
	 * @param start The node event.
	 */
	private void processStartTag(StartElement start) {
		if (policy == PropagationPolicy.All || policy == PropagationPolicy.ElementsOnly) {
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
	
	/**
	 * Propagate the node event to the listeners if appropriate.
	 * @param end The node event.
	 */
	private void processEndTag(EndElement end) {
		if (policy == PropagationPolicy.All || policy == PropagationPolicy.ElementsOnly) {
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
	
	/**
	 * Propagate the node event to the listeners if appropriate.
	 * @param text The node event.
	 */
	private void processText(Text text) {
		if (policy == PropagationPolicy.All) {
			super.onText(text);
		}
	}
	
	/**
	 * Determine if the provided StartElement event should be propagated to the collection of managed listeners.
	 * @param start The StartElement event to be considered.
	 * @return True if the event should be propagated, otherwise false.
	 */
	protected abstract boolean include(StartElement start);

	/**
	 * Determine if the provided Text event should be propagated to the collection of managed listeners.
	 * @param text The Text event to be considered.
	 * @return True if the event should be propagated, otherwise false.
	 */
	protected abstract boolean include(Text text);
	
}
