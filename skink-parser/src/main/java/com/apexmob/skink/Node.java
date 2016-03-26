package com.apexmob.skink;

/**
 * The Node class is the basic unit of data representing a piece of a document tree.
 * 
 * <p>The Node class implements the flyweight pattern where a single instance can be reused by calling
 * the clear method and re-populating the content StringBuilder.</p>
 */
public abstract class Node {
	
	private final StringBuilder buffer;
	private final NodeType type;
	private String content = null;
	
	/**
	 * Construct a new node of the provided type with content provided within a StringBuilder instance.
	 * @param type The type of Node represented by the content.
	 * @param content The content represented by the node.
	 * @throws IllegalArgumentException if the type or content provided is null.
	 */
	protected Node(NodeType type, StringBuilder content) {
		if (type == null) {
			throw new IllegalArgumentException("The type provided is null");
		}
		if (content == null) {
			throw new IllegalArgumentException("The content provided is null");
		}
		this.type = type;
		this.buffer = content;
	}
	
	/**
	 * Clear the content represented by the Node.
	 */
	public void clear() {
		buffer.setLength(0);
		content = null;
	}
	
	/**
	 * Get the type of Node.
	 * @return The type of Node.
	 */
	public NodeType getType() {
		return type;
	}
	
	/**
	 * Get the Node's content.
	 * @return The Node's content.
	 */
	public String getContent() {
		if (content == null) {
			content = getBuffer().toString();
		}
		return content;
	}
	
	/**
	 * Get the StringBuilder containing the Node's content.
	 * @return The StringBuilder containing the Node's content.
	 */
	protected StringBuilder getBuffer() {
		return buffer;
	}

}
