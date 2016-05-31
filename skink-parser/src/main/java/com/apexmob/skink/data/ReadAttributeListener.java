package com.apexmob.skink.data;

import com.apexmob.skink.Data;
import com.apexmob.skink.DataManager;
import com.apexmob.skink.OnStartElementListener;
import com.apexmob.skink.StartElement;

public class ReadAttributeListener extends AbstractDataFieldListener implements OnStartElementListener {
	
	private final String attributeName;
	
	public ReadAttributeListener(DataManager manager, int fieldId, String attributeName) {
		super(manager, fieldId);
		if (attributeName == null) {
			throw new IllegalArgumentException("The attribute name provided is null");
		}
		this.attributeName = attributeName;
	}

	@Override
	public void onStartElement(StartElement start) {
		String value = start.getAttribute(attributeName);
		if (value != null) {
			value = filterAttribute(value);
			if (value != null) {
				getDataEventManager().data(new Data(getFieldId(), value, this));
			}
		}
	}
	
	protected String filterAttribute(String attributeValue) {
		return attributeValue;
	}

}
