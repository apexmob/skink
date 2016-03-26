package com.apexmob.skink.data;

import com.apexmob.skink.DataManager;
import com.apexmob.skink.Data;

public abstract class AbstractDataFieldListener extends AbstractDataNodeListener {
	
	private final int fieldId;
	
	protected AbstractDataFieldListener(DataManager dataEventManager, int fieldId) {
		super(dataEventManager);
		
		this.fieldId = fieldId;
	}
	
	protected void sendDataReadEvent(String dataValue) {
		if (dataValue == null) {
			throw new IllegalArgumentException("The data value provided is null");
		}
		getDataEventManager().data(new Data(getFieldId(), dataValue, this));
	}
	
	protected int getFieldId() {
		return fieldId;
	}
}
