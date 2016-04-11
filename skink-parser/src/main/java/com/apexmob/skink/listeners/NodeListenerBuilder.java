package com.apexmob.skink.listeners;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import com.apexmob.skink.DataManager;
import com.apexmob.skink.NodeListener;
import com.apexmob.skink.Parser;
import com.apexmob.skink.data.FindWithinAttributeListener;
import com.apexmob.skink.data.FindWithinTextListener;
import com.apexmob.skink.data.MatchWithinAttributeListener;
import com.apexmob.skink.data.MatchWithinTextListener;
import com.apexmob.skink.data.ObjectLifecycleListener;
import com.apexmob.skink.data.ReadAttributeListener;
import com.apexmob.skink.data.ReadTextListener;
import com.apexmob.skink.listeners.rules.IncludeTextMatchingPatternFilteringRule;
import com.apexmob.skink.listeners.rules.IncludeTagWithAttributeFilterRule;
import com.apexmob.skink.listeners.rules.IncludeTagWithNameFilterRule;
import com.apexmob.skink.listeners.rules.IncludeTagsByIndexFilteringRule;
import com.apexmob.skink.listeners.rules.IncludeTagsWithAttributeContainingFilterRule;

public class NodeListenerBuilder {
	
	private RuleBasedAndFilteringNodeListener rootListener = new RuleBasedAndFilteringNodeListener();
	
	private RuleBasedAndFilteringNodeListener working = rootListener;
	
	private Map<RuleBasedAndFilteringNodeListener, RuleBasedAndFilteringNodeListener> parentMapping = new HashMap<RuleBasedAndFilteringNodeListener, RuleBasedAndFilteringNodeListener>();
	
	private DataManager eventManager = null;
	
	private NodeListenerBuilder() {
		
	}
	
	public static NodeListenerBuilder builder() {
		return new NodeListenerBuilder();
	}
	
	public NodeListener build() {
		return rootListener;
	}
	
	public NodeListenerBuilder setDataEventManager(DataManager eventManager) {
		if (eventManager == null) {
			throw new IllegalArgumentException("The event manager provided is null");
		}
		this.eventManager = eventManager;
		return this;
	}
	
	public DataManager getDataEventManager() {
		return eventManager;
	}
	
	public NodeListenerBuilder addListener(NodeListener listener) {
		working.addListener(listener);
		return this;
	}
	
	public NodeListenerBuilder addTagRule(TagFilteringRule rule) {
		working.addTagRule(rule);
		return this;
	}
	
	public NodeListenerBuilder addTextRule(TextFilteringRule rule) {
		working.addTextRule(rule);
		return this;
	}
	
	public NodeListenerBuilder addChildListener() {
		RuleBasedAndFilteringNodeListener newWorking = new RuleBasedAndFilteringNodeListener();
		working.addListener(newWorking);
		
		//map child (key) to parent (value)
		parentMapping.put(newWorking, working);
		
		working = newWorking;

		return this;
	}
	
	public NodeListenerBuilder setPropagationPolicy(PropagationPolicy policy) {
		if (policy == null) {
			throw new IllegalArgumentException("The policy provided is null");
		}
		working.setPropagationPolicy(policy);
		return this;
	}
	
	public NodeListenerBuilder addChildListener(NodeListenerWrapper wrapper) {
		RuleBasedAndFilteringNodeListener newWorking = new RuleBasedAndFilteringNodeListener();
		
		wrapper.addListener(newWorking);
		
		working.addListener(wrapper);
		
		//map child (key) to parent (value)
		parentMapping.put(newWorking, working);
		
		working = newWorking;

		return this;
	}
	
	public NodeListenerBuilder buildChild() {
		if (working == rootListener) {
			throw new IllegalStateException("Builder is currently working on the root listener");
		}
		working = parentMapping.get(working);
		return this;
	}
	
	public NodeListenerBuilder filterToAllTagsWithName(String tagName) {
		addTagRule(new IncludeTagWithNameFilterRule(tagName));
		return this;
	}
	
	public NodeListenerBuilder filterToAllTagsWithAttribute(String attributeName, String attributeValue) {
		addTagRule(new IncludeTagWithAttributeFilterRule(attributeName, attributeValue));
		return this;
	}
	
	public NodeListenerBuilder filterToAllTagsWithAttributeContaining(String attributeName, String containedText) {
		addTagRule(new IncludeTagsWithAttributeContainingFilterRule(attributeName, containedText));
		return this;
	}
	
	public NodeListenerBuilder filterToTextMatching(Pattern pattern) {
		addTextRule(new IncludeTextMatchingPatternFilteringRule(pattern));
		return this;
	}
	
	public NodeListenerBuilder readText(int fieldId) {
		if (eventManager == null) {
			throw new IllegalStateException("The event manager has not been set");
		}
		addListener(new ReadTextListener(eventManager, fieldId));
		return this;
	}
	
	public NodeListenerBuilder readAttribute(int fieldId, String attributeName) {
		if (eventManager == null) {
			throw new IllegalStateException("The event manager has not been set");
		}
		addListener(new ReadAttributeListener(eventManager, fieldId, attributeName));
		return this;
	}
	
	public NodeListenerBuilder debug() {
		addListener(new DebugNodeListener());
		return this;
	}
	
	public NodeListenerBuilder forceTagsClosed(String[] tagNames) {
		if (tagNames == null) {
			throw new IllegalArgumentException("The array of tag names provided is null");
		}
		
		SingleTagClosingListener listener = new SingleTagClosingListener();
		for (int i=0; i < tagNames.length; i++) {
			listener.addTag(tagNames[i]);
		}
		
		addChildListener(listener);
		return this;
	}
	
	public NodeListenerBuilder matchWithinAttribute(int fieldId, String attributeName, Pattern pattern, int groupIndex) {
		if (eventManager == null) {
			throw new IllegalStateException("The event manager has not been set");
		}
		addListener(new MatchWithinAttributeListener(eventManager, fieldId, attributeName, pattern, groupIndex));
		return this;
	}
	
	public NodeListenerBuilder matchWithinText(int fieldId, Pattern pattern, int groupIndex) {
		if (eventManager == null) {
			throw new IllegalStateException("The event manager has not been set");
		}
		addListener(new MatchWithinTextListener(eventManager, fieldId, pattern, groupIndex));
		return this;
	}
	
	public NodeListenerBuilder findWithinAttribute(int fieldId, String attributeName, Pattern pattern) {
		if (eventManager == null) {
			throw new IllegalStateException("The event manager has not been set");
		}
		addListener(new FindWithinAttributeListener(eventManager, fieldId, attributeName, pattern));
		return this;
	}
	
	public NodeListenerBuilder findWithinText(int fieldId, Pattern pattern) {
		if (eventManager == null) {
			throw new IllegalStateException("The event manager has not been set");
		}
		addListener(new FindWithinTextListener(eventManager, fieldId, pattern));
		return this;
	}
	
	public NodeListenerBuilder filterToTagWithIndex(int index) {
		addTagRule(new IncludeTagsByIndexFilteringRule(index));
		return this;
	}
	
	public NodeListenerBuilder filterToTagsBetweenIndexes(int startIndex, int endIndex) {
		addTagRule(new IncludeTagsByIndexFilteringRule(startIndex, endIndex));
		return this;
	}
	
	public NodeListenerBuilder manageObjectLifecyle(int startId, int endId) {
		if (eventManager == null) {
			throw new IllegalStateException("The event manager has not been set");
		}
		addListener(new ObjectLifecycleListener(eventManager, startId, endId));
		return this;
	}
	
}
