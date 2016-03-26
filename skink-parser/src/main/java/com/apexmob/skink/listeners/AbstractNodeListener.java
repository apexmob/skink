package com.apexmob.skink.listeners;

import com.apexmob.skink.EndElement;
import com.apexmob.skink.NodeListener;
import com.apexmob.skink.StartElement;
import com.apexmob.skink.Text;

public abstract class AbstractNodeListener implements NodeListener {
	
	protected AbstractNodeListener() {
		
	}

	public void onStartElement(StartElement start) {
		//do nothing
	}

	public void onText(Text text) {
		//do nothing
	}

	public void onEndElement(EndElement end) {
		//do nothing
	}

}
