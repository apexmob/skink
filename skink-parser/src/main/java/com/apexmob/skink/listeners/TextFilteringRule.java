package com.apexmob.skink.listeners;

import com.apexmob.skink.Text;

public interface TextFilteringRule {
	
	/**
	 * Check to see if the Text should be listened for.
	 * @param text The Text to check.
	 * @return True if the text should be listened for, otherwise false;
	 */
	public boolean include(Text text);

}
