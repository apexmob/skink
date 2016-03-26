package com.apexmob.skink;

import java.util.HashMap;
import java.util.Map;

/**
 * The StartElement class represents the starting or opening element of an Element within a document tree.  
 * It can accept content of the following forms:
 * <li>&lt;element attr1="abc" attr2="def" ...&gt;</li>
 * <li>&lt;element attr1="abc" attr2="def" .../&gt;</li>
 * 
 * <p>The StartElement class implements the flyweight pattern where a single instance can be reused by calling
 * the clear method and re-populating the content StringBuilder.</p> 
 */
public class StartElement extends Element {
	
	private final Map<String, String> attributes = new HashMap<String, String>();
	
	private int lastAttributeIndex = 0;
	
	/**
	 * Construct a new StartElement containing the content provided within a StringBuilder instance.
	 * @param content The content represented by the node.
	 * @throws IllegalArgumentException if the type or content provided is null.
	 */
	public StartElement(StringBuilder content) {
		super(NodeType.START_ELEMENT, content);
	}

	/**
	 * Get the value of the attribute with the provided name.
	 * @param name The name of the attribute.
	 * @return The value of the attribute if the attribute is present, otherwise null.
	 * @throws IllegalArgumentException if the name provided is null.
	 * @throws IllegalArgumentException if the name is invalid.
	 */
	public String getAttribute(String name) {
		if (name == null) {
			throw new IllegalArgumentException("The name provided is null");
		}
		if (name.trim().length() == 0) {
			throw new IllegalArgumentException("The name provided is invalid, name=" + name);
		}
		
		String retVal = attributes.get(name);
		
		if (retVal == null) {
			String attName = null;
			do {
				attName = parseNextAttribute();
			} while (attName != null && !attName.equals(name));
			retVal = attributes.get(name);
		}
		
		return retVal;
	}
	
	/**
	 * Parse the next attribute in the element.
	 * @return The name of the next attribute if present, otherwise null. 
	 */
	private String parseNextAttribute() {
		String retVal = null;
		
		StringBuilder buffer = getBuffer();
		
		boolean isAttributeNameStartable = false;
		boolean isAttributeValueStartable = false;
		boolean isAttributeNameStarted = false;
		boolean isAttributeNameFinished = false;
		boolean isAttributeValueStarted = false;
		boolean isAttributeValueFinished = false;
		
		int nameStart = 0;
		int nameEnd = 0;
		int valueStart = 0;
		int valueEnd = 0;
		
		char ch;
		while (++lastAttributeIndex < buffer.length()) {
			ch = buffer.charAt(lastAttributeIndex);
			
			if (!isAttributeNameStartable && Character.isWhitespace(ch)) {
				isAttributeNameStartable = true;
			}
			
			if (isAttributeNameStartable) {
				if (!isAttributeNameStarted && !Character.isWhitespace(ch)) {
					nameStart = lastAttributeIndex;
					isAttributeNameStarted = true;
				} else if (isAttributeNameStarted && !isAttributeNameFinished && ch == '=') {
					isAttributeNameFinished = true;
					nameEnd = lastAttributeIndex;
					isAttributeNameStartable = false;
				} else if (isAttributeNameStarted && !isAttributeNameFinished && (Character.isWhitespace(ch) || ch == '/' || ch == '>')) {
					//no attr value?
					isAttributeNameFinished = true;
					nameEnd = lastAttributeIndex;
					isAttributeNameStartable = false;
					isAttributeValueFinished = true;
				}
			}
			
			if (isAttributeNameFinished && !isAttributeValueStartable && ch == '=') {
				isAttributeValueStartable = true;
			}
			
			if (isAttributeValueStartable) {
				if (!isAttributeValueStarted && ch == '"') {
					isAttributeValueStarted = true;
					valueStart = lastAttributeIndex;
				} else if (isAttributeValueStarted && !isAttributeValueFinished && ch == '"') {
					isAttributeValueFinished = true;
					valueEnd = lastAttributeIndex;
					isAttributeValueStartable = false;
				}
			}
			
			if (isAttributeValueFinished) {
				if (nameEnd > nameStart) {
					retVal = buffer.substring(nameStart, nameEnd);
					//System.out.println("name=" + retVal);
					
					String value = null;
					if (valueEnd - valueStart > 1) {
						value = buffer.substring(valueStart+1, valueEnd);
					} else {
						value ="";
					}
					//System.out.println("value=" + value);
					
					attributes.put(retVal, value);
				}
			}
			
			if (retVal != null) {
				break;
			}
		}
		
		
		
		return retVal;
	}

	/**
	 * Parse the name of the Element from the content.
	 * @return The name of the Element.
	 */
	protected String parseName() {
		StringBuilder buffer = getBuffer();
		
		int i = 0; //assume 1st char is a "<"
		while (++i < buffer.length() && !Character.isWhitespace(buffer.charAt(i))) {
			if (buffer.charAt(i) == '>') {
				break;
			} else if (buffer.charAt(i) == '/') {
				break;
			}
		}
		
		return buffer.substring(1, i);
	}
	
	/**
	 * Clear the content represented by the Node.
	 */
	@Override
	public void clear() {
		super.clear();
		
		attributes.clear();
		lastAttributeIndex = 0;
	}
}
