package com.apexmob.skink;

import java.io.IOException;
import java.io.InputStream;

public interface Parser {
	
	public NodeManager getNodeManager();
	
	public void parse(InputStream in) throws IOException;
	
	public void insert(StartElement start);
	
	public void insert(EndElement end);
	
	public void insert(Text text);

}
