package com.apexmob.skink.listeners;

import com.apexmob.skink.StartElement;
import com.apexmob.skink.listeners.TagFilteringRule;

public class MockTagFilteringRule implements TagFilteringRule {
	
	public boolean includeElement = true;

	public boolean include(StartElement start) {
		return includeElement;
	}

}
