package com.apexmob.skink.data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.apexmob.skink.DataManager;

public class MatchWithinAttributeListener extends ReadAttributeListener {
	
private static final Logger logger = LoggerFactory.getLogger(MatchWithinAttributeListener.class);
	
	private final Pattern pattern;
	private final int groupIndex;

	public MatchWithinAttributeListener(DataManager manager, int fieldId, String attributeName, Pattern pattern, int groupIndex) {
		super(manager, fieldId, attributeName);
		if (pattern == null) {
			throw new IllegalArgumentException("The pattern provided is null");
		}
		if (groupIndex < 0) {
			throw new IllegalArgumentException("The group index provided is negative");
		}
		this.pattern = pattern;
		this.groupIndex = groupIndex;
	}
	
	@Override
	protected String filterAttribute(String attributeValue) {
		String retVal = null;
		Matcher matcher = pattern.matcher(attributeValue);
		if (matcher.matches()) {
			if (logger.isDebugEnabled()) {
				String debug = null;
				for (int i=0; i <= matcher.groupCount(); i++) {
					debug = matcher.group(i);
					logger.debug("Matcher group {} = {}", i, debug);
				}
			}
			if (groupIndex <= matcher.groupCount()) {
				retVal = matcher.group(groupIndex);
			}
		}
		return retVal;
	}

}
