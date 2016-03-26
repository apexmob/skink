package com.apexmob.skink.listeners.rules;

import com.apexmob.skink.StartElement;
import com.apexmob.skink.listeners.TagFilteringRule;

public class IncludeTagsByIndexFilteringRule implements TagFilteringRule {
	
	private final int startIndex;
	private final int endIndex;
	private int currentIndex = 0;
	
	public IncludeTagsByIndexFilteringRule(int index) {
		this(index, index);
	}
	
	public IncludeTagsByIndexFilteringRule(int startIndex, int endIndex) {
		if (startIndex <= 0) {
			throw new IllegalArgumentException("The start index provided is not positive");
		}
		if (endIndex <= 0) {
			throw new IllegalArgumentException("The end index provided is not positive");
		}
		if (startIndex > endIndex) {
			throw new IllegalArgumentException("The start index provided is larger than the end index provided");
		}
		this.startIndex = startIndex;
		this.endIndex = endIndex;
	}

	public boolean include(StartElement start) {
		boolean retVal = false;
		
		currentIndex++;
		if (currentIndex >= startIndex && currentIndex <= endIndex) {
			retVal = true;
		}
		
		return retVal;
	}

}
