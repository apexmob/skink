package com.apexmob.skink.examples.helloworld;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import com.apexmob.skink.AbstractDataListener;
import com.apexmob.skink.Data;
import com.apexmob.skink.DataComplete;
import com.apexmob.skink.DataManager;
import com.apexmob.skink.DefaultDataManager;
import com.apexmob.skink.DefaultParser;
import com.apexmob.skink.listeners.NodeListenerBuilder;

public class HelloWorldData {
	
	public static void main(String[] args) {
		String html = "<!DOCTYPE html><html><body><h1>Hello World!</h1></body></html>";
		InputStream htmlStream = new ByteArrayInputStream(html.getBytes(StandardCharsets.UTF_8));
		
		DataManager dataManager = new DefaultDataManager(); //create a DataManager to process data events
		
		HelloWorldDataListener dataListener = new HelloWorldDataListener(dataManager);
		dataManager.registerListener(dataListener); //register our DataListener with the DataManager to listen for data events
		
		DefaultParser parser = new DefaultParser();
		parser.getNodeManager().registerListener(
				NodeListenerBuilder.builder() //build the listener tree that will process node events
				.setDataEventManager(dataManager) //set the DataManager into the builder so it can register built-in DataListeners for you
				.filterToAllTagsWithName("h1") //filter to just the "h1" element
				.readText(HelloWorldDataListener.TITLE) //listen for the elements that make it past the filter and create Data events containing the element's text.
				.build());
		
		dataManager.registerListener(parser);  //add the parser as a data event listener so it can listen for DataComplete events.
		
		try {
			parser.parse(htmlStream); //tell the parser to go!
		} catch (IOException e) {
			e.printStackTrace(); //do something better than this with exceptions
		}
		
		System.out.println(dataListener.title);
	}
	
	public static class HelloWorldDataListener extends AbstractDataListener {
		public static final int TITLE = 1;
		
		private String title = null;
		
		public HelloWorldDataListener(DataManager dataManager) {
			super(dataManager);
		}
		
		@Override
		public void onData(Data data) {
			switch (data.getFieldId()) {
			case TITLE:
				title = data.getData(); //grab the data I care about
				break;
			default:
			}
			
			if (title != null) {
				getDataManager().dataComplete(new DataComplete()); //tell the parser it can stop parsing because you have all the info you need.
			}
		}
	}
	
}
