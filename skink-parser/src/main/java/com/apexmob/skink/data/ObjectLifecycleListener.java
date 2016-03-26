package com.apexmob.skink.data;

import com.apexmob.skink.DataManager;
import com.apexmob.skink.Data;
import com.apexmob.skink.EndElement;
import com.apexmob.skink.StartElement;

public class ObjectLifecycleListener extends AbstractDataNodeListener {

	private final int startId;
	private final int endId;
	
	private String elementName = null;
	private int openElementCount = 0;
	
	public ObjectLifecycleListener(DataManager dataEventManager, int startId, int endId) {
		super(dataEventManager);
		
		this.startId = startId;
		this.endId = endId;
	}

	@Override
	public void onStartElement(StartElement start) {
		if (openElementCount == 0) {
			elementName = start.getName();
			send(new Data(getStartId(), "", this));
		}
		openElementCount++;
	}

	@Override
	public void onEndElement(EndElement end) {
		if (elementName == null) {
			throw new IllegalStateException("No element name was set from a start element");
		}
		
		openElementCount--;
		if (openElementCount == 0) {
			if (elementName.equalsIgnoreCase(end.getName())) {
				send(new Data(getEndId(), "", this));
				elementName = null;
			} else {
				throw new IllegalStateException("The end element does not have the same name as the start element.");
			}
		}
	}
	
	protected int getStartId() {
		return startId;
	}
	
	protected int getEndId() {
		return endId;
	}

}
