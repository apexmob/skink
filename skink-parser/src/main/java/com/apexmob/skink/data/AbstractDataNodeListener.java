package com.apexmob.skink.data;

import com.apexmob.skink.DataManager;
import com.apexmob.skink.Data;
import com.apexmob.skink.listeners.AbstractNodeListener;

public abstract class AbstractDataNodeListener extends AbstractNodeListener {
	
	private final DataManager dataEventManager;

	protected AbstractDataNodeListener(DataManager dataEventManager) {
		if (dataEventManager == null) {
			throw new IllegalArgumentException("The event manager provided is null");
		}
		
		this.dataEventManager = dataEventManager;
	}
	
	protected DataManager getDataEventManager() {
		return dataEventManager;
	}
	
	protected void send(Data dataReadEvent) {
		if (dataReadEvent == null) {
			throw new IllegalArgumentException("The event provided is null");
		}
		getDataEventManager().data(dataReadEvent);
	}
}
