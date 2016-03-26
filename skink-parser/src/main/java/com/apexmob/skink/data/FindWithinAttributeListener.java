package com.apexmob.skink.data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.apexmob.skink.DataManager;

public class FindWithinAttributeListener extends ReadAttributeListener {
	
	private final Pattern pattern;

	public FindWithinAttributeListener(DataManager manager, int fieldId, String attributeName, Pattern pattern) {
		super(manager, fieldId, attributeName);
		if (pattern == null) {
			throw new IllegalArgumentException("The pattern provided is null");
		}
		this.pattern = pattern;
	}
	
	@Override
	protected String filterAttribute(String attributeValue) {
		String retVal = null;
		Matcher matcher = pattern.matcher(attributeValue);
		if (matcher.find()) {
			retVal = matcher.group();
		}
		return retVal;
	}

}
