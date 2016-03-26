package com.apexmob.skink.listeners.rules;

import com.apexmob.skink.StartElement;
import com.apexmob.skink.listeners.TagFilteringRule;

public class IncludeTagWithAttributeFilterRule implements TagFilteringRule {

	private final String name;
	private final String value;
	
	public IncludeTagWithAttributeFilterRule(String name, String value) {
		if (name == null) {
			throw new IllegalArgumentException("The name provided is null");
		}
		if (value == null) {
			throw new IllegalArgumentException("The value provided is null");
		}
		this.name = name;
		this.value = value;
	}

	public boolean include(StartElement start) {
		return value.equals(start.getAttribute(name));
	}

}
