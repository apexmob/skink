package com.apexmob.skink.examples.helloworld;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import com.apexmob.skink.DefaultParser;
import com.apexmob.skink.Parser;
import com.apexmob.skink.Text;
import com.apexmob.skink.listeners.AbstractNodeListener;
import com.apexmob.skink.listeners.NodeListenerBuilder;

public class HelloWorldNode {
	
	public static void main(String[] args) {
		String html = "<!DOCTYPE html><html><body><h1>Hello World!</h1></body></html>";
		InputStream htmlStream = new ByteArrayInputStream(html.getBytes(StandardCharsets.UTF_8));
		
		Parser parser = new DefaultParser();
		parser.getNodeManager().registerListener( 
				NodeListenerBuilder.builder() //build the listener tree that will process node events
				.filterToAllTagsWithName("h1") //filter to just the "h1" element
				.addListener(new AbstractNodeListener() { //listen for the elements that make it past the filter
					@Override
					public void onText(Text text) {
						System.out.println(text.getContent());	//print the contents of the element to the System.out
					}
				})
				.build());
		
		try {
			parser.parse(htmlStream); //tell the parser to go!
		} catch (IOException e) {
			e.printStackTrace(); //do something better than this with exceptions
		}		
	}
}
