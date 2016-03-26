package com.apexmob.skink.listeners;

import com.apexmob.skink.StartElement;

public interface TagFilteringRule {
	
	/**
	 * Check to see if the StartElement should be listened for.
	 * @param start The StartElement to check.
	 * @return True if the element should be listened for, otherwise false;
	 */
	public boolean include(StartElement start);

}
