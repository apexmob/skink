package com.apexmob.skink.listeners.rules;

import java.util.regex.Pattern;

import com.apexmob.skink.Text;
import com.apexmob.skink.listeners.TextFilteringRule;

public class IncludeTextMatchingPatternFilteringRule implements TextFilteringRule {
	
	private final Pattern pattern;
	
	public IncludeTextMatchingPatternFilteringRule(Pattern pattern) {
		if (pattern == null) {
			throw new IllegalArgumentException("The pattern provided is null");
		}
		this.pattern = pattern;
	}

	public boolean include(Text text) {
		boolean retVal = false;
		
		if (pattern.matcher(text.getContent()).matches()) {
			retVal = true;
		}
		
		return retVal;
	}

}
