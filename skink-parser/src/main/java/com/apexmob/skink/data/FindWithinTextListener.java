package com.apexmob.skink.data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.apexmob.skink.DataManager;

public class FindWithinTextListener extends ReadTextListener {
	
	private final Pattern pattern;

	public FindWithinTextListener(DataManager manager, int fieldId, Pattern pattern) {
		super(manager, fieldId);
		if (pattern == null) {
			throw new IllegalArgumentException("The pattern provided is null");
		}
		this.pattern = pattern;
	}

	@Override
	protected String filterContent(String content) {
		String retVal = null;
		Matcher matcher = pattern.matcher(content);
		if (matcher.find()) {
			retVal = matcher.group();
		}
		return retVal;
	}

}
