package com.apexmob.skink.listeners;

import com.apexmob.skink.Text;
import com.apexmob.skink.listeners.TextFilteringRule;

public class MockTextFilteringRule implements TextFilteringRule {
	
	public boolean includeText = true;

	public boolean include(Text text) {
		return includeText;
	}

}
