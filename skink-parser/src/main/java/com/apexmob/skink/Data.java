package com.apexmob.skink;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An event generated while parsing a document tree that represents the data parsed.  
 * This could be a specific piece of data or simply a notification that a new item 
 * in a collection has started or ended.
 * 
 * <p>Data events are immutable, and therefore threadsafe.</p>
 */
public class Data {
	
	private static final Logger logger = LoggerFactory.getLogger(Data.class);
	
	private static final String LOWERCASE_TRUE = "true";
	private static final String LOWERCASE_FALSE = "false";
	
	private final int fieldId;
	private final String fieldData;
	private final NodeListener source;
	
	/**
	 * Construct a new Data event with the provided field ID.
	 * @param fieldId The field ID.
	 */
	public Data(int fieldId) {
		this(fieldId, null);
	}
	
	/**
	 * Construct a new Data event with the provided field ID and source.
	 * @param fieldId The field ID.
	 * @param source The listener that created the event.
	 */
	public Data(int fieldId, NodeListener source) {
		this(fieldId, null, source);
	}
	
	/**
	 * Construct a new Data event with the provided field ID , data, and source.
	 * @param fieldId The field ID.
	 * @param fieldData The data.
	 * @param source The listener that created the event.
	 */
	public Data(int fieldId, String fieldData, NodeListener source) {
		this.fieldId = fieldId;
		this.fieldData = fieldData;
		this.source = source;
	}

	/**
	 * Get the field ID.
	 * @return The field ID.
	 */
	public int getFieldId() {
		return fieldId;
	}

	/**
	 * Get the data.
	 * @return The data.
	 */
	public String getData() {
		return fieldData;
	}

	/**
	 * Get the source of the event.
	 * @return The source of the event if provided, otherwise null.
	 */
	public NodeListener getSource() {
		return source;
	}
	
	/**
	 * Check to see if the event contains data.
	 * @return True if the data is NOT null, otherwise false.
	 */
	public boolean hasData() {
		return getData() != null;
	}
	
	/**
	 * Check to see if the event's data is an emptystring.
	 * @return True if the data is NOT null and NOT emptystring, otherwise false.
	 */
	public boolean hasNonEmptyData() {
		return hasData() && getData().length() > 0;
	}
	
	/**
	 * Check to see if the event's data contains non-whitespace characters.
	 * @return True if the data is NOT null and when trimmed is NOT an emptystring, otherwise false.
	 */
	public boolean hasNonWhitespaceData() {
		return hasData() && getData().trim().length() > 0;
	}
	
	/**
	 * Get the data as a Byte.
	 * @return The Byte if it can be converted, otherwise null.
	 */
	public Byte getDataAsByte() {
		Byte retVal = null;
		
		try {
			retVal = new Byte(getData());
		} catch (NumberFormatException e) {
			logger.error("Unable to parse Byte from data=" + getData(), e);
		}
		
		return retVal;
	}
	
	/**
	 * Get the data as a Short.
	 * @return The Short if it can be converted, otherwise null.
	 */
	public Short getDataAsShort() {
		Short retVal = null;
		
		try {
			retVal = new Short(getData());
		} catch (NumberFormatException e) {
			logger.error("Unable to parse Short from data=" + getData(), e);
		}
		
		return retVal;
	}
	
	/**
	 * Get the data as a Integer.
	 * @return The Integer if it can be converted, otherwise null.
	 */
	public Integer getDataAsInteger() {
		Integer retVal = null;
		
		try {
			retVal = new Integer(getData());
		} catch (NumberFormatException e) {
			logger.error("Unable to parse Integer from data=" + getData(), e);
		}
		
		return retVal;
	}
	
	/**
	 * Get the data as a Long.
	 * @return The Long if it can be converted, otherwise null.
	 */
	public Long getDataAsLong() {
		Long retVal = null;
		
		try {
			retVal = new Long(getData());
		} catch (NumberFormatException e) {
			logger.error("Unable to parse Long from data=" + getData(), e);
		}
		
		return retVal;
	}
	
	/**
	 * Get the data as a Float.
	 * @return The Float if it can be converted, otherwise null.
	 */
	public Float getDataAsFloat() {
		Float retVal = null;
		
		try {
			retVal = new Float(getData());
		} catch (NumberFormatException e) {
			logger.error("Unable to parse Float from data=" + getData(), e);
		}
		
		return retVal;
	}
	
	/**
	 * Get the data as a Double.
	 * @return The Double if it can be converted, otherwise null.
	 */
	public Double getDataAsDouble() {
		Double retVal = null;
		
		try {
			retVal = new Double(getData());
		} catch (NumberFormatException e) {
			logger.error("Unable to parse Double from data=" + getData(), e);
		}
		
		return retVal;
	}
	
	/**
	 * Get the data as a Date.
	 * @param format The DateFormat to use when parsing the string into a Date.
	 * @return The Date if it can be converted, otherwise null.
	 * @throws IllegalArgumentException if the provided format is null.
	 */
	public Date getDataAsDate(DateFormat format) {
		if (format == null) {
			throw new IllegalArgumentException("The Date Format provided is null");
		}
		
		Date retVal = null;
		
		try {
			retVal = format.parse(getData());
		} catch (ParseException e) {
			logger.error("Unable to parse Date from data=" + getData(), e);
		}
		
		return retVal;
	}
	
	/**
	 * Get the data as a Boolean.  Conversion to Boolean is case insensitive.
	 * @return The Boolean if it can be converted, otherwise null.
	 */
	public Boolean getDataAsBoolean() {
		Boolean retVal = null;
		
		if (hasNonEmptyData()) {
			String lowerCase = getData().toLowerCase();
			if (LOWERCASE_TRUE.equals(lowerCase)) {
				retVal = Boolean.TRUE;
			} else if (LOWERCASE_FALSE.equals(lowerCase)) {
				retVal = Boolean.FALSE;
			} else {
				logger.error("Unable to parse Boolean from data=" + getData());
			}
		}
		
		return retVal;
	}

}
