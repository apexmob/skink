package com.apexmob.skink;

/**
 * The Element class is the basic unit of data representing an Element within a document tree.
 * 
 * <p>The Element class implements the flyweight pattern where a single instance can be reused by calling
 * the clear method and re-populating the content StringBuilder.</p>
 */
public abstract class Element extends Node {
	
	private String name = null;
	
	/**
	 * Construct a new Element of the provided type with content provided within a StringBuilder instance.
	 * @param type The type of Node represented by the content.
	 * @param content The content represented by the node.
	 * @throws IllegalArgumentException if the type or content provided is null.
	 * @throws IllegalArgumentException if the type provided is not NodeType.START_ELEMENT or NodeType.END_ELEMENT.
	 */
	protected Element(NodeType type, StringBuilder content) {
		super(type, content);
		if (!(type == NodeType.START_ELEMENT || type == NodeType.END_ELEMENT)) {
			throw new IllegalArgumentException("The type provided is not NodeType.START_ELEMENT or NodeType.END_ELEMENT");
		}
	}
	
	/**
	 * Get the name of the Element.
	 * @return The name of the Element.
	 */
	public String getName() {
		if (name == null) {
			name = parseName();
		}
		return name;
	}
	
	/**
	 * Clear the content represented by the Node.
	 */
	public void clear() {
		super.clear();
		name = null;
	}
	
	/**
	 * Parse the name of the Element.
	 * @return The name of the Element.
	 */
	protected abstract String parseName();
}
