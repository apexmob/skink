package com.apexmob.skink;

/**
 * The EndElement class represents the ending or closing element of an Element within a document tree.  
 * It can accept content of the following forms:
 * <ul>
 * <li>&lt;element&gt;</li>
 * <li>&lt;element/&gt;</li>
 * </ul>
 * 
 * <p>The EndElement class implements the flyweight pattern where a single instance can be reused by calling
 * the clear method and re-populating the content StringBuilder.</p> 
 */
public class EndElement extends Element {

	/**
	 * Construct a new EndElement containing the content provided within a StringBuilder instance.
	 * @param buffer A buffer to contain the content represented by the node.
	 * @throws IllegalArgumentException if the type or content provided is null.
	 */
	public EndElement(StringBuilder buffer) {
		super(NodeType.END_ELEMENT, buffer);
	}

	/**
	 * Parse the name of the Element from the content.
	 * @return The name of the Element.
	 */
	@Override
	protected String parseName() {
		StringBuilder buffer = getBuffer();
		
		boolean multiTag = false;
		if (buffer.charAt(1) == '/') {
			multiTag = true;
		}
		
		int i = 1; //assume 1st chars are "</" or last 2 are />
		while (++i < buffer.length() && !Character.isWhitespace(buffer.charAt(i))) {
			if (buffer.charAt(i) == '>') {
				break;
			} else if (!multiTag && buffer.charAt(i) == '/') {
				break;
			}
		}
		
		int startIndex = multiTag ? 2 :1;
		
		return buffer.substring(startIndex, i);
	}
	
	

}
