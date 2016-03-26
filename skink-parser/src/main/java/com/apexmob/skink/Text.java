package com.apexmob.skink;

/**
 * The Text class represents a node containing text within a document tree.  
 * 
 * <p>The Text class implements the flyweight pattern where a single instance can be reused by calling
 * the clear method and re-populating the content StringBuilder.</p> 
 */
public class Text extends Node {
	
	/**
	 * Construct a new Text containing the content provided within a StringBuilder instance.
	 * @param content The content represented by the node.
	 * @throws IllegalArgumentException if the type or content provided is null.
	 */
	public Text(StringBuilder buffer) {
		super(NodeType.TEXT, buffer);
	}

}
