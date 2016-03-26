package com.apexmob.skink.listeners.rules;

import com.apexmob.skink.StartElement;
import com.apexmob.skink.listeners.TagFilteringRule;

public class IncludeTagsWithAttributeContainingFilterRule implements TagFilteringRule {
	
	private final String name;
	private final String containedText;
	
	public IncludeTagsWithAttributeContainingFilterRule(String name, String containedText) {
		if (name == null) {
			throw new IllegalArgumentException("The name provided is null");
		}
		if (containedText == null) {
			throw new IllegalArgumentException("The contained text provided is null");
		}
		this.name = name;
		this.containedText = containedText;
	}

	public boolean include(StartElement start) {
		boolean retVal = false;
		
		String attrValue = start.getAttribute(name);
		if (attrValue != null) {
			retVal = attrValue.contains(containedText);
		}
		
		return retVal;
	}

}
