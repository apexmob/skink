package com.apexmob.skink.listeners.rules;

import com.apexmob.skink.StartElement;
import com.apexmob.skink.listeners.TagFilteringRule;

public class IncludeTagWithNameFilterRule implements TagFilteringRule {
	
	private final String name;
	
	public IncludeTagWithNameFilterRule(String name) {
		if (name == null) {
			throw new IllegalArgumentException("The name provided is null");
		}
		this.name = name;
	}

	public boolean include(StartElement start) {
		return name.equalsIgnoreCase(start.getName());
	}

}
