package com.apexmob.skink.listeners;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.apexmob.skink.NodeListener;
import com.apexmob.skink.StartElement;
import com.apexmob.skink.Text;

public class RuleBasedAndFilteringNodeListener extends FilteringNodeListener {
	
	public RuleBasedAndFilteringNodeListener() {
	}
	
	public RuleBasedAndFilteringNodeListener(NodeListener listener) {
		super(listener);
	}

	public RuleBasedAndFilteringNodeListener(Collection<NodeListener> listeners) {
		super(listeners);
	}

	private final List<TagFilteringRule> tagRules = new ArrayList<TagFilteringRule>();
	private final List<TextFilteringRule> textRules = new ArrayList<TextFilteringRule>();

	@Override
	protected boolean include(StartElement start) {
		boolean retVal = true;
		
		for (int i=0; i < tagRules.size(); i++) {
			retVal = tagRules.get(i).include(start);
			if (!retVal) {
				break;
			}
		}
		
		return retVal;
	}
	
	@Override
	protected boolean include(Text text) {
		boolean retVal = true;
		
		for (int i=0; i < textRules.size(); i++) {
			retVal = textRules.get(i).include(text);
			if (!retVal) {
				break;
			}
		}
		
		return retVal;
	}
	
	public void addTagRule(TagFilteringRule rule) {
		if (rule == null) {
			throw new IllegalArgumentException("The rule provided is null");
		}
		tagRules.add(rule);
	}
	
	public void addTextRule(TextFilteringRule rule) {
		if (rule == null) {
			throw new IllegalArgumentException("The rule provided is null");
		}
		textRules.add(rule);
	}

}
