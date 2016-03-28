package com.apexmob.skink.data;

import com.apexmob.skink.Data;
import com.apexmob.skink.DataComplete;
import com.apexmob.skink.DataListener;
import com.apexmob.skink.DataManager;
import com.apexmob.skink.ListenerComplete;

public abstract class AbstractDataListener implements DataListener {
	
	private final DataManager dataManager;
	
	protected AbstractDataListener() {
		dataManager = null;
	}
	
	protected AbstractDataListener(DataManager dataManager) {
		if (dataManager == null) {
			throw new IllegalArgumentException("The data manager provided is null");
		}
		this.dataManager = dataManager;
	}
	
	protected boolean hasDataManager() {
		return dataManager != null;
	}
	
	protected DataManager getDataManager() {
		return dataManager;
	}

	@Override
	public void onData(Data data) {
	}

	@Override
	public void onDataComplete(DataComplete dataComplete) {
	}

	@Override
	public void onListenerComplete(ListenerComplete listenerComplete) {
	}

}
